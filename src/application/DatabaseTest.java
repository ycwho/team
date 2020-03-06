package application;

import java.net.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.core.JavaVersion;
import org.postgresql.util.PSQLException;
import org.postgresql.Driver;
import java.io.*;

/**
 * For Database connection test
 * 
 * @version 2020-02-27
 * @author Yuanchao Hu
 *
 */
public class DatabaseTest implements DatabaseInterface {

	private String url;
	private String user;
	private String password;
	private Connection connection;

	public DatabaseTest() {
		url = "jdbc:postgresql://localhost:5432/postgres";
		user = "postgres";
		password = "postgres";

	}

	public static void main(String[] args) {
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "postgres";

		try {
			System.out.print("Driver-----");
			// Class.forName("org.postgresql.Driver");
			System.out.println("-----Done!");
		} catch (Exception e) {
			
		}

		try (

				Connection con = DriverManager.getConnection(url, user, password);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * FROM account;")) {

			while (rs.next()) {
				System.out.println(rs.getString(2));
			}

			System.out.println("Done!!!");

		} catch (PSQLException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			Logger lgr = Logger.getLogger(JavaVersion.class.getName());
			lgr.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		}

	}

	@Override
	public int run() {

		try {
			this.connection = DriverManager.getConnection(url, user, password);
			return 0;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return 1;
		}

	}

	@Override
	public int quit() {
		try {
			this.connection.close();
			return 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 1;
		}

	}

	@Override
	public int userSignIn(String username, String password) {
		try {
			if (this.connection.isClosed()) {
				int i = 1;

				while (i != 0) {
					i = run();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try (PreparedStatement insertStatement = connection
				.prepareStatement("INSERT INTO account (username,password) VALUES (?,?) ")) {
			insertStatement.setString(1, username);
			insertStatement.setString(2, password);

			// execute the statement
			try {
				insertStatement.executeUpdate();

			} catch (SQLException e) {
				System.out.println("user name is exists");
				e.printStackTrace();
				return 1;
			}

			return 0;

		} catch (SQLException e) {

			Logger lgr = Logger.getLogger(JavaVersion.class.getName());
			lgr.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();

			return 2;
		}

	}

	@Override
	public int userLogIn(String username, String password) {

		try {
			if (this.connection.isClosed()) {
				int i = 1;

				while (i != 0) {
					i = run();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try (PreparedStatement selectStatement = connection
				.prepareStatement("SELECT username, password FROM account WHERE " + "username=? AND password=?")) {
			selectStatement.setString(1, username);
			selectStatement.setString(2, password);

			// execute the statement

			try (ResultSet resultSet = selectStatement.executeQuery()) {
				if (resultSet != null && resultSet.next()) {

					// test if find a name - password pair
					return 0;
				} else {
					return 1;
				}
			} catch (SQLException e) {
				System.out.println("wrong resultSet");
				e.printStackTrace();
				return 2;

			}

		} catch (SQLException e) {

			Logger lgr = Logger.getLogger(JavaVersion.class.getName());
			lgr.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();

			return 2;
		}

	}

	@Override
	public int saveShipPosition(ShipPosition[] positions, int slot) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ShipPosition[] loadShipPosition(String username_or_userId, int slot) {
		// TODO Auto-generated method stub
		return null;
	}

}
