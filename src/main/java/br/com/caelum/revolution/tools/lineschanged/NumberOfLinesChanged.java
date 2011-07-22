package br.com.caelum.revolution.tools.lineschanged;

import org.hibernate.Session;

import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.domain.Modification;
import br.com.caelum.revolution.persistence.ToolThatPersists;
import br.com.caelum.revolution.tools.Tool;
import br.com.caelum.revolution.tools.ToolException;


public class NumberOfLinesChanged implements Tool, ToolThatPersists {

	private Session session;

	public void calculate(Commit commit, BuildResult current)
			throws ToolException {
		
		for (Modification modification : commit.getModifications()) {
			String diff = modification.getDiff();
			
			LinesChangedCount stat = new LinesChangedCount();
			stat.setArtifact(modification.getArtifact());
			stat.setCommit(commit);
			
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
