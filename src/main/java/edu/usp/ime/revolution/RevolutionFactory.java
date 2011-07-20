package edu.usp.ime.revolution;

import java.util.List;

import edu.usp.ime.revolution.analyzers.Analyzer;
import edu.usp.ime.revolution.analyzers.DefaultAnalyzer;
import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildFactory;
import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.persistence.HibernatePersistence;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.SCMFactory;
import edu.usp.ime.revolution.scm.changesets.ChangeSetCollection;
import edu.usp.ime.revolution.scm.changesets.ChangeSetCollectionFactory;
import edu.usp.ime.revolution.tools.Tool;
import edu.usp.ime.revolution.tools.ToolsFactory;

public class RevolutionFactory {

	public Revolution basedOn(Config config) {
		SCM scm = new SCMFactory().basedOn(config);
		Build build = new BuildFactory().basedOn(config);
		List<Tool> tools = new ToolsFactory().basedOn(config);
		ChangeSetCollection collection = new ChangeSetCollectionFactory(scm).basedOn(config);
		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, tools, new HibernatePersistence(config));
		
		return new Revolution(analyzer, collection);
	}

}
