/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package model;

import java.sql.*;
import utility.DatabaseConnectivity;

public class Buyer {
//	The method to create a new Buyer => Buyer registration ------------------------------------------------------------------------------------------------------
	public String createBuyer(String name, String userPhone, String address, String userAgreement, String email, String password, String role, String accStatus) {
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
			
			// The query to get the newly created User/Buyer ID
			String query2 = "SELECT `user_id` FROM `userdb`.`user` WHERE `user_email`=?";
			
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);
			
			// binding values
			preparedStmt2.setString(1, email);
			
			ResultSet set = preparedStmt2.executeQuery();
			
			String userID = Integer.toString(set.getInt("user_id"));
			
			// The query to insert a new record to the Buyer table & prepared statements
			String query3 = " INSERT INTO `userdb`.`buyer` (`name`, `user_phone`, `address`, `user_id`, `user_agreement`) VALUES (?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt3 = con.prepareStatement(query3);
			
			// binding values
			preparedStmt3.setString(1, name);
			preparedStmt3.setString(2, userPhone);
			preparedStmt3.setString(3, address);
			preparedStmt3.setInt(4, Integer.parseInt(userID));
			preparedStmt3.setInt(5, Integer.parseInt(userAgreement));
			
			// execute the statement
			preparedStmt2.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "A new Buyer has created successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while creating the user.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//  The method to read all the Buyer records => for the Administrator & GB Member to View all Buyers ------------------------------------------------------------
	public String getBuyers() {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// Prepare a HTML table to display the Buyers
			output = "<table border='1'>" + "<tr>" + "<th>Name</th>" + "<th>Phone</th>" + "<th>Address</th>" + "<th>Agreement ID</th>" 
					+ "<th>Email</th>" + "<th>Account Status</th>" + "<th>Created At</th>" + "<th>Updated At</th>" 
					+ "</tr>";

			// The query to select all records from Researcher table
			String query1 = "SELECT `name`, `user_phone`, `address`, `user_id`, `user_agreement`, `created_at`,"
					+ " `updated_at` FROM `userdb`.`buyer`";
			
			Statement stmt1 = con.createStatement();
			ResultSet set1 = stmt1.executeQuery(query1);
			
			// Iterate through all the records in the result set
			while (set1.next()) {
				// Reading values from the Result Set - set1
				String name = set1.getString("name");
				String phone = set1.getString("user_phone");
				String address = set1.getString("address");
				String userID = Integer.toString(set1.getInt("user_id"));
				String agreementID = Integer.toString(set1.getInt("user_agreement"));
				String createdAt = set1.getTimestamp("created_at").toString();
				String updatedAt = set1.getTimestamp("updated_at").toString();
				
				// The query to select the certain Buyer record from the User table
				String query2 = "SELECT `user_email`, `accStatus` FROM `userdb`.`user` WHERE `user_id`=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query2);
				
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(userID));
				
				ResultSet set2 = preparedStmt.executeQuery();
				
				// Reading values from the Result Set - set2
				String email = set2.getString("user_email");
				String accStatus = set2.getString("accStatus");
				
				// Add the record in to the HTML table
				output += "<tr><td>" + name + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + address + "</td>";
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
			output = "An error has occurred while reading the Buyer records.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to update a Buyer => a service for both Administrator & Buyer ------------------------------------------------------------------------------------
	public String updateBuyer(String buyerID, String name, String userPhone, String address, String userAgreement) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for updating.";
				
			}
			
			// The query to Update the certain record in the Buyer table & prepared statements
			String query = "UPDATE `userdb`.`buyer` SET `name`=?, `user_phone`=?, `address`=?, `user_agreement`=? WHERE `buyer_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, userPhone);
			preparedStmt.setString(3, address);
			preparedStmt.setInt(4, Integer.parseInt(userAgreement));
			preparedStmt.setInt(5, Integer.parseInt(buyerID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Buyer record has Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the Buyer record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//	The method to update a Buyer's User-email & Password => a service for Buyer ---------------------------------------------------------------------------------
	public String updateBuyerEmailPassword(String userID, String userEmail, String password) {
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
			output = "Buyer User-email & Password records has Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the Buyer record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to disable an existing Buyer by using user ID => for Administrator -------------------------------------------------------------------------------
	public String disableBuyer(String buyerID, String accStatus) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for disabling.";
				
			}
			
			// The query to retrieve user ID from the Buyer table
			String query1 = "SELECT `user_id` FROM `userdb`.`buyer` WHERE `buyer_id`=?";
			
			PreparedStatement preparedStmt1 = con.prepareStatement(query1);
			
			// binding values
			preparedStmt1.setInt(1, Integer.parseInt(buyerID));
			
			ResultSet set = preparedStmt1.executeQuery();
			
			// Reading values from the Result Set - set
			String userID = Integer.toString(set.getInt("user_id"));
			
			// The query to disable a Buyer & prepared statements
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
			output = "Buyer has disabled successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while disabling the Buyer.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//  The method to read a Buyer address using buyer ID => for the Order Management microservice ------------------------------------------------------------------
	public String getBuyerAddress(String buyerID) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for disabling.";
				
			}
			
			// The query to retrieve address from the Buyer table
			String query = "SELECT `address` FROM `userdb`.`buyer` WHERE `buyer_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(buyerID));
			
			ResultSet set = preparedStmt.executeQuery();
			
			// Reading values from the Result Set - set
			output = set.getString("address");
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while disabling the Buyer.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
}