package br.com.caelum.revolution.tools.commitperauthor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.Author;

@Entity
public class CommitPerAuthorCount {

	@Id @GeneratedValue
	private int id;
	@OneToOne
	private Artifact artifact;
	@OneToOne
	private Author author;
	private int count;
	
	protected CommitPerAuthorCount() {}
	
	public CommitPerAuthorCount(Artifact artifact, Author author) {
		this.artifact = artifact;
		this.author = author;
		this.count=1;
	}
	
	public Artifact getArtifact() {
		return artifact;
	}
	public void setArtifact(Artifact artifact) {
		this.artifact = artifact;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public int getId() {
		return id;
	}
	public int getCount() {
		return count;
	}
	public void increaseCount() {
		count++;
	}
	
	
}
