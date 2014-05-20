package com.blink;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.persistence.EntityManager;

import com.blink.designer.model.App;
import com.sun.codemodel.JDefinedClass;

public interface ConfigGenerator {

	public JDefinedClass generateConfig(JDefinedClass configClass,String s,App app) throws FileNotFoundException, IOException;
	public void postConfig(JDefinedClass configClass);
	
}
