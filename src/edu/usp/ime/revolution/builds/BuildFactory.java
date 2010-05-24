package edu.usp.ime.revolution.builds;

import edu.usp.ime.revolution.builds.ant.Ant;
import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.config.Configs;
import edu.usp.ime.revolution.exceptions.BuildNotFoundException;

public class BuildFactory {

	public Build basedOn(Config config) {
		if(config.get(Configs.BUILD).equals("ant")) {
			return new Ant();
		}
		
		throw new BuildNotFoundException();
	}

}
