package edu.usp.ime.revolution.tools.files;

import java.io.File;

import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.metrics.MetricSet;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.tools.MetricTool;

public class NumberOfFiles implements MetricTool {

	public void calculate(ChangeSet changeSet, BuildResult current,
			MetricSet set) {
		
		int qty = new File(changeSet.getPath()).list().length;
		set.setMetric("number-of-files", qty);
	}

	public String getName() {
		return "number-of-files";
	}

}
