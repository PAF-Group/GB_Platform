
/* 
 * @author  IT19160580 Maddumage T.A.R
 * 
 * */

package model;

import java.sql.*;

public class Funds {
	// A common method to connect to the DB
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

		//Insert Agreement
		
		public String insertfund(String date, float amount,String bankslip) {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for inserting.";
				}
				// create a prepared statement
				String query = " insert into funds_table (Funding_ID,Date,Amount,Bank_Slip)"
						+ " values (?, ?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				preparedStmt.setInt(1, 0);
				preparedStmt.setString(2, date);
				preparedStmt.setFloat(3, amount);
				preparedStmt.setString(4, bankslip);
				
				
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
		

		public String readfund() {
			String output = "";
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for reading.";
				}
				// Prepare the html table to be displayed
				output = "<table border='1'><tr><th>Funding_ID</th><th>Date</th>" + "<th>Amount</th>"
						+ "<th>Bank_Slip</th></tr>";

				String query = "select * from funds_table";
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				// iterate through the rows in the result set
				while (rs.next()) {
					String fundingID = Integer.toString(rs.getInt("Funding_ID"));
					String date = rs.getString("Date");
					String amount = Float.toString(rs.getFloat("Amount"));
					String bankslips = rs.getString("Bank_Slip");
					
					
					// Add into the html table
					output += "<tr><td>" +  fundingID  + "</td>";
					output += "<td>" + date + "</td>";
					output += "<td>" + amount + "</td>";
					output += "<td>" + bankslips + "</td>";
					
					
					// buttons
					output += 
							 "<td><form method='post' action='items.jsp'>"
							
							+ "<input name='itemID' type='hidden' value='" + fundingID + "'>" + "</form></td></tr>";
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

		

}
