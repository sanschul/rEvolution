package br.com.caelum.revolution.tools.diffwordcount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.ArtifactKind;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.domain.Modification;
import br.com.caelum.revolution.domain.ModificationKind;
import br.com.caelum.revolution.tools.ToolException;
import br.com.caelum.revolution.tools.diffwordcount.DiffWordCountTool;
import br.com.caelum.revolution.tools.diffwordcount.DiffWordCount;

public class DiffWordCountToolTest {

	private DiffWordCountTool tool;
	private Session session;

	@Before
	public void setUp() {
		session = mock(Session.class);
		tool = new DiffWordCountTool(new String[] { "Test.java", "Tests.java"}, new String[] { "@Test"});
		tool.setSession(session);
	}

	@Test
	public void shouldCountTestsAdded() throws ToolException {
		String code = "@@ -1,17 +1,23 @@\r\n"
				+ "package edu.usp.ime.revolution.builds;\r\n"
				+ "+@Test\r\n"
				+ "+public void test() {\r\n"
				+ "+ xxx\r\n"
				+ "+}\r\n"
				+ "+@Test\r\n"
				+ "+public void test2() {\r\n"
				+ "+ xxx\r\n"
				+ "+}\r\n";

		Artifact artifact = new Artifact("fileTest.java", ArtifactKind.CODE);
		Commit commit = new Commit();
		Modification modification = new Modification(code, commit, artifact, ModificationKind.DEFAULT);
		commit.addArtifact(artifact);
		commit.addModification(modification);


		tool.calculate(commit, new BuildResult("any dir"));

		ArgumentCaptor<DiffWordCount> argument = ArgumentCaptor
				.forClass(DiffWordCount.class);
		verify(session).save(argument.capture());

		DiffWordCount value = argument.getValue();

		assertEquals(2, value.getAdded());
	}

	@Test
	public void shouldCountTestsRemoved() throws ToolException {
		String code = "@@ -1,17 +1,23 @@\r\n"
				+ "package edu.usp.ime.revolution.builds;\r\n" 
				+ "-@Test\r\n"
				+ "-public void test() {\r\n" 
				+ "- xxx\r\n" 
				+ "-}\r\n"
				+ "-@Test\r\n"
				+ "-public void test() {\r\n" 
				+ "- xxx\r\n" 
				+ "-}\r\n";

		Artifact artifact = new Artifact("fileTest.java", ArtifactKind.CODE);
		Commit commit = new Commit();
		Modification modification = new Modification(code, commit, artifact,
				ModificationKind.DEFAULT);
		commit.addArtifact(artifact);
		commit.addModification(modification);

		tool.calculate(commit, new BuildResult("any dir"));

		ArgumentCaptor<DiffWordCount> argument = ArgumentCaptor
				.forClass(DiffWordCount.class);
		verify(session).save(argument.capture());

		DiffWordCount value = argument.getValue();

		assertEquals(2, value.getRemoved());
	}

	@Test
	public void shouldIgnoreIfTestsWereNotAddedOrRemoved() throws ToolException {
		String code = "@@ -1,17 +1,23 @@\r\n"
				+ "package edu.usp.ime.revolution.builds;\r\n"
				+ " import edu.usp.ime.revolution.config.Config;\r\n"
				+ " import edu.usp.ime.revolution.config.Configs;\r\n"
				+ " public class BuildFactory {\r\n"
				+ "public Build basedOn(Config config) {\r\n" + "}\r\n"
				+ "throw new BuildNotFoundException();\r\n" + "}\r\n" + "}\r\n";

		Artifact artifact = new Artifact("fileTest.java", ArtifactKind.CODE);
		Commit commit = new Commit();
		Modification modification = new Modification(code, commit, artifact,
				ModificationKind.DEFAULT);
		commit.addArtifact(artifact);
		commit.addModification(modification);

		tool.calculate(commit, new BuildResult("any dir"));

		ArgumentCaptor<DiffWordCount> argument = ArgumentCaptor
				.forClass(DiffWordCount.class);
		verify(session).save(argument.capture());

		DiffWordCount value = argument.getValue();

		assertEquals(0, value.getRemoved());
		assertEquals(0, value.getAdded());
	}

	@Test
	public void shouldSetTheArtifactAndCommit() throws ToolException {
		String code = "any diff \r\n";

		Artifact artifact = new Artifact("fileTest.java", ArtifactKind.CODE);
		Commit commit = new Commit();
		Modification modification = new Modification(code, commit, artifact,
				ModificationKind.DEFAULT);
		commit.addArtifact(artifact);
		commit.addModification(modification);

		tool.calculate(commit, new BuildResult("any dir"));

		ArgumentCaptor<DiffWordCount> argument = ArgumentCaptor
				.forClass(DiffWordCount.class);
		verify(session).save(argument.capture());

		DiffWordCount value = argument.getValue();
		assertSame(artifact, value.getArtifact());
		assertSame(commit, value.getCommit());
	}
	

	@Test
	public void shouldIgnoreFilesThatDoNotMatch() throws ToolException {
		String code = "any diff \r\n";

		Artifact artifact = new Artifact("file.java", ArtifactKind.CODE);
		Commit commit = new Commit();
		Modification modification = new Modification(code, commit, artifact,
				ModificationKind.DEFAULT);
		commit.addArtifact(artifact);
		commit.addModification(modification);

		tool.calculate(commit, new BuildResult("any dir"));

		verify(session, times(0)).save(any(Object.class));

	}

}
