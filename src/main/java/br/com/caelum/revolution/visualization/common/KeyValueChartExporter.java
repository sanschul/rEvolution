package br.com.caelum.revolution.visualization.common;

import java.util.Map;

public interface KeyValueChartExporter {
	void build(Map<Object, Double> data);
}
