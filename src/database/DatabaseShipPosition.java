import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

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
	 * shipType,shipPosition in the table) ;
	 * save the strategic layout to the slots,
	 * each slot is a table in database: the username is the primary key, and each
	 * ship position takes one row. Before use this method, Server should check
	 * whether the ShipPosition[] is overlapped first
	 * 
	 * @param userName
	 * @param slotName
	 * @param positions positions of the ships
	 * @return
	 */
	public int saveShipPosition(String userName, String slotName, ShipPosition[] positions) {
		// int result = 0; the value returned based on server,so it will change after server finished
		boolean check = false;

		// Check if the player has pre-saved slots
		try {
			ResultSet resultTable = connection.getMetaData().getTables(null, null, slotName, null);
			if (resultTable.next()) {
				check = true;
				System.out.println("The slot has existed!");
			}

			// Create a new slot table according to the warship's information
			if (check == false) {

				StringBuffer sb = new StringBuffer();
				sb.append("CREATE TABLE IF NOT EXISTS " + slotName);
				sb.append(" (userName CHAR(20) ,shipType int, status int,xPosition int,yPosition int);");
				String str = sb.toString();
				System.out.println(str);

				Statement stmt = connection.createStatement();
				stmt.executeUpdate(str);
				System.out.println("The slot created successfully!");

				// insert values into a new slot table.
				for (int i = 0; i < positions.length; i++) {
					StringBuffer sbInsert = new StringBuffer();
					sbInsert.append("INSERT INTO " + slotName);
					sbInsert.append(" (Username,shipType,status,xPosition,yPosition) " + "VALUES (?,?,?,?,?)");
					String sqlInsert = sbInsert.toString();
					System.out.println(sqlInsert);

					PreparedStatement insertStatement = connection.prepareStatement(sqlInsert);
					System.out.println("connect");
					insertStatement.setString(1, userName);
					System.out.println("1");
					insertStatement.setInt(2, positions[i].getType());
					insertStatement.setInt(3, positions[i].getStatus());
					insertStatement.setInt(4, positions[i].getxPosition());
					insertStatement.setInt(5, positions[i].getyPosition());
					insertStatement.executeUpdate();
					// result = insertStatement.executeUpdate();
					System.out.println("Insert successfully");
				}
			}
		}

		catch (Exception e) {
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
	public ArrayList<ShipPosition> loadShipPosition(String userName, String slotName) {
		ArrayList<ShipPosition> shipPositions = new ArrayList<ShipPosition>();
		try {
			// Check if the player has pre-saved slots
			ResultSet resultTable = connection.getMetaData().getTables(null, null, slotName, null);
			if (resultTable.next()) {

				Statement stmt = connection.createStatement();
				StringBuffer selectSql = new StringBuffer();
				selectSql.append("SELECT * FROM ");
				selectSql.append(slotName);
				String selectString = selectSql.toString();

				ResultSet rs = stmt.executeQuery(selectString);

				while (rs.next()) {
					int type = rs.getInt("shipType");
					int status = rs.getInt("status");
					int x = rs.getInt("xPosition");
					int y = rs.getInt("yPosition");
					ShipPosition position = new ShipPosition(type, status, x, y);
					shipPositions.add(position);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shipPositions;
	}

/**
 * 
 * @param slotName
 * @return true if the player delete the slot successfully.
 */
	public boolean dropSlot(String slotName) {
		boolean result = true;
		try {
			StringBuffer sbDelete = new StringBuffer();
			sbDelete.append("drop table " + slotName);
			String sqlDelete = sbDelete.toString();

			Statement st = connection.createStatement();
			st.executeUpdate(sqlDelete);

			StringBuffer sbSelect = new StringBuffer();
			sbSelect.append("select * from " + slotName);
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
