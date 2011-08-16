package br.com.caelum.revolution.visualization.statistic;

import java.io.File;
import java.math.BigInteger;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.visualization.SpecificVisualizationFactory;
import br.com.caelum.revolution.visualization.Visualization;
import br.com.caelum.revolution.visualization.common.BarChart;
import br.com.caelum.revolution.visualization.common.GroupedDataVisualization;

public class BugsPerWeekDayFactory implements SpecificVisualizationFactory {

	public Visualization build(Config config) {

		return new GroupedDataVisualization<BigInteger>(
				new BarChart("Bugged Artifacts", "Bugs per Week Day", "Quantity", new File(config.asString("file")), 1500, 1500),
				"select dayname(x.date) name, count(1) qty from ( select distinct bo.buggedCommit_id, c.date from bugorigin bo inner join modification m on m.id = bo.modification_id inner join commit c on c.id = bo.buggedCommit_id ) x group by dayname(x.date) order by dayofweek(x.date)");

	}

}
