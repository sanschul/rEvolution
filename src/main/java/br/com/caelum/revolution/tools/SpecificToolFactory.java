package br.com.caelum.revolution.tools;

import br.com.caelum.revolution.config.Config;

public interface SpecificToolFactory {
	Tool build(Config config, String prefix);
}
