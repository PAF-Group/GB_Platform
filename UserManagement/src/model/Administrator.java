/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package model;

import java.sql.*;
import com.DatabaseConnectivity;

public class Administrator {
//	The method to create a new Administrator => Administrator registration --------------------------------------------------------------------------------------
	public String createAdministrator(String empID, String firstName, String lastName, String userPhone, String email, String password, String role, String accStatus) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to insert a new record to the User table & prepared statements
			String query1 = "INSERT INTO `userdb` .`user` (`user_email`, `password`, `user_role`, `accStatus`)" + " VALUES (?, ?, ?, ?)";
			
			PreparedStatement preparedStmt1 = con.prepareStatement(query1);
			
			// binding values
			preparedStmt1.setString(5, email);
			preparedStmt1.setString(6, password);
			preparedStmt1.setString(7, role);
			preparedStmt1.setString(8, accStatus);
			
			// execute the statement
			preparedStmt1.execute();
			
			// The query to insert a new record to the Administrator table & prepared statements
			String query2 = "INSERT INTO `userdb`.`administrator` (`employee_id`, `first_name`, `last_name`, `user_phone`)" + " VALUES (?, ?, ?, ?)";
			
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);
			
			// binding values
			//preparedStmt.setInt(1, 0);
			preparedStmt2.setInt(1, Integer.parseInt(empID));
			preparedStmt2.setString(2, firstName);
			preparedStmt2.setString(3, lastName);
			preparedStmt2.setString(4, userPhone);
			
			// execute the statement
			preparedStmt2.execute();
			
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
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// Prepare a HTML table to display the Administrators
			output = "<table border='1'>" + "<tr>" + "<th>Employee ID</th>" + "<th>First Name</th>" + "<th>Last Name</th>" + "<th>Phone</th>"
					+  "<th>Email</th>" + "<th>Account Status</th>" + "<th>Created At</th>" + "<th>Updated At</th>" + "</tr>";

			// The query to select all records from Administrator table
			String query1 = "SELECT `employee_id`, `first_name`, `last_name`, `user_phone`, `user_id`, `created_at`, `updated_at` FROM `userdb`.`administrator`";
			
			Statement stmt1 = con.createStatement();
			ResultSet set1 = stmt1.executeQuery(query1);
			
			// Iterate through all the records in the result set
			while (set1.next()) {
				String empID = Integer.toString(set1.getInt("employee_id"));
				String firstName = set1.getString("first_name");
				String lastName = set1.getString("last_name");
				String phone = set1.getString("user_phone");
				String userID = set1.getString("user_id");
				String createdAt = set1.getTimestamp("created_at").toString();
				String updatedAt = set1.getTimestamp("updated_at").toString();
				
				// The query to select the certain Administrator record from the User table
				String query2 = "SELECT `user_email`, `accStatus` FROM `userdb`.`user` WHERE `user_id` = " + userID;
				
				Statement stmt2 = con.createStatement();
				ResultSet set2 = stmt2.executeQuery(query2);
				
				String email = set2.getString("user_email");
				String accStatus = set2.getString("accStatus");
				
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
	public String updateAdministrator(String adminID, String empID, String firstName, String lastName, String userPhone) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for updating.";
				
			}
			
			// The query to Update the certain record in the Administrator table & prepared statements
			String query = "UPDATE `userdb`.`administrator` SET `employee_id`=?, `first_name`=?, `last_name`=?, `user_phone`=? WHERE `admin_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(2, Integer.parseInt(empID));
			preparedStmt.setString(3, firstName);
			preparedStmt.setString(4, lastName);
			preparedStmt.setString(5, userPhone);
			preparedStmt.setInt(1, Integer.parseInt(adminID));
			
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

//	The method to update an Administrator's User-email & Password => a service for Administrator ----------------------------------------------------------------
	public String updateEmailPassword(String userID, String userEmail, String password) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for updating.";
				
			}
			
			// The query to Update the certain record in the User table & prepared statements
			String query = "UPDATE `userdb`.`user` SET `user_email`=?, `password`=? WHERE `user_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(2, userEmail);
			preparedStmt.setString(3, password);
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Administrator User-email & Password records was Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the Administrator record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//	The method to disable an existing Administrator by using user ID => for Administrator -----------------------------------------------------------------------
	public String disableAdministrator(String adminID, String accStatus) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for disabling.";
				
			}
			
			// The query to retrieve user ID from the Administrator table
			String query1 = "SELECT `user_id` FROM `userdb`.`administrator` WHERE `admin_id` = " + adminID;
			
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery(query1);
			
			String userID = set.getString("user_id");
			
			// The query to disable an Administrator & prepared statements
			String query2 = "UPDATE `userdb`.`user` SET `account_status`=? WHERE `user_id`=" + userID;
			
			PreparedStatement preparedStmt = con.prepareStatement(query2);
			
			// binding values
			preparedStmt.setString(2, accStatus);
			
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
