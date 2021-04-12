package model;
/* 
 * @author W.G. YASIRU RANDIKA 
 * IT19131184
 * 
 * */

import java.sql.*;

public class Orders { 
	/*Method for connect to the database
	 * 
	 * @return Connection
	*/
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gb_ordermanagement", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	/*
	 * Read all records in the orders table
	 * 
	 * */
	public String getAllOrders() {
		String output;
		try {
			Connection con = connect();
			if (con == null) {
				output =  "Error while connecting to the database for reading.";
				return output;
			}
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Order Id</th><th>Buyer Id</th>" + "<th>Date</th>"
								+ "<th>Status</th>" + "<th>Total Amount</th>";
			
			//SQL Query for selecting all orders
			String query = "select * from orders";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				
				String orderId = Integer.toString(rs.getInt("OrderId"));
				String buyerId  = Integer.toString(rs.getInt("BuyerId"));
				String date  = rs.getDate("Date").toString();
				String status = rs.getString("Status");
				String totalAmount = Double.toString(rs.getDouble("TotalAmount"));
				
				// Add into the html table
				output += "<tr><td>" + orderId + "</td>";
				output += "<td>" + buyerId + "</td>";
				output += "<td>" + date + "</td>";
				output += "<td>" + status + "</td>";
				output += "<td>" + totalAmount + "</td></tr>";
				}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the records.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	/*
	 * Add order to the database
	 * 
	 * */
}