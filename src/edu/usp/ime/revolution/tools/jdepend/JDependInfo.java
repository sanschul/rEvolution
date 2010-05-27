package edu.usp.ime.revolution.tools.jdepend;

public class JDependInfo {

	private final String name;
	private int totalClasses;
	private int concreteClasses;
	private int abstractClasses;
	private int ca;
	private int ce;
	private int instability;
	private int abstraction;
	private int distanceFromMainLine;
	private int volatility;

	public JDependInfo(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setTotalClasses(int totalClasses) {
		this.totalClasses = totalClasses;
	}

	public int getTotalClasses() {
		return totalClasses;
	}

	public void setConcreteClasses(int concreteClasses) {
		this.concreteClasses = concreteClasses;
	}

	public int getConcreteClasses() {
		return concreteClasses;
	}

	public void setAbstractClasses(int abstractClasses) {
		this.abstractClasses = abstractClasses;
	}

	public int getAbstractClasses() {
		return abstractClasses;
	}

	public void setCa(int ca) {
		this.ca = ca;
	}

	public int getCa() {
		return ca;
	}

	public void setCe(int ce) {
		this.ce = ce;
	}

	public int getCe() {
		return ce;
	}

	public void setInstability(int instability) {
		this.instability = instability;
	}

	public int getInstability() {
		return instability;
	}

	public void setAbstraction(int abstraction) {
		this.abstraction = abstraction;
	}

	public int getAbstraction() {
		return abstraction;
	}

	public void setDistanceFromMainLine(int distanceFromMainLine) {
		this.distanceFromMainLine = distanceFromMainLine;
	}

	public int getDistanceFromMainLine() {
		return distanceFromMainLine;
	}

	public void setVolatility(int volatility) {
		this.volatility = volatility;
	}

	public int getVolatility() {
		return volatility;
	}

}
