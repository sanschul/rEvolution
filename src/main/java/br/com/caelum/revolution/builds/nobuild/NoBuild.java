package br.com.caelum.revolution.builds.nobuild;

import br.com.caelum.revolution.builds.Build;
import br.com.caelum.revolution.builds.BuildException;
import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.scm.SCM;

public class NoBuild implements Build {

	public BuildResult build(String path, SCM scm) throws BuildException {
		return null;
	}

}
