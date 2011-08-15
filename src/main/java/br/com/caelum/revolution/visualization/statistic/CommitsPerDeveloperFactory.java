package br.com.caelum.revolution.visualization.statistic;

import java.io.File;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.visualization.SpecificVisualizationFactory;
import br.com.caelum.revolution.visualization.Visualization;
import br.com.caelum.revolution.visualization.common.GroupedDataVisualization;
import br.com.caelum.revolution.visualization.common.PieChart;

public class CommitsPerDeveloperFactory implements SpecificVisualizationFactory {

	public Visualization build(Config config) {
		return new GroupedDataVisualization(
				new PieChart("Commits per Developer", new File(config.asString("file")), 1500, 1500), 
				config.asInt("threshold"),
				"select a.name, count(1) qty from author a inner join commit c on c.author_id = a.id group by a.name order by qty desc");
	}
}
