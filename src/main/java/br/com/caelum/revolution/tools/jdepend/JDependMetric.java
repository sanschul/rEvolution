package br.com.caelum.revolution.tools.jdepend;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import br.com.caelum.revolution.domain.Commit;


@Entity
public class JDependMetric {

	@Id @GeneratedValue
	private int id;
	@OneToOne
	private Commit commit;
	private String name;
	private double totalClasses;
	private double concreteClasses;
	private double abstractClasses;
	private double ca;
	private double ce;
	private double instability;
	private double abstraction;
	private double distanceFromMainLine;
	private double volatility;

	public JDependMetric(String name) {
		this.name = name;
	}
	
	public JDependMetric() {}

	public void setTotalClasses(double totalClasses) {
		this.totalClasses = totalClasses;
	}

	public double getTotalClasses() {
		return totalClasses;
	}

	public void setConcreteClasses(double concreteClasses) {
		this.concreteClasses = concreteClasses;
	}

	public double getConcreteClasses() {
		return concreteClasses;
	}

	public void setAbstractClasses(double abstractClasses) {
		this.abstractClasses = abstractClasses;
	}

	public double getAbstractClasses() {
		return abstractClasses;
	}

	public void setCa(double ca) {
		this.ca = ca;
	}

	public double getCa() {
		return ca;
	}

	public void setCe(double ce) {
		this.ce = ce;
	}

	public double getCe() {
		return ce;
	}

	public void setInstability(double instability) {
		this.instability = instability;
	}

	public double getInstability() {
		return instability;
	}

	public void setAbstraction(double abstraction) {
		this.abstraction = abstraction;
	}

	public double getAbstraction() {
		return abstraction;
	}

	public void setDistanceFromMainLine(double distanceFromMainLine) {
		this.distanceFromMainLine = distanceFromMainLine;
	}

	public double getDistanceFromMainLine() {
		return distanceFromMainLine;
	}

	public void setVolatility(double volatility) {
		this.volatility = volatility;
	}

	public double getVolatility() {
		return volatility;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Commit getCommit() {
		return commit;
	}

	public void setCommit(Commit commit) {
		this.commit = commit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
