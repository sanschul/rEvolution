package edu.usp.ime.revolution.tools.files;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.tools.Tool;
import edu.usp.ime.revolution.tools.SpecificToolFactory;

public class NumberOfFilesFactory implements SpecificToolFactory {

	public Tool build(Config config, String prefix) {
		return new NumberOfFiles(config.get(prefix + ".extension"));
	}

}
