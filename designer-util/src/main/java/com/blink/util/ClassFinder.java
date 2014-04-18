package com.blink.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;
import org.reflections.Reflections;

public class ClassFinder {

	Logger logger = Logger.getLogger(ClassFinder.class);
	
	
	public List<Class<?>> getClassesOfType(Class clazz, String packageType) {
		Reflections reflections = new Reflections(packageType);

	    List<Class<?>> clazzes = new ArrayList<Class<?>>();
	    clazzes.addAll(reflections.getSubTypesOf(clazz));
	    
	    return clazzes;

	}
	
	public List<Class<?>> getClasses(String packageName) throws IOException {

		String pathName = getPathName(packageName);
		
		
		List<String> classNamesForProcessing = findClassesForProcessing(pathName);
		List<Class<?>> classes = new ArrayList<Class<?>>() ;

		for(String fullPathOfClass : classNamesForProcessing) {
			String className = fullPathOfClass.substring(fullPathOfClass.indexOf(pathName),fullPathOfClass.length()-6);
			className= className.replace(System.getProperty("file.separator").charAt(0),'.' );
			Class<?> clazz;
			try {
				clazz = Class.forName(className);
				classes.add(clazz);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return classes;
	}

	private  List<String> findClassesForProcessing(String pathName) throws IOException {

		List<String>  classesNameForProcessing = new ArrayList<String>();
		List<String> classpathComponents = getClasspathComponents() ;

		for(String classpathComponent: classpathComponents) {
			File file = new File(classpathComponent);
			System.out.println("Processing:  "+classpathComponent);
			if(!file.exists()) 
				continue;
			if( classpathComponent.endsWith(".jar"))
				classesNameForProcessing.addAll(processJar(file, pathName));
			else  if(file.isDirectory()){
				classesNameForProcessing.addAll(processDir(file, pathName));
			} else {
				classesNameForProcessing.add(processFile(file, pathName ));
			}
		}
		return classesNameForProcessing;
	}


	private  List<String> getClasspathComponents() {
		List<String> classpathComponents= new ArrayList<String>();
		Collections.addAll(classpathComponents,System.getProperty("java.class.path").split("[:]"));
		
		/**
		 * For webapps 
		 */
		Enumeration name = null;
		try {
			name = this.getClass().getClassLoader().getResources("/");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		logger.error("name :" + name);
		
		return classpathComponents;
	}
	
	public  List<String> processJar(File jarFile, String pathName) throws IOException {

		List<String> classesNameForProcessing = new ArrayList<String>();
		ZipInputStream zip = new ZipInputStream( new FileInputStream( jarFile));
		ZipEntry ze = null;

		while( ( ze = zip.getNextEntry() ) != null ) {
			String entryName = ze.getName().replace("/",System.getProperty("file.separator"));
			if( entryName.startsWith(pathName) && entryName.endsWith(".class") ) {
				classesNameForProcessing.add( entryName  );
			}
		}
		zip.close();
		return classesNameForProcessing;
	}

	private  String getPathName(String packageName) {
		final String pathName = packageName.replace(".", System.getProperty("file.separator"));
		return pathName;
	}

	public  List<String> processDir(File dir, String pathName) {
		return walk(dir, pathName);
	}

	private  List<String> walk( File dirPath, String pathName ) {
		List<String>  classesNameForProcessing = new ArrayList<String>();

		File[] list = dirPath.listFiles();

		for ( File f : list ) {
			if ( f.isDirectory() ) {
				classesNameForProcessing.addAll(walk( f.getAbsoluteFile() , pathName));
			}
			else {
				String name = processFile(f, pathName);
				if (name != null)
					classesNameForProcessing.add(name);
			}
		}
		return classesNameForProcessing; 
	}

	private String processFile(File file, String pathName) {
		logger.debug("\t\t\tProcessing File :"+ file.getAbsolutePath() + " for pathName: "+ pathName);
		String fileName = file.getAbsolutePath();
		if(fileName.endsWith(".class") && fileName.contains(pathName)) {
			return fileName ;
		}
		return null;
	}
}
