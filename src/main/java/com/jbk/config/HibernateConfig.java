package com.jbk.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.jbk.entity.Author;
import com.jbk.entity.Book;
import com.jbk.entity.Borrower;

public class HibernateConfig {

	public static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration();
		configuration.configure().addAnnotatedClass(Book.class).addAnnotatedClass(Author.class).addAnnotatedClass(Borrower.class);

		SessionFactory sessionFactory = configuration.buildSessionFactory();

		return sessionFactory;
	}
}
