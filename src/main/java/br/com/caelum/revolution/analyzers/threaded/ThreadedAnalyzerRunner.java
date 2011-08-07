package br.com.caelum.revolution.analyzers.threaded;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import br.com.caelum.revolution.AnalyzerRunner;
import br.com.caelum.revolution.analyzers.Analyzer;
import br.com.caelum.revolution.changesets.ChangeSet;
import br.com.caelum.revolution.changesets.ChangeSetCollection;
import br.com.caelum.revolution.changesets.FixedListChangeSet;

public class ThreadedAnalyzerRunner implements AnalyzerRunner {

	private final List<Analyzer> analyzers;
	private List<List<ChangeSet>> partition;
	private static Logger log = LoggerFactory.getLogger(ThreadedAnalyzerRunner.class);

	public ThreadedAnalyzerRunner(List<Analyzer> analyzers, ChangeSetCollection allCs) {
		this.analyzers = analyzers;
		List<ChangeSet> x = allCs.get();
		this.partition = Lists.partition(x, analyzers.size());
	}
	
	public void start() {
		BlockingQueue<Runnable> worksQueue = new ArrayBlockingQueue<Runnable>(5);
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 30, TimeUnit.SECONDS, worksQueue);
		executor.allowCoreThreadTimeOut(true);

		for(int i = 0; i < analyzers.size(); i++) {
			log.info("Starting thread " + i);
			ChangeSetCollection splittedCs = new FixedListChangeSet(partition.get(i));
			executor.execute(new RunnableAnalyzer(analyzers.get(i), splittedCs));
		}
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