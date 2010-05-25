package edu.usp.ime.revolution.tools;

import edu.usp.ime.revolution.config.Config;

public interface SpecificToolFactory {
	MetricTool build(Config config, String prefix);
}
