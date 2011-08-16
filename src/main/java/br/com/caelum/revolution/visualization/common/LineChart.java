package br.com.caelum.revolution.visualization.common;

import java.io.File;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

public class LineChart implements Chart {

	private String title;
	private String xTitle;
	private String yTitle;
	private File file;
	private int width;
	private int height;
	private final MapToDataSetConverter converter;

	public LineChart(String title, String xTitle, String yTitle, File file, int width, int height, MapToDataSetConverter converter) {
		this.title = title;
		this.xTitle = xTitle;
		this.yTitle = yTitle;
		this.file = file;
		this.width = width;
		this.height = height;
		this.converter = converter;
	}
	
	public void build(Map<Object, Double> data) {
		JFreeChart chart = ChartFactory.createLineChart(title, xTitle, yTitle, converter.toCategoryDataset(title, data), PlotOrientation.VERTICAL, true, false, false);
		
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, width, height);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}

	}

	
}
