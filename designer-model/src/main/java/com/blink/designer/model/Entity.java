package com.blink.designer.model;

import java.util.List;



public class Entity extends BaseBlinkModel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 955914790502950659L;
	
	
	private Package parentPackage;
	
    private String createAction;
	
	private String updateAction;
	
	private String deleteAction;
	
	private String readAction;
	

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

	public String getCreateAction() {
		return createAction;
	}

	public void setCreateAction(String createAction) {
		this.createAction = createAction;
	}

	public String getUpdateAction() {
		return updateAction;
	}

	public void setUpdateAction(String updateAction) {
		this.updateAction = updateAction;
	}

	public String getDeleteAction() {
		return deleteAction;
	}

	public void setDeleteAction(String deleteAction) {
		this.deleteAction = deleteAction;
	}

	public String getReadAction() {
		return readAction;
	}

	public void setReadAction(String readAction) {
		this.readAction = readAction;
	}

}
