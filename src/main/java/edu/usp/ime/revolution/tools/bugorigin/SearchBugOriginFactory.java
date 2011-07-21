package edu.usp.ime.revolution.tools.bugorigin;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.tools.SpecificToolFactory;
import edu.usp.ime.revolution.tools.Tool;

public class SearchBugOriginFactory implements SpecificToolFactory {

	public Tool build(Config config, String prefix) {
		return new SearchBugOrigin(config.get(prefix + ".keywords").split(";"));
	}

}
