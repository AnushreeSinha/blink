package com.blink.designer.model;

import java.util.List;



public class Entity extends BaseBlinkModel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 955914790502950659L;
	
	
	private Package parentPackage;
	

	public Package getParentPackage() {
		return parentPackage;
	}

	public void setParentPackage(Package parentPackage) {
		this.parentPackage = parentPackage;
	}

	private List<EntityAttribute> entityAttributes;
	
	public List<EntityAttribute> getEntityAttributes() {
		return entityAttributes;
	}

	public void setEntityAttributes(List<EntityAttribute> entityAttributes) {
		this.entityAttributes = entityAttributes;
	}

}
