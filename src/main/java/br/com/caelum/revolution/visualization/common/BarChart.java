package br.com.caelum.revolution.visualization.common;

import java.io.File;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarChart implements KeyValueChart {

	private final String title;
	private final File file;
	private final int height;
	private final int width;
	private final String yTitle;
	private final String xTitle;
	
	public BarChart(String title, String xTitle, String yTitle, File file, int width, int height) {
		this.title = title;
		this.xTitle = xTitle;
		this.yTitle = yTitle;
		this.file = file;
		this.width = width;
		this.height = height;
	}
	
	public void build(Map<Object, Double> data) {
		JFreeChart chart = ChartFactory.createBarChart(title, xTitle, yTitle, dataset(data), PlotOrientation.VERTICAL, true, false, false);
		
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, width, height);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}

	}

	private CategoryDataset dataset(Map<Object, Double> data) {
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		
		for(Object key : data.keySet()) {
			ds.addValue(data.get(key), title, key.toString());
		}

		return ds;
	}

}
