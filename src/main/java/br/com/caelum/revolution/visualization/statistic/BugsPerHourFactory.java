package br.com.caelum.revolution.visualization.statistic;

import java.io.File;
import java.math.BigInteger;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.visualization.SpecificVisualizationFactory;
import br.com.caelum.revolution.visualization.Visualization;
import br.com.caelum.revolution.visualization.common.BarChart;
import br.com.caelum.revolution.visualization.common.GroupedDataVisualization;

public class BugsPerHourFactory implements SpecificVisualizationFactory{

	public Visualization build(Config config) {
		
		return new GroupedDataVisualization<BigInteger>(
				new BarChart("Bugged Artifacts", "Bugs per Hour", "Quantity", new File(config.asString("file")), 1500, 1500),
				"select convert(hour(c.date), char) name, count(1) qty from bugorigin bo inner join commit c on c.id = bo.buggedCommit_id  group by hour(c.date) order by hour(c.date)");
		
		
		
	}

}
