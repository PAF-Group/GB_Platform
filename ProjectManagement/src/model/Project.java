
/* 
 * @author  IT19160580 Maddumage T.A.R
 * 
 * */

package model;

import java.sql.*;



public class Project { 
	// get the connection
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", "admin");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	//Insert Project  to the sysytem and funding body can see those projects
	
	public String insertProject(String name, String description, String field,String url,int researcher_id) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into project_table (Project_ID,Name,Description,Filed,Project_Report_Url,Researcher_ID)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, description);
			preparedStmt.setString(4, field);
			preparedStmt.setString(5, url);
			preparedStmt.setInt(6, researcher_id);
			
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

	// Read the projects when researcher add to the system
	public String readProjects() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Project_ID</th><th>Project Name</th>" + "<th>Description</th>"
					+ "<th>Field</th>" +"<th>URL</th>"+"<th>Researcher_ID</th></tr>";

			String query = "select * from project_table";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String projectID = Integer.toString(rs.getInt("Project_ID"));
				String projectname = rs.getString("Name");
				String description = rs.getString("Description");
				String filed = rs.getString("Filed");
				String url = rs.getString("Project_Report_Url");
				String researcherID = Integer.toString(rs.getInt("Researcher_ID"));
				
				// Add into the html table
				output += "<tr><td>" + projectID + "</td>";
				output += "<td>" + projectname + "</td>";
				output += "<td>" + description + "</td>";
				output += "<td>" + filed + "</td>";
				output += "<td>" + url + "</td>";
				output += "<td>" + researcherID + "</td>";
				// buttons
				output += 
						 "<td><form method='post' action='items.jsp'>"
						
						+ "<input name='itemID' type='hidden' value='" + projectID + "'>" + "</form></td></tr>";
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

//Update project details when they want

	public String updateProject(String ID, String name, String description, String filed, String url,String researcherid ) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE project_table SET Name=?,Description=?,Filed=?,Project_Report_Url=?,Researcher_ID=? WHERE Project_ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, description);
			preparedStmt.setString(3, filed);
			preparedStmt.setString(4, url);
			preparedStmt.setInt(5, Integer.parseInt(researcherid));
			preparedStmt.setInt(6, Integer.parseInt(ID));
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

	//Delete  project when they want
	public String deleteProject(String projectid) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from project_table where Project_ID =?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(projectid));
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