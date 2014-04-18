package com.blink.designer.model;

public enum NativeType {

	LONG("java.lang.Long"),
	DATE("java.util.Date"),
	DOUBLE("java.lang.Double");
	
	private String type;

    private NativeType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }    
}

