/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package model;

import java.sql.*;

public class Researcher {
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

//	The method to create a new Researcher => Researcher registration --------------------------------------------------------------------------------------------
	public String createResearcher(String firstName, String lastName, String userPhone, String userAgreement, String email, String password, String accStatus) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to insert a new record to the Researcher table & prepared statements
			String query = " INSERT INTO `userdb`.`researcher` (`first_name`,`last_name`,`user_phone`,`user_agreement`, `user_email`, `password`, `account_status`)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			//preparedStmt.setInt(1, 0);
			preparedStmt.setString(1, firstName);
			preparedStmt.setString(2, lastName);
			preparedStmt.setString(3, userPhone);
			preparedStmt.setInt(4, Integer.parseInt(userAgreement));
			preparedStmt.setString(5, email);
			preparedStmt.setString(6, password);
			preparedStmt.setString(7, accStatus);
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "A new Researcher created successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while creating the user.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//  The method to read all the Researcher records => for the Administrator & GB Member to View all Researchers ----------------------------------------------------
	public String getResearchers() {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// Prepare a HTML table to display the Researchers
			output = "<table border='1'>" + "<tr>" + "<th>First Name</th>" + "<th>Last Name</th>" + "<th>Phone</th>"
					+ "<th>Agreement ID</th>" + "<th>Email</th>" + "<th>Account Status</th>" + "<th>Created At</th>" + "<th>Updated At</th>" 
					+ "</tr>";

			// The query to select all records from Researcher table
			String query = "SELECT `first_name`,`last_name`,`user_phone`,`user_agreement`, `user_email`, `account_status`," 
							+ " `created_at`, `updated_at` FROM `userdb`.`researcher`";
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// Iterate through all the records in the result set
			while (rs.next()) {
				//String userID = Integer.toString(rs.getInt("user_id"));
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String phone = rs.getString("user_phone");
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
	public String updateResearcher(String userID, String firstName, String lastName, String phone, String userAgreement, String email) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has while connecting to the database for updating.";
				
			}
			
			// The query to Update the certain record in the Researcher table & prepared statements
			String query = "UPDATE `userdb`.`researcher` SET `first_name`=?, `last_name`=?, `user_phone`=?, `user_agreement`=?, `user_email`=?"
							+ " WHERE `user_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(2, firstName);
			preparedStmt.setString(3, lastName);
			preparedStmt.setString(4, phone);
			preparedStmt.setInt(5, Integer.parseInt(userAgreement));
			preparedStmt.setString(6, email);
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Researcher record was Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the Researcher record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to disable an existing Researcher by using user ID => for Administrator --------------------------------------------------------------------------
	public String disableResearcher(String userID, String accStatus) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for deleting.";
				
			}
			
			// The query to disable a Researcher
			String query = "UPDATE `userdb`.`researcher` SET `account_status`=? WHERE `user_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(2, accStatus);
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Researcher was disabled successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while disabling the Funder.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
}