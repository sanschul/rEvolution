package br.com.caelum.revolution.tools.files;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.caelum.revolution.domain.Commit;


@Entity
public class NumberOfFilesPerCommit {

	@Id @GeneratedValue
	private int id;
	private int qty;
	@ManyToOne
	private Commit commit;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public Commit getCommit() {
		return commit;
	}
	public void setCommit(Commit commit) {
		this.commit = commit;
	}
	
	
}
