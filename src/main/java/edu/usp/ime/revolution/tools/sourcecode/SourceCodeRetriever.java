package edu.usp.ime.revolution.tools.sourcecode;

import org.hibernate.Session;

import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.domain.Artifact;
import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.persistence.ToolThatPersists;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.ToolThatUsesSCM;
import edu.usp.ime.revolution.tools.Tool;
import edu.usp.ime.revolution.tools.ToolException;

public class SourceCodeRetriever implements Tool, ToolThatPersists,
		ToolThatUsesSCM {

	private Session session;
	private SCM scm;
	private final String[] extensions;

	public SourceCodeRetriever(String[] extensions) {
		this.extensions = extensions;
	}

	public Class<?>[] classesToPersist() {
		return new Class<?>[] { SourceCode.class };
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void calculate(Commit commit, BuildResult current)
			throws ToolException {

		for (Artifact artifact : commit.getArtifacts()) {
			if (matches(artifact.getName())) {
				String code = scm.sourceOf(commit.getCommitId(),
						artifact.getName());

				SourceCode sourceCode = new SourceCode();
				sourceCode.setSource(code);
				sourceCode.setArtifact(artifact);
				session.save(sourceCode);
			}
		}

	}

	private boolean matches(String name) {
		for (String extension : extensions) {
			if (name.endsWith(extension))
				return true;
		}
		return false;
	}

	public String getName() {
		return "sourcecode";
	}

	public void setSCM(SCM scm) {
		this.scm = scm;
	}

}
