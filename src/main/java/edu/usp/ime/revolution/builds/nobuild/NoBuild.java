package edu.usp.ime.revolution.builds.nobuild;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildException;
import edu.usp.ime.revolution.builds.BuildResult;

public class NoBuild implements Build {

	public BuildResult build(String path) throws BuildException {
		return null;
	}

}
