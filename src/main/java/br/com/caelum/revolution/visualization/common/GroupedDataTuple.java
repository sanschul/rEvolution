package br.com.caelum.revolution.visualization.common;

import java.math.BigInteger;

public class GroupedDataTuple {

	private BigInteger qty;
	private String name;
	
	public int getQty() {
		return qty.intValue();
	}
	public String getName() {
		return name;
	}
	public void setQty(BigInteger qty) {
		this.qty = qty;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
