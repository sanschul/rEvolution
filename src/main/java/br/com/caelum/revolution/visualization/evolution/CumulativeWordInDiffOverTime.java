package br.com.caelum.revolution.visualization.evolution;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import br.com.caelum.revolution.persistence.VisualizationThatQueries;
import br.com.caelum.revolution.visualization.Visualization;
import br.com.caelum.revolution.visualization.common.Chart;

public class CumulativeWordInDiffOverTime implements Visualization,
		VisualizationThatQueries {

	private Session session;
	private final Chart chart;
	private final String name;

	public CumulativeWordInDiffOverTime(String name, Chart chart) {
		this.name = name;
		this.chart = chart;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}

	@SuppressWarnings("unchecked")
	public void export() {
		List<WordDiffInCommit> diffs = (List<WordDiffInCommit>) session
				.createSQLQuery(
						"select convert(sum(added), signed integer) added, convert(sum(removed), signed integer) removed, c.id from diffwordcount d inner join commit c on c.id = d.commit_id where d.name = '"+name+"' group by c.id order by c.id")
				.setResultTransformer(Transformers.aliasToBean(WordDiffInCommit.class))
				.list();

		
		Map<Object, Double> data = new LinkedHashMap<Object, Double>();
		
		double current = 0;
		for(WordDiffInCommit diff : diffs) {
			current += (diff.getAdded().subtract(diff.getRemoved()).intValue());
			data.put(diff.getId(), current);
		}
		
		chart.build(data);
	}
}
