package br.com.caelum.revolution.visualization.statistic;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.caelum.revolution.persistence.VisualizationThatQueries;
import br.com.caelum.revolution.visualization.Visualization;
import br.com.caelum.revolution.visualization.common.ScatterPlot;
import br.com.caelum.revolution.visualization.common.SmallLabeledXYPoint;

public class ModifiedArtifactsXBuggedArtifacts implements Visualization,
		VisualizationThatQueries {

	private Session session;
	private final ScatterPlot chart;
	private final int threshold;

	public ModifiedArtifactsXBuggedArtifacts(ScatterPlot chart, int threshold) {
		this.chart = chart;
		this.threshold = threshold;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public void export() {
		StringBuilder sql = new StringBuilder();
		sql.append("select aa.name label, ");
		sql.append("convert(( ");
		sql.append("select count(1) from modification m inner join bugorigin bo on bo.modification_id = m.id ");
		sql.append("where m.artifact_id = aa.id ");
		sql.append("), decimal(10,2)) x, ");
		sql.append("convert (( ");
		sql.append("select count(1) from artifact a inner join modification m on m.artifact_id = a.id ");
		sql.append("where a.id = aa.id ");
		sql.append("), decimal(10,2)) y ");
		sql.append("from artifact aa ");
		sql.append("order by y desc");

		List<SmallLabeledXYPoint> points = (List<SmallLabeledXYPoint>) session
				.createSQLQuery(sql.toString())
				.setResultTransformer(Transformers.aliasToBean(SmallLabeledXYPoint.class))
				.setMaxResults(threshold)
				.list();
		
		chart.build(points);
	}
}
