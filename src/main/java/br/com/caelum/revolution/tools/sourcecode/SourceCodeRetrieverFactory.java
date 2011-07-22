package br.com.caelum.revolution.tools.sourcecode;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.tools.SpecificToolFactory;
import br.com.caelum.revolution.tools.Tool;

public class SourceCodeRetrieverFactory implements SpecificToolFactory {

	public Tool build(Config config, String prefix) {
		return new SourceCodeRetrieverTool(config.get(prefix + ".extensions").split(";"));
	}

}
