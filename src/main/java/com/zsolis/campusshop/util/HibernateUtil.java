package com.zsolis.campusshop.util;

import org.hibernate.*;
import org.hibernate.boot.registry.*;
import org.hibernate.cfg.*;
import org.springframework.stereotype.*;

@Component
public class HibernateUtil {
	private SessionFactory sessionFactory;
	
	public HibernateUtil() {
		try {
			Configuration configure = new Configuration();
			configure.setNamingStrategy(new ImprovedNamingStrategy() {
				private static final long serialVersionUID = -6720392369389199107L;
				
				@Override
				public String classToTableName(String className) {
					// TODO Auto-generated method stub
					return "GLM_" + className.toUpperCase();
				}

				@Override
				public String propertyToColumnName(String propertyName) {
					// TODO Auto-generated method stub
					return propertyName.toUpperCase();
				}
				
				@Override
				public String tableName(String tableName) {
					// TODO Auto-generated method stub
					return "GLM_" + tableName.toUpperCase();
				}
				
				@Override
				public String columnName(String columnName) {
					// TODO Auto-generated method stub
					return columnName.toUpperCase();
				}
			});
			configure.configure();
			StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
			                    .applySettings(configure.getProperties())
			                    .build();
			sessionFactory = configure.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
