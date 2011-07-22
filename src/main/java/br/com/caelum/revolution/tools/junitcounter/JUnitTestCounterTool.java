package br.com.caelum.revolution.tools.junitcounter;

import org.hibernate.Session;

import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.domain.Modification;
import br.com.caelum.revolution.persistence.ToolThatPersists;
import br.com.caelum.revolution.tools.Tool;
import br.com.caelum.revolution.tools.ToolException;

public class JUnitTestCounterTool implements Tool, ToolThatPersists {

	private Session session;
	private final String[] extensions;

	public JUnitTestCounterTool(String[] extensions) {
		this.extensions = extensions;
	}

	public Class<?>[] classesToPersist() {
		return new Class<?>[] { JUnitTestQuantity.class };
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void calculate(Commit commit, BuildResult current)
			throws ToolException {

		for (Modification modification : commit.getModifications()) {

			if (matches(modification)) {
				JUnitTestQuantity qty = new JUnitTestQuantity();
				for (String line : modification.getDiff().replace("\r", "")
						.split("\n")) {
					if (line.startsWith("+") && line.contains("@Test")) {
						qty.setTestsAdded(qty.getTestsAdded() + 1);
					}
					if (line.startsWith("-") && line.contains("@Test")) {
						qty.setTestsRemoved(qty.getTestsRemoved() + 1);
					}
				}

				qty.setCommit(commit);
				qty.setArtifact(modification.getArtifact());
				session.save(qty);
			}
		}
	}

	private boolean matches(Modification modification) {
		for (String extension : extensions) {
			if (modification.getArtifact().getName().endsWith(extension))
				return true;
		}
		return false;
	}

	public String getName() {
		return "junit-test-counter";
	}

}
