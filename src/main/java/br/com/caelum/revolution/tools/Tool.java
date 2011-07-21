package br.com.caelum.revolution.tools;

import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.domain.Commit;

public interface Tool {

	void calculate(Commit commit, BuildResult current) throws ToolException;
	String getName();

}
