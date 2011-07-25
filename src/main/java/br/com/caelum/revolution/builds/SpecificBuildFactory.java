package br.com.caelum.revolution.builds;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.scm.SCM;

public interface SpecificBuildFactory {
	Build build(Config config, SCM scm);
}
