package database;

import java.net.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.postgresql.core.JavaVersion;
import org.postgresql.util.PSQLException;
import org.postgresql.Driver;





/**For Database connection test
 * @version 2020-02-27
 * @author Yuanchao Hu
 *
 */
public class DatabaseTest {

	public DatabaseTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "postgres";
        
        try {
        	System.out.print("Driver-----");
        	Class.forName("org.postgresql.Driver");
        	System.out.println("-----Done!");
        }catch(Exception e){
        	
        }
        
        try (

        		Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM account;")) {

            while (rs.next()) {
                System.out.println(rs.getString(2));
            }
            
            System.out.println("Done!!!");
        }catch(PSQLException e) {
        	
            e.printStackTrace();
        }catch(SQLException e) {
            Logger lgr = Logger.getLogger(JavaVersion.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
            e.printStackTrace();
        }

	}

}
