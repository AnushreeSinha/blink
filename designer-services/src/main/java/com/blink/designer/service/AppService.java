package com.blink.designer.service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;

import com.blink.AppGenerationError;
import com.blink.AppGenerator;
import com.blink.designer.model.App;
import com.blink.model.generator.ModelGenerator;
import com.blink.scm.RepositoryExistsException;
import com.blink.scm.SCMManager;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import org.apache.maven.cli.MavenCli;


import org.codehaus.plexus.util.xml.pull.XmlPullParserException;



@Path("/autogen/app")
public class AppService {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SCMManager scmManager; 

	@Autowired
	private ModelGenerator modelGenerator; 

	@Autowired
	private String workspace; 

	@Autowired
	private Map<String,AppGenerator> generators; 

	public void setGenerators(Map<String, AppGenerator> generators) {
		this.generators = generators;
	}

	@POST
	@Path("/{apptype}/{appid}")
	public Response generateApp(@PathParam("appid") Long id, @PathParam("apptype") String apptype) throws GitAPIException, RepositoryExistsException, IOException, XmlPullParserException  {

		App app = entityManager.find(App.class, id);

		String appName = app.getName().replaceAll(" ", "");

		/***
		 * Create the model first 
		 */
		try {

			String projectLocation  = scmManager.createLocalRepository(appName)  ;

			String srcLocation = projectLocation + File.separator +app.getName() + File.separator +"src/main/java";

			File jarName = modelGenerator.generateModel(app,srcLocation);
			
			URLClassLoader sysLoader;
			URL u = null;
			try {

				File file = new File(jarName.getAbsolutePath());
				u = file.toURI().toURL();
				sysLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();

				Class sysclass = URLClassLoader.class;
				Class[] parameters = new Class[] { URL.class };
				Method method = sysclass.getDeclaredMethod("addURL", parameters);

				method.setAccessible(true);
				method.invoke(sysLoader, new Object[] { u });

				} 
			catch (Throwable t) {
				t.printStackTrace(System.err);
				throw new IOException("Error, could not add file " + u.toExternalForm() + " to system classloader");
				}



			
			
			AppGenerator appGenerator = generators.get(apptype);
            String classPath = System.getProperty("java.class.path") +":" + jarName.getAbsolutePath(); 
			System.setProperty("java.class.path", classPath);
			System.err.println("Classpath is : " + System.getProperty("java.class.path"));
			appGenerator.generateApp(app, srcLocation);

			scmManager.createRemoteRepository(appName);
			scmManager.pushFiles(appName);
			MavenCli cli=new MavenCli();
			int result = cli.doMain(new String[]{"package"},
			projectLocation + File.separator +app.getName(),System.out, System.out);


		} catch (ClassNotFoundException e1) {
			return Response.status(500).build();
		} catch (IOException e1) {
			return Response.status(500).build();
		} catch (AppGenerationError e) {
			e.printStackTrace();
			return Response.status(500).build();
		}

		return Response.ok().build(); 
	}



	public SCMManager getScmManager() {
		return scmManager;
	}

	public void setScmManager(SCMManager scmManager) {
		this.scmManager = scmManager;
	}

	public ModelGenerator getModelGenerator() {
		return modelGenerator;
	}

	public void setModelGenerator(ModelGenerator modelGenerator) {
		this.modelGenerator = modelGenerator;
	}


}
