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
			String tool = config.get("tools." + counter);
			tools.add(buildTool(tool));
			
			counter++;
		}
		
		return tools;
	}

	private MetricTool buildTool(String tool) {
		if(tool.equals("number-of-files")) return new NumberOfFiles();
		throw new ToolNotFoundException();
	}

}
