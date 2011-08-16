package br.com.caelum.revolution.visualization.common;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.caelum.revolution.persistence.VisualizationThatQueries;
import br.com.caelum.revolution.visualization.Visualization;

public class GroupedDataVisualization<T extends Number> implements Visualization,
		VisualizationThatQueries {

	private Session session;
	private final Chart chart;
	private final String sql;

	public GroupedDataVisualization(Chart chart, String sql) {
		this.chart = chart;
		this.sql = sql;
	}

	@SuppressWarnings("unchecked")
	public void export() {
		Query query = buildQuery();
		
		List<GroupedDataTuple<T>> results = query.list();

		chart.build(convertTo(results));
	}

	protected Query buildQuery() {
		Query query = session
				.createSQLQuery(sql)
				.setResultTransformer(Transformers.aliasToBean(GroupedDataTuple.class));
		return query;
	}

	private Map<Object, Double> convertTo(List<GroupedDataTuple<T>> results) {

		Map<Object, Double> map = new LinkedHashMap<Object, Double>();

		for (GroupedDataTuple<T> tuple : results) {
			map.put(tuple.getName(), new Double(tuple.getQty().doubleValue()));
		}

		return map;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
