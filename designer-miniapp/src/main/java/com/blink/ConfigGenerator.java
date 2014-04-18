package com.blink;



import com.blink.designer.model.App;
import com.sun.codemodel.JDefinedClass;

public interface ConfigGenerator {

	public JDefinedClass generateConfig(JDefinedClass configClass, String pathRepo, App app);
	public void postConfig(JDefinedClass configClass);

}
