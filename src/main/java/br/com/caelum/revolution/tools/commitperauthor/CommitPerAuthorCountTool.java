package br.com.caelum.revolution.tools.commitperauthor;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.persistence.ToolThatPersists;
import br.com.caelum.revolution.tools.Tool;
import br.com.caelum.revolution.tools.ToolException;

public class CommitPerAuthorCountTool implements Tool, ToolThatPersists {

	private Session session;

	public Class<?>[] classesToPersist() {
		return new Class<?>[] { CommitPerAuthorCount.class };
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void calculate(Commit commit, BuildResult current)
			throws ToolException {

		for (Artifact artifact : commit.getArtifacts()) {
			CommitPerAuthorCount counter = (CommitPerAuthorCount) session.createCriteria(CommitPerAuthorCount.class)
					.add(Restrictions.eq("author", commit.getAuthor()))
					.add(Restrictions.eq("artifact", artifact)).uniqueResult();
			
			if(counter != null) {
				counter.increaseCount();
			}
			else {
				CommitPerAuthorCount newCounter = new CommitPerAuthorCount(artifact, commit.getAuthor());
				session.save(newCounter);
			}
		}

	}

	public String getName() {
		return "commit-per-author-count";
	}

}
