package edu.usp.ime.revolution.tools;

import java.util.ArrayList;
import java.util.List;
import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.tools.files.NumberOfFilesFactory;

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

	private SpecificToolFactory getToolFactory(String tool) {
		if(tool.equals("number-of-files")) return new NumberOfFilesFactory();
		throw new ToolNotFoundException();
	}

}
