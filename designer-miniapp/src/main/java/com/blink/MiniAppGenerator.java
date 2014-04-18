package com.blink;

import static com.blink.CodeUtil.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.blink.designer.model.App;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;

public class MiniAppGenerator extends AbstractAppGenerator {

	private ServiceMethodGenerator serviceGenerator;
	private BizMethodGenerator bizMethodGenerator;
	private DAOMethodGenerator daoMethodGenerator;	
	
	@Autowired
	private ConfigGeneratorImpl configGeneratorImpl;  
	
	public MiniAppGenerator() {
		init();
	}

	public MiniAppGenerator(String packageName, String serviceName, String fileRepository) {
		super(packageName,serviceName, fileRepository);
		init();
	}

	private void init() {
		serviceGenerator = new ServiceMethodGeneratorImpl();
		bizMethodGenerator = new BizMethodGeneratorImpl();
		daoMethodGenerator = new DAOMethodGeneratorImpl();
		//configGenerator = new ConfigGeneratorImpl();	
	}
	
	public void generateApp(App app, String repositoryFile) throws AppGenerationError {
		
		init(app.getBasePackage(), app.getName(),repositoryFile);
		generateApp(app);
		
	}
	
	public void generateApp(App app) throws AppGenerationError {
		try {
			generateBeans();
			try {
				JDefinedClass configClass = generateConfig(app);
				JDefinedClass doFacade = generatDAOFacade();
				GeneratorContext.registerFacade(PackageType.DO,doFacade );
				JDefinedClass bizFacade = generateBizFacade();
				GeneratorContext.registerFacade(PackageType.BIZ,bizFacade);

				JDefinedClass serviceFacade = generateServiceFacade();

				GeneratorContext.registerFacade(PackageType.DTO,serviceFacade);
				postConfig(getWebConfig());

			} catch (JClassAlreadyExistsException e) {
				e.printStackTrace();
			}

			persist(); 
		}catch(Exception ex) {
			throw new AppGenerationError("Cannot generate App : "+ex.getMessage() , ex);
		}
	}

	protected void postConfig(JDefinedClass configClass) {
		configGeneratorImpl.postConfig(configClass);	
	}
	@Override
	protected void createDOClasses(JCodeModel codeModel,Class<?> clazz) throws JClassAlreadyExistsException, IOException {
		JDefinedClass definedClass = createClasses(codeModel,clazz,PackageType.DO);
		definedClass.annotate(javax.persistence.Entity.class);
		JAnnotationUse annotationUse = definedClass.annotate(javax.persistence.Table.class) ;
		annotationUse.param("name", clazz.getSimpleName());

		Map<String,JFieldVar> fields = definedClass.fields();
		Iterator<String> i = fields.keySet().iterator();
		System.out.println("These are motog fields");
		while ( i.hasNext()) {
			JFieldVar field = fields.get(i.next());
			System.out.println(field);
			if( field.type().isPrimitive()) {

			}else if(field.type().binaryName().startsWith(getPackageName()) )
				field.annotate(javax.persistence.OneToOne.class);
			else if (((JClass)codeModel._ref(java.util.Collection.class)).isAssignableFrom((JClass)field.type())) {
				field.annotate(javax.persistence.OneToMany.class);
			}
		}
	}

	@Override
	protected void createDTOClasses(JCodeModel codeModel,Class<?> clazz)
			throws JClassAlreadyExistsException, IOException {

		JDefinedClass definedClass = createClasses(codeModel,clazz,PackageType.DTO);
		definedClass.annotate(javax.xml.bind.annotation.XmlRootElement.class);

	}

	private JDefinedClass createClasses(JCodeModel codeModel,Class<?> clazz, PackageType packageType)
			throws JClassAlreadyExistsException, IOException {

		JDefinedClass foo =  getDefinedClass(codeModel,clazz,packageType);//Creates a new class

		Field[] fields = clazz.getDeclaredFields();
		for ( int i=0 ; i < fields.length; i++) {
			Field field = fields[i];
			if( field.getType().getName().startsWith(getPackageName())) {
				foo.field(JMod.PRIVATE, getDefinedClass(codeModel,field.getType(), packageType), field.getName());
			}else {

				if(field.getType().getTypeParameters().length == 0) {
					JFieldVar fId=foo.field(JMod.PRIVATE, field.getType(), field.getName());
					if(packageType.toString() == PackageType.DO.toString() && (field.getName().contains("_id") || field.getName().contains("Id"))){
						fId.annotate(javax.persistence.Id.class);
					}
					} else {
					foo.field(JMod.PRIVATE,getParameterizedClass(codeModel,field,packageType),field.getName());
				}
			}
			createGetter(foo,field,packageType);
			createSetter(foo,field,packageType);
		}
		return foo;
	}

	@Override
	public JDefinedClass createServiceFacade(JCodeModel codeModel) {
		JDefinedClass serviceClass = null;
		try{
			serviceClass = codeModel._class(getPackageName()+ "service."+ getServiceName()+  "Service");
			System.out.println("This is service class "+serviceClass);
			addBeanService(getConfig(),serviceClass);
			addAutowiredField(serviceClass,GeneratorContext.getFacade(PackageType.BIZ));
			Map<String, JDefinedClass> clazzes= getClasses(PackageType.DTO);
			Iterator<String> definedClasses = clazzes.keySet().iterator();
			while ( definedClasses.hasNext()){
				JDefinedClass definedClass = clazzes.get(definedClasses.next());
				generateServiceCRUDOps(serviceClass,definedClass);
			}
		}catch (JClassAlreadyExistsException ex) {

		}
		return serviceClass;
	}

	public JDefinedClass createBizFacade(JCodeModel codeModel) {
		JDefinedClass bizServiceClass =  null;
		try{
			bizServiceClass = codeModel._class(getPackageName()+ "biz."+ getServiceName()+  "BizService");
			addBean(getConfig(),bizServiceClass);
			addAutowiredField(bizServiceClass,GeneratorContext.getFacade(PackageType.DO));
			List<Class<?>>  clazzes= getClassesForProcessing();
			for(Class<?> bizClass : clazzes) {
				bizMethodGenerator.generateAllBizMethods(bizServiceClass, bizClass);
			}
		}catch (JClassAlreadyExistsException ex) {

		}
		return bizServiceClass;
	}


	public JDefinedClass createDAOFacade(JCodeModel codeModel) {
		JDefinedClass daoServiceClass = null;
		try{
			daoServiceClass = codeModel._class(getPackageName()+ "dao."+ getServiceName()+  "DAOService");
			addBean(getConfig(),daoServiceClass);
			Map<String, JDefinedClass> clazzes= getClasses(PackageType.DO);
			Iterator<String> definedClasses = clazzes.keySet().iterator();
			while ( definedClasses.hasNext()){
				JDefinedClass definedClass = clazzes.get(definedClasses.next());
				generateDAOCRUDOps(daoServiceClass,definedClass);
			}
		}catch (JClassAlreadyExistsException ex) {

		}
		return daoServiceClass;
	}


	private void generateDAOCRUDOps(JDefinedClass daoServiceClass,
			JDefinedClass daoDataClass) {
		daoMethodGenerator.generateAllDAOMethods(daoServiceClass, daoDataClass);

	}

	private void generateServiceCRUDOps(JDefinedClass serviceClass,JDefinedClass definedClass) {
		serviceGenerator.generateAllServiceMethods(serviceClass,definedClass);
	}

	@Override
	protected JDefinedClass createConfig(JDefinedClass definedClass, String pathRepo, App app) {
		return configGeneratorImpl.generateConfig(definedClass, pathRepo, app);
		//setConfig();
	}

	private void addBean(JDefinedClass definedClass,JDefinedClass bean) {
		String beanName = CodeUtil.camelCase(bean.name());
		JMethod method = getConfig().method(JMod.PUBLIC, bean,beanName );
		JAnnotationUse annotation = method.annotate(Bean.class);
		annotation.param("name", CodeUtil.camelCase(bean.name()));
		method.body()._return(JExpr._new(bean));
	}
	
	private void addBeanService(JDefinedClass definedClass,JDefinedClass bean) {
		String beanName = CodeUtil.camelCase(bean.name());
		JMethod method = getConfig().method(JMod.PUBLIC, bean,beanName );
		JAnnotationUse annotation = method.annotate(Bean.class);
		annotation.param("name", "serviceBean");
		method.body()._return(JExpr._new(bean));
	}
	
	public ConfigGeneratorImpl getConfigGeneratorImpl(){
		return configGeneratorImpl;
	}
	
	public void setConfigGeneratorImpl(ConfigGeneratorImpl configGeneratorImpl){
		this.configGeneratorImpl=configGeneratorImpl;
	}
}
