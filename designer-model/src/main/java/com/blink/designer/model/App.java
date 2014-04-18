package com.blink.designer.model;

public class App extends BaseBlinkModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2966011436100520439L;
	
	private DBType db;
	
	private PersistenceAPIType persistenceAPI;
	
	private SecurityAPIType securityAPI;
	
	private SecurityStoreType securityStore;
	
	private FrontendType frontend;
	
	private ServiceAPIType serviceAPIType;
	
	private String basePackage;
	

	public String getBasePackage() {
		return basePackage;
	}

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	public DBType getDb() {
		return db;
	}

	public void setDb(DBType db) {
		this.db = db;
	}

	public PersistenceAPIType getPersistenceAPI() {
		return persistenceAPI;
	}

	public void setPersistenceAPI(PersistenceAPIType persistenceAPI) {
		this.persistenceAPI = persistenceAPI;
	}

	public SecurityAPIType getSecurityAPI() {
		return securityAPI;
	}

	public void setSecurityAPI(SecurityAPIType securityAPI) {
		this.securityAPI = securityAPI;
	}

	public SecurityStoreType getSecurityStore() {
		return securityStore;
	}

	public void setSecurityStore(SecurityStoreType securityStore) {
		this.securityStore = securityStore;
	}

	public FrontendType getFrontend() {
		return frontend;
	}

	public void setFrontend(FrontendType frontend) {
		this.frontend = frontend;
	}

	public ServiceAPIType getServiceAPIType() {
		return serviceAPIType;
	}

	public void setServiceAPIType(ServiceAPIType serviceAPIType) {
		this.serviceAPIType = serviceAPIType;
	}

	@Override
	public String toString() {
		return "App [db=" + db + ", persistenceAPI=" + persistenceAPI
				+ ", securityAPI=" + securityAPI + ", securityStore="
				+ securityStore + ", frontend=" + frontend
				+ ", serviceAPIType=" + serviceAPIType + "]";
	} 
  
}
