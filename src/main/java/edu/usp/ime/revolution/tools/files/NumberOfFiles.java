package edu.usp.ime.revolution.tools.files;

import java.io.File;

import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.tools.Tool;
import edu.usp.ime.revolution.tools.ToolException;

public class NumberOfFiles implements Tool {

	private String extension;

	public NumberOfFiles(String extension) {
		this.extension = extension;
	}

	public void calculate(Commit commit, BuildResult current) throws ToolException {
		try {
			//int qty = countFiles(changeSet.getPath());
			
			//set.setMetric(getName(), qty, "N/A", Metric.PROJECT_LEVEL, getName());
		}
		catch(Exception e) {
			throw new ToolException(e);
		}
	}

	public String getName() {
		return "number-of-files";
	}
	
	private int countFiles(String directory) {
		int qty = 0;
		
		File[] files = new File(directory).listFiles();
		
		for(File file : files) {
			if(file.getName().endsWith(extension)) qty++;
			if(file.isDirectory()) qty += countFiles(file.getPath());
		}
		
		return qty;
	}
}
