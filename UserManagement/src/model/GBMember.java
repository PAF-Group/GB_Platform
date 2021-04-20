/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package model;

import java.sql.*;

public class GBMember {
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

//	The method to create a new GB Member => Member registration -------------------------------------------------------------------------------------------------
	public String createMember(String empID, String firstName, String lastName, String memberType, String userPhone, String email, String password, String accStatus) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to insert a new record to the GB Member table & prepared statements
			String query = " INSERT INTO `userdb`.`gb_member` (`employee_id`, `first_name`, `last_name`, `member_type`, `user_phone`,"
					+ " `user_email`, `password`, `account_status`)" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			//preparedStmt.setInt(1, 0);
			preparedStmt.setInt(1, Integer.parseInt(empID));
			preparedStmt.setString(2, firstName);
			preparedStmt.setString(3, lastName);
			preparedStmt.setString(4, memberType);
			preparedStmt.setString(5, userPhone);
			preparedStmt.setString(6, email);
			preparedStmt.setString(7, password);
			preparedStmt.setString(8, accStatus);
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "A new Member of GB created successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error occurred while creating the user.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//  The method to read all the GB Members => for the Administrator & GB Member to View all GB Members --------------------------------------------------------
	public String getMembers() {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// Prepare a HTML table to display the GB Members
			output = "<table border='1'>" + "<tr>" + "<th>Employee ID</th>" + "<th>First Name</th>" + "<th>Last Name</th>" + "<th>Member Type</th>"
					+ "<th>Phone</th>" + "<th>Email</th>" + "<th>Account Status</th>" + "<th>Created At</th>" + "<th>Updated At</th>" 
					+ "</tr>";

			// The query to select all records from GB Member table
			String query = "SELECT `employee_id`, `first_name`, `last_name`, `member_type`, `user_phone`, `user_email`, `account_status`, `created_at`,"
					+ " `updated_at` FROM `userdb`.`gb_member`";
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// Iterate through all the records in the result set
			while (rs.next()) {
				String empID = Integer.toString(rs.getInt("employee_id"));
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String memberType = rs.getString("member_type");
				String phone = rs.getString("user_phone");
				String email = rs.getString("email");
				//String password = rs.getString("password");
				String accStatus = rs.getString("account_status");
				String createdAt = rs.getTimestamp("created_at").toString();
				String updatedAt = rs.getTimestamp("updated_at").toString();
				
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
			output = "An error occurred while reading the Member records.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to update a GB Member => a service for both Administrator & Member -------------------------------------------------------------------------------
	public String updateMember(String userID, String empID, String firstName, String lastName, String memberType, String userPhone, String email) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for updating.";
				
			}
			
			// The query to Update the certain record in the GB Member table & prepared statements
			String query = "UPDATE `userdb`.`gb_member` SET `employee_id`=?, `first_name`=?, `last_name`=?, `member_type`=?, `user_phone`=?, `user_email`=?"
							+ " WHERE `user_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(2, Integer.parseInt(empID));
			preparedStmt.setString(3, firstName);
			preparedStmt.setString(4, lastName);
			preparedStmt.setString(5, memberType);
			preparedStmt.setString(6, userPhone);
			preparedStmt.setString(7, email);
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "GB Member record was Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the GB Member record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//	The method to disable an existing GB Member by using user ID => for Administrator -------------------------------------------------------------------------------
	public String disableMember(String userID, String accStatus) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database for disabling.";
				
			}
			
			// The query to disable a GB Member & prepared statements
			String query = "UPDATE `userdb`.`gb_member` SET `account_status`=? WHERE `user_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(2, accStatus);
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "GB Member was disabled successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while disabling the GB Member.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
}
