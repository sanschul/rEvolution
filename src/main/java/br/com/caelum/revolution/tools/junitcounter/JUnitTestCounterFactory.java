package br.com.caelum.revolution.tools.junitcounter;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.tools.SpecificToolFactory;
import br.com.caelum.revolution.tools.Tool;

public class JUnitTestCounterFactory implements SpecificToolFactory{

	public Tool build(Config config, String prefix) {
		return new JUnitTestCounterTool(config.get(prefix + ".extensions").split(";"));
	}

}
