package edu.usp.ime.revolution.tools;

import java.util.ArrayList;
import java.util.List;
import edu.usp.ime.revolution.config.Config;

public class ToolsFactory {

	public List<MetricTool> basedOn(Config config) {
		List<MetricTool> tools = new ArrayList<MetricTool>();
		
		int counter = 1;
		while(config.contains(toolConfigName(counter))) {
			String toolName = config.get(toolConfigName(counter));
			SpecificToolFactory toolFactory = getToolFactory(toolName);
			tools.add(toolFactory.build(config, toolConfigName(counter)));
			
			counter++;
		}
		
		return tools;
	}

	private String toolConfigName(int counter) {
		return "tools." + counter;
	}

	@SuppressWarnings("unchecked")
	private SpecificToolFactory getToolFactory(String tool) {
		try {
			Class theClass = Class.forName(tool);
			return (SpecificToolFactory)theClass.newInstance();
		} catch (Exception e) {
			throw new ToolNotFoundException();
		}
	}

}
