package com.blink.designer.service;

import com.blink.designer.model.BaseBlinkModel;

public interface TypeRegistry {

	void register(String entityType, Class<? extends BaseBlinkModel> baseBlinkModel);
	Class<? extends BaseBlinkModel> getType(String entityType);
	
}
