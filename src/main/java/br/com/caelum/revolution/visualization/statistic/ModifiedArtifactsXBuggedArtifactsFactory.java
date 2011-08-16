package br.com.caelum.revolution.visualization.statistic;

import java.io.File;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.visualization.SpecificVisualizationFactory;
import br.com.caelum.revolution.visualization.Visualization;
import br.com.caelum.revolution.visualization.common.ScatterPlot;

public class ModifiedArtifactsXBuggedArtifactsFactory implements
		SpecificVisualizationFactory {

	public Visualization build(Config config) {
		return new ModifiedArtifactsXBuggedArtifacts(
				new ScatterPlot("Modifications x Number of Bugs", "Number of Bugs", "Artifact", new File(config.asString("file")), 1500, 1500),
				config.asInt("threshold"));
	}
}
