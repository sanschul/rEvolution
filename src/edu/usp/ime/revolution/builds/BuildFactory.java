package edu.usp.ime.revolution.builds;

import edu.usp.ime.revolution.builds.ant.AntFactory;
import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.config.Configs;

public class BuildFactory {

	public Build basedOn(Config config) {
		SpecificBuildFactory buildFactory = getBuildFactory(config.get(Configs.BUILD));
		return buildFactory.build(config);
	}

	private SpecificBuildFactory getBuildFactory(String name) {
		if(name.equals("ant")) {
			return new AntFactory();
		}
		
		throw new BuildNotFoundException();

	}

}
