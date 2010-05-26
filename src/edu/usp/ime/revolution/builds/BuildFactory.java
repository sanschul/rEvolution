package edu.usp.ime.revolution.builds;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.config.Configs;

public class BuildFactory {

	public Build basedOn(Config config) {
		SpecificBuildFactory buildFactory = getBuildFactory(config.get(Configs.BUILD));
		return buildFactory.build(config);
	}

	@SuppressWarnings("unchecked")
	private SpecificBuildFactory getBuildFactory(String name) {
		try {
			Class theClass = Class.forName(name);
			return (SpecificBuildFactory)theClass.newInstance();
		} catch (Exception e) {
			throw new BuildNotFoundException();
		}

	}

}
