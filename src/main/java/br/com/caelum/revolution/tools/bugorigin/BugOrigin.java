package br.com.caelum.revolution.tools.bugorigin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.Commit;


@Entity
public class BugOrigin {

	@Id @GeneratedValue
	private int id;
	@ManyToOne
	private Artifact fixedArtifact;
	@ManyToOne
	private Commit buggedCommit;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Artifact getFixedArtifact() {
		return fixedArtifact;
	}
	public void setFixedArtifact(Artifact fixedArtifact) {
		this.fixedArtifact = fixedArtifact;
	}
	public Commit getBuggedCommit() {
		return buggedCommit;
	}
	public void setBuggedCommit(Commit buggedCommit) {
		this.buggedCommit = buggedCommit;
	}
	
	
}
