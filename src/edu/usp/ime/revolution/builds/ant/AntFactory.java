package edu.usp.ime.revolution.builds.ant;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.SpecificBuildFactory;
import edu.usp.ime.revolution.config.Config;

public class AntFactory implements SpecificBuildFactory {

	public Build build(Config config) {
		return new Ant();
	}

}
