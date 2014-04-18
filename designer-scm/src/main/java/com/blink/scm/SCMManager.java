package com.blink.scm;

import java.io.IOException;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;


import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;

public interface SCMManager {

    void init() throws IOException;

	void createRemoteRepository(String repositoryName)
			throws IOException, RepositoryExistsException;

	String createLocalRepository(String repositoryName)
			throws GitAPIException, RepositoryExistsException, IOException, XmlPullParserException;

	void pushFiles(String repositoryName) throws IOException,
			NoFilepatternException, GitAPIException;

}