/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package utility;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnectivity {
//	Database/UserDB Connectivity; @return Connection ------------------------------------------------------------------------------------------------------------
	public static Connection connect() {
		Connection con = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the UserDB details: DBServer/DBName, user-name, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/userdb", "root", "qwerty");
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return con;
		
	}
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
}
