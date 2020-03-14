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
	public boolean checkExistUser(String user, String pass) {
		try {
			String sql = "select username from users where " + "username= ? and password = ?";
			PreparedStatement selectStatement = this.connection.prepareStatement(sql);
			selectStatement.setString(1, user);
			selectStatement.setString(2, pass);
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				String username = resultSet.getString("username").substring(0, user.length());
				if (user.equals(username)) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	// Checking if the user' information existed(if the user has registered?)?
	// if not, return false ,and insert his username,password.
	public boolean checkPassword(String user, String pass) {
		try {
			String sql = "select password from users where " + "username= ? and password = ?";
			PreparedStatement selectStatement = this.connection.prepareStatement(sql);
			selectStatement.setString(1, user);
			selectStatement.setString(2, pass);
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				String password = resultSet.getString("password").substring(0, pass.length());
				if (pass.equals(password)) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	// Insert new user' information who has not registered before.
	public void insertUser(String user, String pass) {
		try {
			String sql = "INSERT INTO USERS (Username,Password) " + "VALUES (?,?)";
			PreparedStatement insertStatement = connection.prepareStatement(sql);
			insertStatement.setString(1, user);
			insertStatement.setString(2, pass);
//			insertStatement.executeUpdate();
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

			insertStatement.setString(1, "Tom2");
			insertStatement.setString(2, "Password");

			// execute the statement
			insertStatement.executeUpdate();

			// more such data
			insertStatement.setString(1, "Jim2");
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
			// System.out.println("SQLException");
		}
	}

	public int saveShipPosition(String username, String option, String position) {
		int result = 0;// if database insert successfully,return 0.
		boolean check = false;

		try {

			String selectSql = "SELECT * FROM SHIPPOSITION;";
			PreparedStatement selectStatement = connection.prepareStatement(selectSql);
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				String existedUsername = resultSet.getString("username");
				String existedOption = resultSet.getString("option");
				String existedPosition = resultSet.getString("shippositions");

				// Check if the player has pre-saved option,if existed update ship positions.
				if (existedUsername.equals(username) && existedOption.equals(option)) {
					check = true;
					StringBuffer updateString = new StringBuffer();
					updateString.append("UPDATE SHIPPOSITION SET SHIPPOSITIONS = '" + position
							+ "' WHERE SHIPPOSITIONS = '" + existedPosition + "' AND USERNAME = '" + username + ""
							+ "' AND OPTION = '" + option + "';");
					String updateSql = updateString.toString();
					System.out.println(updateSql);
					Statement stmt = connection.createStatement();
					stmt.executeUpdate(updateSql);
					break;
				}
			}
			if (check == false) {
				PreparedStatement insertStatement = connection.prepareStatement(
						"INSERT INTO SHIPPOSITION (username,option,shipPositions)" + "VALUES (?,?,?)");

				System.out.println("connect");
				insertStatement.setString(1, username);
				insertStatement.setString(2, option);
				insertStatement.setString(3, position);
				insertStatement.executeUpdate();
				System.out.println("Insert successfully");
			}

		}

		catch (

		Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * load the ship Position from database
	 * 
	 * @param option
	 * @param username
	 * @return ship positions
	 */
	public String loadShipPosition(String username, String option) {
		String positions = "";
//		String[] loadPosition = shipPosition.split(" ");
//
//		String username = loadPosition[1];
//		String option = loadPosition[2];
		try {
			// Check if the player has pre-saved options
			Statement stmt = connection.createStatement();
			StringBuffer selectSql = new StringBuffer();
			selectSql.append("SELECT shippositions FROM SHIPPOSITION WHERE USERNAME = '" + username + "' AND OPTION = '"
					+ option + "';");
			String selectString = selectSql.toString();
			System.out.println(selectString);

			ResultSet rs = stmt.executeQuery(selectString);
			System.out.println("0");
			if (rs != null && rs.next()) {
				positions = rs.getString("shipPositions");
			} else {
				positions = "EMPTY";
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return positions;
	}

	/**
	 * 
	 * @param OPTION
	 * @return true if the player delete the slot successfully.
	 */
	public boolean deleteOption(String optionServer) {
		boolean result = true;
		String[] deleteOption = optionServer.split(",");
		String username = deleteOption[0];
		String option = deleteOption[1];
		try {
			StringBuffer sbDelete = new StringBuffer();
			sbDelete.append("delete from shipposition where option =");
			sbDelete.append(option + "and username =" + username + ";");
			String sqlDelete = sbDelete.toString();

			Statement st = connection.createStatement();
			st.executeUpdate(sqlDelete);

			StringBuffer sbSelect = new StringBuffer();
			sbSelect.append("select * from " + username + ";");
			String sqlSelect = sbSelect.toString();

			ResultSet rs = st.executeQuery(sqlSelect);

			if (rs.next()) {
				result = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
