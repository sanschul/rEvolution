package br.com.caelum.revolution.visualization.common;

import org.hibernate.Query;

public class ThresholdedGroupedDataVisualization<T extends Number> extends GroupedDataVisualization<T> {

	private final int threshold;

	public ThresholdedGroupedDataVisualization(Chart chart, int threshold, String sql) {
		super(chart, sql);
		this.threshold = threshold;
	}

	protected Query buildQuery() {
		Query query = super.buildQuery();
		query.setMaxResults(threshold);
		return query;
	}

}
