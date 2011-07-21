package br.com.caelum.revolution.tools.lineschanged;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.caelum.revolution.domain.Artifact;


@Entity
public class LinesChangedCount {

	@Id @GeneratedValue
	private int id;
	@ManyToOne
	private Artifact artifact;
	private int linesAdded;
	private int linesRemoved;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Artifact getArtifact() {
		return artifact;
	}
	public void setArtifact(Artifact artifact) {
		this.artifact = artifact;
	}
	public int getLinesAdded() {
		return linesAdded;
	}
	public void setLinesAdded(int linesAdded) {
		this.linesAdded = linesAdded;
	}
	public int getLinesRemoved() {
		return linesRemoved;
	}
	public void setLinesRemoved(int linesRemoved) {
		this.linesRemoved = linesRemoved;
	}
	
	
}
