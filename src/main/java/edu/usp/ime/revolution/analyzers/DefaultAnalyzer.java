package edu.usp.ime.revolution.analyzers;

import java.util.ArrayList;
import java.util.List;

import edu.usp.ime.revolution.analyzers.observers.AnalyzerObserver;
import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.scm.ChangeSetCollection;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.tools.MetricTool;

public class DefaultAnalyzer implements Analyzer {

	private final Build sourceBuilder;
	private final List<MetricTool> tools;
	private final List<AnalyzerObserver> observers;
	private final List<Error> errors;
	private final SCM scm;

	public DefaultAnalyzer(SCM scm, Build build, List<MetricTool> tools) {
		this.scm = scm;
		this.sourceBuilder = build;
		this.tools = tools;
		this.observers = new ArrayList<AnalyzerObserver>();
		this.errors = new ArrayList<Error>();
	}

	public void start(ChangeSetCollection collection) {
		for(ChangeSet changeSet : collection) {
			try {
				Commit commit = scm.detail(changeSet.getInfo().getId());
				BuildResult currentBuild = sourceBuilder.build(changeSet);
				
				runTools(changeSet, currentBuild);
				notifyAll(changeSet);
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

	private void runTools(ChangeSet changeSet, BuildResult currentBuild) {
		for(MetricTool tool : tools) {
			try {
				tool.calculate(changeSet, currentBuild);
			}
			catch(Exception e) {
				errors.add(new Error(tool, changeSet, e));
			}
		}
	}

	private void notifyAll(ChangeSet cs) {
		for(AnalyzerObserver observer : observers) {
			observer.notify(cs);
		}
	}
	
}
