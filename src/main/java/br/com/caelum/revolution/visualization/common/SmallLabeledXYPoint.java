package br.com.caelum.revolution.visualization.common;

public class SmallLabeledXYPoint extends XYPoint{

	public String getLabel() {
		String label = super.getLabel();
		String[] splittedLabel = label.split("/");
		
		return splittedLabel[splittedLabel.length - 1];
	}
}
