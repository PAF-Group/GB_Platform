/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package model;

import java.sql.*;
import utility.DatabaseConnectivity;

public class Administrator {
//	The method to create a new Administrator => Administrator registration --------------------------------------------------------------------------------------
	public String createAdministrator(String empID, String firstName, String lastName, String userPhone, String userEmail, String password, String role, String accStatus) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to retrieve all the user-email & password records
			String query1 = "SELECT `user_email`, `password` FROM `user`";
			
			Statement stmt = con.createStatement();
			
			// Retrieve records and store it in a ResultSet
			ResultSet set1 = stmt.executeQuery(query1);
			
			// Check whether the new user-email & password are already existing in the DB
			while(set1.next()) {
				String email = set1.getString("user_email");
				String pwd = set1.getString("password");
				
				if(userEmail.equals(email) && password.equals(pwd)) {
					return "This User-email & Password has already taken. Please try again!...";
					
				}
				
			}
			
			// The query to insert a new record to the User table & prepared statements
			String query2 = "INSERT INTO `user` (`user_email`, `password`, `user_role`, `account_status`) VALUES (?, ?, ?, ?)";
			
			PreparedStatement preparedStmt1 = con.prepareStatement(query2);
			
			// binding values
			preparedStmt1.setString(1, userEmail);
			preparedStmt1.setString(2, password);
			preparedStmt1.setString(3, role);
			preparedStmt1.setString(4, accStatus);
			
			// execute the statement
			preparedStmt1.execute();
			
			// The query to get the newly created User/Administrator ID
			String query3 = "SELECT `user_id` FROM `user` WHERE `user_email`=?";
			
			PreparedStatement preparedStmt2 = con.prepareStatement(query3);
			
			// binding values
			preparedStmt2.setString(1, userEmail);
			
			// Retrieve records and store it in a ResultSet
			ResultSet set2 = preparedStmt2.executeQuery();
			
			set2.next();
			
			int userID = set2.getInt("user_id");
			
			// The query to insert a new record to the Administrator table & prepared statements
			String query4 = "INSERT INTO `administrator` (`employee_id`, `first_name`, `last_name`, `user_phone`, `user_id`) VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt3 = con.prepareStatement(query4);
			
			// binding values
			preparedStmt3.setInt(1, Integer.parseInt(empID));
			preparedStmt3.setString(2, firstName);
			preparedStmt3.setString(3, lastName);
			preparedStmt3.setString(4, userPhone);
			preparedStmt3.setInt(5, userID);
			
			// execute the statement
			preparedStmt3.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "A new Administrator has created successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while creating the user!...";
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
			String query1 = "SELECT `employee_id`, `first_name`, `last_name`, `user_phone`, `user_id`, `created_at`, `updated_at` FROM `administrator`";
			
			Statement stmt1 = con.createStatement();
			ResultSet set1 = stmt1.executeQuery(query1);
			
			// Iterate through all the records in the result set
			while (set1.next()) {
				// Reading values from the Result Set - set1
				String empID = Integer.toString(set1.getInt("employee_id"));
				String firstName = set1.getString("first_name");
				String lastName = set1.getString("last_name");
				String phone = set1.getString("user_phone");
				int userID = set1.getInt("user_id");
				String createdAt = set1.getTimestamp("created_at").toString();
				String updatedAt = set1.getTimestamp("updated_at").toString();
				
				// The query to select the certain Administrator record from the User table
				String query2 = "SELECT `user_email`, `account_status` FROM `user` WHERE `user_id`=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query2);
				
				// binding values
				preparedStmt.setInt(1, userID);
				
				ResultSet set2 = preparedStmt.executeQuery();
				
				set2.next();
				
				// Reading values from the Result Set - set2
				String email = set2.getString("user_email");
				String accStatus = set2.getString("account_status");
				
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
			output = "An error has occurred while reading the Administrator records.";
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
			String query = "UPDATE `administrator` SET `employee_id`=?, `first_name`=?, `last_name`=?, `user_phone`=? WHERE `admin_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(empID));
			preparedStmt.setString(2, firstName);
			preparedStmt.setString(3, lastName);
			preparedStmt.setString(4, userPhone);
			preparedStmt.setInt(5, Integer.parseInt(adminID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Administrator record has Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the Administrator record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to update an Administrator's User-email & Password => a service for Administrator ----------------------------------------------------------------
	public String updateAdminEmailPassword(String userID, String userEmail, String password) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for updating.";
				
			}
			
			// The query to retrieve all the user-email & password records
			String query1 = "SELECT `user_email`, `password` FROM `user`";
			
			Statement stmt = con.createStatement();
			
			// Retrieve records and store it in a ResultSet
			ResultSet set = stmt.executeQuery(query1);
			
			// Check whether the new user-email & password are already existing in the DB
			while(set.next()) {
				String email = set.getString("user_email");
				String pwd = set.getString("password");
				
				if(userEmail.equals(email) && password.equals(pwd)) {
					return "This User-email & Password has already taken. Please try again!...";
					
				}
				
			}
			
			// The query to Update the certain record in the User table & prepared statements
			String query2 = "UPDATE `user` SET `user_email`=?, `password`=? WHERE `user_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query2);
			
			// binding values
			preparedStmt.setString(1, userEmail);
			preparedStmt.setString(2, password);
			preparedStmt.setInt(3, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Administrator User-email & Password records has Updated successfully!...";
			
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
			String query1 = "SELECT `user_id` FROM `administrator` WHERE `admin_id`=?";
			
			PreparedStatement preparedStmt1 = con.prepareStatement(query1);
			
			// binding values
			preparedStmt1.setInt(1, Integer.parseInt(adminID));
			
			ResultSet set = preparedStmt1.executeQuery();
			
			set.next();
			
			// Reading values from the Result Set - set
			int userID = set.getInt("user_id");
			
			// The query to disable an Administrator & prepared statements
			String query2 = "UPDATE `user` SET `account_status`=? WHERE `user_id`=?";
			
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);
			
			// binding values
			preparedStmt2.setString(1, accStatus);
			preparedStmt2.setInt(2, userID);
			
			// execute the statement
			preparedStmt2.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "An Administrator has disabled successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while disabling the Administrator.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
}
