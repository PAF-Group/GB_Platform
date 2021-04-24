package model;

import java.sql.*;

public class Submit {
	
	
	// A common method to connect to the DB
		private Connection connect() {
			Connection con = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");

				// Provide the correct details: DBServer/DBName, user name, password
				con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/req_project", "root", "okanda");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return con;
		}
		//insert method
		public String insertSubmission(String name, String descriptiom, String report_url,String researcher_id,String pRequest_id) {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for inserting.";
				}
				// create a prepared statement
				String query = " insert into request_proposal (`project_id`,`name`,`descriptiom`,`report_url`,`researcher_id`,`pRequest_id`)"
						+ " values (?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, name);
				preparedStmt.setString(3, descriptiom);
				preparedStmt.setString(4, report_url);
				preparedStmt.setString(5, researcher_id);
				preparedStmt.setInt(6, Integer.parseInt(pRequest_id));
				
	// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Inserted successfully";
			} catch (Exception e) {
				output = "Error while inserting the Submission.";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		public String readSubmissions() {  //data read method
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for reading.";
				}
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>Name</th><th>Description</th>" + "<th>Submission_Url</th>"
						+ "<th>Researcher_ID</th>";

				String query = "select * from request_proposal";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				// iterate through the rows in the result set
				while (rs.next()) {
					String project_id = Integer.toString(rs.getInt("project_id"));
					String name = rs.getString("name");
					String descriptiom = rs.getString("descriptiom");
					String report_url = rs.getString("report_url");
					String researcher_id = rs.getString("researcher_id");
					// Add into the html table
					output += "<tr><td>" + name + "</td>";
					output += "<td>" + descriptiom + "</td>";
					output += "<td>" + report_url + "</td>";
					output += "<td>" + researcher_id + "</td>";
					// buttons
//					output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
//							+ "<td><form method='post' action='items.jsp'>"
//							+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
//							+ "<input name='pRequest_id' type='hidden' value='" + pRequest_id+ "'>" + "</form></td></tr>";
				}
				con.close();
				// Complete the html table
				output += "</table>";
			} catch (Exception e) {
				output = "Error while reading the Submissions.";
				System.err.println(e.getMessage());
			}
			return output;
		}
		
		public String updateSubmission(String ID, String name, String descriptiom, String report_url, String researcher_id) {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for updating.";
				}
				// create a prepared statement
				String query = "UPDATE request_proposal SET name=?,descriptiom=?,report_url=?,researcher_id=? WHERE project_id=?";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setString(1, name);
				preparedStmt.setString(2, descriptiom);
				preparedStmt.setString(3,report_url);
				preparedStmt.setString(4, researcher_id);
				preparedStmt.setInt(5, Integer.parseInt(ID));
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Updated successfully";
			} catch (Exception e) {
				output = "Error while updating the Submission.";
				System.err.println(e.getMessage());
			}
			return output;
		}

		public String deleteSubmission(String project_id) {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for deleting.";
				}
			
				// create a prepared statement
				String query = "delete from request_proposal where project_id=?";
		
				PreparedStatement preparedStmt = con.prepareStatement(query);
				
				// binding values
				preparedStmt.setInt(1, Integer.parseInt(project_id));
		
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Deleted successfully";
				
			} catch (Exception e) {
				output = "Error while deleting the Submission.";
				System.err.println(e.getMessage());
			}
			return output;
		}


}
