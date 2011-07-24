package br.com.caelum.revolution.persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import br.com.caelum.revolution.config.Config;
import br.com.caelum.revolution.domain.Artifact;
import br.com.caelum.revolution.domain.Author;
import br.com.caelum.revolution.domain.Commit;
import br.com.caelum.revolution.domain.Modification;


public class HibernatePersistence {

	private Session session;
	private final Config config;
	private static SessionFactory sessionFactory;
	
	public HibernatePersistence(Config config) {
		this.config = config;
	}
	
	public void initMechanism(List<Class<?>> classes) {
		if(sessionFactory==null) {
			if(sessionFactory==null) {
				Configuration configuration = new Configuration();
				configuration.setProperty("hibernate.connection.driver_class", config.get("driver_class"));
				configuration.setProperty("hibernate.connection.url", config.get("connection_string"));
				configuration.setProperty("hibernate.dialect", config.get("dialect"));
				configuration.setProperty("hibernate.connection.username", config.get("db_user"));
				configuration.setProperty("hibernate.connection.password", config.get("db_pwd"));
				configuration.setProperty("hibernate.jdbc.batch_size", "20");
				
				configuration.setProperty("hibernate.c3p0.acquire_increment", "5");
				configuration.setProperty("hibernate.c3p0.idle_test_period", "100");
				configuration.setProperty("hibernate.c3p0.max_size", "20");
				configuration.setProperty("hibernate.c3p0.max_statements", "0");
				configuration.setProperty("hibernate.c3p0.min_size", "5");
				configuration.setProperty("hibernate.c3p0.timeout", "1800");
		
				
				configuration.addAnnotatedClass(Commit.class);
				configuration.addAnnotatedClass(Artifact.class);
				configuration.addAnnotatedClass(Modification.class);
				configuration.addAnnotatedClass(Author.class);
				
				for (Class<?> clazz : classes) {
					configuration.addAnnotatedClass(clazz);
				}
				
				if(config.get("create_tables").equals("true")) {
					SchemaExport se = new SchemaExport(configuration);
					se.create(false, true);
				}
				
				sessionFactory = configuration.buildSessionFactory();
			}
		}
	}
	
	public void beginTransaction() {
		session = sessionFactory.openSession();
		session.beginTransaction();
	}
	
	public void commit() {
		session.flush();
		session.getTransaction().commit();
		session.close();
	}
	
	public Session getSession() {
		return session;
	}

	public void end() {
		sessionFactory.close();
	}

	public void rollback() {
		session.getTransaction().rollback();
		session.close();
	}
	
}
