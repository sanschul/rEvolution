package br.com.caelum.revolution.builds.nobuild;

import br.com.caelum.revolution.builds.Build;
import br.com.caelum.revolution.builds.SpecificBuildFactory;
import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.scm.SCM;

public class NoBuildFactory implements SpecificBuildFactory {

	public Build build(Config config, SCM scm) {
		return new NoBuild();
	}
}
