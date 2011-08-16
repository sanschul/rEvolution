package br.com.caelum.revolution.visualization.common;

import java.io.File;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class PieChart implements KeyValueChart {

	private final String title;
	private final File file;
	private final int height;
	private final int width;
	
	public PieChart(String title, File file, int width, int height) {
		this.title = title;
		this.file = file;
		this.width = width;
		this.height = height;
	}
	
	public void build(Map<Object, Double> data) {
		JFreeChart chart = ChartFactory.createPieChart(title, datasets(data), false, false, false);
		
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, width, height);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	private PieDataset datasets(Map<Object, Double> data) {
		DefaultPieDataset ds = new DefaultPieDataset();
		
		for(Object key : data.keySet()) {
			ds.setValue(key.toString(), data.get(key));
		}
		
		return ds;
		
	}
}
