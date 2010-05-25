package edu.usp.ime.revolution.scm.git;

import java.util.ArrayList;
import java.util.List;

public class DefaultGitLogParser implements GitLogParser {

	private final int SHA1_SIZE = 40;
	
	public List<String> parse(String log) {
		String[] lines = log.replace("\r\n", "\n").split("\n");
		List<String> shas = new ArrayList<String>();
		
		for(String line : lines) {
			if(line.startsWith("commit ")) {
				String[] commitLine = line.split(" ");
				if(commitLine[1].length() == SHA1_SIZE) {
					shas.add(commitLine[1]);
				}	
			}
		}
		
		return shas;
	}

}
