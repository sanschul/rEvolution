package br.com.caelum.revolution.visualization.common;

import java.util.Map;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class MapToDataSetConverter {


	public PieDataset toPieDataset(Map<Object, Double> data) {
		DefaultPieDataset ds = new DefaultPieDataset();
		
		for(Object key : data.keySet()) {
			ds.setValue(key.toString(), data.get(key));
		}
		
		return ds;
	}
	

	public CategoryDataset toCategoryDataset(String category, Map<Object, Double> data) {
		DefaultCategoryDataset ds = new DefaultCategoryDataset();
		
		for(Object key : data.keySet()) {
			ds.addValue(data.get(key), category, key.toString());
		}

		return ds;
	}

	
}
