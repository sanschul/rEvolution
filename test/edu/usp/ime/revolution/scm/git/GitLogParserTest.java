package edu.usp.ime.revolution.scm.git;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class GitLogParserTest {

	private String mediumLog =  "commit 4cb75005f86ce1ba7bb5164ca1e0918693a22e42\n"+
								"Author: Mauricio Aniche <some@email.com>\n" +
								"Date:   2010-05-24 23:50:53 -0300\n"+
								"\n"+
								"    Teste\n"+
								"\n"+
								"commit 7e4f749e8d321a0c90a3fa403301211d6b0942b0\n"+
								"Author: Mauricio Aniche <some@email.com>\n"+
								"Date:   2010-05-24 23:52:01 -0300\n"+
								"\n"+
								"    Teste2";
	
	@Test
	public void ShouldGetAllSHAs() {
		List<String> shas = new DefaultGitLogParser().parse(mediumLog);
	
		assertEquals(2, shas.size());
		assertEquals("4cb75005f86ce1ba7bb5164ca1e0918693a22e42", shas.get(0));
		assertEquals("7e4f749e8d321a0c90a3fa403301211d6b0942b0", shas.get(1));
	}
	
	@Test
	public void ShouldIgnoreTrashContent() {
		List<String> shas = new DefaultGitLogParser().parse(mediumLog + "\r\n????");
		
		assertEquals(2, shas.size());
		assertEquals("4cb75005f86ce1ba7bb5164ca1e0918693a22e42", shas.get(0));
		assertEquals("7e4f749e8d321a0c90a3fa403301211d6b0942b0", shas.get(1));		
	}
}
