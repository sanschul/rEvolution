package br.com.caelum.revolution.visualization;

import br.com.caelum.revolution.config.Config;

public class FakeVisualizationFactory implements SpecificVisualizationFactory{

	public Visualization build(Config config) {
		return new FakeVisualization();
	}

}
