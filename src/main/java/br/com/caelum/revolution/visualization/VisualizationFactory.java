package br.com.caelum.revolution.visualization;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.persistence.HibernatePersistence;

public class VisualizationFactory {
	
	public VisualizationRunner basedOn(Config config, String visualizationName) {
		Visualization visualization = getSpecificFactoryFor(visualizationName).build(config);
		
		return new VisualizationRunner(visualization, new HibernatePersistence(config));
	}

	private SpecificVisualizationFactory getSpecificFactoryFor(
			String visualization) {

		try {
			Class<?> factoryClass = (Class<?>) Class
					.forName("br.com.caelum.revolution.visualization."
							+ visualization);
			return (SpecificVisualizationFactory) factoryClass.newInstance();
		} catch (Exception e) {
			throw new VisualizationNotFoundException(e);
		}
	}

}
