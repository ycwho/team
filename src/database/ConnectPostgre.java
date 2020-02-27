package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import org.postgresql.Driver;

public class ConnectPostgre {
    public static void main(String[] args) {
    	try {
    	Class.forName("org.postgresql.Driver");
    	}catch(ClassNotFoundException e){
    		
    	}
        Connection c = null;
        try {
            String url = "jdbc:postgresql://localhost:5432/asos";
            Properties props = new Properties();
            props.setProperty("user", "asos");
            props.setProperty("password", "as2xv9i16m");
            c = DriverManager.getConnection(url, props);
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS USERS" +
                    "(ID SERIAL PRIMARY KEY ," +
                    "NAME CHAR(20) NOT NULL ," +
                    "PASSWORD CHAR(20) NOT NULL)";
            stmt.executeUpdate(sql);

            String insertSql = "INSERT INTO USERS (NAME,PASSWORD)" +
                    "VALUES ('Tom','222')";
            stmt.executeUpdate(insertSql);

            String selectSql = "SELECT * FROM USERS";
            ResultSet rs = stmt.executeQuery(selectSql);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String password = rs.getString("PASSWORD");
                System.out.println(id + " " + name + " " + password);
            }
            rs.close();

            String updateSql = "UPDATE USERS SET NAME = '777' WHERE ID=1";
            stmt.executeUpdate(updateSql);

            rs = stmt.executeQuery(selectSql);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String password = rs.getString("PASSWORD");
                System.out.println(id + " " + name + " " + password);
            }
            rs.close();

            String deleteSql = "DELETE FROM USERS WHERE ID=1";
            stmt.executeUpdate(deleteSql);
            rs = stmt.executeQuery(selectSql);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String name = rs.getString("NAME");
                String password = rs.getString("PASSWORD");
                System.out.println(id + " " + name + " " + password);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}
