package edu.usp.ime.revolution.scm.changesets;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.config.Configs;
import edu.usp.ime.revolution.scm.SCM;

public class ChangeSetCollectionFactory {

	private final SCM scm;

	public ChangeSetCollectionFactory(SCM scm) {
		this.scm = scm;
	}

	public ChangeSetCollection basedOn(Config config) {
		SpecificChangeSetFactory factory = getConfigFactory(config.get(Configs.CHANGESETS));
		return factory.build(scm, config);
	}

	@SuppressWarnings("rawtypes")
	private SpecificChangeSetFactory getConfigFactory(String name) {
		try {
			Class theClass = Class.forName(name);
			return (SpecificChangeSetFactory)theClass.newInstance();
		} catch (Exception e) {
			throw new ChangeSetNotFoundException(e);
		}
	}

}
