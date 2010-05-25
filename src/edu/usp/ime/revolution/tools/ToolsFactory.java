package edu.usp.ime.revolution.tools;

import java.util.ArrayList;
import java.util.List;
import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.tools.files.NumberOfFiles;

public class ToolsFactory {

	public List<MetricTool> basedOn(Config config) {
		List<MetricTool> tools = new ArrayList<MetricTool>();
		
		int counter = 1;
		while(config.contains("tools." + counter)) {
			String toolName = config.get("tools." + counter);
			MetricTool tool = buildTool(toolName);
			tool.load(config, "tools." + counter);
			tools.add(tool);
			
			counter++;
		}
		
		return tools;
	}

	private MetricTool buildTool(String tool) {
		if(tool.equals("number-of-files")) return new NumberOfFiles();
		throw new ToolNotFoundException();
	}

}
