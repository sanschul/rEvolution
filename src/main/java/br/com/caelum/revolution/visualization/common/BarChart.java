package br.com.caelum.revolution.visualization.common;

import java.io.File;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

public class BarChart implements Chart {

	private final String title;
	private final File file;
	private final int height;
	private final int width;
	private final String yTitle;
	private final String xTitle;
	private final MapToDataSetConverter converter;
	
	public BarChart(String title, String xTitle, String yTitle, File file, int width, int height, MapToDataSetConverter converter) {
		this.title = title;
		this.xTitle = xTitle;
		this.yTitle = yTitle;
		this.file = file;
		this.width = width;
		this.height = height;
		this.converter = converter;
	}
	
	public void build(Map<Object, Double> data) {
		JFreeChart chart = ChartFactory.createBarChart(title, xTitle, yTitle, converter.toCategoryDataset(title, data), PlotOrientation.VERTICAL, true, false, false);
		
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, width, height);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}

	}

}
