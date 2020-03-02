package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import org.postgresql.Driver;

public class Database {
	 Connection connection;
	 String username;
	 String password;
	 String url;

	public Database() {
		try (FileInputStream input = new FileInputStream(new File("db.properties"))) {

			Properties props = new Properties();
			props.load(input);
			this.username = (String) props.getProperty("username");
			this.password = (String) props.getProperty("password");// as2xv9i16m
			this.url = (String) props.getProperty("URL");

			this.connection = DriverManager.getConnection(this.url, this.username, this.password);
	} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Checking if the user' information existed(if the user has registered?)?
	// if not, return false ,and insert his firstname,lastname,password.
	public boolean checkExistUser(String[] input) {
		try {
			String sql = "SELECT Username FROM USERS WHERE " + "Username= ?";
			PreparedStatement selectStatement = this.connection.prepareStatement(sql);

			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				String Username = resultSet.getString("Username");
				//String lastName = resultSet.getString("last_name");

				System.out.println(Username);
			//	String[] userStrings = user.split(" ");
				if (input[1] == Username) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	// Insert new user' information who has not registered before.
	public void insertUser(String[] user) {
		try {
			PreparedStatement insertStatement = connection
					.prepareStatement("INSERT INTO USERS (Username,Password) " + "VALUES (?,?) ");
			System.out.println("Connection established");
			//String[] userStrings = user.split(",");
			insertStatement.setString(1, user[1]);
			insertStatement.setString(2, user[2]);
	//	insertStatement.setString(3, userStrings[3]);
			insertStatement.executeUpdate();
			int result = insertStatement.executeUpdate();
			System.out.println(result);

		} catch (Exception e) {

		}

	}

	public static void main(String[] args) throws IOException {
		Database d = new Database();

		try (Connection connection = DriverManager.getConnection(d.url, d.username, d.password);
				PreparedStatement insertStatement = connection
						.prepareStatement("INSERT INTO USERS (Username,Password) " + "VALUES (?,?) ");
				PreparedStatement selectStatement = connection
						.prepareStatement("SELECT Username, Password FROM USERS WHERE " + "Username= ?")) {

			System.out.println("Connection established");

			insertStatement.setString(1, "John");
			insertStatement.setString(2, "Password");

			// execute the statement
			insertStatement.executeUpdate();

			// more such data
			insertStatement.setString(1, "Jim");
			insertStatement.setString(2, "Pass");

			insertStatement.executeUpdate();
			
			selectStatement.setString(1, "John");
			try (ResultSet resultSet = selectStatement.executeQuery()) {
				while (resultSet.next()) {
					String username = resultSet.getString("Username");
					String password = resultSet.getString("Password");

					System.out.println(username + " " + password);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			//System.out.println("SQLException");
		}
	}
}
