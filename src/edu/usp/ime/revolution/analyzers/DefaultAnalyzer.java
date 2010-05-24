package edu.usp.ime.revolution.analyzers;

import java.util.List;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.metrics.MetricSet;
import edu.usp.ime.revolution.metrics.MetricStore;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.ChangeSetCollection;
import edu.usp.ime.revolution.tools.MetricTool;

public class DefaultAnalyzer implements Analyzer {

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
			MetricSet metricSet = store.setFor(set);
			
			for(MetricTool tool : tools) {
				tool.calculate(set, current, metricSet);
			}
		}
	}

	public MetricStore getMetricStore() {
		return store;
	}

}
