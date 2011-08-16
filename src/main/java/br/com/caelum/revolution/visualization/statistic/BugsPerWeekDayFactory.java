package br.com.caelum.revolution.visualization.statistic;

import java.io.File;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.visualization.SpecificVisualizationFactory;
import br.com.caelum.revolution.visualization.Visualization;
import br.com.caelum.revolution.visualization.common.BarChart;
import br.com.caelum.revolution.visualization.common.ThresholdGroupedDataVisualization;

public class BugsPerWeekDayFactory implements SpecificVisualizationFactory {

	public Visualization build(Config config) {

		return new ThresholdGroupedDataVisualization(
				new BarChart("Bugged Artifacts", "Bugs per Week Day", "Quantity", new File(config.asString("file")), 1500, 1500),
				7,
				"select dayname(c.date) name, count(1) qty from bugorigin bo inner join commit c on c.id = bo.buggedCommit_id group by dayname(c.date) order by dayofweek(c.date)");

	}

}
