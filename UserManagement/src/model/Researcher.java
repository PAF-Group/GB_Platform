/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package model;

import java.sql.*;
import utility.DatabaseConnectivity;

public class Researcher {
//	The method to create a new Researcher => Researcher registration --------------------------------------------------------------------------------------------
	public String createResearcher(String firstName, String lastName, String userPhone, String userAgreement, String email, String password, String role, String accStatus) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to insert a new record to the User table & prepared statements
			String query1 = "INSERT INTO `userdb`.`user` (`user_email`, `password`, `user_role`, `accStatus`)" + " VALUES (?, ?, ?, ?)";
						
			PreparedStatement preparedStmt1 = con.prepareStatement(query1);
						
			// binding values
			preparedStmt1.setString(1, email);
			preparedStmt1.setString(2, password);
			preparedStmt1.setString(3, role);
			preparedStmt1.setString(4, accStatus);
						
			// execute the statement
			preparedStmt1.execute();
			
			// The query to get the newly created User/Researcher ID
			String query2 = "SELECT `user_id` FROM `userdb`.`user` WHERE `user_email`=?";
			
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);
			
			// binding values
			preparedStmt2.setString(1, email);
			
			ResultSet set = preparedStmt2.executeQuery();
			
			String userID = Integer.toString(set.getInt("user_id"));
			
			// The query to insert a new record to the Researcher table & prepared statements
			String query = " INSERT INTO `userdb`.`researcher` (`first_name`, `last_name`, `user_phone`, `user_id`, `user_agreement`) VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, firstName);
			preparedStmt.setString(2, lastName);
			preparedStmt.setString(3, userPhone);
			preparedStmt.setInt(4, Integer.parseInt(userID));
			preparedStmt.setInt(5, Integer.parseInt(userAgreement));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "A new Researcher has created successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while creating the user.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//  The method to read all the Researcher records => for the Administrator & GB Member to View all Researchers --------------------------------------------------
	public String getResearchers() {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// Prepare a HTML table to display the Researchers
			output = "<table border='1'>" + "<tr>" + "<th>First Name</th>" + "<th>Last Name</th>" + "<th>Phone</th>"
					+ "<th>Agreement ID</th>" + "<th>Email</th>" + "<th>Account Status</th>" + "<th>Created At</th>" + "<th>Updated At</th>" 
					+ "</tr>";

			// The query to select all records from Researcher table
			String query1 = "SELECT `first_name`, `last_name`, `user_phone`, `user_id`, `user_agreement`, `created_at`, `updated_at` FROM `userdb`.`researcher`";
			
			Statement stmt = con.createStatement();
			ResultSet set1 = stmt.executeQuery(query1);
			
			// Iterate through all the records in the result set
			while (set1.next()) {
				// Reading values from the Result Set - set1
				String firstName = set1.getString("first_name");
				String lastName = set1.getString("last_name");
				String phone = set1.getString("user_phone");
				String userID = Integer.toString(set1.getInt("user_id"));
				String agreementID = Integer.toString(set1.getInt("user_agreement"));
				String createdAt = set1.getTimestamp("created_at").toString();
				String updatedAt = set1.getTimestamp("updated_at").toString();
				
				// The query to select the certain Researcher record from the User table
				String query2 = "SELECT `user_email`, `accStatus` FROM `userdb`.`user` WHERE `user_id`=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query2);
				
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(userID));
				
				ResultSet set2 = preparedStmt.executeQuery();
				
				// Reading values from the Result Set - set2
				String email = set2.getString("user_email");
				String accStatus = set2.getString("accStatus");
				
				// Add the record in to the HTML table
				output += "<tr><td>" + firstName + "</td>";
				output += "<td>" + lastName + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + agreementID + "</td>";
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
			output = "An error has occurred while reading the Researcher records.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to update a Researcher => a service for both Administrator & Researcher --------------------------------------------------------------------------
	public String updateResearcher(String researcherID, String firstName, String lastName, String phone, String userAgreement) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for updating.";
				
			}
			
			// The query to Update the certain record in the Researcher table & prepared statements
			String query = "UPDATE `userdb`.`researcher` SET `first_name`=?, `last_name`=?, `user_phone`=?, `user_agreement`=? WHERE `user_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, firstName);
			preparedStmt.setString(2, lastName);
			preparedStmt.setString(3, phone);
			preparedStmt.setInt(4, Integer.parseInt(userAgreement));
			preparedStmt.setInt(5, Integer.parseInt(researcherID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Researcher record has Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the Researcher record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//	The method to update a Researcher's User-email & Password => a service for Researcher -----------------------------------------------------------------------
	public String updateResearcherEmailPassword(String userID, String userEmail, String password) {
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
			preparedStmt.setString(1, userEmail);
			preparedStmt.setString(2, password);
			preparedStmt.setInt(3, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Researcher User-email & Password records has Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the Researcher record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to disable an existing Researcher by using user ID => for Administrator --------------------------------------------------------------------------
	public String disableResearcher(String researcherID, String accStatus) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for deleting.";
				
			}
			
			// The query to retrieve user ID from the Researcher table
			String query1 = "SELECT `user_id` FROM `userdb`.`researcher` WHERE `researcher_id`=?";
			
			PreparedStatement preparedStmt1 = con.prepareStatement(query1);
			
			// binding values
			preparedStmt1.setInt(1, Integer.parseInt(researcherID));
			
			ResultSet set = preparedStmt1.executeQuery();
			
			// Reading values from the Result Set - set
			String userID = Integer.toString(set.getInt("user_id"));
			
			// The query to disable a Researcher
			String query2 = "UPDATE `userdb`.`user` SET `account_status`=? WHERE `user_id`=?";
			
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);
			
			// binding values
			preparedStmt2.setString(1, accStatus);
			preparedStmt2.setInt(2, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt2.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Researcher has disabled successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while disabling the Researcher.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
}