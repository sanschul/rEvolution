package br.com.caelum.revolution.tools.cc;

import org.hibernate.Session;

import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.persistence.ToolThatPersists;
import br.com.caelum.revolution.tools.Tool;
import br.com.caelum.revolution.tools.ToolException;

public class JavaNCSSTool implements Tool, ToolThatPersists {

	public Class<?>[] classesToPersist() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSession(Session session) {
		// TODO Auto-generated method stub
		
	}

	public void calculate(Commit commit, BuildResult current)
			throws ToolException {
		// TODO Auto-generated method stub
		
		// por no classpath /lib
		// caminho do javancss
		// ./javancss -object -xml -recursive /Users/mauricioaniche/dev/workspace/rEvolution/src/main/java/
		
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
