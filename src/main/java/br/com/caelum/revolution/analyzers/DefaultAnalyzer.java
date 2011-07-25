package br.com.caelum.revolution.analyzers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.revolution.builds.Build;
import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.domain.PersistedCommitConverter;
import br.com.caelum.revolution.persistence.HibernatePersistence;
import br.com.caelum.revolution.persistence.ToolThatPersists;
import br.com.caelum.revolution.scm.CommitData;
import br.com.caelum.revolution.scm.SCM;
import br.com.caelum.revolution.scm.ToolThatUsesSCM;
import br.com.caelum.revolution.scm.changesets.ChangeSet;
import br.com.caelum.revolution.scm.changesets.ChangeSetCollection;
import br.com.caelum.revolution.tools.Tool;

public class DefaultAnalyzer implements Analyzer {

	private final Build sourceBuilder;
	private final List<Tool> tools;
	private final SCM scm;
	private final HibernatePersistence persistence;
	private static Logger log = LoggerFactory.getLogger(DefaultAnalyzer.class);
	private final PersistedCommitConverter convert;

	public DefaultAnalyzer(SCM scm, Build build, List<Tool> tools,
			PersistedCommitConverter convert, HibernatePersistence persistence) {
		this.scm = scm;
		this.sourceBuilder = build;
		this.tools = tools;
		this.convert = convert;
		this.persistence = persistence;
	}

	public void start(ChangeSetCollection collection) {
		startPersistenceEngine();
		giveSCMToTools();

		for (ChangeSet changeSet : collection.get()) {
			try {
				log.info("--------------------------");
				log.info("Starting analyzing changeset " + changeSet.getId());
				CommitData data = scm.detail(changeSet.getId());
				BuildResult currentBuild = sourceBuilder.build(changeSet.getId());

				persistence.beginTransaction();
				Commit commit = convert.toDomain(data, persistence.getSession());
				log.info("Author: " + commit.getAuthor().getName() + " on " + commit.getDate().getTime() + " with " + commit.getArtifacts().size() + " artifacts");

				giveSessionToTools();
				runTools(commit, currentBuild);
				persistence.commit();
			} catch (Exception e) {
				persistence.rollback();
				log.warn("Something went wrong in changeset " + changeSet.getId(), e);
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

	private void runTools(Commit commit, BuildResult currentBuild) {
		for (Tool tool : tools) {
			try {
				log.info("running tool: " + tool.getName());
				tool.calculate(commit, currentBuild);
			} catch (Exception e) {
				log.error("error in tool " + tool.getName(), e);
			}
		}
	}

}
