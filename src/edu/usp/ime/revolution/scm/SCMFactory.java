package edu.usp.ime.revolution.scm;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.config.Configs;
import edu.usp.ime.revolution.scm.git.GitFactory;

public class SCMFactory {

	public SCM basedOn(Config config) {
		SpecificSCMFactory scmFactory = getToolFactory(config.get(Configs.SCM));
		return scmFactory.build(config);		
	}

	private SpecificSCMFactory getToolFactory(String scmName) {
		if(scmName.equals("git")) {
			return new GitFactory();
		}
		
		throw new SCMNotFoundException();
	}

}
