package edu.usp.ime.revolution.tools.sourcecode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import edu.usp.ime.revolution.domain.Artifact;

@Entity
public class SourceCode {
	@Id
	@GeneratedValue
	private int id;
	@ManyToOne
	private Artifact artifact;
	@Type(type="text")
	private String source;
	
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}

	
}
