package br.com.caelum.revolution.tools.cc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.executor.CommandExecutor;
import br.com.caelum.revolution.scm.SCM;
import br.com.caelum.revolution.tools.ToolException;

public class JavaNCSSToolTest {

	private JavaNCSSOutputParse parse;
	private CommandExecutor exec;
	private Session session;
	private SCM scm;
	private JavaNCSSTool tool;

	@Before
	public void setUp() {
		parse = mock(JavaNCSSOutputParse.class);
		exec = mock(CommandExecutor.class);
		session = mock(Session.class);
		scm = mock(SCM.class);
		
		tool = new JavaNCSSTool("lib path", "jar path", parse, exec);
		tool.setSession(session);
		tool.setSCM(scm);

	}
	
	@Test
	public void shouldCreateACyclomaticComplexityBasedOnJavaNCSSOutput() throws ToolException {
		
		Artifact artifact = makeSessionReturnAnArtifact();

		ParsedJavaNCSS parsedResult = buildAParsedJavaNcss();
		when(parse.parse(any(String.class))).thenReturn(parsedResult);

		Commit commit = new Commit();
		tool.calculate(commit, new BuildResult("any dir"));
		
		ArgumentCaptor<CyclomaticComplexity> argument = ArgumentCaptor.forClass(CyclomaticComplexity.class);
		verify(session).save(argument.capture());
		
		CyclomaticComplexity cc = argument.getValue();
		
		assertSame(commit, cc.getCommit());
		assertEquals(15, cc.getCc());
		assertSame(artifact, cc.getArtifact());

	}

	private ParsedJavaNCSS buildAParsedJavaNcss() {
		ParsedJavaNCSS parsedResult = new ParsedJavaNCSS();
		ParsedJavaNCSSObject obj = new ParsedJavaNCSSObject();
		obj.setName("br.com.caelum.SomeClass");
		obj.setNcss(15);
		parsedResult.addObject(obj);
		return parsedResult;
	}

	private Artifact makeSessionReturnAnArtifact() {
		Artifact artifact = new Artifact();
		Criteria criteria = mock(Criteria.class);
		when(session.createCriteria(Artifact.class)).thenReturn(criteria);
		when(criteria.add(any(Criterion.class))).thenReturn(criteria);
		when(criteria.uniqueResult()).thenReturn(artifact);
		return artifact;
	}
}
