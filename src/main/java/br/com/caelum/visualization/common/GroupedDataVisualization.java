package br.com.caelum.visualization.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.caelum.revolution.persistence.VisualizationThatQueries;
import br.com.caelum.revolution.visualization.Visualization;
import br.com.caelum.visualization.common.KeyValueChartExporter;

public class GroupedDataVisualization implements Visualization,
		VisualizationThatQueries {

	private Session session;
	private final KeyValueChartExporter chart;
	private final int threshold;
	private final String sql;

	public GroupedDataVisualization(KeyValueChartExporter chart, int threshold, String sql) {
		this.chart = chart;
		this.threshold = threshold;
		this.sql = sql;
	}

	@SuppressWarnings("unchecked")
	public void export() {
		Query query = session
				.createSQLQuery(sql)
				.setResultTransformer(Transformers.aliasToBean(GroupedDataTuple.class)).setMaxResults(threshold);
		List<GroupedDataTuple> results = query.list();

		chart.build(convertTo(results));
	}

	private Map<Object, Double> convertTo(List<GroupedDataTuple> results) {

		Map<Object, Double> map = new HashMap<Object, Double>();

		for (GroupedDataTuple tuple : results) {
			map.put(tuple.getName(), new Double(tuple.getQty()));
		}

		return map;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
