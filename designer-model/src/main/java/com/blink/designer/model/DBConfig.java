package com.blink.designer.model;

public class DBConfig extends BaseBlinkModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3909761734052576745L;
	
	private String jdbcURL;
	private String jdbcDriver;
	private String username;
	private String password;
	
	public String getJdbcURL() {
		return jdbcURL;
	}
	public void setJdbcURL(String jdbcURL) {
		this.jdbcURL = jdbcURL;
	}
	public String getJdbcDriver() {
		return jdbcDriver;
	}
	public void setJdbcDriver(String jdbcDriver) {
		this.jdbcDriver = jdbcDriver;
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
	
	

}
