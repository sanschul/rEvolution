package br.com.caelum.revolution.visualization.evolution;

import java.math.BigInteger;

public class WordDiffInCommit {

	private BigInteger added;
	private BigInteger removed;
	private int id;
	public BigInteger getAdded() {
		return added;
	}
	public void setAdded(BigInteger added) {
		this.added = added;
	}
	public BigInteger getRemoved() {
		return removed;
	}
	public void setRemoved(BigInteger removed) {
		this.removed = removed;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
