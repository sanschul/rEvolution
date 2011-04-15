package edu.usp.ime.revolution.scm;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.config.Configs;

public class SCMFactory {

	public SCM basedOn(Config config) {
		SpecificSCMFactory scmFactory = getToolFactory(config.get(Configs.SCM));
		return scmFactory.build(config);		
	}

	@SuppressWarnings({ "rawtypes" })
	private SpecificSCMFactory getToolFactory(String scmName) {
		try {
			Class theClass = Class.forName(scmName);
			return (SpecificSCMFactory)theClass.newInstance();
		} catch (Exception e) {
			throw new SCMNotFoundException();
		}
	}

}
