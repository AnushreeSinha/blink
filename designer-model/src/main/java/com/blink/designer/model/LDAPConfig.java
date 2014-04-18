package com.blink.designer.model;

public class LDAPConfig extends BaseBlinkModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2343920434855023553L;
	private String userProvider;
	private String userFilter;
	private String authzIdentity;
	
	public String getUserProvider(){
		return userProvider;
	}
	public String getUserFilter(){
		return userFilter;
	}
	public String getAuthzIdentity(){
		return authzIdentity;
	}
	
	public void setUserProvider(String userProvider){
		this.userProvider=userProvider;
	}
	public void setUserFilter(String userFilter){
		this.userFilter=userFilter;
	}
	public void setAuthzIdentity(String authzIdentity){
		this.authzIdentity=authzIdentity;
	}
	
	
	
}
