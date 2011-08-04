package br.com.caelum.revolution.builds;

import br.com.caelum.revolution.config.Config;

public interface SpecificBuildFactory {
	Build build(Config config);
}
