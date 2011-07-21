package edu.usp.ime.revolution.tools.lineschanged;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.tools.SpecificToolFactory;
import edu.usp.ime.revolution.tools.Tool;

public class NumberOfLinesChangedFactory implements SpecificToolFactory{

	public Tool build(Config config, String prefix) {
		return new NumberOfLinesChanged();
	}

}
