package edu.usp.ime.revolution.scm;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.config.Configs;
import edu.usp.ime.revolution.exceptions.SCMNotFoundException;
import edu.usp.ime.revolution.scm.git.Git;

public class SCMFactory {

	public SCM basedOn(Config config) {
		if(config.get(Configs.SCM).equals("git")) {
			return new Git(config.get(Configs.SCM_REPOSITORY));
		}
		
		throw new SCMNotFoundException();
	}

}
