package edu.usp.ime.revolution.analyzers;

import java.util.ArrayList;
import java.util.List;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.postaction.PostAction;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.changesets.ChangeSet;
import edu.usp.ime.revolution.scm.changesets.ChangeSetCollection;
import edu.usp.ime.revolution.tools.Tool;

public class DefaultAnalyzer implements Analyzer {

	private final Build sourceBuilder;
	private final List<Tool> tools;
	private final List<PostAction> actions;
	private final List<Error> errors;
	private final SCM scm;

	public DefaultAnalyzer(SCM scm, Build build, List<Tool> tools) {
		this.scm = scm;
		this.sourceBuilder = build;
		this.tools = tools;
		this.actions = new ArrayList<PostAction>();
		this.errors = new ArrayList<Error>();
	}

	public void start(ChangeSetCollection collection) {
		for(ChangeSet changeSet : collection) {
			try {
				Commit commit = scm.detail(changeSet.getId());
				
				String path = scm.goTo(changeSet);
				BuildResult currentBuild = sourceBuilder.build(path);
				
				runTools(commit, currentBuild);
				notifyAll(commit);
			}
			catch(Exception e) {
				errors.add(new Error(changeSet, e));
			}
		}
	}

	public void addObserver(PostAction observer) {
		actions.add(observer);
	}


	public List<Error> getErrors() {
		return errors;
	}

	private void runTools(Commit commit, BuildResult currentBuild) {
		for(Tool tool : tools) {
			try {
				tool.calculate(commit, currentBuild);
			}
			catch(Exception e) {
				errors.add(new Error(tool, commit, e));
			}
		}
	}

	private void notifyAll(Commit commit) {
		for(PostAction action : actions) {
			action.notify(commit);
		}
	}
	
}
