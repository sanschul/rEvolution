package br.com.caelum.revolution.tools.diffwordcount;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.tools.SpecificToolFactory;
import br.com.caelum.revolution.tools.Tool;

public class DiffWordCountFactory implements SpecificToolFactory{

	public Tool build(Config config) {
		return new DiffWordCountTool(extensions(config), patterns(config));
	}

	private String[] patterns(Config config) {
		return config.get("patterns").split(";");
	}
	
	private String[] extensions(Config config) {
		return config.get("extensions").split(";");
	}

}
