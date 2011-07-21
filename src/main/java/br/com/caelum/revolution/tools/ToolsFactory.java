package br.com.caelum.revolution.tools;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.revolution.config.Config;

public class ToolsFactory {

	public List<Tool> basedOn(Config config) {
		List<Tool> tools = new ArrayList<Tool>();
		
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

	@SuppressWarnings("rawtypes")
	private SpecificToolFactory getToolFactory(String tool) {
		try {
			Class theClass = Class.forName(tool);
			return (SpecificToolFactory)theClass.newInstance();
		} catch (Exception e) {
			throw new ToolNotFoundException();
		}
	}

}
