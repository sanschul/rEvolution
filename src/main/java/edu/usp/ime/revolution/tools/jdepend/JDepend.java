package edu.usp.ime.revolution.tools.jdepend;

import java.io.ByteArrayInputStream;
import java.util.List;
import edu.usp.ime.revolution.builds.BuildResult;
import edu.usp.ime.revolution.executor.CommandExecutor;
import edu.usp.ime.revolution.metrics.Metric;
import edu.usp.ime.revolution.metrics.MetricSet;
import edu.usp.ime.revolution.scm.ChangeSet;
import edu.usp.ime.revolution.tools.MetricTool;
import edu.usp.ime.revolution.tools.ToolException;

public class JDepend implements MetricTool {

	private final CommandExecutor executor;
	private final JDependXMLInterpreter interpreter;
	private final String jDependPath;

	public JDepend(CommandExecutor executor, JDependXMLInterpreter interpreter, String jDependPath) {
		this.executor = executor;
		this.interpreter = interpreter;
		this.jDependPath = jDependPath;	
	}

	public void calculate(ChangeSet changeSet, BuildResult current,
			MetricSet set) throws ToolException {
		try {
			executor.setEnvironmentVar("CLASSPATH", jDependPath);
			executor.runCommand("java jdepend.xmlui.JDepend " + current.getDirectory());
			
			String response = extractJDependXMLBlockFrom(executor.getCommandOutput());
			
			List<JDependInfo> infos = interpreter.interpret(new ByteArrayInputStream(response.getBytes("UTF-8")));
			for(JDependInfo info : infos) {
				putJDependInfosOnSet(set, info);
			}
		} catch (Exception e) {
			throw new ToolException(e);
		}
	}

	public String getName() {
		return "jdepend";
	}
	
	private void putJDependInfosOnSet(MetricSet set, JDependInfo info) {
		set.setMetric("afferent-coupling", info.getCa(), info.getName(), Metric.PACKAGE_LEVEL, getName());
		set.setMetric("efferent-coupling", info.getCe(), info.getName(), Metric.PACKAGE_LEVEL, getName());
		set.setMetric("abstract-classes", info.getAbstractClasses(), info.getName(), Metric.PACKAGE_LEVEL, getName());
		set.setMetric("abstraction", info.getAbstraction(), info.getName(), Metric.PACKAGE_LEVEL, getName());
		set.setMetric("concrete-classes", info.getConcreteClasses(), info.getName(), Metric.PACKAGE_LEVEL, getName());
		set.setMetric("distance-from-main-line", info.getDistanceFromMainLine(), info.getName(), Metric.PACKAGE_LEVEL, getName());
		set.setMetric("instability", info.getInstability(), info.getName(), Metric.PACKAGE_LEVEL, getName());
		set.setMetric("total-classes", info.getTotalClasses(), info.getName(), Metric.PACKAGE_LEVEL, getName());
		set.setMetric("volatility", info.getVolatility(), info.getName(), Metric.PACKAGE_LEVEL, getName());
	}

	private String extractJDependXMLBlockFrom(String output) {
		return output.substring(0, output.lastIndexOf("</JDepend>")+10);
	}

}
