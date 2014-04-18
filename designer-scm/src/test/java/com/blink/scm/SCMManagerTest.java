package com.blink.scm;

import java.io.IOException;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.AfterSuite;


public class SCMManagerTest {

	AbstractApplicationContext context  = null;

	@Test
	public void scmTest() throws IOException, GitAPIException, RepositoryExistsException, IOException, XmlPullParserException {

		SCMManager scmManager = (SCMManager) context.getBean("scmManager");
		scmManager.init();

		scmManager.createLocalRepository("test");

		scmManager.createRemoteRepository("test");

		scmManager.pushFiles("test");
	}
	@BeforeMethod
	public void beforeMethod() {
	}

	@AfterMethod
	public void afterMethod() {
	}

	@BeforeClass
	public void beforeClass() {
		context = new ClassPathXmlApplicationContext("classpath*:META-INF/beans*.xml");
	}

	@AfterClass
	public void afterClass() {
		context.close();
	}

	@BeforeTest
	public void beforeTest() {
	}

	@AfterTest
	public void afterTest() {
	}

	@BeforeSuite
	public void beforeSuite() {
	}

	@AfterSuite
	public void afterSuite() {
	}

}
