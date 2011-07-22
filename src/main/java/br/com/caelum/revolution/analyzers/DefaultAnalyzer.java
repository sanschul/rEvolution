package br.com.caelum.revolution.analyzers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.revolution.builds.Build;
import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.domain.Modification;
import br.com.caelum.revolution.persistence.HibernatePersistence;
import br.com.caelum.revolution.persistence.ToolThatPersists;
import br.com.caelum.revolution.postaction.PostAction;
import br.com.caelum.revolution.scm.CommitData;
import br.com.caelum.revolution.scm.DiffData;
import br.com.caelum.revolution.scm.SCM;
import br.com.caelum.revolution.scm.ToolThatUsesSCM;
import br.com.caelum.revolution.scm.changesets.ChangeSet;
import br.com.caelum.revolution.scm.changesets.ChangeSetCollection;
import br.com.caelum.revolution.tools.Tool;

public class DefaultAnalyzer implements Analyzer {

	private final Build sourceBuilder;
	private final List<Tool> tools;
	private final List<PostAction> actions;
	private final List<Error> errors;
	private final SCM scm;
	private final HibernatePersistence persistence;
	private static Logger log = LoggerFactory.getLogger(DefaultAnalyzer.class);

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

				CommitData data = scm.detail(changeSet.getId());
				Commit commit = transform(data);

				String path = scm.goTo(changeSet);
				BuildResult currentBuild = sourceBuilder.build(path);

				runTools(commit, currentBuild);
				notifyAll(commit);

				persistence.commit();
			} catch (Exception e) {
				log.warn(
						"Something happened in changeset " + changeSet.getId(),
						e);
				errors.add(new Error(changeSet, e));
			}
		}

		persistence.end();
	}

	private Commit transform(CommitData data) throws ParseException {
		Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(data
				.getDate());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		Commit commit = new Commit(data.getCommitId(), data.getAuthor(),
				data.getEmail(), calendar, data.getMessage(), data.getDiff());
		persistence.getSession().save(commit);
		
		for (DiffData diff : data.getDiffs()) {
			Artifact artifact = (Artifact) persistence.getSession()
					.createCriteria(Artifact.class)
					.add(Restrictions.eq("name", diff.getName()))
					.uniqueResult();

			if (artifact == null) {
				artifact = new Artifact(diff.getName(), diff.getArtifactKind());
				persistence.getSession().save(artifact);
			}
			Modification modification = new Modification(diff.getDiff(), commit, artifact, diff.getModificationKind());
			artifact.addModification(modification);
			commit.addModification(modification);
			persistence.getSession().save(modification);
		}

		return commit;
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
