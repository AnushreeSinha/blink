package com.blink.designer.model;

public class FacebookConfig extends BaseBlinkModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5399034604011817384L;
	
	private String clientId;
	private String clientSecret;
	
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientSecret() {
		return clientSecret;
	}
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;

}
}
