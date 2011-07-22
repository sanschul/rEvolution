package br.com.caelum.revolution.tools.lineschanged;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.com.caelum.revolution.builds.BuildResult;
import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.ArtifactKind;
import br.com.caelum.revolution.domain.Modification;
import br.com.caelum.revolution.domain.ModificationKind;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.tools.ToolException;
import br.com.caelum.revolution.tools.lineschanged.LinesChangedCount;
import br.com.caelum.revolution.tools.lineschanged.NumberOfLinesChanged;


public class NumberOfLinesChangedTest {

	private NumberOfLinesChanged tool;
	private Session session;

	@Before
	public void setUp() {
		session = mock(Session.class);
		tool = new NumberOfLinesChanged();
		tool.setSession(session);
	}

	@Test
	public void shouldCountLinesAdded() throws ToolException {
		String code = "@@ -1,17 +1,23 @@\r\n"
				+ "package edu.usp.ime.revolution.builds;\r\n"
				+ "-import edu.usp.ime.revolution.builds.ant.Ant;\r\n"
				+ "+import edu.usp.ime.revolution.builds.ant.AntFactory;\r\n"
				+ " import edu.usp.ime.revolution.config.Config;\r\n"
				+ " import edu.usp.ime.revolution.config.Configs;\r\n"
				+ " public class BuildFactory {\r\n"
				+ "public Build basedOn(Config config) {\r\n"
				+ "-		if(config.get(Configs.BUILD).equals(\"ant\")) {\r\n"
				+ "-			return new Ant();\r\n"
				+ "+		SpecificBuildFactory buildFactory = getBuildFactory(config.get(Configs.BUILD));\r\n"
				+ "+		return buildFactory.build(config);\r\n"
				+ "+	}\r\n"
				+ "+\r\n"
				+ "+	private SpecificBuildFactory getBuildFactory(String name) {\r\n"
				+ "+		if(name.equals(\"ant\")) {\r\n"
				+ "+			return new AntFactory();\r\n" + "}\r\n"
				+ "throw new BuildNotFoundException();\r\n" + "+\r\n" + "}\r\n"
				+ "}\r\n";

		Artifact artifact = new Artifact("file.java", ArtifactKind.CODE);
		Commit commit = new Commit();
		Modification modification = new Modification(code, commit, artifact, ModificationKind.DEFAULT);
		commit.addArtifact(artifact);
		commit.addModification(modification);


		tool.calculate(commit, new BuildResult("any dir"));

		ArgumentCaptor<LinesChangedCount> argument = ArgumentCaptor
				.forClass(LinesChangedCount.class);
		verify(session).save(argument.capture());

		LinesChangedCount value = argument.getValue();

		assertEquals(9, value.getLinesAdded());
	}

	@Test
	public void shouldCountLinesRemoved() throws ToolException {
		String code = "@@ -1,17 +1,23 @@\r\n"
				+ "package edu.usp.ime.revolution.builds;\r\n"
				+ "-import edu.usp.ime.revolution.builds.ant.Ant;\r\n"
				+ "+import edu.usp.ime.revolution.builds.ant.AntFactory;\r\n"
				+ " import edu.usp.ime.revolution.config.Config;\r\n"
				+ " import edu.usp.ime.revolution.config.Configs;\r\n"
				+ " public class BuildFactory {\r\n"
				+ "public Build basedOn(Config config) {\r\n"
				+ "-		if(config.get(Configs.BUILD).equals(\"ant\")) {\r\n"
				+ "-			return new Ant();\r\n"
				+ "+		SpecificBuildFactory buildFactory = getBuildFactory(config.get(Configs.BUILD));\r\n"
				+ "+		return buildFactory.build(config);\r\n"
				+ "+	}\r\n"
				+ "+\r\n"
				+ "+	private SpecificBuildFactory getBuildFactory(String name) {\r\n"
				+ "+		if(name.equals(\"ant\")) {\r\n"
				+ "+			return new AntFactory();\r\n" + "}\r\n"
				+ "throw new BuildNotFoundException();\r\n" + "+\r\n" + "}\r\n"
				+ "}\r\n";

		Artifact artifact = new Artifact("file.java", ArtifactKind.CODE);
		Commit commit = new Commit();
		Modification modification = new Modification(code, commit, artifact, ModificationKind.DEFAULT);
		commit.addArtifact(artifact);
		commit.addModification(modification);


		tool.calculate(commit, new BuildResult("any dir"));

		ArgumentCaptor<LinesChangedCount> argument = ArgumentCaptor
				.forClass(LinesChangedCount.class);
		verify(session).save(argument.capture());

		LinesChangedCount value = argument.getValue();

		assertEquals(3, value.getLinesRemoved());
	}

	@Test
	public void shouldIgnoreIfLineIsNotAddedOrRemoved() throws ToolException {
		String code = "@@ -1,17 +1,23 @@\r\n"
				+ "package edu.usp.ime.revolution.builds;\r\n"
				+ " import edu.usp.ime.revolution.config.Config;\r\n"
				+ " import edu.usp.ime.revolution.config.Configs;\r\n"
				+ " public class BuildFactory {\r\n"
				+ "public Build basedOn(Config config) {\r\n" + "}\r\n"
				+ "throw new BuildNotFoundException();\r\n" + "}\r\n" + "}\r\n";

		Artifact artifact = new Artifact("file.kava", ArtifactKind.CODE);
		Commit commit = new Commit();
		Modification modification = new Modification(code, commit, artifact, ModificationKind.DEFAULT);
		commit.addArtifact(artifact);
		commit.addModification(modification);


		tool.calculate(commit, new BuildResult("any dir"));

		ArgumentCaptor<LinesChangedCount> argument = ArgumentCaptor
				.forClass(LinesChangedCount.class);
		verify(session).save(argument.capture());

		LinesChangedCount value = argument.getValue();

		assertEquals(0, value.getLinesRemoved());
		assertEquals(0, value.getLinesAdded());
	}

	@Test
	public void shouldSetTheArtifactAndCommit() throws ToolException {
		String code = "any diff \r\n";

		Artifact artifact = new Artifact("file.java", ArtifactKind.CODE);
		Commit commit = new Commit();
		Modification modification = new Modification(code, commit, artifact, ModificationKind.DEFAULT);
		commit.addArtifact(artifact);
		commit.addModification(modification);


		tool.calculate(commit, new BuildResult("any dir"));

		ArgumentCaptor<LinesChangedCount> argument = ArgumentCaptor
				.forClass(LinesChangedCount.class);
		verify(session).save(argument.capture());

		LinesChangedCount value = argument.getValue();
		assertSame(artifact, value.getArtifact());
		assertSame(commit, value.getCommit());
	}

}
