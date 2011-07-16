package edu.usp.ime.revolution.tools.jdepend;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.domain.Commit;
import edu.usp.ime.revolution.executor.CommandExecutor;

public class JDependTest {
	private CommandExecutor exec;
	private JDependXMLInterpreter interpreter;
	private String jDependPath;
	private BuildResult current;
	private Commit commit;

	@Before
	public void setUp() throws ParserConfigurationException, SAXException, IOException {
		exec = mock(CommandExecutor.class);
		interpreter = mock(JDependXMLInterpreter.class);
		jDependPath = "/some/path";
		
		commit = mock(Commit.class);
		current = new BuildResult("some/build/path");
		
		when(exec.execute(any(String.class), any(String.class))).thenReturn(jdependXml());
		when(interpreter.interpret(any(InputStream.class))).thenReturn(jDependInfos());
	}
	
	@Test
	public void shouldInterpretResultsFromJDepend() throws Exception {
		JDepend jdepend = new JDepend(exec, interpreter, jDependPath);
		jdepend.calculate(commit, current);
		
		verify(exec).execute(any(String.class), any(String.class));
		verify(exec).setEnvironmentVar("CLASSPATH", jDependPath);
		verify(interpreter).interpret(any(InputStream.class));
	}
	
	@Test @Ignore
	public void shouldStoreValuesInSet() throws Exception {
		JDepend jdepend = new JDepend(exec, interpreter, jDependPath);
		jdepend.calculate(commit, current);		
		
//		verify(set).setMetric("afferent-coupling", 1, "target", "package", jdepend.getName());
//		verify(set).setMetric("efferent-coupling", 2, "target", "package", jdepend.getName());
//		verify(set).setMetric("abstract-classes", 3, "target", "package", jdepend.getName());
//		verify(set).setMetric("abstraction", 4, "target", "package", jdepend.getName());
//		verify(set).setMetric("concrete-classes", 5, "target", "package", jdepend.getName());
//		verify(set).setMetric("distance-from-main-line", 6, "target", "package", jdepend.getName());
//		verify(set).setMetric("instability", 7, "target", "package", jdepend.getName());
//		verify(set).setMetric("total-classes", 8, "target", "package", jdepend.getName());
//		verify(set).setMetric("volatility", 9, "target", "package", jdepend.getName());
	}
	
	

	private List<JDependInfo> jDependInfos() {
		List<JDependInfo> infos = new ArrayList<JDependInfo>();
		JDependInfo info = new JDependInfo("target");
		info.setCa(1);
		info.setCe(2);
		info.setAbstractClasses(3);
		info.setAbstraction(4);
		info.setConcreteClasses(5);
		info.setDistanceFromMainLine(6);
		info.setInstability(7);
		info.setTotalClasses(8);
		info.setVolatility(9);
		
		infos.add(info);
		return infos;
	}

	private String jdependXml() {
		return "<JDepend> ... some xml ... </JDepend>";
	}

}
