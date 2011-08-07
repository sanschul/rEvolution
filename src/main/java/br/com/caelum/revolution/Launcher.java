package br.com.caelum.revolution;

import java.io.FileInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.revolution.analyzers.AnalyzerFactory;
import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.config.PropertiesConfig;


public class Launcher {
	private static Logger log = LoggerFactory.getLogger(Launcher.class);
	
	public static void main(String[] args) throws Exception {
		if(args.length==0) throw new Exception("missing config file");
		
		InputStream configStream = new FileInputStream(args[0]);
		Config config = new PropertiesConfig(configStream);
		
		log.info("rEvolution");
		log.info("starting...");
		
		AnalyzerRunner rev = new AnalyzerFactory().basedOn(config);
		rev.start();
		
		log.info("FINISHED!");
	}
}
