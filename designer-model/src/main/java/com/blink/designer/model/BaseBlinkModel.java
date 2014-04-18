package com.blink.designer.model;

import java.io.Serializable;

public class BaseBlinkModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1937624936562296113L;
	private long id; 
	private String name;
	private String description;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
