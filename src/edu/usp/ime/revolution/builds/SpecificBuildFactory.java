package edu.usp.ime.revolution.builds;

import edu.usp.ime.revolution.config.Config;

public interface SpecificBuildFactory {
	Build build(Config config);
}
