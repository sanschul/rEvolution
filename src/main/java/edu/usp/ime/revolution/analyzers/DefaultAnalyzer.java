package edu.usp.ime.revolution.analyzers;

import java.util.ArrayList;
import java.util.List;

import edu.usp.ime.revolution.analyzers.observers.AnalyzerObserver;
import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.metrics.MetricSet;
import edu.usp.ime.revolution.metrics.MetricSetFactory;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.ChangeSetCollection;
import edu.usp.ime.revolution.tools.MetricTool;

public class DefaultAnalyzer implements Analyzer {

	private final Build sourceBuilder;
	private final List<MetricTool> tools;
	private final MetricSetFactory metricSetFactory;
	private final List<AnalyzerObserver> observers;
	private final List<Error> errors;

	public DefaultAnalyzer(Build build, MetricSetFactory metricSetFactory, List<MetricTool> tools) {
		this.sourceBuilder = build;
		this.metricSetFactory = metricSetFactory;
		this.tools = tools;
		this.observers = new ArrayList<AnalyzerObserver>();
		this.errors = new ArrayList<Error>();
	}

	public void start(ChangeSetCollection collection) {
		for(ChangeSet changeSet : collection) {
			try {
				BuildResult currentBuild = sourceBuilder.build(changeSet);
				MetricSet metricSet = metricSetFactory.setFor(changeSet);
				
				runTools(changeSet, currentBuild, metricSet);
				notifyAll(changeSet, metricSet);
			}
			catch(Exception e) {
				errors.add(new Error(changeSet, e));
			}
		}
	}

	public void addObserver(AnalyzerObserver observer) {
		observers.add(observer);
	}


	public List<Error> getErrors() {
		return errors;
	}

	private void runTools(ChangeSet changeSet, BuildResult currentBuild, MetricSet metricSet) {
		for(MetricTool tool : tools) {
			try {
				tool.calculate(changeSet, currentBuild, metricSet);
			}
			catch(Exception e) {
				errors.add(new Error(tool, changeSet, e));
			}
		}
	}

	private void notifyAll(ChangeSet cs, MetricSet set) {
		for(AnalyzerObserver observer : observers) {
			observer.notify(cs, set);
		}
	}
	
}
