package edu.usp.ime.revolution.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import edu.usp.ime.revolution.metrics.Metric;
import edu.usp.ime.revolution.metrics.MetricSet;

public class SqlitePersistence implements MetricPersistence {

	private final String db;
	private final String projectName; 
	
	private Connection connection = null;  
    private PreparedStatement statement = null;
	
	public SqlitePersistence(String db, String projectName) {
		this.db = db;
		this.projectName = projectName;
	}
	
	public void save(MetricSet set) {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + db);
			
			int csId = saveChangeSet(set);
			for(Metric metric: set.getMetrics()) {
				saveMetric(csId, metric);
			} 
			
			connection.close();
		}
		catch(Exception e) {
			throw new PersistenceException(e);
		}
	}

	private void saveMetric(int csId, Metric metric) throws SQLException {
		statement = connection.prepareStatement(
			"insert into metrics (changeset, name, value, target, tool) values (?,?,?,?,?)");

		statement.setInt(1, csId);
		statement.setString(2, metric.getName());
		statement.setDouble(3, metric.getValue());
		statement.setString(4, metric.getTarget());
		statement.setString(5, metric.getTool());
		
		statement.execute();
	}

	private int saveChangeSet(MetricSet set) throws SQLException {
		statement = connection.prepareStatement(
			"insert into changesets (project, name, date) values (?,?,datetime(?))");

		statement.setString(1, projectName);
		statement.setString(2, set.getName());
		statement.setString(3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(set.getDate().getTime()));
		
		statement.execute();
		
		statement = connection.prepareStatement("select id from changesets where project=? and name=?");
		statement.setString(1, projectName);
		statement.setString(2, set.getName());
		statement.execute();
		ResultSet rs = statement.getResultSet();
		
		int id = 0;
		if(rs.next()){
			id = rs.getInt("id");
		}
		
		rs.close();
		return id;
	}

}
	