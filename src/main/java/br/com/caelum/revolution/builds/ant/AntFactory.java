package br.com.caelum.revolution.builds.ant;

import br.com.caelum.revolution.builds.Build;
import br.com.caelum.revolution.builds.SpecificBuildFactory;
import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.executor.SimpleCommandExecutor;
import br.com.caelum.revolution.scm.SCM;

public class AntFactory implements SpecificBuildFactory {

	public Build build(Config config, SCM scm) {
		return new Ant(
				scm,
				new SimpleCommandExecutor(), 
				config.get("ant.task"),
				config.get("ant.buildPath"));
	}

}
