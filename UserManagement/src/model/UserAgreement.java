/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package model;

import java.sql.*;
import utility.DatabaseConnectivity;

public class UserAgreement {
//	The method to create a new User Agreement => User Agreement creation ----------------------------------------------------------------------------------------
	public String createAgreement(String agreementName, String description, String fileLocation) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to insert a new record to the User Agreement table & prepared statements
			String query = "INSERT INTO `userdb`.`user_agreement` (`agreement_name`, `description`, `file_location`) VALUES (?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, agreementName);
			preparedStmt.setString(2, description);
			preparedStmt.setString(3, fileLocation);
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "A new User Agreement created successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while creating the agreement.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//  The method to read all the User Agreements => for the Administrator & GB Member to View User Agreements -----------------------------------------------------
	public String getAgreements() {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// Prepare a HTML table to display the User Agreements
			output = "<table border='1'>" + "<tr>" + "<th>Agreement Name</th>" + "<th>Description</th>" + "<th>File URL</th>" 
					+ "<th>Created At</th>" + "<th>Updated At</th>" + "</tr>";

			// The query to select all records from User Agreement table
			String query = "SELECT `agreement_name`, `description`, `file_location`, `created_at`, `updated_at` FROM `userdb`.`user_agreement`";
			
			Statement stmt = con.createStatement();
			
			// Store all the retrieved records in a result set
			ResultSet set = stmt.executeQuery(query);
			
			// Iterate through all the records in the result set
			while (set.next()) {
				String agreementName = set.getString("agreement_name");
				String description = set.getString("description");
				String url = set.getString("file_location");
				String createdAt = set.getTimestamp("created_at").toString();
				String updatedAt = set.getTimestamp("updated_at").toString();
				
				// Add the record in to the HTML table
				output += "<tr><td>" + agreementName + "</td>";
				output += "<td>" + description + "</td>";
				output += "<td>" + url + "</td>";
				output += "<td>" + createdAt + "</td>";
				output += "<td>" + updatedAt + "</td></tr>";
				
			}
			
			// Close the database connection
			con.close();
			
			// End of the HTML table
			output += "</table>";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while reading the User Agreement records.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to update a User Agreement => a service for both Administrator & Member --------------------------------------------------------------------------
	public String updateAgreement(String agreementID, String agreementName, String description, String fileLocation) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for updating.";
				
			}
			
			// The query to Update the certain record in the User Agreement table & prepared statements
			String query = "UPDATE `userdb`.`user_agreement` SET `agreement_name`=?, `description`=?, `file_location`=? WHERE `agreement_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(2, agreementName);
			preparedStmt.setString(3, description);
			preparedStmt.setString(4, fileLocation);
			preparedStmt.setInt(1, Integer.parseInt(agreementID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "User Agreement record was Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the User Agreement record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to remove an existing User Agreement by using agreement ID => for Administrator ------------------------------------------------------------------
	public String deleteAgreement(String agreementID) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for disabling.";
				
			}
			
			// The query to remove a User Agreement & prepared statements
			String query = "DELETE FROM `userdb`.`user_agreement` WHERE `agreement_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(agreementID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "User Agreement was removed successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while removing the User Agreement.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
}
