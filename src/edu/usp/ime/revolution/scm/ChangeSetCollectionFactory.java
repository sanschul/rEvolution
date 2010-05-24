package edu.usp.ime.revolution.scm;

import edu.usp.ime.revolution.config.Config;

public class ChangeSetCollectionFactory {

	public ChangeSetCollection basedOn(Config config, SCM scm) {
		return new AllChangeSets(scm);
	}

}
