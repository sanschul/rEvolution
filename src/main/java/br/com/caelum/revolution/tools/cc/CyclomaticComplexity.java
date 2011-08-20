package br.com.caelum.revolution.tools.cc;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.Commit;

@Entity
public class CyclomaticComplexity {

	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	private Artifact artifact;
	@ManyToOne
	private Commit commit;
	private int cc;

	public Artifact getArtifact() {
		return artifact;
	}

	public void setArtifact(Artifact artifact) {
		this.artifact = artifact;
	}

	public Commit getCommit() {
		return commit;
	}

	public void setCommit(Commit commit) {
		this.commit = commit;
	}

	public int getCc() {
		return cc;
	}

	public void setCc(int cc) {
		this.cc = cc;
	}

	public int getId() {
		return id;
	}

}
