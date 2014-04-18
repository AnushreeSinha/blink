package com.blink.scm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.log4j.Logger;
import org.apache.maven.cli.MavenCli;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import com.gitblit.client.GitblitClient;
import com.gitblit.client.GitblitRegistration;
import com.gitblit.models.RepositoryModel;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.maven.model.*;

import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;



public class BlinkSCMManager implements SCMManager {

	private static final String PATH_SEPARATOR = File.separator;
	
	private static final Logger logger = Logger.getLogger(BlinkSCMManager.class);

	static {

		System.setProperty("javax.net.ssl.keyStore","/Users/asinha1/Documents/serverKeyStore.jks");
		System.setProperty("javax.net.ssl.trustStore", "/Users/asinha1/Documents/serverTrustStore.jks");
		System.setProperty("javax.net.ssl.keyStorePassword", "gitblit");
		System.setProperty("javax.net.ssl.trustStorePassword", "gitblit");
		System.setProperty("maven.home", "/Users/asinha1/Documents/apache-maven-3.0.5");
	}


	private GitblitClient client;
	private String username; 
	private String password; 
	private String remoteRepoUrl; 
	private String localWorkspace; 

	public BlinkSCMManager() {

	}

	/* (non-Javadoc)
	 * @see com.blink.scm.SCMManager#init()
	 */
	public void init() throws IOException  {
		GitblitRegistration registration = new GitblitRegistration("test",remoteRepoUrl,username,password.toCharArray());
		client = new GitblitClient(registration);
        System.out.println("Login into " + remoteRepoUrl);
		client.login();
	}


	/* (non-Javadoc)
	 * @see com.blink.scm.SCMManager#createRemoteRepository(java.lang.String)
	 */
	public  void createRemoteRepository(String repositoryName) throws IOException, RepositoryExistsException {
		
		logger.debug("Starting to create Remote repository with name " +  repositoryName);
		System.err.println("Hello");
		RepositoryModel repositoryModel = new RepositoryModel();
		repositoryModel.name = repositoryName;
		repositoryModel.isBare = true;

		if ( client.getRepository(repositoryName) != null ) 
			throw new RepositoryExistsException("Remote Repository " + repositoryName+ " already exists");

		client.createRepository(repositoryModel,null);
		
		logger.debug("Created Remote repository with name " +  repositoryName);

	}

	/* (non-Javadoc)
	 * @see com.blink.scm.SCMManager#createLocalRepository(java.lang.String)
	 */
	public String createLocalRepository(String repositoryName) throws GitAPIException, RepositoryExistsException, IOException, XmlPullParserException {

		final String localRepo = localWorkspace + PATH_SEPARATOR + repositoryName;
        
		InitCommand init = Git.init();
		File initFile = new File(localRepo);
		if(initFile.exists()) 
			throw new RepositoryExistsException("Local Repository " + repositoryName+ " already exists in Workspace");
		initFile.mkdirs();
		init.setDirectory(initFile);

		init.call();
		
		createMavenProject(localRepo, repositoryName);
		if(!repositoryName.equals("test"))
			new File(localWorkspace + "/" + repositoryName + "/" + repositoryName+ "/src/main/java").mkdirs();

		
		return localRepo;

	}

	private void createMavenProject(String projectDirectory, String projectName) throws FileNotFoundException, IOException, XmlPullParserException {
		MavenCli cli = new MavenCli();
		int result = cli.doMain(new String[]{"archetype:generate","-DgroupId=com.mycompany.app", "-DartifactId="+ projectName,"-DarchetypeGroupId=org.apache.maven.archetypes", 
				"-DarchetypeArtifactId=maven-archetype-webapp", "-DarchetypeVersion=1.0","-DinteractiveMode=false"},
				projectDirectory,  System.out, System.out);
		//String baseDir = "/Users/asinha1/blink/Honda/Honda"; 
		//String baseDir_new="/Users/asinha1/blink/"+projectName+"/"+projectName;
		String baseDir_new=projectDirectory+"/"+projectName;
		System.out.println("baseDir "+baseDir_new);
		MavenXpp3Reader reader = new MavenXpp3Reader(); 
		Model model = reader.read(new FileInputStream(new File(baseDir_new, "/pom.xml")));
		MavenXpp3Writer writer = new MavenXpp3Writer(); 
		Dependency d=new Dependency();
		Dependency d1=new Dependency();
		Dependency d2=new Dependency();
		Dependency d3=new Dependency();
		Dependency d4=new Dependency();
		Dependency d5=new Dependency();
		Dependency d6=new Dependency();
		Dependency d7=new Dependency();
		Dependency d8=new Dependency();
		Dependency d9=new Dependency();
		Dependency d10=new Dependency();
		Dependency d12=new Dependency();
		
		d.setGroupId("javax.persistence"); 
		d.setArtifactId("persistence-api"); 
		d.setVersion("1.0.2"); 
		model.addDependency(d);
		
		d1.setGroupId("net.sf.dozer"); 
		d1.setArtifactId("dozer"); 
		d1.setVersion("5.1"); 
		model.addDependency(d1);
		
		d2.setGroupId("c3p0"); 
		d2.setArtifactId("c3p0"); 
		d2.setVersion("0.8.4.5"); 
		model.addDependency(d2);
		
		d3.setGroupId("org.springframework"); 
		d3.setArtifactId("spring-context"); 
		d3.setVersion("4.0.1.RELEASE"); 
		model.addDependency(d3);
		
		d4.setGroupId("org.springframework"); 
		d4.setArtifactId("spring-orm"); 
		d4.setVersion("4.0.1.RELEASE"); 
		model.addDependency(d4);
		
		d5.setGroupId("org.springframework"); 
		d5.setArtifactId("spring-tx"); 
		d5.setVersion("4.0.1.RELEASE"); 
		model.addDependency(d5);
		
		d6.setGroupId("org.springframework"); 
		d6.setArtifactId("spring-web"); 
		d6.setVersion("4.0.1.RELEASE"); 
		model.addDependency(d6);
		
		d7.setGroupId("org.springframework"); 
		d7.setArtifactId("spring-beans"); 
		d7.setVersion("4.0.1.RELEASE"); 
		model.addDependency(d7);
		
		d8.setGroupId("javax.servlet"); 
		d8.setArtifactId("javax.servlet-api"); 
		d8.setVersion("3.0.1");
		model.addDependency(d8);
		
		d9.setGroupId("org.apache.cxf"); 
		d9.setArtifactId("cxf-bundle"); 
		d9.setVersion("2.2.9");
		model.addDependency(d9);
		
		d10.setGroupId("org.hibernate"); 
		d10.setArtifactId("hibernate-entitymanager"); 
		d10.setVersion("4.1.3.Final");
		model.addDependency(d10);
		
		d12.setGroupId("log4j");
		d12.setArtifactId("log4j");
		d12.setVersion("1.2.12");
		model.addDependency(d12);

		
		
		writer.write(new FileOutputStream(new File(baseDir_new, "/pom.xml")), model);
		
	}

	/* (non-Javadoc)
	 * @see com.blink.scm.SCMManager#pushFiles(java.lang.String)
	 */
	public void pushFiles(String repositoryName) throws IOException, NoFilepatternException, GitAPIException {

		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		File f = new File(localWorkspace + PATH_SEPARATOR + repositoryName + PATH_SEPARATOR +".git");

		Repository db = builder.setGitDir(f).findGitDir().build();
		Git git = new Git(db);
		AddCommand add = git.add();
		add.addFilepattern(".").call();

		CommitCommand commit = git.commit();
		commit.setAll(true);
		commit.setMessage("Project Initialized");
		commit.call();  

		String remotePath = remoteRepoUrl +"/git/" +  repositoryName+ ".git";

		PushCommand pushCommand = git.push();
		pushCommand.setRemote(remotePath);
		CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
		pushCommand.setCredentialsProvider(credentialsProvider);
		pushCommand.call(); 
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRemoteRepoUrl() {
		return remoteRepoUrl;
	}

	public void setRemoteRepoUrl(String remoteRepoUrl) {
		this.remoteRepoUrl = remoteRepoUrl;
	}

	public String getLocalWorkspace() {
		return localWorkspace;
	}

	public void setLocalWorkspace(String localWorkspace) {
		this.localWorkspace = localWorkspace;
	}
}
