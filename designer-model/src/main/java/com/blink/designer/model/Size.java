package com.blink.designer.model;

import java.io.Serializable;



public class Size implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6483270463248384720L;

	private int maxSize;
	
	private int minSize;
	
	private long id;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public int getMinSize() {
		return minSize;
	}

	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}
	
	
}
