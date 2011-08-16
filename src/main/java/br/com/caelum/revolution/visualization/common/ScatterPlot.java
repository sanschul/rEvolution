package br.com.caelum.revolution.visualization.common;

import java.io.File;
import java.text.NumberFormat;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ScatterPlot {

	private final String yTitle;
	private final String xTitle;
	private final String title;
	private final File file;
	private final int height;
	private final int width;
	
	public ScatterPlot(String title, String xTitle, String yTitle, File file, int width, int height) {
		this.title = title;
		this.xTitle = xTitle;
		this.yTitle = yTitle;
		this.file = file;
		this.width = width;
		this.height = height;
	}
	
	public void build(List<? extends XYPoint> points) {
		JFreeChart chart = ChartFactory.createScatterPlot(title, xTitle, yTitle, createDataSet(points), PlotOrientation.VERTICAL, true, false, false);
		
		NumberFormat format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(2);
		XYItemLabelGenerator generator =
		    new StandardXYItemLabelGenerator("{0}", format, format);
		chart.getXYPlot().getRenderer().setBaseItemLabelsVisible(true);
		chart.getXYPlot().getRenderer().setBaseItemLabelGenerator(generator);
		
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, width, height);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}

	}

	private XYDataset createDataSet(List<? extends XYPoint> points) {
		XYSeriesCollection dataset = new XYSeriesCollection();
		
		for (XYPoint point : points) {
			XYSeries series = new XYSeries(point.getLabel());
			series.add(point.getX(), point.getY());
		
			dataset.addSeries(series);
		}

		return dataset;
	}
}
