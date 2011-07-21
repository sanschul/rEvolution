package edu.usp.ime.revolution.tools.jdepend;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
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
	private Session session;

	@Before
	public void setUp() throws ParserConfigurationException, SAXException, IOException {
		exec = mock(CommandExecutor.class);
		interpreter = mock(JDependXMLInterpreter.class);
		jDependPath = "/some/path";
		
		session = mock(Session.class);
		
		commit = mock(Commit.class);
		current = new BuildResult("some/build/path");
		
		when(exec.execute(any(String.class), any(String.class))).thenReturn(jdependXml());
		when(interpreter.interpret(any(InputStream.class))).thenReturn(jDependInfos());
	}
	
	@Test
	public void shouldInterpretResultsFromJDepend() throws Exception {
		JDepend jdepend = new JDepend(exec, interpreter, jDependPath);
		jdepend.setSession(session);
		jdepend.calculate(commit, current);
		
		verify(exec).execute(any(String.class), any(String.class));
		verify(exec).setEnvironmentVar("CLASSPATH", jDependPath);
		verify(interpreter).interpret(any(InputStream.class));
	}
	
	@Test
	public void shouldStoreValuesInEntityAndSaveIt() throws Exception {
		JDepend jdepend = new JDepend(exec, interpreter, jDependPath);
		jdepend.setSession(session);
		jdepend.calculate(commit, current);		
		
		
		ArgumentCaptor<JDependMetric> argument = ArgumentCaptor.forClass(JDependMetric.class);
		verify(session).save(argument.capture());
		
		JDependMetric value = argument.getValue();
		
		assertEquals("target", value.getName());
		assertEquals(1, value.getCa(), 0.00001);
		assertEquals(2, value.getCe(), 0.00001);
		assertEquals(3, value.getAbstractClasses(), 0.00001);
		assertEquals(4, value.getAbstraction(), 0.00001);
		assertEquals(5, value.getConcreteClasses(), 0.00001);
		assertEquals(6, value.getDistanceFromMainLine(), 0.00001);
		assertEquals(7, value.getInstability(), 0.00001);
		assertEquals(8, value.getTotalClasses(), 0.00001);
		assertEquals(9, value.getVolatility(), 0.00001);
	}
	
	

	private List<JDependMetric> jDependInfos() {
		List<JDependMetric> infos = new ArrayList<JDependMetric>();
		JDependMetric info = new JDependMetric("target");
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
