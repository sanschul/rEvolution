package edu.usp.ime.revolution.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

@Entity
public class Commit {
	@Id @GeneratedValue
	private int id;
	
	private String commitId;
	private String author;
	private String email;
	private String date;
	private String message;
	@Type(type="text")
	private String diff;

	public String getCommitId() {
		return commitId;
	}

	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDiff() {
		return diff;
	}

	public void setDiff(String diff) {
		this.diff = diff;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
