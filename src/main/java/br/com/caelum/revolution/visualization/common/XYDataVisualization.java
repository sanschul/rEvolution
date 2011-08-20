package br.com.caelum.revolution.visualization.common;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.caelum.revolution.persistence.VisualizationThatQueries;
import br.com.caelum.revolution.visualization.Visualization;

public class XYDataVisualization implements Visualization,
		VisualizationThatQueries {

	private Session session;
	private final ScatterPlot chart;
	private final int threshold;
	private final String sql;

	public XYDataVisualization(ScatterPlot chart, String sql, int threshold) {
		this.chart = chart;
		this.sql = sql;
		this.threshold = threshold;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public void export() {


		List<SmallLabeledXYPoint> points = (List<SmallLabeledXYPoint>) session
				.createSQLQuery(sql)
				.setResultTransformer(Transformers.aliasToBean(SmallLabeledXYPoint.class))
				.setMaxResults(threshold)
				.list();
		
		chart.build(points);
	}
}
