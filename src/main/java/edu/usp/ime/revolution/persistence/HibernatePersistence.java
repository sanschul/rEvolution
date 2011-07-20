package edu.usp.ime.revolution.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import edu.usp.ime.revolution.config.Config;
import edu.usp.ime.revolution.domain.Commit;

public class HibernatePersistence {

	private Session session;
	private final Config config;
	private SessionFactory sessionFactory;
	
	public HibernatePersistence(Config config) {
		this.config = config;
	}
	
	public void initMechanism(List<Class<?>> classes) {
		Configuration configuration = new Configuration();
		configuration.setProperty("hibernate.connection.driver_class", config.get("driver_class"));
		configuration.setProperty("hibernate.connection.url", config.get("connection_string"));
		configuration.setProperty("hibernate.dialect", config.get("dialect"));
		configuration.setProperty("hibernate.connection.username", config.get("db_user"));
		configuration.setProperty("hibernate.connection.password", config.get("db_pwd"));
		configuration.setProperty("hibernate.show_sql", "true");
		configuration.setProperty("hibernate.format_sql", "true");
		configuration.setProperty("hibernate.jdbc.batch_size", "20");
		configuration.setProperty("hibernate.hbm2ddl.auto", "update");
		
		configuration.addAnnotatedClass(Commit.class);
		for (Class<?> clazz : classes) {
			configuration.addAnnotatedClass(clazz);
		}
		
		sessionFactory = configuration.buildSessionFactory();
		session = sessionFactory.openSession();
	}
	
	public void beginTransaction() {
		session.beginTransaction();
	}
	
	public void commit() {
		session.getTransaction().commit();
	}
	
	public Session getSession() {
		return session;
	}

	public void end() {
		session.close();
		sessionFactory.close();
	}
	
}
