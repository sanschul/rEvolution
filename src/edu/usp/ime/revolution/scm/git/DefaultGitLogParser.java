package edu.usp.ime.revolution.scm.git;

import java.util.ArrayList;
import java.util.List;

public class DefaultGitLogParser implements GitLogParser {

	private final int SHA1_SIZE = 40;
	
	public List<String> parse(String log) {
		String[] lines = log.replace("\r\n", "\n").split("\n");
		List<String> shas = new ArrayList<String>();
		for(String line : lines) {
			String[] content = line.split(" ");
			
			if(content[0].length() == SHA1_SIZE) {
				shas.add(content[0]);
			}
		}
		
		return shas;
	}

}
