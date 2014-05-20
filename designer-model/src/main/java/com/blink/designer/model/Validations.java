package com.blink.designer.model;

import java.io.Serializable;


public class Validations implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7069779110883434786L;

	private Size size;
	
	private boolean email;
	
	private boolean creditCard;
	
	private boolean assertTrue;
	
	private boolean assertFalse;

	private long id;
	
	

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public boolean isEmail() {
		return email;
	}

	public void setEmail(boolean email) {
		this.email = email;
	}

	public boolean isCreditCard() {
		return creditCard;
	}

	public void setCreditCard(boolean creditCard) {
		this.creditCard = creditCard;
	}

	public boolean isAssertTrue() {
		return assertTrue;
	}

	public void setAssertTrue(boolean assertTrue) {
		this.assertTrue = assertTrue;
	}

	public boolean isAssertFalse() {
		return assertFalse;
	}

	public void setAssertFalse(boolean assertFalse) {
		this.assertFalse = assertFalse;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	

}
