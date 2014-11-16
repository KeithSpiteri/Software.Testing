package db.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistant.User;

public class DbService {

	private static Logger LOGGER = LoggerFactory.getLogger(DbService.class);
	private static SessionFactory factory;

	private static DbService dbService = null;

	private DbService() {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			LOGGER.error("Failed to create sessionFactory object.", ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static DbService getInstance() {
		if (dbService == null) {
			dbService = new DbService();
		}

		return dbService;
	}

	public boolean addUser(User user) {

		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			LOGGER.error("Hibernate Exception in adding new User", e);
			return false;
		} finally {
			session.close();
		}
		return true;
	}

	public User loadUser(String username) {
		Session session = factory.openSession();
		User user = (User) session.get(User.class, username);
		return user;
	}
}
