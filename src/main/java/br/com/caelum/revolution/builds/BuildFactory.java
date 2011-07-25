package br.com.caelum.revolution.builds;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.config.Configs;
import br.com.caelum.revolution.scm.SCM;

public class BuildFactory {

	public Build basedOn(Config config, SCM scm) {
		SpecificBuildFactory buildFactory = getBuildFactory(config.get(Configs.BUILD));
		return buildFactory.build(config, scm);
	}

	@SuppressWarnings("rawtypes")
	private SpecificBuildFactory getBuildFactory(String name) {
		try {
			Class theClass = Class.forName(name);
			return (SpecificBuildFactory)theClass.newInstance();
		} catch (Exception e) {
			throw new BuildNotFoundException();
		}

	}

}
