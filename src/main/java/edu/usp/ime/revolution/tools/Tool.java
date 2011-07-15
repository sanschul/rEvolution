package edu.usp.ime.revolution.tools;

import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.domain.Commit;

public interface Tool {

	void calculate(Commit commit, BuildResult current) throws ToolException;
	String getName();

}
