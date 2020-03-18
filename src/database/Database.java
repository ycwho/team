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
			this.password = (String) props.getProperty("password");
			this.url = (String) props.getProperty("URL");

			this.connection = DriverManager.getConnection(this.url, this.username, this.password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Checking whether the user's information existed(if the user has registered?)?
	 * if not, return false ,and insert his user name,password.
	 * 
	 * @param user
	 * @param pass
	 * @return true the user' data existed
	 */
	public boolean checkExistUser(String user, String pass) {
		boolean check = false;
		try {
			String sql = "select username from users;";
			PreparedStatement selectStatement = this.connection.prepareStatement(sql);
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				String username = resultSet.getString("username");

				if (user.equals(username)) {
					check = true;
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return check;
	}

	/**
	 * 
	 * Checking whether the user's password right? if not, return false .
	 * 
	 * @param user
	 * @param pass
	 * @return true the password is right
	 */
	public boolean checkPassword(String user, String pass) {
		boolean check = false;
		try {
			String sql = "select password from users where " + "username= ?";
			PreparedStatement selectStatement = this.connection.prepareStatement(sql);
			selectStatement.setString(1, user);
			ResultSet resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				String password = resultSet.getString("password");
				if (pass.equals(password)) {
					check = true;
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return check;
	}

	/**
	 * Insert new user' information who has not registered before.
	 * 
	 * @param user
	 * @param pass
	 */
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

	/**
	 * 
	 * @param username
	 * @param option
	 * @param position
	 * @return if database insert successfully,return 0
	 */
	public int saveShipPosition(String username, String option, String position) {
		int result = 1;// if database insert successfully,return 0.
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
					System.out.println("Update successfully");
					result = result - 1;
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
				result = result - 1;
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
	public boolean deleteOption(String username, String option) {
		boolean result = true;
		try {
			StringBuffer sbDelete = new StringBuffer();
			sbDelete.append("delete from shipposition where option ='");
			sbDelete.append(option + "' and username ='" + username + "';");
			String sqlDelete = sbDelete.toString();
			System.out.println(sqlDelete);

			Statement st = connection.createStatement();
			st.executeUpdate(sqlDelete);

			StringBuffer select = new StringBuffer();
			select.append("select * from shipposition where option ='");
			select.append(option + "' and username ='" + username + "';");
			String sqlSelect = sbDelete.toString();

			ResultSet rs = st.executeQuery(sqlSelect);

			if (rs != null && rs.next()) {
				result = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * record score,if three people play the game, the winner get 2 and the other two loser get -1 respectively.
	 * @param user
	 * @param score
	 * @return 0 if record score successfully.
	 */
	public int recordScore(String playerName, int score) {
		boolean check = false;
		int result = 1;
		int totalScore = 0;
		try {
			PreparedStatement statement = this.connection.prepareStatement("SELECT USERNAME FROM SCORE;");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String username = resultSet.getString("USERNAME");
				
				if (username.equals(playerName)) {

					String selectSql = "SELECT total_score FROM SCORE WHERE " + "USERNAME = '"+username+"';";
					System.out.println(selectSql);
					PreparedStatement selectStatement = this.connection.prepareStatement(selectSql);
					ResultSet rs = selectStatement.executeQuery();

					while (rs.next()) {
						int s=rs.getInt("total_score");
						totalScore =s + (score);

						StringBuffer updateString = new StringBuffer();
						updateString.append("UPDATE SCORE SET total_score = " + totalScore + " WHERE USERNAME = '"
								+ username + "';");
						String updateSql = updateString.toString();

						PreparedStatement updateStatement = connection.prepareStatement(updateSql);
						Statement stmt = connection.createStatement();
						stmt.executeUpdate(updateSql);
						System.out.println("Update successfully");
						result = result - 1;					
					}
					check = true;
					break;

				}
			}

			if (check == false) {

				PreparedStatement insertStatement = connection
						.prepareStatement("INSERT INTO SCORE (USERNAME,TOTAL_SCORE)" + "VALUES (?,?)");

				insertStatement.setString(1, playerName);
				insertStatement.setInt(2, score);
				insertStatement.executeUpdate();
				result = result - 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

//public static void main(String[] args) throws IOException {
//	Database d = new Database();
//
//	try (Connection connection = DriverManager.getConnection(d.url, d.username, d.password);
//			PreparedStatement insertStatement = connection
//					.prepareStatement("INSERT INTO USERS (Username,Password) " + "VALUES (?,?) ");
//			PreparedStatement selectStatement = connection
//					.prepareStatement("SELECT Username, Password FROM USERS WHERE " + "Username= ?")) {
//
//		System.out.println("Connection established");
//
//		insertStatement.setString(1, "Tom2");
//		insertStatement.setString(2, "Password");
//
//		// execute the statement
//		insertStatement.executeUpdate();
//
//		// more such data
//		insertStatement.setString(1, "Jim2");
//		insertStatement.setString(2, "Pass");
//
//		insertStatement.executeUpdate();
//
//		selectStatement.setString(1, "John");
//		try (ResultSet resultSet = selectStatement.executeQuery()) {
//			while (resultSet.next()) {
//				String username = resultSet.getString("Username");
//				String password = resultSet.getString("Password");
//
//				System.out.println(username + " " + password);
//			}
//		}
//	} catch (SQLException e) {
//		e.printStackTrace();
//		// System.out.println("SQLException");
//	}
}

