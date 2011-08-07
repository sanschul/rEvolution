package br.com.caelum.revolution.tools.diffwordcount;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.tools.SpecificToolFactory;
import br.com.caelum.revolution.tools.Tool;

public class DiffWordCountFactory implements SpecificToolFactory{

	public Tool build(Config config, String prefix) {
		return new DiffWordCountTool(extensions(config, prefix), patterns(config, prefix));
	}

	private String[] patterns(Config config, String prefix) {
		return config.get(prefix + ".patterns").split(";");
	}
	
	private String[] extensions(Config config, String prefix) {
		return config.get(prefix + ".extensions").split(";");
	}

}
