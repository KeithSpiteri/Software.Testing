package db.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistant.Bet;
import persistant.User;

public class DbService {

	private static Logger LOGGER = LoggerFactory.getLogger(DbService.class);
	private static SessionFactory factory;
	private Session session;

	private static DbService dbService = null;

	@SuppressWarnings("deprecation")
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
		Connection connection = null;
		LOGGER.info("Inserting User");

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/sql457634", "root", "");

			LOGGER.info("Created Connection");

			String insertUser = "INSERT INTO sql457634.user"
					+ "(username, password, name, surname, dob, credit_card_number, expiry_date, cvv, premium, free, failed_login) VALUES"
					+ "(?,?,?,?,?,?,?,?,?,?,?)";
			LOGGER.info("Preparing Statment " + user.getName() + " "
					+ user.getSurname() + " " + user.getDob() + " ");

			PreparedStatement preparedStatement = connection
					.prepareStatement(insertUser);
			LOGGER.info("Prepared Statment");
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			LOGGER.info("Filled Statment");
			preparedStatement.setString(3, user.getName());
			preparedStatement.setString(4, user.getSurname());
			preparedStatement.setTimestamp(5, new Timestamp(user.getDob()
					.getTime()));
			preparedStatement.setString(6, user.getCreditCardNumber());
			preparedStatement.setTimestamp(7, new Timestamp(user.getExpiry()
					.getTime()));
			preparedStatement.setString(8, user.getCvv());
			preparedStatement.setBoolean(9, user.isPremium());
			preparedStatement.setBoolean(10, user.isFree());
			preparedStatement.setInt(11, user.getFailedLogins());
			
			// execute insert SQL stetement

			preparedStatement.executeUpdate();
			LOGGER.info("Executed Statment");
		} catch (Exception e) {
			LOGGER.error("ERROR", e);
			return false;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean addBet(Bet bet) {
		Connection connection = null;
		LOGGER.info("Inserting User");

		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/sql457634", "root", "");

			LOGGER.info("Created Connection");

			String insertUser = "INSERT INTO sql457634.bet"
					+ "(risk_level, amount, user_id) VALUES" + "(?,?,?)";

			PreparedStatement preparedStatement = connection
					.prepareStatement(insertUser);
			LOGGER.info("Prepared Statment");
			preparedStatement.setString(1, bet.getRiskLevel());
			preparedStatement.setFloat(2, bet.getAmount());
			preparedStatement.setString(3, bet.getUserName());

			// execute insert SQL stetement
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("ERROR", e);
			return false;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public User loadUser(String username) {
		User user = null;
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/sql457634", "root", "");

			Statement statement = connection.createStatement();
			ResultSet resultset = statement
					.executeQuery("select * from sql457634.user where username = \""
							+ username + "\"");
			while (resultset.next()) {
				user = new User();
				user.setUsername(username);
				user.setPassword(resultset.getString("password"));
				user.setLockedTill(null);
				user.setFailedLogins(Integer.parseInt(resultset
						.getString("failed_login")));
				String fString = resultset.getString("free");
				if (fString.equals("1"))
					user.setFree(true);
				else
					user.setPremium(true);
			}
		} catch (Exception e) {

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	public boolean update(Object toSave) {
//		Transaction tx = null;
//		try {
//			session = factory.openSession();
//			tx = session.beginTransaction();
//			session.update(toSave);
//			tx.commit();
//		} catch (HibernateException e) {
//			try {
//				try {
//					if (tx != null)
//						tx.rollback();
//					LOGGER.error("Hibernate Exception in updating user info", e);
//				} catch (Exception exc) {
//				}
//			} catch (Exception exc) {
//			}
//			return false;
//		} finally {
//			try {
//				session.close();
//			} catch (Exception e) {
//			}
//		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/sql457634", "root", "");

			Statement statement = connection.createStatement();
			statement
					.execute("Update sql457634.user SET failed_login = "+ ((User) toSave).getFailedLogins()+1 +" where username = \""
							+ ((User) toSave).getUsername() + "\"");
			
		} catch (Exception e) {

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public boolean deleteUser(User user) {

		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/sql457634", "root", "");

			Statement statment = connection.createStatement();
			String sql = "DELETE FROM sql457634.user where username = \""
					+ user.getUsername() + "\"";
			statment.executeUpdate(sql);
		} catch (Exception exc) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	public List<Bet> getUserBets(User user) {
		session = factory.openSession();

		Criteria cr = session.createCriteria(Bet.class);
		cr.add(Restrictions.eq("userName", user.getUsername()));
		@SuppressWarnings("rawtypes")
		List result = cr.list();

		try {
			session.close();
		} catch (Exception e) {
		}

		return (List<Bet>) result;
	}

	public int countBets(User user) {
		int count = 0;

		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost/sql457634", "root", "");

			Statement statment = connection.createStatement();
			ResultSet r = statment
					.executeQuery("SELECT COUNT(*) AS rowcount FROM sql457634.bet where user_id = \""
							+ user.getUsername() + "\"");
			r.next();
			count = r.getInt("rowcount");
			r.close();
		} catch (Exception exc) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/*
		 * session = factory.openSession(); Long count = (long) 0; try {
		 * Criteria crit = session.createCriteria(Bet.class);
		 * crit.setProjection(Projections.rowCount());
		 * crit.add(Restrictions.eq("userName", user.getUsername())); count =
		 * (Long) crit.uniqueResult(); } catch (Exception e) {
		 * LOGGER.error("Error occured################################", e); }
		 * try { session.close(); } catch (Exception e) { }
		 * 
		 */
		LOGGER.info("bets Count returning: " + count);
		return count;
	}
}
