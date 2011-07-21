package edu.usp.ime.revolution.tools.sourcecode;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.tools.SpecificToolFactory;
import edu.usp.ime.revolution.tools.Tool;

public class SourceCodeRetrieverFactory implements SpecificToolFactory {

	public Tool build(Config config, String prefix) {
		return new SourceCodeRetriever(config.get(prefix + ".extensions").split(";"));
	}

}
