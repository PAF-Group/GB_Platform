package model;

import java.sql.*;

public class Agreement {
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "admin");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	//Insert Agreement
	
	public String insertItem(int Fundingbodyid, String path, String status,int projectid) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into project_table (`Agreement_ID`,`FundingBody_ID`,`Agreement_Path`,`Status`,`Project_ID`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setInt(2, Fundingbodyid);
			preparedStmt.setString(3,  path);
			preparedStmt.setString(4, status);
			preparedStmt.setInt(5, projectid);
		
			
// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
		} catch (Exception e) {
			output = "Inserted Unsuccessfully.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	

	public String readItems() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Agreement_ID</th><th>FundingBody_ID</th>" + "<th>Agreement_Path</th>"
					+ "<th>Status</th>" +"<th>Project_IDL</th>"+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from agreement_table";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String agreementID = Integer.toString(rs.getInt("Agreement_ID"));
				String fundingbodyid = Integer.toString(rs.getInt("FundingBody_ID"));
				String path = rs.getString("Agreement_Path");
				String Status = rs.getString("Status");
				String researcherID = Integer.toString(rs.getInt("Researcher_ID"));
				
				// Add into the html table
				output += "<tr><td>" +  agreementID + "</td>";
				output += "<td>" + fundingbodyid + "</td>";
				output += "<td>" + path + "</td>";
				output += "<td>" + Status + "</td>";
				output += "<td>" + researcherID + "</td>";
				
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
						+ "<input name='itemID' type='hidden' value='" + agreementID + "'>" + "</form></td></tr>";
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

	public String updateItem(String Agreementid, String fundingbodyid, String agreementpath, String status, String projectid ) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE agreement_table SET FundingBody_ID=?,Agreement_Path=?,Status=?,Project_ID=? WHERE Agreement_ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(fundingbodyid));
			preparedStmt.setString(2, agreementpath);
			preparedStmt.setString(3, status);
			preparedStmt.setInt(4, Integer.parseInt(projectid));
			preparedStmt.setInt(5, Integer.parseInt(Agreementid));
			
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

	public String deleteItem(String agreementid) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from agreement_table where Agreement_ID =?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(agreementid));
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