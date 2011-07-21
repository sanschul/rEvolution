package edu.usp.ime.revolution.tools.lineschanged;

import org.hibernate.Session;

import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.domain.Artifact;
import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.persistence.ToolThatPersists;
import edu.usp.ime.revolution.tools.Tool;
import edu.usp.ime.revolution.tools.ToolException;

public class NumberOfLinesChanged implements Tool, ToolThatPersists {

	private Session session;

	public void calculate(Commit commit, BuildResult current)
			throws ToolException {
		
		for (Artifact artifact : commit.getArtifacts()) {
			String diff = artifact.getDiff();
			
			LinesChangedCount stat = new LinesChangedCount();
			stat.setArtifact(artifact);
			
			for(String line : diff.replace("\r", "").split("\n")) {
				if(line.startsWith("+")) {
					stat.setLinesAdded(stat.getLinesAdded()+1);
				}
				if(line.startsWith("-")) {
					stat.setLinesRemoved(stat.getLinesRemoved()+1);
				}
			}
			
			session.save(stat);
		}
		
	}

	public String getName() {
		return "numberOfLinesChanged";
	}

	public Class<?>[] classesToPersist() {
		return new Class<?>[] { LinesChangedCount.class } ;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
