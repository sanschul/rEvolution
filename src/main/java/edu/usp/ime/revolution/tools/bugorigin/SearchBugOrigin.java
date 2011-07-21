package edu.usp.ime.revolution.tools.bugorigin;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.domain.Artifact;
import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.persistence.ToolThatPersists;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.ToolThatUsesSCM;
import edu.usp.ime.revolution.tools.Tool;
import edu.usp.ime.revolution.tools.ToolException;

public class SearchBugOrigin implements Tool, ToolThatPersists, ToolThatUsesSCM {

	private SCM scm;
	private Session session;
	private Set<String> commitsAlreadyAdded;
	private final String[] keywords;

	public SearchBugOrigin(String[] keywords) {
		this.keywords = keywords;
	}

	public void setSCM(SCM scm) {
		this.scm = scm;
	}

	public Class<?>[] classesToPersist() {
		return new Class<?>[] { BugOrigin.class };
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void calculate(Commit commit, BuildResult current)
			throws ToolException {
		commitsAlreadyAdded = new HashSet<String>();
		
		if(!noKeywordsIn(commit)) return;
		
		for (Artifact artifact : commit.getArtifacts()) {
			String[] lines = artifact.getDiff().replace("\r", "").split("\n");

			for (int i = 0; i < lines.length; i++) {
				if (isRemoved(lines[i])) {
					String hash = scm.blameCurrent(artifact.getName(), i + 1);
					if (!commitsAlreadyAdded.contains(hash)) {
						
						Commit buggedCommit = (Commit) session
								.createCriteria(Commit.class)
								.add(Restrictions.eq("commitId", hash))
								.uniqueResult();

						BugOrigin origin = new BugOrigin();
						origin.setBuggedCommit(buggedCommit);
						origin.setFixedArtifact(artifact);
						session.save(origin);

						commitsAlreadyAdded.add(hash);
					}
				}
			}
		}
	}

	private boolean noKeywordsIn(Commit commit) {
		for (String keyword : keywords) {
			if(commit.getMessage().contains(keyword)) return true;
		}
		return false;
	}

	private boolean isRemoved(String line) {
		return line.startsWith("-");
	}

	public String getName() {
		return "search-bug-origin";
	}

}
