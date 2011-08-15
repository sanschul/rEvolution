package br.com.caelum.revolution.visualization;

import java.util.ArrayList;

import br.com.caelum.revolution.persistence.HibernatePersistence;
import br.com.caelum.revolution.persistence.VisualizationThatQueries;

public class VisualizationRunner {

	private final HibernatePersistence persistence;
	private final Visualization visualization;

	public VisualizationRunner(Visualization visualization, HibernatePersistence persistence) {
		this.visualization = visualization;
		this.persistence = persistence;
	}
	
	public Visualization getVisualization() {
		return visualization;
	}

	private void checkIfItNeedsPersistence() {
		if(visualization instanceof VisualizationThatQueries) {
			VisualizationThatQueries convertedVisualization = (VisualizationThatQueries) visualization;
			convertedVisualization.setSession(persistence.getSession());
		}
	}

	
	public void start() {
		persistence.initMechanism(new ArrayList<Class<?>>());
		persistence.openSession();
		checkIfItNeedsPersistence();
		
		visualization.export();
		
		persistence.close();
	}
}
