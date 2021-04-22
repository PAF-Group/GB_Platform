/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package model;

import java.sql.*;
import utility.DatabaseConnectivity;

public class GBMember {
//	The method to create a new GB Member => Member registration -------------------------------------------------------------------------------------------------
	public String createMember(String empID, String firstName, String lastName, String memberType, String userPhone, String email, String password, String role, String accStatus) {
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
			
			// The query to get the newly created User/GBMember ID
			String query2 = "SELECT `user_id` FROM `userdb`.`user` WHERE `user_email`=?";
			
			PreparedStatement preparedStmt2 = con.prepareStatement(query2);
			
			// binding values
			preparedStmt2.setString(1, email);
			
			ResultSet set = preparedStmt2.executeQuery();
			
			String userID = Integer.toString(set.getInt("user_id"));
			
			// The query to insert a new record to the GB Member table & prepared statements
			String query = " INSERT INTO `userdb`.`gb_member` (`employee_id`, `first_name`, `last_name`, `member_type`, `user_phone`, `user_id`) VALUES (?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			//preparedStmt.setInt(1, 0);
			preparedStmt.setInt(1, Integer.parseInt(empID));
			preparedStmt.setString(2, firstName);
			preparedStmt.setString(3, lastName);
			preparedStmt.setString(4, memberType);
			preparedStmt.setString(5, userPhone);
			preparedStmt.setInt(6, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "A new Member of GB has created successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while creating the user.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//  The method to read all the GB Members => for the Administrator & GB Member to View all GB Members -----------------------------------------------------------
	public String getMembers() {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// Prepare a HTML table to display the GB Members
			output = "<table border='1'>" + "<tr>" + "<th>Employee ID</th>" + "<th>First Name</th>" + "<th>Last Name</th>" + "<th>Member Type</th>"
					+ "<th>Phone</th>" + "<th>Email</th>" + "<th>Account Status</th>" + "<th>Created At</th>" + "<th>Updated At</th>" 
					+ "</tr>";

			// The query to select all records from GB Member table
			String query = "SELECT `employee_id`, `first_name`, `last_name`, `member_type`, `user_phone`, `user_id`, `created_at`, `updated_at` FROM `userdb`.`gb_member`";
			
			Statement stmt = con.createStatement();
			ResultSet set1 = stmt.executeQuery(query);
			
			// Iterate through all the records in the result set
			while (set1.next()) {
				// Reading values from the Result Set - set1
				String empID = Integer.toString(set1.getInt("employee_id"));
				String firstName = set1.getString("first_name");
				String lastName = set1.getString("last_name");
				String memberType = set1.getString("member_type");
				String phone = set1.getString("user_phone");
				String userID = Integer.toString(set1.getInt("user_id"));
				String createdAt = set1.getTimestamp("created_at").toString();
				String updatedAt = set1.getTimestamp("updated_at").toString();
				
				// The query to select the certain GB Member record from the User table
				String query2 = "SELECT `user_email`, `accStatus` FROM `userdb`.`user` WHERE `user_id`=?";
				
				PreparedStatement preparedStmt = con.prepareStatement(query2);
				
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(userID));
				
				ResultSet set2 = preparedStmt.executeQuery();
				
				// Reading values from the Result Set - set2
				String email = set2.getString("user_email");
				String accStatus = set2.getString("accStatus");
				
				// Add the record in to the HTML table
				output += "<tr><td>" + empID + "</td>";
				output += "<td>" + firstName + "</td>";
				output += "<td>" + lastName + "</td>";
				output += "<td>" + memberType + "</td>";
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
			output = "An error has occurred while reading the Member records.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to update a GB Member => a service for both Administrator & Member -------------------------------------------------------------------------------
	public String updateMember(String memberID, String empID, String firstName, String lastName, String memberType, String userPhone) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for updating.";
				
			}
			
			// The query to Update the certain record in the GB Member table & prepared statements
			String query = "UPDATE `userdb`.`gb_member` SET `employee_id`=?, `first_name`=?, `last_name`=?, `member_type`=?, `user_phone`=? WHERE `member_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(empID));
			preparedStmt.setString(2, firstName);
			preparedStmt.setString(3, lastName);
			preparedStmt.setString(4, memberType);
			preparedStmt.setString(5, userPhone);
			preparedStmt.setInt(6, Integer.parseInt(memberID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "GB Member record has Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the GB Member record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//	The method to update a GB Member's User-email & Password => a service for GB Member -------------------------------------------------------------------------
	public String updateMemberEmailPassword(String userID, String userEmail, String password) {
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
			output = "GB Member User-email & Password records has Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the Member record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to disable an existing GB Member by using user ID => for Administrator ---------------------------------------------------------------------------
	public String disableMember(String memberID, String accStatus) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for disabling.";
				
			}
			
			// The query to retrieve user ID from the GB Member table
			String query1 = "SELECT `user_id` FROM `userdb`.`gb_member` WHERE `member_id`=?";
			
			PreparedStatement preparedStmt1 = con.prepareStatement(query1);
			
			// binding values
			preparedStmt1.setInt(1, Integer.parseInt(memberID));
			
			ResultSet set = preparedStmt1.executeQuery();
			
			// Reading values from the Result Set - set
			String userID = Integer.toString(set.getInt("user_id"));
			
			// The query to disable a GB Member & prepared statements
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
			output = "GB Member has disabled successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while disabling the GB Member.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
}
