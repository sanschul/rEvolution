package edu.usp.ime.revolution;

import java.util.List;

import edu.usp.ime.revolution.algorithms.Analyzer;
import edu.usp.ime.revolution.algorithms.DefaultAnalyzer;
import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildFactory;
import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.metrics.MetricStore;
import edu.usp.ime.revolution.scm.ChangeSetCollection;
import edu.usp.ime.revolution.scm.ChangeSetCollectionFactory;
import edu.usp.ime.revolution.scm.SCM;
import edu.usp.ime.revolution.scm.SCMFactory;
import edu.usp.ime.revolution.tools.MetricTool;
import edu.usp.ime.revolution.tools.ToolsFactory;

public class RevolutionFactory {

	public Revolution basedOn(Config config) {
		SCM scm = new SCMFactory().basedOn(config);
		Build build = new BuildFactory().basedOn(config);
		MetricStore store = new MetricStore();
		List<MetricTool> tools = new ToolsFactory().basedOn(config);
		ChangeSetCollection collection = new ChangeSetCollectionFactory().basedOn(config, scm);
		
		Analyzer analyzer = new DefaultAnalyzer(build, store, tools);
		
		return new Revolution(analyzer, collection);
	}

}
