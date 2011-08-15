package br.com.caelum.visualization.common;

import java.util.Map;

public interface KeyValueChartExporter {
	void build(Map<Object, Double> data);
}
