package br.com.caelum.revolution.visualization.common;


public class GroupedDataTuple<T extends Number> {

	private T qty;
	private String name;
	
	public T getQty() {
		return qty;
	}
	
	public String getName() {
		return name;
	}
	public void setQty(T qty) {
		this.qty = qty;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
