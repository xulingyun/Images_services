package org.xulingyun.util;

import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.SessionFactory.SessionFactoryOptions;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
			// Create the SessionFactory from hibernate.cfg.xml
			// return new Configuration().configure().buildSessionFactory(
			// new StandardServiceRegistryBuilder().build() );
			Configuration cfg = new Configuration().configure();
			SessionFactory sf = cfg.buildSessionFactory();
			return sf;
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
