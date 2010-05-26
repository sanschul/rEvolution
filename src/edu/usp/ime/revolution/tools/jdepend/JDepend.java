package edu.usp.ime.revolution.tools.jdepend;

import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.metrics.MetricSet;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.tools.MetricTool;

public class JDepend implements MetricTool {

	public JDepend(CommandExecutor executor, String jDependPath) {
		
	}

	public void calculate(ChangeSet changeSet, BuildResult current,
			MetricSet set) {
		// TODO Auto-generated method stub		
	}

	public String getName() {
		return "jdepend";
	}
}
