package br.com.caelum.revolution.visualization;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.config.PrefixedConfig;
import br.com.caelum.revolution.persistence.HibernatePersistence;

public class VisualizationFactory {
	
	public VisualizationRunner basedOn(Config config) {
		
		List<Visualization> all = new ArrayList<Visualization>();
		
		int counter = 1;
		while(config.contains(visualizationConfigName(counter))) {
			String visualizationName = config.asString(visualizationConfigName(counter));
			all.add(getSpecificFactoryFor(visualizationName).build(new PrefixedConfig(config, visualizationConfigName(counter))));
			
			counter++;
		}

		return new VisualizationRunner(all, new HibernatePersistence(config));
	}
	
	private String visualizationConfigName(int counter) {
		return "visualizations." + counter;
	}

	private SpecificVisualizationFactory getSpecificFactoryFor(
			String visualization) {

		try {
			Class<?> factoryClass = (Class<?>) Class.forName(visualization);
			return (SpecificVisualizationFactory) factoryClass.newInstance();
		} catch (Exception e) {
			throw new VisualizationNotFoundException(e);
		}
	}

}
