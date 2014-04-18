package com.blink;

import com.blink.designer.model.App;

public interface AppGenerator {

	void generateApp(App app, String repositoryFile) throws AppGenerationError;
}
