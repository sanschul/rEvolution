package edu.usp.ime.revolution.analyzers;

import java.util.ArrayList;
import java.util.List;

import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.persistence.HibernatePersistence;
import edu.usp.ime.revolution.persistence.ToolThatPersists;
import edu.usp.ime.revolution.postaction.PostAction;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.ToolThatUsesSCM;
import edu.usp.ime.revolution.scm.changesets.ChangeSet;
import edu.usp.ime.revolution.scm.changesets.ChangeSetCollection;
import edu.usp.ime.revolution.tools.Tool;

public class DefaultAnalyzer implements Analyzer {

	private final Build sourceBuilder;
	private final List<Tool> tools;
	private final List<PostAction> actions;
	private final List<Error> errors;
	private final SCM scm;
	private final HibernatePersistence persistence;

	public DefaultAnalyzer(SCM scm, Build build, List<Tool> tools,
			HibernatePersistence persistence) {
		this.scm = scm;
		this.sourceBuilder = build;
		this.tools = tools;
		this.persistence = persistence;
		this.actions = new ArrayList<PostAction>();
		this.errors = new ArrayList<Error>();
	}

	public void start(ChangeSetCollection collection) {
		startPersistenceEngine();
		giveSessionToTools();
		giveSCMToTools();

		for (ChangeSet changeSet : collection) {
			try {
				persistence.beginTransaction();

				Commit commit = scm.detail(changeSet.getId());
				persistence.getSession().save(commit);

				String path = scm.goTo(changeSet);
				BuildResult currentBuild = sourceBuilder.build(path);

				runTools(commit, currentBuild);
				notifyAll(commit);

				persistence.commit();
			} catch (Exception e) {
				errors.add(new Error(changeSet, e));
			}
		}

		persistence.end();
	}

	private void giveSCMToTools() {
		for (Tool tool : tools) {
			if (tool instanceof ToolThatUsesSCM) {
				ToolThatUsesSCM x = (ToolThatUsesSCM) tool;
				x.setSCM(scm);
			}
		}
	}

	private void startPersistenceEngine() {
		List<Class<?>> classes = new ArrayList<Class<?>>();

		for (Tool tool : tools) {
			if (tool instanceof ToolThatPersists) {
				ToolThatPersists x = (ToolThatPersists) tool;
				for (Class<?> clazz : x.classesToPersist()) {
					classes.add(clazz);
				}
			}
		}

		persistence.initMechanism(classes);
	}

	private void giveSessionToTools() {

		for (Tool tool : tools) {
			if (tool instanceof ToolThatPersists) {
				ToolThatPersists x = (ToolThatPersists) tool;
				x.setSession(persistence.getSession());
			}
		}

	}

	public void addPostAction(PostAction observer) {
		actions.add(observer);
	}

	public List<Error> getErrors() {
		return errors;
	}

	private void runTools(Commit commit, BuildResult currentBuild) {
		for (Tool tool : tools) {
			try {
				tool.calculate(commit, currentBuild);
			} catch (Exception e) {
				errors.add(new Error(tool, commit, e));
			}
		}
	}

	private void notifyAll(Commit commit) {
		for (PostAction action : actions) {
			action.notify(commit);
		}
	}

}
