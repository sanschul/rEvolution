package br.com.caelum.revolution.tools.bugorigin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.domain.Modification;


@Entity
public class BugOrigin {

	@Id @GeneratedValue
	private int id;
	@ManyToOne
	private Modification modification;
	@ManyToOne
	private Commit buggedCommit;
	
	public BugOrigin(Commit buggedCommit, Modification modification) {
		this.buggedCommit = buggedCommit;
		this.modification = modification;
	}
	
	public BugOrigin() {}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Commit getBuggedCommit() {
		return buggedCommit;
	}
	public void setBuggedCommit(Commit buggedCommit) {
		this.buggedCommit = buggedCommit;
	}
	public Modification getModification() {
		return modification;
	}
	public void setModification(Modification modification) {
		this.modification = modification;
	}
	
	
}
