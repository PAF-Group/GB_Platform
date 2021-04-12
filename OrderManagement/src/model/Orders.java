package model;
/* 
 * @author W.G. YASIRU RANDIKA 
 * IT19131184
 * 
 * */

import java.sql.*;

public class Orders { 
	/*Method for connect to the database
	 * 
	 * @return Connection
	*/
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gb_ordermanagement", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}