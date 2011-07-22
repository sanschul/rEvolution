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
		configuration.setProperty("hibernate.jdbc.batch_size", "20");
		
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

	public void rollback() {
		session.getTransaction().rollback();
	}
	
}
