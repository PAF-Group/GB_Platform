package model;
/* 
 * @author W.G. YASIRU RANDIKA 
 * IT19131184
 * 
 * */

import java.sql.*;

import javax.ws.rs.core.MediaType;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

public class Orders {
	/*
	 * Method for connect to the database
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
	 */
	public String getAllOrders() {
		String output;
		try {
			Connection con = connect();
			if (con == null) {
				output = "Error while connecting to the database for reading.";
				return output;
			}

			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Order Id</th><th>Buyer Id</th>" + "<th>Date</th>" + "<th>Status</th>"
					+ "<th>Total Amount</th>";

			// SQL Query for selecting all orders
			String query = "select * from orders";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {

				String orderId = Integer.toString(rs.getInt("OrderId"));
				String buyerId = Integer.toString(rs.getInt("BuyerId"));
				String date = rs.getDate("Date").toString();
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
	 */
	public String addOrder(int buyerId, String shippingAddress, JsonArray orders) {
		String output = "";
		int orderId;
		double total = 0;
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			String sAdr = getShippingAddress(shippingAddress, String.valueOf(buyerId));

			// create a prepared statement
			String query = "INSERT INTO Orders(BuyerId, ShippingAddress) VALUES(?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, buyerId);
			preparedStmt.setString(2, sAdr);
			// execute the statement
			preparedStmt.execute();

			String queryOrderId = "SELECT OrderId FROM orders WHERE BuyerId = ? ORDER BY OrderId DESC LIMIT 1";
			PreparedStatement preparedStmt1 = con.prepareStatement(queryOrderId);
			// binding values
			preparedStmt1.setInt(1, buyerId);
			// execute the statement
			ResultSet resultSet = preparedStmt1.executeQuery();

			if (resultSet.next()) {
				orderId = resultSet.getInt(1);
			} else {
				return "Error whiling processing";
			}

			// Get the items in the order and add them to the Order details table
			for (int i = 0; i < orders.size(); i++) {
				JsonObject ord = orders.get(i).getAsJsonObject();
				String productId = ord.get("productId").getAsString();
				int quantity = ord.get("quantity").getAsInt();

				// Get the unit price of the product from Product Micro Service
				Client client = new Client();
				WebResource resource = client.resource("http://localhost:8080/Lab05Rest/ItemService/Items");
				String response = resource.queryParam("id", productId).accept(MediaType.TEXT_PLAIN).get(String.class);

				// Calculate the amount
				double amount = quantity * Double.parseDouble(response);

				// Add the amount to the total price
				total += amount;

				String queryOD = "INSERT INTO `orderdetails`(`OrderId`, `ProductId`, `Quantity`, `UnitPrice`) VALUES (? , ? , ? , ?)";
				PreparedStatement preparedStmt2 = con.prepareStatement(queryOD);
				// binding values
				preparedStmt2.setInt(1, orderId);
				preparedStmt2.setInt(2, Integer.parseInt(productId));
				preparedStmt2.setInt(3, quantity);
				preparedStmt2.setDouble(4, amount);
				// execute the statement
				preparedStmt2.execute();
			}

			// Update the orders table with total price of the order
			String queryTP = "UPDATE `orders` SET `TotalAmount`= ? WHERE OrderId = ?";
			PreparedStatement preparedStmt3 = con.prepareStatement(queryTP);
			// binding values
			preparedStmt3.setInt(2, orderId);
			preparedStmt3.setDouble(1, total);
			// execute the statement
			preparedStmt3.execute();

			con.close();
			output = "<h5>Order Placed Successfully</h5> <ul><li>Order ID : " + orderId + "</li><li>Total Amount : LKR "
					+ total + "</li></ul><p>Next, please add your payment to process the order</p>";
		} catch (Exception e) {
			output = "Error while inserting data";
			System.err.println(e.getMessage());
		}
		return output;
	}

	/*
	 * Get the shipping address from for the order If the user has mentioned same
	 * then the shipping address is taken from User Management via API call Else use
	 * the address provided by the user at the order placement
	 * 
	 */
	private String getShippingAddress(String shippingAddress, String buyerId) {
		String sAdr;
		if (shippingAddress.equals("Same Address")) {
			Client client = new Client();
			WebResource resource = client.resource("http://localhost:8080/Lab05Rest/ItemService/Items");
			sAdr = resource.queryParam("id", buyerId).accept(MediaType.TEXT_PLAIN).get(String.class);
		} else {
			sAdr = shippingAddress;
		}
		return sAdr;
	}

	/*
	 * Delete a order When deleting the order it will check the stage of the order
	 * According to the status of the order it will decide whether delete(cancel)
	 * the order or not
	 * 
	 * If we can delete the order, it will delete all the records related to that
	 * order
	 */
	public String deleteOrder(String orderId) {
		String output = null;
		String status = null;
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// Get the status of the order
			String query = "SELECT `status` from orders where orderId = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(orderId));
			// execute the statement
			ResultSet rs = preparedStmt.executeQuery();

			if (rs.next()) {
				status = rs.getString(1);
			} else {
				return "Something went wrong";
			}

			if (status.equals("SHIPPED ALL") || status.equals("SHIPPED SOME ITEMS")) {
				output = "Your order has been shipped therefore you cannot delete the order now";
				return output;
			}

			// Delete the record in order table
			String query1 = "DELETE FROM orders WHERE orderId = ?";
			PreparedStatement preparedStmt1 = con.prepareStatement(query1);
			// binding values
			preparedStmt1.setInt(1, Integer.parseInt(orderId));
			// execute the statement
			preparedStmt1.execute();

			output = "Order Deleted Successfully";

			// Close the connection
			con.close();

		} catch (Exception e) {
			output = "Error while deleting order";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateOrder(int orderId, int buyerId, String shippingAddress, JsonArray orders) {
		String output = "";
		double total = 0;
		String orStatus;
		String sAdr;
		String currentSA;
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// Get the status and shipping address of the order
			String query = "SELECT `status`, shippingAddress from orders where orderId = ?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, orderId);
			// execute the statement
			ResultSet rs = preparedStmt.executeQuery();

			// Get the shipping address
			sAdr = getShippingAddress(shippingAddress, String.valueOf(buyerId));

			if (rs.next()) {
				orStatus = rs.getString(1);
				currentSA = rs.getString(2);
			} else {
				return "Something went wrong";
			}

			if ((orStatus.equals("SHIPPED ALL") || orStatus.equals("SHIPPED SOME ITEMS")) && !currentSA.equals(sAdr)) {
				output = "<h6>Your order has been shipped therefore you cannot change the shipping address<h6>";
				sAdr = currentSA;
			}

			// Get the items in the order and add them to the Order details table
			for (int i = 0; i < orders.size(); i++) {
				String pStatus;
				JsonObject ord = orders.get(i).getAsJsonObject();
				String productId = ord.get("productId").getAsString();
				int quantity = ord.get("quantity").getAsInt();
				int qty;
				double unitPrice;
				
				// Get the status and quantity of the product in the order
				String query2 = "SELECT `status`, quantity, unitPrice from orderDetails where orderId = ? and productId = ?";
				PreparedStatement preparedStmt2 = con.prepareStatement(query2);
				// binding values
				preparedStmt2.setInt(1, orderId);
				preparedStmt2.setInt(2, Integer.parseInt(productId));
				// execute the statement
				ResultSet rs2 = preparedStmt2.executeQuery();

				if (rs2.next()) {
					pStatus = rs2.getString(1);
					qty = rs2.getInt(2);
					unitPrice = rs2.getDouble(3);
				} else {
					return "Something went wrong in checking products";
				}
				
				//If quantity has not been changed no need to update
				if (qty == quantity) {
					total += qty * unitPrice;
					break;
				}
				
				if (pStatus.equals("SHIPPED")) {
					output += "<h6>Your product " + productId + " has been shipped therefore you cannot change the quantity now</h6>";
					break;
				}
				
				// Calculate the amount
				double amount = quantity * unitPrice;

				// Add the amount to the total price
				total += amount;

				String query3 = "UPDATE `orderdetails` SET `Quantity` = ?";
				PreparedStatement preparedStmt3 = con.prepareStatement(query3);
				// binding values
				preparedStmt3.setInt(1, quantity);
				// execute the statement
				preparedStmt3.execute();
			}

			// Update the orders table with total price of the order
			String queryTP = "UPDATE `orders` SET `TotalAmount`= ?, shippingAddress = ? WHERE OrderId = ?";
			PreparedStatement preparedStmt3 = con.prepareStatement(queryTP);
			// binding values
			preparedStmt3.setInt(3, orderId);
			preparedStmt3.setDouble(1, total);
			preparedStmt3.setString(2, sAdr);
			// execute the statement
			preparedStmt3.execute();

			con.close();
			output += "<h5>Update operation successfully executed. Check whehter some errors in the description<h5>";
		} catch (Exception e) {
			output = "Error while updating data";
			System.err.println(e.getMessage());
		}
		return output;
	}
}