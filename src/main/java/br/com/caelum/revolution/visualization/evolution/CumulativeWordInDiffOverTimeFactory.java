package br.com.caelum.revolution.visualization.evolution;

import java.io.File;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.visualization.SpecificVisualizationFactory;
import br.com.caelum.revolution.visualization.Visualization;
import br.com.caelum.revolution.visualization.common.BarChart;
import br.com.caelum.revolution.visualization.common.MapToDataSetConverter;

public class CumulativeWordInDiffOverTimeFactory implements SpecificVisualizationFactory{

	public Visualization build(Config config) {
		return new CumulativeWordInDiffOverTime(config.asString("name"), 
				new BarChart("Cumulative Appearance of " + config.asString("name"), "Commits", "Quantity", new File(config.asString("file")), 1500, 1500, new MapToDataSetConverter()));
	}

}
