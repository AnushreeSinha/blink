package com.blink.model.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.blink.designer.model.App;
import com.blink.designer.model.Entity;
import com.blink.designer.model.EntityAttribute;
import com.blink.designer.model.Type;
import com.mysql.jdbc.StringUtils;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JFieldVar;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JPackage;
import com.sun.codemodel.JType;

public class ModelGenerator {

	@PersistenceContext
	private EntityManager entityManager;

	private String localRepo;

	private static final String targetClassesLocation = "../../../target/classes";
	private static final String targetPackageLocation = "../../../target/bin";
	private static final String JAVA_SUFFIX= ".java";

	public File generateModel(App app, String srcLocation) throws IOException, ClassNotFoundException {
		JCodeModel codeModel = new JCodeModel();
		 
		if(entityManager == null)
			System.out.println("entitymanager is null in model generator now 777");
		else 
			System.out.println("entitymanager is not null in model generator");
		
		List<Entity> entities = entityManager.createQuery("from com.blink.designer.model.Entity").getResultList();
		for( Entity entity: entities) {
			generateModel(codeModel,entity,app);
		}

		File srcDirectory = new File(srcLocation); //getTempDirectory();
		codeModel.build(srcDirectory);

		return compileModel(srcDirectory, app.getName());
	}

	protected File compileModel(File srcDirectory, String appName) throws FileNotFoundException, IOException {

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

		Iterable<? extends JavaFileObject> compilationUnits1 = fileManager.getJavaFileObjectsFromFiles(getFilesWithType(srcDirectory,JAVA_SUFFIX));

		File classesDir = new File(srcDirectory,targetClassesLocation);
		classesDir.mkdirs();
		Iterable<String> options = Arrays.asList( new String[] { "-d", classesDir.getAbsolutePath()} );

		compiler.getTask(null, fileManager, null, options, null, compilationUnits1).call();

		File modelJarLocation = new File(srcDirectory,targetPackageLocation );
		modelJarLocation.mkdirs();
		return packageModel(classesDir, modelJarLocation, appName);
	}

	private List<File> getFilesWithType(File dir, String suffix) {
		System.out.println("Finding java in directory :" + dir.getAbsolutePath());
		List<File> files = new ArrayList<>();
		for( File file : dir.listFiles()) {
			if( file.isDirectory() ) 
				files.addAll(getFilesWithType(file,suffix)) ;
			else {
				if( file.getName().endsWith(suffix))
					files.add(file);
			}
		}
		return files;
	}

	protected File packageModel(File classesDirectory, File modelJarLocation, String appName) throws FileNotFoundException, IOException {
		List<File> clazzFiles = getFilesWithType(classesDirectory,".class");

		File jarFile = new File(modelJarLocation + "/"+ appName+ ".jar"); 
		if (jarFile.exists() == true) { 
			jarFile.delete(); 
		} 

		try(JarOutputStream jarout = new JarOutputStream(new FileOutputStream(jarFile));) {

			for(File clazzFile : clazzFiles) {
				
				String relativeClasspath = classesDirectory.toURI().relativize(clazzFile.toURI()).getPath();
				
				JarEntry entry = new JarEntry(relativeClasspath);
				jarout.putNextEntry(entry);

				try (InputStream filereader = new FileInputStream(clazzFile); ) {
					final int buffersize = 1024; 
					byte buffer[] = new byte[buffersize]; 
					int readcount = 0;
					while ((readcount = filereader.read(buffer, 0, buffersize)) >= 0) {
						if (readcount > 0) {
							jarout.write(buffer, 0, readcount); 
						} 
					} 
				}
			}
		} 
		
		return jarFile;
	}

	private void generateModel(JCodeModel codeModel,Entity entity,App app) throws ClassNotFoundException {
		JPackage parentPackage = null;
		if( app.getBasePackage() != null)
			parentPackage =  getPackage(codeModel, app.getBasePackage());
		else 
			parentPackage = getPackage(codeModel, entity.getParentPackage().getName());
		generateModel(codeModel,entity, parentPackage);
	}

	protected void generateModel(JCodeModel codeModel,Entity entity,JPackage parentPackage) throws ClassNotFoundException {

		JDefinedClass entityClass = null;
		try {
			entityClass = parentPackage._class(entity.getName());
		} catch (JClassAlreadyExistsException e) {
			entityClass = parentPackage._getClass(entity.getName());
		}

		generateAttributes(codeModel, entityClass, entity) ;
	}


	protected void generateAttributes(JCodeModel codeModel, JDefinedClass entityClass, Entity entity) throws ClassNotFoundException {
		if(  entity.getEntityAttributes() == null) {
			return; 
		}

		for(EntityAttribute entityAttribute : entity.getEntityAttributes()) {
			
			JFieldVar typeClass; 
			Type typeId = null;
			String fieldName = null;
			JType type = null;
	
		if(entityAttribute.getPrimitiveId()!=null){
		System.out.println("Revlon");
		System.out.print(entityAttribute.getName());
		typeClass = entityClass.field(JMod.PRIVATE, getPrimitiveType(codeModel,entityAttribute.getPrimitiveId().getName() ), entityAttribute.getName());
		typeId =entityAttribute.getPrimitiveId();
		fieldName =entityAttribute.getName();
		type=getPrimitiveType(codeModel,entityAttribute.getPrimitiveId().getName());
		}
		
		else if(entityAttribute.getCompositeId()!=null && entityAttribute.getMultiType().equals("null")){
			System.out.println("Rev123");
			System.out.print(entityAttribute.getName());
			typeClass = entityClass.field(JMod.PRIVATE, getCompositeType(codeModel,entityAttribute.getCompositeId().getName() ), entityAttribute.getName());
			typeId =entityAttribute.getPrimitiveId();
			fieldName =entityAttribute.getName();
			type=getCompositeType(codeModel,entityAttribute.getCompositeId().getName() );
		}
		
		else if(entityAttribute.getCompositeId()!=null && !entityAttribute.getMultiType().equals("null")){
			System.out.println("Revlon456");
			System.out.print(entityAttribute.getName());
			typeClass = entityClass.field(JMod.PRIVATE, getMultiType(codeModel, entityAttribute.getMultiType(), entityAttribute.getCompositeId().getName() ), entityAttribute.getName());
			typeId =entityAttribute.getPrimitiveId();
			fieldName =entityAttribute.getName();
			type=getMultiType(codeModel,entityAttribute.getMultiType(), entityAttribute.getCompositeId().getName());
		}
		
		String getterName = ("java.lang.Boolean".equals(typeId) ? "is" : "get")+ String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
		JMethod getterMethod = entityClass.method(JMod.PUBLIC, type, getterName ); getterMethod.body()._return(JExpr.ref(fieldName)); 
		String setterName = ("java.lang.Boolean".equals(typeId) ? "is" : "set")+ String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1); 
		JMethod setterMethod = entityClass.method(JMod.PUBLIC,void.class,setterName ); setterMethod.param(type, fieldName); 
		setterMethod.body().assign(JExpr.refthis(fieldName), JExpr.ref(fieldName));
		}
		
		
		entityClass.field(JMod.PRIVATE, Long.class, "id");
		JMethod jMethod=entityClass.method(JMod.PUBLIC, Long.class, "getId");
		jMethod.body()._return(JExpr.ref("id"));
		
		JMethod jMethod_2=entityClass.method(JMod.PUBLIC, void.class, "setId");
		jMethod_2.param(Long.class, "id");
		jMethod_2.body().assign(JExpr.refthis("id"), JExpr.ref("id"));
		
	}
	
	private JType getMultiType(JCodeModel codeModel, String name, String compName ) throws ClassNotFoundException {
		return codeModel.parseType("java.util."+name+"<"+compName+">");
	}


	private JType getPrimitiveType(JCodeModel codeModel, String name ) throws ClassNotFoundException {
		Type type = (Type) entityManager.createQuery("from com.blink.designer.model.Type where name = '" + name+"'").getSingleResult() ;
		return codeModel.parseType(type.getClassName());
	}

	private JType getCompositeType(JCodeModel codeModel, String name) throws ClassNotFoundException {
		return codeModel.parseType(name);
	}
	protected JPackage getPackage(JCodeModel codeModel , String packageName) {
		return codeModel._package(packageName);
	}

	private File getTempDirectory() throws IOException {
		final File temp;

		File repoFile = new File(localRepo);
		if( !repoFile.exists() ) 
			repoFile.mkdirs();

		temp = File.createTempFile("blink", Long.toString(System.nanoTime()), repoFile);

		if(!(temp.delete()))
		{
			throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
		}

		if(!(temp.mkdir()))
		{
			throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
		}

		return (temp);
	}

	public String getLocalRepo() {
		return localRepo;
	}

	public void setLocalRepo(String localRepo) {
		this.localRepo = localRepo;
	}


}
