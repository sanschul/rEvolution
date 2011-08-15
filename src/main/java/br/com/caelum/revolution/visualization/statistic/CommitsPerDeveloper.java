package br.com.caelum.revolution.visualization.statistic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.caelum.revolution.persistence.VisualizationThatQueries;
import br.com.caelum.revolution.visualization.Visualization;
import br.com.caelum.visualization.common.KeyValueChartExporter;

public class CommitsPerDeveloper implements Visualization,
		VisualizationThatQueries {

	private Session session;
	private final KeyValueChartExporter chart;
	private final int threshold;

	public CommitsPerDeveloper(KeyValueChartExporter chart, int threshold) {
		this.chart = chart;
		this.threshold = threshold;
	}

	@SuppressWarnings("unchecked")
	public void export() {
		Query query = session
				.createSQLQuery(
						"select a.name, count(1) qty from author a inner join commit c on c.author_id = a.id group by a.name order by qty desc")
				.setResultTransformer(Transformers.aliasToBean(CommitPerDeveloperTuple.class)).setMaxResults(threshold);
		List<CommitPerDeveloperTuple> results = query.list();

		chart.build(convertTo(results));
	}

	private Map<Object, Double> convertTo(List<CommitPerDeveloperTuple> results) {

		Map<Object, Double> map = new HashMap<Object, Double>();

		for (CommitPerDeveloperTuple tuple : results) {
			map.put(tuple.getName(), new Double(tuple.getQty()));
		}

		return map;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
