package edu.usp.ime.revolution.scm.git;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import edu.usp.ime.revolution.domain.ArtifactStatus;
import edu.usp.ime.revolution.domain.Artifact;

public class DefaultGitDiffParser implements GitDiffParser {

	public List<Artifact> parse(String diff) {
		List<Artifact> allDiffs = new ArrayList<Artifact>();

		List<String> diffs = Arrays.asList(diff.split("diff --git "));
		diffs = diffs.subList(1, diffs.size());
		for (String unformattedDiff : diffs) {
			List<String> lines = Arrays.asList(unformattedDiff.replace("\r", "").split("\n"));
			
			String content = findContent(lines);
			String name = extractFileNameIn(lines.get(0));
			ArtifactStatus status = findStatusIn(lines);
			allDiffs.add(new Artifact(name, content, status));
		}

		return allDiffs;
	}

	private int findDiffStart(List<String> lines) {
		int start = 0;
		for(String line : lines) {
			start++;
			if(line.startsWith("index ")) break;
		}
		return start;
	}
	
	private String findContent(List<String> lines) {
		int lineDiffsStarts = findDiffStart(lines);
		if(!lines.get(lineDiffsStarts).startsWith("Binary files")) {
			return transformInStringTheList(lines.subList(lineDiffsStarts+2, lines.size()));
		}
		return "";
	}

	private ArtifactStatus findStatusIn(List<String> lines) {
		
		int modeLine = findDiffStart(lines) - 2;
		
		for(ArtifactStatus st : EnumSet.allOf(ArtifactStatus.class)) {
			if(lines.get(modeLine).startsWith(st.getPattern())) return st;
		}
		
		return ArtifactStatus.DEFAULT;
	}

	public String extractFileNameIn(String line) {
		return line.substring(2, line.indexOf(" "));
	}

	private String transformInStringTheList(List<String> list) {
		StringBuilder builder = new StringBuilder();
		for (String line : list) {
			builder.append(line + "\r\n");
		}

		return builder.toString();
	}

}
