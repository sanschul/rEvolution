package br.com.caelum.revolution.analyzers.threaded;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import br.com.caelum.revolution.AnalyzerRunner;
import br.com.caelum.revolution.Error;
import br.com.caelum.revolution.analyzers.Analyzer;
import br.com.caelum.revolution.scm.FixedListChangeSet;
import br.com.caelum.revolution.scm.changesets.ChangeSet;
import br.com.caelum.revolution.scm.changesets.ChangeSetCollection;

public class ThreadedAnalyzerRunner implements AnalyzerRunner {

	private final List<Analyzer> analyzers;
	private List<ChangeSet> allCs;

	public ThreadedAnalyzerRunner(List<Analyzer> analyzers, ChangeSetCollection allCs) {
		this.analyzers = analyzers;
		this.allCs = allCs.get();
	}
	
	public void start() {
		BlockingQueue<Runnable> worksQueue = new ArrayBlockingQueue<Runnable>(2);
		ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 10, TimeUnit.SECONDS, worksQueue);
		executor.allowCoreThreadTimeOut(true);

		for(Analyzer analyzer : analyzers) {
			ChangeSetCollection splittedCs = splitChangeSetsABitMore();
			executor.execute(new RunnableAnalyzer(analyzer, splittedCs));
		}
		
	}

	private ChangeSetCollection splitChangeSetsABitMore() {
		if(allCs.size() > analyzers.size()) {
			return new FixedListChangeSet(getAPiece());
		}
		else {
			return new FixedListChangeSet(allCs);
		}
	}

	private List<ChangeSet> getAPiece() {
		List<ChangeSet> list = allCs.subList(0, analyzers.size());
		allCs.removeAll(list);
		return list;
	}

	public List<Error> getErrors() {
		return null;
	}

}

class RunnableAnalyzer implements Runnable {

	private final Analyzer analyzer;
	private final ChangeSetCollection cs;

	public RunnableAnalyzer(Analyzer analyzer, ChangeSetCollection cs) {
		this.analyzer = analyzer;
		this.cs = cs;
	}

	public void run() {
		analyzer.start(cs);
	}
	
}