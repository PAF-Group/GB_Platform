package model;
/* 
 * @author W.G. YASIRU RANDIKA 
 * IT19131184
 * 
 * */

import java.sql.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

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
	public String addOrder(int buyerId, String shippingAddress, JsonArray orders) {
		String output = "";
		int orderId;
		double total = 0;
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "INSERT INTO Orders(BuyerId, ShippingAddress) VALUES(?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, buyerId);
			preparedStmt.setString(2, shippingAddress);
			// execute the statement
			preparedStmt.execute();
			
			String queryOrderId = "SELECT OrderId FROM orders WHERE BuyerId = ? ORDER BY OrderId DESC LIMIT 1";
			PreparedStatement preparedStmt1 = con.prepareStatement(queryOrderId);
			//binding values
			preparedStmt1.setInt(1, buyerId);
			// execute the statement
			ResultSet resultSet = preparedStmt1.executeQuery();
			
			if (resultSet.next()) {
				orderId = resultSet.getInt(1);
			} else {
				return "Error whiling processing";
			}
			
			Client client = new Client();
			
			//Get the items in the order and add them to the Order details table
			for(int i = 0; i < orders.size(); i++) {
				JsonObject ord =  orders.get(i).getAsJsonObject();
				String productId = ord.get("productId").getAsString();
				int quantity = ord.get("quantity").getAsInt();
				
				//Get the unit price of the product from Product Micro Service
				WebResource resource = client.resource("http://localhost:8080/Lab05Rest/ItemService/Items");
		        String response = resource.queryParam("id", productId).accept(MediaType.TEXT_PLAIN).get(String.class);
				
		        //Calculate the amount 
		        double amount = quantity * Double.parseDouble(response);
		        
		        //Add the amount to the total price
		        total += amount;
		        
		        String queryOD = "INSERT INTO `orderdetails`(`OrderId`, `ProductId`, `Quantity`, `UnitPrice`) VALUES (? , ? , ? , ?)";
				PreparedStatement preparedStmt2 = con.prepareStatement(queryOD);
				//binding values
				preparedStmt2.setInt(1, orderId);
				preparedStmt2.setInt(2, Integer.parseInt(productId));
				preparedStmt2.setInt(3, quantity);
				preparedStmt2.setDouble(4, amount);
				// execute the statement
				preparedStmt2.execute();
			}
			
			//Update the orders table with total price of the order
			String queryTP = "UPDATE `orders` SET `TotalAmount`= ? WHERE OrderId = ?";
			PreparedStatement preparedStmt3 = con.prepareStatement(queryTP);
			//binding values
			preparedStmt3.setInt(2, orderId);
			preparedStmt3.setDouble(1, total);
			// execute the statement
			preparedStmt3.execute();
			
			con.close();
			output = "<h5>Order Placed Successfully</h5> <ul><li>Order ID : " + orderId + "</li><li>Total Amount : LKR " +total + "</li></ul><p>Next, please add your payment to process the order</p>";
		} catch (Exception e) {
			output = "Error while inserting data";
			System.err.println(e.getMessage());
		}
		return output;
	}
}