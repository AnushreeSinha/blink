package com.blink;

import static com.blink.CodeUtil.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import com.blink.designer.model.App;
import com.blink.designer.model.Entity;
import com.blink.designer.model.EntityAttribute;
import com.blink.designer.model.Type;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.grizzly.util.http.mapper.Mapper;

import javax.validation.constraints.*;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.CreditCardNumber;



public class MiniAppGenerator extends AbstractAppGenerator {

	private ServiceMethodGenerator serviceGenerator;
	private BizMethodGenerator bizMethodGenerator;
	private DAOMethodGenerator daoMethodGenerator;	
	
	@Autowired
	private ConfigGeneratorImpl configGeneratorImpl; 
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public MiniAppGenerator() {
		init();
	}
	
	String pathRepo2;

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
		System.out.println("Water is here");
		Map<String,JFieldVar> fields = definedClass.fields();
		Iterator<String> i = fields.keySet().iterator();
		System.out.println("These are motog fields");
		while ( i.hasNext()) {
			JFieldVar field = fields.get(i.next());
			System.out.println("$$$$$$$$$$$$");
			System.out.println("Fields in MiniAppGenerator Class Malayalam"+field);
			System.out.println("$$$$$$$$$$$$");
			System.out.println(field.type());
			if( field.type().isPrimitive()) {
			}
			else if(field.type().binaryName().startsWith(getPackageName()) )
				field.annotate(javax.persistence.OneToOne.class);
			else if (((JClass)codeModel._ref(java.util.Collection.class)).isAssignableFrom((JClass)field.type())) {
				//field.annotate(javax.persistence.OneToMany.class);
				JAnnotationUse fetch=field.annotate(javax.persistence.OneToMany.class);
				fetch.param("fetch",javax.persistence.FetchType.EAGER);
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
			JFieldVar fId = null;
			if( field.getType().getName().startsWith(getPackageName())) {
				fId=foo.field(JMod.PRIVATE, getDefinedClass(codeModel,field.getType(), packageType), field.getName());
				}
			else {
				if(field.getType().getTypeParameters().length == 0) {
					fId=foo.field(JMod.PRIVATE, field.getType(), field.getName());
					System.out.println("Value of JFieldVar in second if petunia. Its is second if "+fId.name());
					if(packageType.toString() == PackageType.DO.toString() && (field.getName().contains("id") || field.getName().contains("Id")))
						fId.annotate(javax.persistence.Id.class);
					}
				else
					{
						fId=foo.field(JMod.PRIVATE,getParameterizedClass(codeModel,field,packageType),field.getName());
					}
			}
			String name=fId.name();
			if(name.equalsIgnoreCase("id")){
				;
			}
			else
			{
			Entity entity=(Entity)entityManager.createQuery("from com.blink.designer.model.Entity where name = '" + clazz.getSimpleName()+"'").getSingleResult();
			for(EntityAttribute entityAttribute:entity.getEntityAttributes()){
			System.out.println("Entity name is "+clazz.getSimpleName());
			System.out.println("Attribute name is "+entityAttribute.getName());
			if(fId.name().equals(entityAttribute.getName())){
			if(entityAttribute.getIsRequired()==true){
				
				System.out.println("Oberyn not null is true");
				fId.annotate(javax.validation.constraints.NotNull.class);
			}
			if(entityAttribute.getValidations().isAssertFalse()){
				System.out.println(" Oberyn assertfalse is true");
				fId.annotate(javax.validation.constraints.AssertFalse.class);	
			}
			if(entityAttribute.getValidations().isAssertTrue()){
				System.out.println("Oberyn assertfalse is true");
				fId.annotate(javax.validation.constraints.AssertTrue.class);	
			}
			if(entityAttribute.getValidations().isCreditCard()){
				System.out.println("Oberyn creditcard is true");
				fId.annotate( org.hibernate.validator.constraints.CreditCardNumber.class);	
			}
			if(entityAttribute.getValidations().isEmail()){
				System.out.println("Oberyn email is true");
				fId.annotate(org.hibernate.validator.constraints.Email.class);	
			}
			if(entityAttribute.getValidations().getSize()!=null){
				System.out.println("Oberyn size is true");
				JAnnotationUse annot=fId.annotate(javax.validation.constraints.Size.class);
				annot.param("min", entityAttribute.getValidations().getSize().getMinSize());
				annot.param("max",entityAttribute.getValidations().getSize().getMaxSize());

			}}}}	
			
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
			bizServiceClass = codeModel._class(getPackageName()+ ".biz."+ getServiceName()+  "BizService");
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

	/*public JDefinedClass createCreateActionFacade(JCodeModel codeModel){
		JDefinedClass createActionClass= null;
		try{
			createActionClass = codeModel._class(getPackageName()+ "crudAction."+ getServiceName()+  "BizService");
		}
	}*/

	public JDefinedClass createDAOFacade(JCodeModel codeModel) {
		JDefinedClass daoServiceClass = null;
		try{
			daoServiceClass = codeModel._class(getPackageName()+ "dao."+ getServiceName()+  "DAOService");
			daoServiceClass.annotate(Transactional.class);
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
//		System.out.println("this is pathRepo in ServiceFacade Macbeth "+pathRepo);
//		pathRepo2=pathRepo;
//		System.out.println("this is newPathRepo in ServiceFacade Macbeth "+pathRepo2);
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
	
	@SuppressWarnings("unused")
	public void callCRUDClass(String pathRepo) throws FileNotFoundException, IOException{
		System.out.println("this is package name in ServiceFacade signoraware "+getPackageName());
		System.out.println("this is new path repo in ServiceFacade signoraware "+pathRepo);
		String packageName=getPackageName();
		CRUDcallsMethodGeneratorImpl cRUDcallsMethodGeneratorImpl=new CRUDcallsMethodGeneratorImpl();
		cRUDcallsMethodGeneratorImpl.generateAbstractActionClass(pathRepo, packageName);
		
	}
	
}
