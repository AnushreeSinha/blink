package com.blink.designer.model;

public class AppConfig extends BaseBlinkModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2990985096003199792L;

	private App app;
	
	private DBConfig dbConfig;
	private PersistenceAPIConfig persistenceAPIConfig;
	private SecurityAPIConfig securityAPIConfig;
	private SecurityStoreConfig securityStoreConfig;

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public DBConfig getDbConfig() {
		return dbConfig;
	}

	public void setDbConfig(DBConfig dbConfig) {
		this.dbConfig = dbConfig;
	}

	public PersistenceAPIConfig getPersistenceAPIConfig() {
		return persistenceAPIConfig;
	}

	public void setPersistenceAPIConfig(PersistenceAPIConfig persistenceAPIConfig) {
		this.persistenceAPIConfig = persistenceAPIConfig;
	}

	public SecurityAPIConfig getSecurityAPIConfig() {
		return securityAPIConfig;
	}

	public void setSecurityAPIConfig(SecurityAPIConfig securityAPIConfig) {
		this.securityAPIConfig = securityAPIConfig;
	}

	public SecurityStoreConfig getSecurityStoreConfig() {
		return securityStoreConfig;
	}

	public void setSecurityStoreConfig(SecurityStoreConfig securityStoreConfig) {
		this.securityStoreConfig = securityStoreConfig;
	} 
	
	

}
