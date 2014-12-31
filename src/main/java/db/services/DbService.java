package db.services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistant.Bet;
import persistant.User;

public class DbService {

	private static Logger LOGGER = LoggerFactory.getLogger(DbService.class);
	private static SessionFactory factory;
	private Session session;

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

		Transaction tx = null;
		try {
			session = factory.openSession();
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

	public boolean addBet(Bet bet) {

		Transaction tx = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.save(bet);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			LOGGER.error("Hibernate Exception in placing new bet", e);
			return false;
		} finally {
			session.close();
		}
		return true;
	}

	public User loadUser(String username) {
		session = factory.openSession();
		User user = (User) session.get(User.class, username);
		try {
			session.close();
		} catch (Exception e) {
		}
		return user;
	}

	public boolean update(Object toSave) {
		Transaction tx = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.update(toSave);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			LOGGER.error("Hibernate Exception in updating user info", e);
			return false;
		} finally {
			session.close();
		}
		return true;
	}

	public boolean deleteUser(User user) {

		Transaction tx = null;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.delete(user);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			LOGGER.error("Hibernate Exception in deleting User", e);
			return false;
		} finally {
			session.close();
		}
		return true;
	}

	public List<Bet> getUserBets(User user) {
		session = factory.openSession();
		Query query = session.createSQLQuery(
				"select * from bet  where user_id = :user").setParameter(
				"user", user.getUsername());
		List<Bet> result = (List<Bet>) query.list();

		try {
			session.close();
		} catch (Exception e) {
		}

		return result;
	}

	public int countBets(User user) {
		session = factory.openSession();
		int count = ((Integer) session.createQuery("select count(*) from bet where user_id='"+user.getUsername()+"'").iterate()
				.next()).intValue();
		try {
			session.close();
		} catch (Exception e) {
		}
		return count;
	}
}
