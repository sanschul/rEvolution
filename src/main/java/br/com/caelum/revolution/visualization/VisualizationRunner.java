package br.com.caelum.revolution.visualization;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.revolution.persistence.HibernatePersistence;
import br.com.caelum.revolution.persistence.VisualizationThatQueries;

public class VisualizationRunner {

	private final HibernatePersistence persistence;
	private final List<Visualization> visualizations;

	public VisualizationRunner(List<Visualization> visualizations, HibernatePersistence persistence) {
		this.visualizations = visualizations;
		this.persistence = persistence;
	}
	
	public List<Visualization> getVisualizations() {
		return visualizations;
	}

	private void checkIfItNeedsPersistence(Visualization visualization) {
		if(visualization instanceof VisualizationThatQueries) {
			VisualizationThatQueries convertedVisualization = (VisualizationThatQueries) visualization;
			convertedVisualization.setSession(persistence.getSession());
		}
	}

	
	public void start() {
		persistence.initMechanism(new ArrayList<Class<?>>());
		persistence.openSession();
		
		for(Visualization visualization : visualizations) {
			checkIfItNeedsPersistence(visualization);
			visualization.export();
		}
		
		persistence.close();
	}
}
