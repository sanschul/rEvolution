package edu.usp.ime.revolution.tools.jdepend;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.executor.SysCommandExecutor;
import edu.usp.ime.revolution.tools.Tool;
import edu.usp.ime.revolution.tools.SpecificToolFactory;

public class JDependFactory implements SpecificToolFactory {

	public Tool build(Config config, String prefix) {
		return new JDepend(
				new SysCommandExecutor(),  
				new DefaultJDependXMLInterpreter(),
				config.get(prefix + ".jDependPath"));
	}

}
