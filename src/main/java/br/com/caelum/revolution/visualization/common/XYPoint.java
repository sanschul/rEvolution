package br.com.caelum.revolution.visualization.common;

import java.math.BigDecimal;

public class XYPoint {

	private String label;
	private BigDecimal x;
	private BigDecimal y;

	public String getLabel() {
		return label;
	}

	public BigDecimal getX() {
		return x;
	}

	public BigDecimal getY() {
		return y;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setX(BigDecimal x) {
		this.x = x;
	}

	public void setY(BigDecimal y) {
		this.y = y;
	}
	
	
}
