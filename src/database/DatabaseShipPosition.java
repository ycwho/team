

import java.io.File;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import com.sun.org.apache.regexp.internal.recompile;

/**
 * @author Ning Wei
 *
 */
public class DatabaseShipPosition {
	Connection connection;
	String username;
	String password;
	String url;

	public DatabaseShipPosition() {
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

	/**
	 * save shipPosition(the player can create a slot table and insert
	 * shipType,shipPosition in the table) ; save the strategic layout to the slots,
	 * each slot is a table in database: the username is the primary key, and each
	 * ship position takes one row. Before use this method, Server should check
	 * whether the ShipPosition[] is overlapped first
	 * 
	 * @param userName
	 * @param slotName
	 * @param positions positions of the ships
	 * @return
	 */
	public int saveShipPosition(String shipPositionString) {
		// int result = 0; the value returned based on server,so it will change after
		// server finished
		boolean check = false;
		String[] positions = shipPositionString.split(",");
		String username = positions[0];
		String option = positions[1];
		String position = positions[2];
		// Check if the player has pre-saved slots
		try {
			ResultSet resultTable = connection.getMetaData().getTables(null, null, username, null);
			if (resultTable.next()) {
				check = true;

				StringBuffer sbInsert = new StringBuffer();
				sbInsert.append("INSERT INTO " + username);
				sbInsert.append(" (username,option,shipPositions) " + "VALUES (?,?,?)");
				String sqlInsert = sbInsert.toString();
				System.out.println(sqlInsert);

				PreparedStatement insertStatement = connection.prepareStatement(sqlInsert);
				System.out.println("connect");

				insertStatement.setString(1, username);
				System.out.println("u");
				insertStatement.setString(2, option);
				System.out.println("o");
				insertStatement.setString(3, position);
				System.out.println("p");
				insertStatement.executeUpdate();
				// result = insertStatement.executeUpdate();
				System.out.println("Insert successfully");
			}

			// Create a new table according to the warship's information
			if (check == false) {

				StringBuffer sb = new StringBuffer();
				sb.append("CREATE TABLE IF NOT EXISTS " + username);
				sb.append(
						" (userName varchar(7000) ,option varchar(7000) ,shipPositions varchar(7000),foreign key(username) references users(username));");
				String str = sb.toString();
				System.out.println(str);

				Statement stmt = connection.createStatement();
				stmt.executeUpdate(str);
				System.out.println("The table created successfully!");

				// insert values into a new table.

				StringBuffer sbInsert = new StringBuffer();
				sbInsert.append("INSERT INTO " + username);
				sbInsert.append(" (username,option,shipPositions) " + "VALUES (?,?,?)");
				String sqlInsert = sbInsert.toString();
				System.out.println(sqlInsert);

				PreparedStatement insertStatement = connection.prepareStatement(sqlInsert);
				System.out.println("connect");
				insertStatement.setString(1, username);
				insertStatement.setString(2, option);
				insertStatement.setString(3, position);
				insertStatement.executeUpdate();
				// result = insertStatement.executeUpdate();
				System.out.println("Insert successfully");
			}
		}

		catch (

		Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * load the ship Position from database
	 * 
	 * @param slotName
	 * @param username
	 * @return ship positions
	 */
	public String loadShipPosition(String shipPosition) {
		String positions = null;
		String[] loadPosition = shipPosition.split(",");

		String username = loadPosition[0];
		String option = loadPosition[1];
		try {
			// Check if the player has pre-saved slots
			ResultSet resultTable = connection.getMetaData().getTables(null, null, username, null);
			if (resultTable.next()) {

				Statement stmt = connection.createStatement();
				StringBuffer selectSql = new StringBuffer();
				selectSql.append("SELECT positions FROM ");
				selectSql.append(username + "where option= ");
				selectSql.append(option + ";");
				String selectString = selectSql.toString();

				ResultSet rs = stmt.executeQuery(selectString);
				while (rs.next()) {
					positions = rs.getString("shipPositions");
				}
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return positions;
	}

	/**
	 * 
	 * @param slotName
	 * @return true if the player delete the slot successfully.
	 */
	public boolean deleteOption(String optionServer) {
		boolean result = true;
		String[] deleteOption = optionServer.split(",");
		String username = deleteOption[0];
		String option = deleteOption[1];
		try {
			StringBuffer sbDelete = new StringBuffer();
			sbDelete.append("delete from " + username);
			sbDelete.append("where option = " + option + ";");
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
