package com.blink.tests;

import org.testng.annotations.Test;

import com.blink.AbstractAppGenerator;
import com.blink.AppGenerationError;
import com.blink.MiniAppGenerator;
import com.blink.designer.model.App;

public class AppGeneratorTest {
	@Test
	public void testGenerateApp() throws AppGenerationError {

		App app = new App();
		app.setBasePackage("com.campusanytime.model.");
		app.setName("CampusAnytime");
		AbstractAppGenerator enumerator = new MiniAppGenerator();
		enumerator.generateApp(app,"/Users/rpoosar/Documents/workspace");
	}
}
