package com.blink.designer.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;


import com.blink.designer.model.BaseBlinkModel;
import com.blink.util.ClassFinder;

public class TypeRegistryImpl implements TypeRegistry {


	private Logger logger =  Logger.getLogger(TypeRegistryImpl.class);
	private String modelPackage ;

	private static Map<String, Class<? extends BaseBlinkModel>> registry = new ConcurrentHashMap<String, Class<? extends BaseBlinkModel>>();

	public  TypeRegistryImpl(String modelPackage){
		try {

			logger.debug("Initializing Type Registry");
			this.setModelPackage(modelPackage);
			ClassFinder classFinder = new ClassFinder();
			List<Class<?>> clazzes = classFinder.getClasses(modelPackage);


			clazzes.addAll(classFinder.getClassesOfType(BaseBlinkModel.class, modelPackage));

			for(Class<?> clazz : clazzes) {
				registry.put(clazz.getSimpleName().toLowerCase(), (Class<? extends BaseBlinkModel>) clazz);
			}

			logger.debug("Type Registry initialized");
			for(Class<?> clazz : clazzes) {
				logger.debug("Type Registry element:" + clazz.getName());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void register(String entityType,Class<? extends BaseBlinkModel> baseBlinkModel) {

	}

	@Override
	public Class<? extends BaseBlinkModel> getType(String entityType) {
		Class<? extends BaseBlinkModel> clazz = registry.get(entityType);
		if( clazz == null) {
			try {
				clazz  = (Class<? extends BaseBlinkModel>) Class.forName(getModelPackage()+"."+entityType);
				registry.put(entityType, clazz);
			} catch (ClassNotFoundException e) {
				logger.error("Entity with reference name :"+ entityType +" not found");
			}
		}

		return clazz;
	}

	public String getModelPackage() {
		return modelPackage;
	}

	public void setModelPackage(String modelPackage) {
		this.modelPackage = modelPackage;
	}



}
