package model;

import java.sql.*;

public class NoticeItem { // A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/req_project", "root", "okanda");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertItem(String name, String feild, String description, String submission_link,String fundingbody_id) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into project_request (`pRequest_id`,`name`,`feild`,`description`,`submission_link`,`fundingbody_id`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, feild);
			preparedStmt.setString(4, description);
			preparedStmt.setString(5, submission_link);
			preparedStmt.setString(6, fundingbody_id);
			
// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Error while inserting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readItems() {  //data retrive method
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Name</th><th>Feild</th>" + "<th>Description</th>"
					+ "<th>Submission_Link</th>" ;

			String query = "select * from project_request";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String pRequest_id = Integer.toString(rs.getInt("pRequest_id"));
				String name = rs.getString("name");
				String feild = rs.getString("feild");
				String description = rs.getString("description");
				String submission_link = rs.getString("submission_link");
				// Add into the html table
				output += "<tr><td>" + name + "</td>";
				output += "<td>" + feild + "</td>";
				output += "<td>" + description + "</td>";
				output += "<td>" + submission_link + "</td>";
				// buttons
//				output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
//						+ "<td><form method='post' action='items.jsp'>"
//						+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
//						+ "<input name='pRequest_id' type='hidden' value='" + pRequest_id+ "'>" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateItem(String ID, String name, String feild, String description, String submission_link) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE project_request SET name=?,feild=?,description=?,submission_link=? WHERE pRequest_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, feild);
			preparedStmt.setString(3,description);
			preparedStmt.setString(4, submission_link);
			preparedStmt.setInt(5, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteItem(String pRequest_id) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
		
			// create a prepared statement
			String query = "delete from project_request where pRequest_id=?";
	
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(pRequest_id));
	
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
			
		} catch (Exception e) {
			output = "Error while deleting the item.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}