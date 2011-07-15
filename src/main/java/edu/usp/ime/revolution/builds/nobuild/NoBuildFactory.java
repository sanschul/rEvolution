package edu.usp.ime.revolution.builds.nobuild;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.SpecificBuildFactory;
import edu.usp.ime.revolution.config.Config;

public class NoBuildFactory implements SpecificBuildFactory {

	public Build build(Config config) {
		return new NoBuild();
	}
}
