package br.com.caelum.revolution.visualization.statistic;

import java.io.File;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.visualization.SpecificVisualizationFactory;
import br.com.caelum.revolution.visualization.Visualization;
import br.com.caelum.visualization.common.PieChart;

public class CommitsPerDeveloperFactory implements SpecificVisualizationFactory {

	public Visualization build(Config config) {
		return new CommitsPerDeveloper(new PieChart("Commits per Developer", new File(config.asString("file")), 1500, 1500), config.asInt("threshold"));
	}

}
