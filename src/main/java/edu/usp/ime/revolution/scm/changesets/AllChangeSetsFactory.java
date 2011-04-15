package edu.usp.ime.revolution.scm.changesets;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.scm.ChangeSetCollection;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.SpecificChangeSetFactory;

public class AllChangeSetsFactory implements SpecificChangeSetFactory {

	public ChangeSetCollection build(SCM scm, Config config) {
		return new AllChangeSets(scm);
	}

}
