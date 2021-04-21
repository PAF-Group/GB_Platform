/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package model;

import java.sql.*;
import utility.DatabaseConnectivity;

public class User {
//	The method to create a new User => User registration --------------------------------------------------------------------------------------------------------
	public String createUser(String userEmail, String password, String userRole, String accStatus) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to insert a new record to the User table & prepared statements
			String query = "INSERT INTO `userdb`.`user` (`user_email`, `password`, `user_role`, `account_status`)" + " VALUES (?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, userEmail);
			preparedStmt.setString(2, password);
			preparedStmt.setString(3, userRole);
			preparedStmt.setString(4, accStatus);
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "A new User created successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error occurred while creating the user.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//  The method to read all the Users => for the Administrator & GB Member to View all Users ---------------------------------------------------------------------
	public String getUsers() {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// Prepare a HTML table to display the Users
			output = "<table border='1'>" + "<tr>" + "<th>User Email</th>" + "<th>User Role</th>" + "<th>Account Status</th>" + "<th>Created At</th>" 
			+ "<th>Updated At</th>" + "</tr>";
			
			// The query to select all records from User table
			String query = "SELECT `user_email`, `user_role`, `account_status`, `created_at`, `updated_at` FROM `userdb`.`user`";
			
			Statement stmt = con.createStatement();
			ResultSet set = stmt.executeQuery(query);
			
			// Iterate through all the records in the result set
			while (set.next()) {
				// Reading values from the Result Set - set1
				String userEmail = set.getString("user_email");
				String userRole = set.getString("user_role");
				String accStatus = set.getString("account_status");
				String createdAt = set.getTimestamp("created_at").toString();
				String updatedAt = set.getTimestamp("updated_at").toString();
				
				// Add the record in to the HTML table
				output += "<tr><td>" + userEmail + "</td>";
				output += "<td>" + userRole + "</td>";
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
			output = "An error has occurred while reading the User records.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//	The method to update a User => a service for all Users ------------------------------------------------------------------------------------------------------
	public String updateUser(String userID, String userEmail, String password, String role) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for updating.";
				
			}
			
			// The query to Update the certain record in the User table & prepared statements
			String query = "UPDATE `userdb`.`user` SET `user_email`=?, `password`=?, `user_role`=? WHERE `user_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(2, userEmail);
			preparedStmt.setString(3, password);
			preparedStmt.setString(4, role);
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "User record was Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the User record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to disable an existing User by using user ID => for Administrator --------------------------------------------------------------------------------
	public String disableUser(String userID, String accStatus) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for disabling.";
				
			}
			
			// The query to disable User & prepared statements
			String query = "UPDATE `userdb`.`user` SET `account_status`=? WHERE `user_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(2, accStatus);
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "User was disabled successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while disabling the User.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}

}
