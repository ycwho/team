import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import org.postgresql.Driver;

public class Database {
	private static Connection connection;
	private static String username;
	private static String password;
	private static String url;

	public Database() {
		try (FileInputStream input = new FileInputStream(new File("db.properties"))) {

			Properties props = new Properties();
			props.load(input);
			this.username = (String) props.getProperty("username");
			this.password = (String) props.getProperty("password");// as2xv9i16m
			url = (String) props.getProperty("URL");

			this.connection = DriverManager.getConnection(this.url, this.username, this.password);
	} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Checking if the user' information existed(if the user has registered?)?
	// if not, return false ,and insert his firstname,lastname,password.
	public boolean checkExistUser(String user) {
		try {
			String sql = "SELECT first_name, last_name FROM USERS WHERE " + "first_name= ?";
			PreparedStatement selectStatement = this.connection.prepareStatement(sql);

			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");

				System.out.println(firstName + " " + lastName);
				String[] userStrings = user.split(",");
				if (userStrings[1] == firstName && userStrings[2] == lastName) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	// Insert new user' information who has not registered before.
	public void insertUser(String user) {
		try {
			PreparedStatement insertStatement = connection
					.prepareStatement("INSERT INTO USERS (first_name,last_name,password) " + "VALUES (?,?,?) ");
			System.out.println("Connection established");
			String[] userStrings = user.split(",");
			insertStatement.setString(1, userStrings[1]);
			insertStatement.setString(2, userStrings[2]);
		insertStatement.setString(3, userStrings[3]);
			insertStatement.executeUpdate();
			int result = insertStatement.executeUpdate();
			System.out.println(result);

		} catch (Exception e) {

		}

	}

	public static void main(String[] args) throws IOException {

		try (Connection connection = DriverManager.getConnection(url, username, password);
				PreparedStatement insertStatement = connection
						.prepareStatement("INSERT INTO USERS (first_name,last_name,password) " + "VALUES (?,?,?) ");
				PreparedStatement selectStatement = connection
						.prepareStatement("SELECT first_name, last_name FROM USERS WHERE " + "first_name= ?")) {

			System.out.println("Connection established");

			insertStatement.setString(1, "John");
			insertStatement.setString(2, "Smith");
			insertStatement.setString(3, "123");

			// execute the statement
			insertStatement.executeUpdate();

			// more such data
			insertStatement.setString(1, "John");
			insertStatement.setString(2, "Anderson");
			insertStatement.setString(3, "1234");

			insertStatement.executeUpdate();
			
			selectStatement.setString(1, "John");
			try (ResultSet resultSet = selectStatement.executeQuery()) {
				while (resultSet.next()) {
					String firstName = resultSet.getString("first_name");
					String lastName = resultSet.getString("last_name");

					System.out.println(firstName + " " + lastName);
				}
			}
		} catch (Exception e) {
		}
	}
}
