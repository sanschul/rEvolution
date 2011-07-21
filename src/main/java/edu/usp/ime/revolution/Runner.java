package edu.usp.ime.revolution;

import java.io.FileInputStream;
import java.io.InputStream;

import edu.usp.ime.revolution.analyzers.AnalyzerFactory;
import edu.usp.ime.revolution.analyzers.Error;
import edu.usp.ime.revolution.analyzers.AnalyzerRunner;
import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.config.PropertiesConfig;

public class Runner {
	public static void main(String[] args) throws Exception {
		if(args.length==0) throw new Exception("missing config file");
		
		InputStream configStream = new FileInputStream(args[0]);
		Config config = new PropertiesConfig(configStream);
		
		System.out.println("rEvolution");
		System.out.println("starting...");
		
		AnalyzerRunner rev = new AnalyzerFactory().basedOn(config);
		rev.start();
		
		System.out.println("finished!");
		System.out.println("errors: " + rev.getErrors().size());
		for(Error e : rev.getErrors()) {
			System.out.println("====================================================");
			System.out.println(e.getError());
		}
	}
}
