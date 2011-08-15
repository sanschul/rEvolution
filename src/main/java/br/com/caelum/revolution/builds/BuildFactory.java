package br.com.caelum.revolution.builds;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.config.Configs;

public class BuildFactory {

	public Build basedOn(Config config) {
		SpecificBuildFactory buildFactory = getBuildFactory(config.asString(Configs.BUILD));
		return buildFactory.build(config);
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
