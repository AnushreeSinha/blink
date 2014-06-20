package com.blink;

import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;

import org.apache.cxf.jaxrs.servlet.CXFNonSpringJaxrsServlet;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.util.Log4jConfigListener;

import com.blink.designer.model.App;
import com.blink.designer.model.AppConfig;
import com.blink.designer.model.DBConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.sun.codemodel.JAnnotationUse;
import com.sun.codemodel.JBlock;
import com.sun.codemodel.JClassAlreadyExistsException;
import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JExpr;
import com.sun.codemodel.JInvocation;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import com.sun.codemodel.JTryBlock;
import com.sun.codemodel.JType;
import com.sun.codemodel.JVar;



public class ConfigGeneratorImpl implements ConfigGenerator {
	
	@PersistenceContext(unitName="jpa.blink")
	private EntityManager entityManager;
	
	public static App app;
	
	public JDefinedClass generateConfig(JDefinedClass configClass, String pathRepo, App app) {
		JDefinedClass webConfig = null;
		try {
			
			webConfig = generateWebConfig(configClass);
			System.out.println("generateWebConfig ran...");
			generateJPAConfig(configClass, app);
			System.out.println("generateJPAConfig ran...");
			try {
				generateBeansXML(pathRepo);
			} catch ( IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (JClassAlreadyExistsException e) {
			e.printStackTrace();
		}
		
        configClass.annotate(Configuration.class);
        return webConfig;
	}
	
	
	
	private void generateJPAConfig(JDefinedClass configClass, App app) throws JClassAlreadyExistsException {
		JCodeModel codeModel = configClass.owner();
		configClass.annotate(EnableTransactionManagement.class);
		configClass.annotate(ImportResource.class).param("value", "classpath:beans.xml");
		
		if(entityManager == null)
			System.out.println("entitymanager is null now with func prada");
		else 
			System.out.println("entitymanager is not null with func prada");
		
		/*JFieldVar dataSourceField = configClass.field(JMod.PRIVATE, javax.sql.DataSource.class, "dataSource");
		dataSourceField.annotate(Autowired.class); */
		long id=app.getId();
		
		
		Query w=entityManager.createQuery("Select c from com.blink.designer.model.AppConfig c where c.app = (Select d from com.blink.designer.model.App d where d.id = :id)");
		w.setParameter("id", id);
		AppConfig appConfig=(AppConfig) w.getSingleResult();
		DBConfig dbConfig=appConfig.getDbConfig();
		
		JMethod dataSourceMethod = configClass.method(JMod.PUBLIC, ComboPooledDataSource.class, "dataSource");
		dataSourceMethod.annotate(Bean.class);
		//dataSourceMethod._throws(PropertyVetoException.class);
		JTryBlock tryBlock = dataSourceMethod.body()._try();
		JBlock tryBlockBody = tryBlock.body();
		JVar dataSourceField = tryBlockBody.decl(codeModel.ref(ComboPooledDataSource.class), "dataSource",JExpr._new(codeModel.ref(ComboPooledDataSource.class)));
		tryBlockBody.add(dataSourceField.invoke("setDriverClass").arg(dbConfig.getJdbcDriver()));
		tryBlockBody.add(dataSourceField.invoke("setJdbcUrl").arg(dbConfig.getJdbcURL()));
		tryBlockBody.add(dataSourceField.invoke("setUser").arg(dbConfig.getUsername()));
		tryBlockBody.add(dataSourceField.invoke("setPassword").arg(dbConfig.getPassword()));
		
		/*tryBlockBody.add(dataSourceField.invoke("setDriverClass").arg("com.mysql.jdbc.Driver"));
		tryBlockBody.add(dataSourceField.invoke("setJdbcUrl").arg("jdbc:mysql://localhost:3306/campusanytime"));
		tryBlockBody.add(dataSourceField.invoke("setUser").arg("campusanytime"));
		tryBlockBody.add(dataSourceField.invoke("setPassword").arg("password"));*/
		tryBlockBody._return(dataSourceField);
		tryBlock._catch(codeModel.ref(PropertyVetoException.class)).body()._return(JExpr._null());
		
		JMethod method = configClass.method(JMod.PUBLIC, PersistenceExceptionTranslator.class, "persistenceExceptionTranslator");
		method.annotate(Bean.class);
		method.body()._return(JExpr._new(codeModel.ref(HibernateExceptionTranslator.class)));
		
		JMethod entityManagerFactoryMethod = configClass.method(JMod.PUBLIC, EntityManagerFactory.class, "entityManagerFactory");
		JVar vendorFieldVar = entityManagerFactoryMethod.body().decl(codeModel.ref(HibernateJpaVendorAdapter.class), "vendorAdapter",JExpr._new(codeModel.ref(HibernateJpaVendorAdapter.class)));
		entityManagerFactoryMethod.body().add(vendorFieldVar.invoke("setGenerateDdl").arg(JExpr.TRUE));
		entityManagerFactoryMethod.annotate(Bean.class);
		JVar factoryFieldVar = entityManagerFactoryMethod.body().decl(codeModel.ref(LocalContainerEntityManagerFactoryBean.class), "factory",JExpr._new(codeModel.ref(LocalContainerEntityManagerFactoryBean.class)));
		entityManagerFactoryMethod.body().add(factoryFieldVar.invoke("setJpaVendorAdapter").arg(vendorFieldVar));
		entityManagerFactoryMethod.body().add(factoryFieldVar.invoke("setPackagesToScan").arg(app.getBasePackage()+".jpa"));
		entityManagerFactoryMethod.body().add(factoryFieldVar.invoke("setDataSource").arg(JExpr.invoke(dataSourceMethod)));
		entityManagerFactoryMethod.body().add(factoryFieldVar.invoke("afterPropertiesSet"));
		entityManagerFactoryMethod.body()._return(factoryFieldVar.invoke("getObject"));
		
		JMethod transactionManagerMethod = configClass.method(JMod.PUBLIC,PlatformTransactionManager.class , "transactionManager");
		JVar field = transactionManagerMethod.body().decl(codeModel.ref(JpaTransactionManager.class), "txManager", JExpr._new(codeModel.ref(JpaTransactionManager.class)));
		transactionManagerMethod.annotate(Bean.class);
		transactionManagerMethod.body().add(field.invoke("setEntityManagerFactory").arg(JExpr.invoke(entityManagerFactoryMethod)));
		transactionManagerMethod.body()._return(field);
		
		JMethod methodMapper = configClass.method(JMod.PUBLIC,org.dozer.DozerBeanMapper.class, "mapper");
		JAnnotationUse annotation = methodMapper.annotate(Bean.class);
		annotation.param("name", "mapper");
		methodMapper.body()._return(JExpr._new(codeModel._ref(org.dozer.DozerBeanMapper.class)));
		
	}
    private JDefinedClass generateWebConfig(JDefinedClass configClass) throws JClassAlreadyExistsException {
    	JCodeModel codeModel = configClass.owner();
    	JDefinedClass webConfig = codeModel._class(configClass._package().name()+".WebAppInitializer");
		//definedClass.annotate(javax.servlet.annotation.WebListener.class);
		webConfig._implements(WebApplicationInitializer.class);
		JType type = codeModel.ref(AnnotationConfigWebApplicationContext.class);
		JMethod method = webConfig.method(JMod.PUBLIC, void.class, "onStartup");
		JVar param = method.param(ServletContext.class, "servletContext");
		JVar field = method.body().decl(type, "rootContext", JExpr._new(type));
		method.body().add(field.invoke("scan").arg(configClass._package().name()));
		
		method.body().add(param.invoke("addListener").arg(JExpr._new(codeModel.ref(Log4jConfigListener.class))));
		method.body().add(param.invoke("addListener").arg(JExpr._new(codeModel.ref(ContextLoaderListener.class)).arg(field)));
		
		return webConfig;
	    		
    }
    
    /*Dynamic dynamic = servletContext.addServlet("cxfServlet",CXFNonSpringJaxrsServlet.class);
    dynamic.setInitParameter("jaxrs.serviceClasses", CampusAnytimeService.class.getName());
    dynamic.addMapping("/services/");
    dynamic.setLoadOnStartup(1); */
    
    public  void postConfig(JDefinedClass definedClass) {
    	JCodeModel codeModel = definedClass.owner();
    	JMethod method = null;
    	for (JMethod methodTmp : definedClass.methods() ) {
    		System.err.println(methodTmp.name());
    		
    		if(methodTmp.name().equals("onStartup")) {
    			method = methodTmp;
    		}
    	}
    	JVar param = null;
    	for ( JVar varTmp : method.listParams() ) {
    		if(varTmp.name().equals("servletContext")) {
    			param = varTmp;
    		}
    	}
    	JInvocation invocation = param.invoke("addServlet");
	    invocation.arg("cxfServlet");
	    invocation.arg(codeModel.ref(CXFServlet.class).dotclass());
		
	    JVar dynamic = method.body().decl(codeModel.ref(Dynamic.class), "dynamic",invocation);
		
	    method.body().add(dynamic.invoke("setInitParameter").arg("jaxrs.serviceClasses").arg(GeneratorContext.getFacade(PackageType.DTO).fullName()));
	    method.body().add(dynamic.invoke("addMapping").arg("/services/*"));
		
		
    }
    
    public void generateBeansXML(String pathRepo) throws FileNotFoundException, IOException{
    	try {
    		pathRepo=pathRepo.substring(0, pathRepo.length()-5);
    		String area=pathRepo+"/resources/beans.xml";
    		
    		 System.out.println("pathRepo contents are "+ pathRepo);
    	  	 System.out.println("area contents are "+ area);
    	  	    
    		
    		
    		 
  	    File dest = new File(area);
  	      
  	    File source =new File("/Users/asinha1/blink/designer-miniapp/src/main/resources/beansCXFConfig.xml");
  	    if(!dest.exists()) 
  		  dest.createNewFile();
  		
	 

	    InputStream inStream = new FileInputStream(source);
	    OutputStream outStream = new FileOutputStream(dest);

	    byte[] buffer = new byte[1024];

	    int length; 
	    while ((length = inStream.read(buffer)) > 0){
	    	outStream.write(buffer, 0, length);
	    	}

	    inStream.close();
	    outStream.close();
   
  	     
   
      	} catch (IOException e) {
  	      e.printStackTrace();
  	}
    	
    /*	CrudCallsMethodGenerationImpl crudCalls=new CrudCallsMethodGenerationImpl();
    	crudCalls.generateAbstractActionClass(pathRepo); 
    	*/
    }
    
}
