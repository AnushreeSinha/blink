package com.blink.designer.model;



public class EntityAttribute extends BaseBlinkModel{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = -5098212742045206213L;
	
	
	private Type primitiveId;
	
	private boolean searchable;

	private Entity compositeId;
	
	private boolean primaryKey;
	
	private String multiType;
	
	private boolean isRequired;
	
	private Validations validations;
	
	public boolean isSearchable() {
		return searchable;
	}
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}
	public Type getPrimitiveId() {
		return primitiveId;
	}
	public void setPrimitiveId(Type primitiveId) {
		this.primitiveId = primitiveId;
	}
	public Entity getCompositeId() {
		return compositeId;
	}
	public void setCompositeId(Entity compositeId) {
		this.compositeId = compositeId;
	}
	public boolean isPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getMultiType() {
		return multiType;
	}
	public void setMultiType(String multiType) {
		this.multiType = multiType;
	}
	public boolean getIsRequired() {
		return isRequired;
	}
	public void setIsRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	public Validations getValidations() {
		return validations;
	}
	public void setValidations(Validations validations) {
		this.validations = validations;
	}
	
	
}
