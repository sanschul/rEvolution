package edu.usp.ime.revolution.metrics;

public class Metric {

	private final String name;
	private final double value;
	private final String target;
	private final String tool;
	
	public static final String PROJECT_LEVEL = "project";
	
	public Metric(String name, double value, String target, String tool) {
		this.name = name;
		this.value = value;
		this.target = target;
		this.tool = tool;
	}

	public String getName() {
		return name;
	}

	public double getValue() {
		return value;
	}

	public String getTarget() {
		return target;
	}

	public String getTool() {
		return tool;
	}
}
