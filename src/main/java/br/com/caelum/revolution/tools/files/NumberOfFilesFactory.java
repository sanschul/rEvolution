package br.com.caelum.revolution.tools.files;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.tools.SpecificToolFactory;
import br.com.caelum.revolution.tools.Tool;

public class NumberOfFilesFactory implements SpecificToolFactory {

	public Tool build(Config config, String prefix) {
		return new NumberOfFiles(config.get(prefix + ".extension"));
	}

}
