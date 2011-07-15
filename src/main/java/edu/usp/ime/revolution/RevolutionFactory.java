package edu.usp.ime.revolution;

import java.util.List;

import edu.usp.ime.revolution.analyzers.Analyzer;
import edu.usp.ime.revolution.analyzers.DefaultAnalyzer;
import edu.usp.ime.revolution.analyzers.observers.ConsoleLog;
import edu.usp.ime.revolution.analyzers.observers.PersistMetrics;
import edu.usp.ime.revolution.builds.Build;
import edu.usp.ime.revolution.builds.BuildFactory;
import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.metrics.MetricSetFactory;
import edu.usp.ime.revolution.persistence.PersistenceFactory;
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
		MetricSetFactory metricSetFactory = new MetricSetFactory();
		List<MetricTool> tools = new ToolsFactory().basedOn(config);
		ChangeSetCollection collection = new ChangeSetCollectionFactory(scm).basedOn(config);
		
		Analyzer analyzer = new DefaultAnalyzer(scm, build, metricSetFactory, tools);
		analyzer.addObserver(new ConsoleLog());
		analyzer.addObserver(new PersistMetrics(new PersistenceFactory().basedOn(config)));
		
		return new Revolution(analyzer, collection);
	}

}
