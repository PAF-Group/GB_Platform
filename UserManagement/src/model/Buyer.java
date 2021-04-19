/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package model;

import java.sql.*;

public class Buyer {
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

//	The method to create a new Buyer => Buyer registration ------------------------------------------------------------------------------------------------------
	public String createBuyer(String name, String userPhone, String address, String userAgreement, String email, String password, String accStatus) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to insert a new record to the Buyer table & prepared statements
			String query = " INSERT INTO `userdb`.`buyer` (`name`, `user_phone`, `address`, `user_agreement`,"
					+ " `user_email`, `password`, `account_status`)" + " VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			//preparedStmt.setInt(1, 0);
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, userPhone);
			preparedStmt.setString(3, address);
			preparedStmt.setInt(4, Integer.parseInt(userAgreement));
			preparedStmt.setString(5, email);
			preparedStmt.setString(6, password);
			preparedStmt.setString(7, accStatus);
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "A new Buyer created successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error occurred while creating the user.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//  The method to read all the Buyer records => for the Administrator & GB Member to View all Buyers --------------------------------------------------------
	public String getBuyers() {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// Prepare a HTML table to display the Buyers
			output = "<table border='1'>" + "<tr>" + "<th>Name</th>" + "<th>Phone</th>" + "<th>Address</th>" + "<th>Agreement ID</th>" 
					+ "<th>Email</th>" + "<th>Account Status</th>" + "<th>Created At</th>" + "<th>Updated At</th>" 
					+ "</tr>";

			// The query to select all records from Researcher table
			String query = "SELECT `name`, `user_phone`, `address`, `user_agreement`, `user_email`, `account_status`, `created_at`,"
					+ " `updated_at` FROM `userdb`.`buyer`";
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// Iterate through all the records in the result set
			while (rs.next()) {
				//String userID = Integer.toString(rs.getInt("user_id"));
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String phone = rs.getString("user_phone");
				String address = rs.getString("address");
				String agreementID = Integer.toString(rs.getInt("user_agreement"));
				String email = rs.getString("user_email");
				//String password = rs.getString("password");
				String accStatus = rs.getString("account_status");
				String createdAt = rs.getTimestamp("created_at").toString();
				String updatedAt = rs.getTimestamp("updated_at").toString();
				
				// Add the record in to the HTML table
				output += "<tr><td>" + firstName + "</td>";
				output += "<td>" + lastName + "</td>";
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
			output = "An error occurred while reading the Buyer records.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to update a Buyer => a service for both Administrator & Buyer ------------------------------------------------------------------------------------
	public String updateBuyer(String userID, String name, String userPhone, String address, String userAgreement, String email) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for updating.";
				
			}
			
			// The query to Update the certain record in the Buyer table & prepared statements
			String query = "UPDATE `userdb`.`buyer` SET `name`=?, `user_phone`=?, `address`=?, `user_agreement`=?, `user_email`=?"
							+ " WHERE `user_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, userPhone);
			preparedStmt.setString(4, address);
			preparedStmt.setInt(5, Integer.parseInt(userAgreement));
			preparedStmt.setString(6, email);
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Buyer record was Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the Buyer record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to disable an existing Buyer by using user ID => for Administrator -------------------------------------------------------------------------------
	public String disableBuyer(String userID, String accStatus) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for disabling.";
				
			}
			
			// The query to disable a Buyer & prepared statements
			String query = "UPDATE `userdb`.`buyer` SET `account_status`=? WHERE `user_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(2, accStatus);
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Buyer was disabled successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while disabling the Buyer.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
}
