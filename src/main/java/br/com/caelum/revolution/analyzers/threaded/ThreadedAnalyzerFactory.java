package br.com.caelum.revolution.analyzers.threaded;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.revolution.AnalyzerRunner;
import br.com.caelum.revolution.analyzers.Analyzer;
import br.com.caelum.revolution.analyzers.DefaultAnalyzer;
import br.com.caelum.revolution.builds.Build;
import br.com.caelum.revolution.builds.BuildFactory;
import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.domain.PersistedCommitConverter;
import br.com.caelum.revolution.persistence.HibernatePersistence;
import br.com.caelum.revolution.scm.SCM;
import br.com.caelum.revolution.scm.SCMFactory;
import br.com.caelum.revolution.scm.ThreadableSCM;
import br.com.caelum.revolution.scm.changesets.ChangeSetCollection;
import br.com.caelum.revolution.scm.changesets.ChangeSetCollectionFactory;
import br.com.caelum.revolution.tools.Tool;
import br.com.caelum.revolution.tools.ToolsFactory;

public class ThreadedAnalyzerFactory {

	public AnalyzerRunner basedOn(Config config) {
		ChangeSetCollection globalCollection = null;
		Build build = new BuildFactory().basedOn(config);
		List<Tool> tools = new ToolsFactory().basedOn(config);
		
		List<Analyzer> all = new ArrayList<Analyzer>();
		for(int i = 0; i < 5; i++) {
			SCM specificScm = new SCMFactory().basedOn(config);
			ThreadableSCM threadableScm = (ThreadableSCM)specificScm;
			threadableScm.setRepositoryNumber(i+1);
			
			all.add(new DefaultAnalyzer(specificScm, build, tools, new PersistedCommitConverter(), new HibernatePersistence(config)));
			
			if(globalCollection==null) {
				globalCollection = new ChangeSetCollectionFactory(specificScm).basedOn(config);
			}
		}
		
		return new ThreadedAnalyzerRunner(all, globalCollection);
	}
	
}
