package br.com.caelum.revolution.tools.sourcecode;

import org.hibernate.Session;

import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.persistence.ToolThatPersists;
import br.com.caelum.revolution.scm.SCM;
import br.com.caelum.revolution.scm.ToolThatUsesSCM;
import br.com.caelum.revolution.tools.Tool;
import br.com.caelum.revolution.tools.ToolException;


public class SourceCodeRetrieverTool implements Tool, ToolThatPersists,
		ToolThatUsesSCM {

	private Session session;
	private SCM scm;
	private final String[] extensions;

	public SourceCodeRetrieverTool(String[] extensions) {
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
