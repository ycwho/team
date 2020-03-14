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
import com.sun.org.apache.xpath.internal.operations.And;

import org.postgresql.Driver;

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
		try (FileInputStream input = new FileInputStream(new File("db2.properties"))) {

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
	 * @param a string contains user name and option,ship positions data¡£
	 * @return
	 */
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

			ResultSet rs = stmt.executeQuery(selectString);System.out.println("0");
			while (rs.next()) {
				positions = rs.getString("shipPositions");System.out.println("llll");
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
