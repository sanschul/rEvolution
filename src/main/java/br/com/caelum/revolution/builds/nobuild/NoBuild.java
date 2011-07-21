package br.com.caelum.revolution.builds.nobuild;

import br.com.caelum.revolution.builds.Build;
import br.com.caelum.revolution.builds.BuildException;
import br.com.caelum.revolution.builds.BuildResult;

public class NoBuild implements Build {

	public BuildResult build(String path) throws BuildException {
		return null;
	}

}
