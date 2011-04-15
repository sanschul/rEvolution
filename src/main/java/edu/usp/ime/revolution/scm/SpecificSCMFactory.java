package edu.usp.ime.revolution.scm;

import edu.usp.ime.revolution.config.Config;

public interface SpecificSCMFactory {
	SCM build(Config config);
}
