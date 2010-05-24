package edu.usp.ime.revolution.scm;

import edu.usp.ime.revolution.config.Config;

public class ChangeSetCollectionFactory {

	private final SCM scm;

	public ChangeSetCollectionFactory(SCM scm) {
		this.scm = scm;
	}

	public ChangeSetCollection basedOn(Config config) {
		return new AllChangeSets(scm);
	}

}
