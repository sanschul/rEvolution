package br.com.caelum.revolution.tools.diffwordcount;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.Commit;

@Entity
public class DiffWordCount {

	@Id @GeneratedValue
	private int id;
	@ManyToOne
	private Commit commit;
	@ManyToOne
	private Artifact artifact;
	private int added;
	private int removed;
	private String pattern;
	
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
	public int getAdded() {
		return added;
	}
	public void setAdded(int added) {
		this.added = added;
	}
	public int getRemoved() {
		return removed;
	}
	public void setRemoved(int removed) {
		this.removed = removed;
	}
	public int getId() {
		return id;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	
}
