package edu.usp.ime.revolution.scm.git;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class GitLogParserTest {

	private String exampleLog = "342aa1564045ab81c7995262f38ad25b7acf3bf0 MetricStore now builds a MetricSet base\r\nc69862c8c6cb7d8b4ed147b3231c2f00ab9da50c MetricSet now contains changeset date";
	
	@Test
	public void ShouldGetAllSHAs() {
		List<String> shas = new DefaultGitLogParser().parse(exampleLog);
	
		assertEquals(2, shas.size());
		assertEquals("342aa1564045ab81c7995262f38ad25b7acf3bf0", shas.get(0));
		assertEquals("c69862c8c6cb7d8b4ed147b3231c2f00ab9da50c", shas.get(1));
	}
	
	@Test
	public void ShouldIgnoreTrashContent() {
		List<String> shas = new DefaultGitLogParser().parse(exampleLog + "\r\n????");
		
		assertEquals(2, shas.size());
		assertEquals("342aa1564045ab81c7995262f38ad25b7acf3bf0", shas.get(0));
		assertEquals("c69862c8c6cb7d8b4ed147b3231c2f00ab9da50c", shas.get(1));		
	}
}
