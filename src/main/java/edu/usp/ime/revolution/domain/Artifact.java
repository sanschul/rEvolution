package edu.usp.ime.revolution.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class Artifact {

	@Id @GeneratedValue
	private int id;
	
	private String name;
	@Type(type="text")
	private String content;
	@Enumerated(EnumType.STRING)
	private ArtifactStatus status;
	@ManyToOne
	private Commit commit;
	
	public Artifact(String name, String content, ArtifactStatus status) {
		this.name = name;
		this.content = content;
		this.status = status;
	}
	
	public Artifact(){}
	public String getName() {
		return name;
	}
	public String getContent() {
		return content;
	}
	public ArtifactStatus getStatus() {
		return status;
	}
	public Commit getCommit() {
		return commit;
	}
	public void setCommit(Commit commit) {
		this.commit = commit;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setStatus(ArtifactStatus status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
