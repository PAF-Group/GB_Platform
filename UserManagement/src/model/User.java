/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package model;

import java.sql.*;
import utility.DatabaseConnectivity;

public class User {	
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
			String query = "SELECT `user_email`, `user_role`, `account_status`, `created_at`, `updated_at` FROM `user`";
			
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
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//	The method to return the user role of a user after confirming user-email, password -------------------------------------------------------------------------
	public String getUserRole(String userEmail, String password) {
		String output = "";
		String userRole = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to select a specific user using user-email & password
			String query = "SELECT `user_role`, `account_status` FROM `user` WHERE `user_email`=? AND `password`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, userEmail);
			preparedStmt.setString(2, password);
			// execute the statement
			ResultSet set = preparedStmt.executeQuery();
			
			if(set.next() == true) {
				String accStatus = set.getString("account_status");
				
				if(accStatus.toLowerCase().equals("active")) {
					userRole = set.getString("user_role");
					
				} else {
					output = "Unregistered User!...";
					
				}
				
			} else {
				output = "Unregistered User!...";
				
			}
			
			// Close Database connection
			con.close();
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while reading the User records.";
			System.err.println(e.getMessage());
			
		}
		
		if(!userRole.equals("")) {
			return userRole;
			
		} else {
			return output;
			
		}
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//	The method to check whether a user is active or inactive ---------------------------------------------------------------------------------------------------
	public String checkUserValidity(String userID) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to get the account status using user
			String query = "SELECT `account_status` FROM `user` WHERE `user_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			// execute the statement
			ResultSet set = preparedStmt.executeQuery();
			
			if(set.next() == true) {
				String accStatus = set.getString("account_status");
				
				if(accStatus.equals("active")) {
					output = "valid";
					
				} else {
					output = "invalid";
					
				}
				
			} else {
				output = "invalid";
				
			}
			
			// Close Database connection
			con.close();
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while reading the User records.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}

}
