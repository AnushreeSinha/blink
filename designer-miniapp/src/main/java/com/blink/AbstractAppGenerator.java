package com.blink;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.blink.designer.model.App;
import com.sun.codemodel.JClass;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JType;
import com.sun.codemodel.writer.FileCodeWriter;

public abstract class AbstractAppGenerator implements AppGenerator{

	private List<String> classesNameForProcessing = new ArrayList<String>();
	private Map<String,JDefinedClass> definedClasses = new HashMap<String,JDefinedClass>();
	private List<Class<?>> classesForProcessing = new ArrayList<Class<?>>();
	private String pathName;
	protected String packageName;
	private JCodeModel codeModel ;
	protected String serviceName;
	private File projectRepository;
	private JDefinedClass config;
	private JDefinedClass webConfig;
	private String pathRepo;


	public  AbstractAppGenerator(String packageName, String serviceName, String fileRepository) {
		this();
		init(packageName,serviceName,fileRepository);
	}

	public AbstractAppGenerator() {
		this.codeModel = new JCodeModel();
	}

	protected void init(String packageName, String serviceName, String fileRepository) {
		this.pathName = getPathName(packageName);
		this.packageName = packageName;
		this.serviceName = serviceName;
		this.projectRepository = new File(fileRepository);
		this.pathRepo=fileRepository;
	}

	
	protected void print() {
		for(String clazz: classesNameForProcessing) {
			System.out.println(clazz);
		}
		System.out.println("pathRepo in abstractappgenerator Akhi "+pathRepo);
	}

	public List<Class<?>>  generateBeans() throws IOException, ClassNotFoundException {
		if(classesForProcessing.size() >0 ) 
			return classesForProcessing;

		findClassesForProcessing();

		for(String fullPathOfClass : classesNameForProcessing) {
			String className = fullPathOfClass.substring(fullPathOfClass.indexOf(pathName),fullPathOfClass.length()-6);
			className= className.replace(System.getProperty("file.separator").charAt(0),'.' );
			Class<?> clazz = Class.forName(className);
			classesForProcessing.add(clazz);
			try {
				createClasses(codeModel,clazz, pathRepo);
			} catch (JClassAlreadyExistsException e) {
				e.printStackTrace();
			}
		}

		return classesForProcessing;
	}

	private void createClasses(JCodeModel jCodeModel,Class<?> clazz, String pathRepo) throws JClassAlreadyExistsException, IOException {
		createDTOClasses(jCodeModel,clazz);
		createDOClasses(jCodeModel,clazz);
		callCRUDClass(pathRepo);
	}

	protected void createGetter(JDefinedClass definedClass, Field field, PackageType suffix)  {
		String fieldName = field.getName();
		String typeName = field.getType().getName();

		String getterName = ("java.lang.Boolean".equals(typeName) ? "is" : "get")+ String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
		JMethod method = null;
		if( field.getType().getName().startsWith(getPackageName()))
			method = definedClass.method(JMod.PUBLIC, getDefinedClass(definedClass.owner(),field.getType(),suffix.toString())  ,getterName );
		else {
			if(field.getType().getTypeParameters().length == 0) {
				method = definedClass.method(JMod.PUBLIC,field.getType() ,getterName );
			} else {
				method = definedClass.method(JMod.PUBLIC, getParameterizedClass(definedClass.owner(),field,suffix.toString()), getterName);
			}
		}
		method.body()._return(JExpr.ref(fieldName));
		
		
	}


	protected void createSetter(JDefinedClass definedClass, Field field, PackageType suffix) {
		String fieldName = field.getName();

		String setterName = "set" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);

		JMethod method = definedClass.method(JMod.PUBLIC, void.class ,setterName );
		if( field.getType().getName().startsWith(getPackageName()))
			method.param(getDefinedClass(definedClass.owner(),field.getType(),suffix.toString()), fieldName);
		else {
			if(field.getType().getTypeParameters().length == 0) {
				method.param(field.getType(), fieldName);
			} else {
				method.param(getParameterizedClass(definedClass.owner(),field,suffix.toString()), fieldName);
			}
		}
		method.body().assign(JExpr.refthis(fieldName), JExpr.ref(fieldName));
	}


	private  List<String> findClassesForProcessing() throws IOException {
		if(classesNameForProcessing.size() > 0) {
			return classesNameForProcessing;
		}
		List<String> classpathComponents = getClasspathComponents() ;

		for(String classpathComponent: classpathComponents) {
			File file = new File(classpathComponent);
			System.out.println("Processing:  "+classpathComponent);
			if(!file.exists()) 
				continue;
			if( classpathComponent.endsWith(".jar"))
				processJar(file);
			else  if(file.isDirectory()){
				processDir(file);
			} else {
				processFile(file);
			}
		}
		return classesNameForProcessing;
	}

	protected JDefinedClass getDefinedClass(JCodeModel codeModel, Class<?> clazz, PackageType suffix) {
		return getDefinedClass( codeModel, clazz,  suffix.toString());
	}

	protected JDefinedClass getDefinedClass(JCodeModel codeModel, Class<?> clazz, String suffix) {
		String key = clazz + suffix;
		if( definedClasses.get(key) != null) 
			return definedClasses.get(key);
		try {
			String packageName = getPackageName(clazz, suffix.toLowerCase());
			JPackage pack = codeModel._package(packageName);
			JDefinedClass jDefinedClass = pack._class( clazz.getSimpleName() +suffix );
			if( !clazz.getSuperclass().getName().equals("java.lang.Object")) {
				if( clazz.getSuperclass().getName().startsWith(getPackageName()) )
					jDefinedClass._extends(getDefinedClass(codeModel,clazz.getSuperclass(),suffix));
				else 
					jDefinedClass._extends(getDefinedClass(codeModel,clazz.getSuperclass(),""));
			}
			definedClasses.put(key, jDefinedClass);
			return jDefinedClass;
		} catch ( JClassAlreadyExistsException ex) {
			return codeModel._getClass(clazz.getCanonicalName() +suffix);
		}
	}

	private String getPackageName(Class<?> clazz, String suffix) {
		return clazz.getPackage().getName() +"."+suffix;
	}

	protected  JType getParameterizedClass(JCodeModel codeModel, Field field, PackageType suffix){
		return getParameterizedClass( codeModel,  field, suffix.toString());
	}
	protected  JType getParameterizedClass(JCodeModel codeModel, Field field, String suffix){
		Type p = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
		if( ((Class<?>)p).getName().startsWith(getPackageName()))
			return ((JClass)codeModel._ref(field.getType())).narrow(getDefinedClass(codeModel,(Class<?>)p,suffix));
		else
			return ((JClass)codeModel._ref(field.getType())).narrow((Class<?>)p);

	}

	private void processFile(File file) {
		String fileName = file.getAbsolutePath();
		if(fileName.endsWith(".class") && fileName.contains(pathName)) {
			classesNameForProcessing.add(fileName);
		}
	}

	private  List<String> getClasspathComponents() {
		List<String> classpathComponents= new ArrayList<String>();
		Collections.addAll(classpathComponents,System.getProperty("java.class.path").split("[:]"));
		return classpathComponents;
	}

	public  void processJar(File jarFile) throws IOException {
		ZipInputStream zip = new ZipInputStream( new FileInputStream( jarFile));
		ZipEntry ze = null;

		while( ( ze = zip.getNextEntry() ) != null ) {
			String entryName = ze.getName().replace("/",System.getProperty("file.separator"));
			if( entryName.startsWith(pathName) && entryName.endsWith(".class") ) {
				classesNameForProcessing.add( entryName  );
			}
		}
		zip.close();
	}

	private  String getPathName(String packageName) {
		final String pathName = packageName.replace(".", System.getProperty("file.separator"));
		return pathName;
	}

	public  void processDir(File dir) {
		walk(dir);
	}

	private  void walk( File dirPath ) {

		File[] list = dirPath.listFiles();

		for ( File f : list ) {
			if ( f.isDirectory() ) {
				walk( f.getAbsoluteFile() );
			}
			else {
				processFile(f);
			}
		}
	}

	protected Map<String, JDefinedClass> getClasses() {
		return definedClasses;
	}

	protected List<Class<?>> getClassesForProcessing() {
		final List<Class<?>>  temp = new ArrayList<Class<?>>();
		temp.addAll(classesForProcessing);
		return temp;
	}

	protected Map<String, JDefinedClass> getClasses(PackageType packageType) {
		final Map<String, JDefinedClass> c = new HashMap<String, JDefinedClass> ();
		Iterator<String> i = definedClasses.keySet().iterator();
		while( i.hasNext()) {
			String className = i.next();
			if( definedClasses.get(className).name().endsWith(packageType.toString())) {
				c.put(className, definedClasses.get(className));
			}
		}
		return c;
	}

	public JDefinedClass generateServiceFacade() throws JClassAlreadyExistsException, IOException {
		return createServiceFacade(codeModel);
	}

	public JDefinedClass generateBizFacade() throws JClassAlreadyExistsException, IOException  {
		return createBizFacade(codeModel);
	}

	public JDefinedClass generatDAOFacade() throws JClassAlreadyExistsException, IOException  {
		return createDAOFacade(codeModel);
	}

	protected abstract JDefinedClass createBizFacade(JCodeModel codeModel);
	protected abstract JDefinedClass createDAOFacade(JCodeModel codeModel);
	protected abstract JDefinedClass createConfig(JDefinedClass configClass, String pathRepo, App app) ;
	protected abstract JDefinedClass createServiceFacade(JCodeModel jCodeModel) throws JClassAlreadyExistsException, IOException ;
	protected abstract void postConfig(JDefinedClass configClass);
	protected abstract void createDOClasses(JCodeModel jCodeModel,Class<?> clazz)throws JClassAlreadyExistsException, IOException ; 
	protected abstract void createDTOClasses(JCodeModel jCodeModel,Class<?> clazz) throws JClassAlreadyExistsException, IOException ;
	protected abstract void callCRUDClass(String pathRepo) throws FileNotFoundException, IOException;
	public void persist() throws IOException {
		codeModel.build(new FileCodeWriter(projectRepository));
	}

	public JDefinedClass generateConfig(App app) {
		try {
			JDefinedClass definedClass = codeModel._class(getPackageName()+"config." + getServiceName()+"Config");
			config = definedClass;
		
			webConfig = createConfig(definedClass, pathRepo, app);
			return definedClass;
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		return null;  

	}

	protected String getPackageName() {
		return packageName;
	}

	protected String getServiceName() {
		return serviceName;
	}

	public File getProjectRepository() {
		return projectRepository;
	}

	public void setProjectRepository(File projectRepository) {
		this.projectRepository = projectRepository;
	}

	public JDefinedClass getWebConfig() {
		return webConfig;
	}

	public void setWebConfig(JDefinedClass webConfig) {
		this.webConfig = webConfig;
	}

	public JDefinedClass getConfig() {
		return config;
	}

	public void setConfig(JDefinedClass config) {
		this.config = config;
	}
}
