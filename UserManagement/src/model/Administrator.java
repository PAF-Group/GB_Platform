/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package model;

import java.sql.*;

public class Administrator {
//	Database/UserDB Connectivity; @return Connection ------------------------------------------------------------------------------------------------------------
	private Connection connect() {
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

//	The method to create a new Administrator => Administrator registration --------------------------------------------------------------------------------------
	public String createAdministrator(String empID, String firstName, String lastName, String userPhone, String email, String password, String accStatus) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to insert a new record to the Administrator table & prepared statements
			String query = "INSERT INTO `userdb`.`administrator` (`employee_id`, `first_name`, `last_name`, `user_phone`,"
					+ " `user_email`, `password`, `account_status`)" + " VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			//preparedStmt.setInt(1, 0);
			preparedStmt.setInt(1, Integer.parseInt(empID));
			preparedStmt.setString(2, firstName);
			preparedStmt.setString(3, lastName);
			preparedStmt.setString(4, userPhone);
			preparedStmt.setString(5, email);
			preparedStmt.setString(6, password);
			preparedStmt.setString(7, accStatus);
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "A new Administrator created successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error occurred while creating the user.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//  The method to read all the Administrators => for the Administrator & GB Member to View all Administrators ---------------------------------------------------
	public String getAdministrators() {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// Prepare a HTML table to display the Administrators
			output = "<table border='1'>" + "<tr>" + "<th>Employee ID</th>" + "<th>First Name</th>" + "<th>Last Name</th>" + "<th>Phone</th>"
					+  "<th>Email</th>" + "<th>Account Status</th>" + "<th>Created At</th>" + "<th>Updated At</th>" + "</tr>";

			// The query to select all records from Administrator table
			String query = "SELECT `employee_id`, `first_name`, `last_name`, `user_phone`, `user_email`, `account_status`, `created_at`, `updated_at` FROM `userdb`.`administrator`";
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// Iterate through all the records in the result set
			while (rs.next()) {
				String empID = Integer.toString(rs.getInt("employee_id"));
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String phone = rs.getString("user_phone");
				String email = rs.getString("user_email");
				//String password = rs.getString("password");
				String accStatus = rs.getString("account_status");
				String createdAt = rs.getTimestamp("created_at").toString();
				String updatedAt = rs.getTimestamp("updated_at").toString();
				
				// Add the record in to the HTML table
				output += "<tr><td>" + empID + "</td>";
				output += "<td>" + firstName + "</td>";
				output += "<td>" + lastName + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + accStatus + "</td>";
				output += "<td>" + createdAt + "</td>";
				output += "<td>" + updatedAt + "</td></tr>";
				
			}
			
			// Close the database connection
			con.close();
			
			// End of the HTML table
			output += "</table>";
			
		} catch (Exception e) {
			// Failure
			output = "An error occurred while reading the Administrator records.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to update an Administrator => a service for Administrator ----------------------------------------------------------------------------------------
	public String updateAdministrator(String userID, String empID, String firstName, String lastName, String userPhone, String email) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for updating.";
				
			}
			
			// The query to Update the certain record in the Administrator table & prepared statements
			String query = "UPDATE `userdb`.`administrator` SET `employee_id`=?, `first_name`=?, `last_name`=?, `user_phone`=?, `user_email`=? WHERE `user_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(2, Integer.parseInt(empID));
			preparedStmt.setString(3, firstName);
			preparedStmt.setString(4, lastName);
			preparedStmt.setString(5, userPhone);
			preparedStmt.setString(6, email);
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Administrator record was Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the Administrator record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to disable an existing Administrator by using user ID => for Administrator -------------------------------------------------------------------------------
	public String disableAdministrator(String userID, String accStatus) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for disabling.";
				
			}
			
			// The query to disable an Administrator & prepared statements
			String query = "UPDATE `userdb`.`administrator` SET `account_status`=? WHERE `user_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(2, accStatus);
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Administrator was disabled successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while disabling the Administrator.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
}
