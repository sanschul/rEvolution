package br.com.caelum.revolution.tools.junitcounter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.Commit;

@Entity
public class JUnitTestQuantity {

	@Id @GeneratedValue
	private int id;
	@ManyToOne
	private Commit commit;
	@ManyToOne
	private Artifact artifact;
	private int testsAdded;
	private int testsRemoved;
	
	public Commit getCommit() {
		return commit;
	}
	public void setCommit(Commit commit) {
		this.commit = commit;
	}
	public Artifact getArtifact() {
		return artifact;
	}
	public void setArtifact(Artifact artifact) {
		this.artifact = artifact;
	}
	public int getTestsAdded() {
		return testsAdded;
	}
	public void setTestsAdded(int testsAdded) {
		this.testsAdded = testsAdded;
	}
	public int getTestsRemoved() {
		return testsRemoved;
	}
	public void setTestsRemoved(int testsRemoved) {
		this.testsRemoved = testsRemoved;
	}
	public int getId() {
		return id;
	}
	
	
}
