package com.blink.model.generator.test;

import java.io.IOException;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.blink.designer.model.App;
import com.blink.model.generator.ModelGenerator;

public class ModelGeneratorTest {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		AbstractApplicationContext context  = new ClassPathXmlApplicationContext("classpath*:META-INF/beans*.xml");
		ModelGenerator modelGenerator = (ModelGenerator) context.getBean("modelGenerator");
		App app = new App();
		app.setId(163841);
		app.setName("TestApp");
		app.setBasePackage("com.test");
		/*try {
			modelGenerator.generateModel(app);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

}
