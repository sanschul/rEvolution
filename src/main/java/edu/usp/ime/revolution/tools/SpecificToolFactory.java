package edu.usp.ime.revolution.tools;

import edu.usp.ime.revolution.config.Config;

public interface SpecificToolFactory {
	Tool build(Config config, String prefix);
}
