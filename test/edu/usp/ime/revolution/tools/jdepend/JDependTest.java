package edu.usp.ime.revolution.tools.jdepend;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.metrics.MetricSet;
import edu.usp.ime.revolution.scm.ChangeSet;

public class JDependTest {
	private CommandExecutor exec;
	private JDependXMLInterpreter interpreter;
	private String jDependPath;
	private MetricSet set;
	private BuildResult current;
	private ChangeSet cs;

	@Before
	public void SetUp() throws ParserConfigurationException, SAXException, IOException {
		exec = mock(CommandExecutor.class);
		interpreter = mock(JDependXMLInterpreter.class);
		jDependPath = "/some/path";
		
		cs = mock(ChangeSet.class);
		current = new BuildResult("some/build/path");
		set = mock(MetricSet.class);
		
		when(exec.getCommandOutput()).thenReturn(jdependXml());
		when(interpreter.interpret(any(InputStream.class))).thenReturn(jDependInfos());
	}
	
	@Test
	public void ShouldInterpretResultsFromJDepend() throws Exception {
		JDepend jdepend = new JDepend(exec, interpreter, jDependPath);
		jdepend.calculate(cs, current, set);
		
		verify(exec).getCommandOutput();
		verify(exec).runCommand(any(String.class));
		verify(exec).setEnvironmentVar("CLASSPATH", jDependPath);
		verify(interpreter).interpret(any(InputStream.class));
	}
	
	@Test
	public void ShouldStoreValuesInSet() throws Exception {
		JDepend jdepend = new JDepend(exec, interpreter, jDependPath);
		jdepend.calculate(cs, current, set);		
		
		verify(set).setMetric("afferent-coupling", 1, "target", "package", jdepend.getName());
		verify(set).setMetric("efferent-coupling", 2, "target", "package", jdepend.getName());
		verify(set).setMetric("abstract-classes", 3, "target", "package", jdepend.getName());
		verify(set).setMetric("abstraction", 4, "target", "package", jdepend.getName());
		verify(set).setMetric("concrete-classes", 5, "target", "package", jdepend.getName());
		verify(set).setMetric("distance-from-main-line", 6, "target", "package", jdepend.getName());
		verify(set).setMetric("instability", 7, "target", "package", jdepend.getName());
		verify(set).setMetric("total-classes", 8, "target", "package", jdepend.getName());
		verify(set).setMetric("volatility", 9, "target", "package", jdepend.getName());
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
