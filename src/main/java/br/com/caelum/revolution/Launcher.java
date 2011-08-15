package br.com.caelum.revolution;

import java.io.FileInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.revolution.analyzers.AnalyzerFactory;
import br.com.caelum.revolution.analyzers.AnalyzerRunner;
import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.config.PropertiesConfig;
import br.com.caelum.revolution.visualization.VisualizationFactory;
import br.com.caelum.revolution.visualization.VisualizationRunner;


public class Launcher {
	private static Logger log = LoggerFactory.getLogger(Launcher.class);
	
	public static void main(String[] args) throws Exception {
		if(args.length==0) throw new Exception("missing config file");
		
		InputStream configStream = new FileInputStream(args[0]);
		Config config = new PropertiesConfig(configStream);
		
		log.info("rEvolution");
		log.info("starting...");

		if(args.length >= 3 && args[2] != null && !args[2].equals("--only-visualization")) {
			AnalyzerRunner analyzerRunner = new AnalyzerFactory().basedOn(config);
			analyzerRunner.start();
		}
		
		if(args.length >= 2 && args[1] != null && args[1].length() > 0) {
			VisualizationRunner visualizationRunner = new VisualizationFactory().basedOn(config, args[1]);
			visualizationRunner.start();
		}
		
		log.info("FINISHED!");
	}
}
