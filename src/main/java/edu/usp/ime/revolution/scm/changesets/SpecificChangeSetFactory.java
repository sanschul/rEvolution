package edu.usp.ime.revolution.scm.changesets;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.scm.SCM;

public interface SpecificChangeSetFactory {
	ChangeSetCollection build(SCM scm, Config config);
}
