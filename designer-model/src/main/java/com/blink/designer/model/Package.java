package com.blink.designer.model;

public class Package extends BaseBlinkModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1181336906730065839L;
	
	
	private Package parentPackage;

	public Package getParentPackage() {
		return parentPackage;
	}

	public void setParentPackage(Package parentPackage) {
		this.parentPackage = parentPackage;
	}


}
