package edu.usp.ime.revolution.tools.jdepend;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.executor.SimpleCommandExecutor;
import edu.usp.ime.revolution.tools.SpecificToolFactory;
import edu.usp.ime.revolution.tools.Tool;

public class JDependFactory implements SpecificToolFactory {

	public Tool build(Config config, String prefix) {
		return new JDepend(
				new SimpleCommandExecutor(),  
				new DefaultJDependXMLInterpreter(),
				config.get(prefix + ".jDependPath"));
	}

}
