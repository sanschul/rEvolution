package edu.usp.ime.revolution.tools.files;

import java.io.File;

import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.metrics.MetricSet;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.tools.MetricTool;

public class NumberOfFiles implements MetricTool {

	private String extension;

	public NumberOfFiles(String extension) {
		this.extension = extension;
	}

	public void calculate(ChangeSet changeSet, BuildResult current,
			MetricSet set) {
		
		String[] files = new File(changeSet.getPath()).list();
		int qty = 0;
		for(String file : files) {
			if(file.endsWith(extension)) qty++;
		}
		
		set.setMetric(getName(), qty, "N/A", getName());
	}

	public String getName() {
		return "number-of-files";
	}
}
