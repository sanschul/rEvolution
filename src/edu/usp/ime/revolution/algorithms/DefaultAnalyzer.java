package edu.usp.ime.revolution.algorithms;

import java.util.List;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.metrics.MetricStore;
import edu.usp.ime.revolution.metrics.MetricTool;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.ChangeSetCollection;

public class DefaultAnalyzer {

	private final Build build;
	private final List<MetricTool> tools;
	private final MetricStore store;

	public DefaultAnalyzer(Build build, MetricStore store, List<MetricTool> tools) {
		this.build = build;
		this.store = store;
		this.tools = tools;
	}

	public void start(ChangeSetCollection collection) {
		for(ChangeSet set : collection) {
			BuildResult current = build.build(set);
			
			for(MetricTool tool : tools) {
				tool.calculate(set, current, store.buildSet(tool.getName()));
			}
		}
		
	}

}
