package edu.usp.ime.revolution.tools;

import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.metrics.MetricSet;
import edu.usp.ime.revolution.scm.ChangeSet;

public interface MetricTool {

	void calculate(ChangeSet changeSet, BuildResult current, MetricSet set) throws ToolException;
	String getName();

}
