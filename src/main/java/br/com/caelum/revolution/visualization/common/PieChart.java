package br.com.caelum.revolution.visualization.common;

import java.io.File;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

public class PieChart implements Chart {

	private final String title;
	private final File file;
	private final int height;
	private final int width;
	private final MapToDataSetConverter converter;
	
	public PieChart(String title, File file, int width, int height, MapToDataSetConverter converter) {
		this.title = title;
		this.file = file;
		this.width = width;
		this.height = height;
		this.converter = converter;
	}
	
	public void build(Map<Object, Double> data) {
		JFreeChart chart = ChartFactory.createPieChart(title, converter.toPieDataset(data), false, false, false);
		
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, width, height);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
