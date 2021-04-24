/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package model;

import java.sql.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import utility.DatabaseConnectivity;

public class Product {
//	The method to create a new Product record => for Researchers ------------------------------------------------------------------------------------------------
	public String createProduct(String productName, String description, String unitPrice, String category, String productStatus, String sellerID) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// See whether the Seller/Researcher is active or inactive
			ClientConfig clientC = new ClientConfig();

			Client client = ClientBuilder.newClient(clientC);

			Response response = client.target("http://localhost:8080/UserManagement/user-management-service/user/user-status")
					.queryParam("userID", sellerID).request().get();
			
			String status = response.readEntity(String.class);
			
			if(status.equals("valid")) {
				// The query to insert a new record to the Product table & prepared statements
				String query = "INSERT INTO `productdb`.`product` (`product_name`, `description`, `unit_price`, `category`, `status`, `seller_id`) VALUES (?, ?, ?, ?, ?, ?)";
							
				PreparedStatement preparedStmt = con.prepareStatement(query);
							
				// binding values
				preparedStmt.setString(1, productName);
				preparedStmt.setString(2, description);
				preparedStmt.setDouble(3, Double.parseDouble(unitPrice));
				preparedStmt.setString(4, category);
				preparedStmt.setString(5, productStatus);
				preparedStmt.setInt(6, Integer.parseInt(sellerID));
							
				// execute the statement
				preparedStmt.execute();
				
				// Close the database connection
				con.close();
				
				// Success
				output = "A new Product has created successfully!...";
				
			} else if(status.equals("invalid")) {
				output = "Invalid User - Can not process this request!...";
			
			} else {
				output = "An error has occurred while creating the Product.";
				
			}
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while creating the Product.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------
	
//  The method to read all the products in a category -----------------------------------------------------------------------------------------------------------
	public String getProductsByCategory( String category ) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// Prepare a HTML table to display the Products ordered by a Category
			output = "<table border='1'>" + "<tr>" + "Category : " + category + "</tr>" + "<tr>" + "<th>Product Name</th>" + "<th>Description</th>" + "<th>Unit Price</th>"
					+ "<th>Product Status</th>" + "<th>Created At</th>" + "<th>Updated At</th>" + "</tr>";

			// The query to select all Product records ordered by a Category
			String query = "SELECT `product_name`, `description`, `unit_price`, `status`, `created_at`, `updated_at` FROM `productdb`.`product` WHERE `category`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, category);
			
			ResultSet set = preparedStmt.executeQuery();
			
			// Iterate through all the records in the result set
			while (set.next()) {
				// Reading values from the Result Set - set
				String productName = set.getString("product_name");
				String description = set.getString("description");
				String unitPrice = Double.toString(set.getDouble("unit_price"));
				String productStatus = Integer.toString(set.getInt("status"));
				String createdAt = set.getTimestamp("created_at").toString();
				String updatedAt = set.getTimestamp("updated_at").toString();
				
				// Add the record in to the HTML table
				output += "<tr><td>" + productName + "</td>";
				output += "<td>" + description + "</td>";
				output += "<td>" + unitPrice + "</td>";
				output += "<td>" + productStatus + "</td>";
				output += "<td>" + createdAt + "</td>";
				output += "<td>" + updatedAt + "</td></tr>";
				
			}
			
			// Close the database connection
			con.close();
			
			// End of the HTML table
			output += "</table>";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while reading the Product records.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//  The method to get a product by product ID -------------------------------------------------------------------------------------------------------------------
	public String getProductByID( String productID ) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// Prepare a HTML table to display the Product details
			output = "<table border='1'>" + "<tr>" + "<th>Product Name</th>" + "<th>Description</th>" + "<th>Unit Price</th>"
					+ "<th>Category</th>" + "<th>Product Status</th>" + "<th>Created At</th>" + "<th>Updated At</th>" + "</tr>";
			
			// The query to select the Product record by product ID
			String query = "SELECT `product_name`, `description`, `unit_price`, `category`, `status`, `created_at`, `updated_at` FROM `productdb`.`product` WHERE `product_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(productID));
			
			ResultSet set = preparedStmt.executeQuery();
			
			// Iterate through all the records in the result set
			while (set.next()) {
				// Reading values from the Result Set - set
				String productName = set.getString("product_name");
				String description = set.getString("description");
				String unitPrice = Double.toString(set.getDouble("unit_price"));
				String category = set.getString("category");
				String productStatus = set.getString("status");
				String createdAt = set.getTimestamp("created_at").toString();
				String updatedAt = set.getTimestamp("updated_at").toString();
				
				// Add the record in to the HTML table
				output += "<tr><td>" + productName + "</td>";
				output += "<td>" + description + "</td>";
				output += "<td>" + unitPrice + "</td>";
				output += "<td>" + category + "</td>";
				output += "<td>" + productStatus + "</td>";
				output += "<td>" + createdAt + "</td>";
				output += "<td>" + updatedAt + "</td></tr>";
				
			}
			
			// Close the database connection
			con.close();
			
			// End of the HTML table
			output += "</table>";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while reading the Product record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}

//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//  The method to get products by seller ID ---------------------------------------------------------------------------------------------------------------------
	public String getProductsBySellerID( String sellerID ) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// Prepare a HTML table to display the Product details
			output = "<table border='1'>" + "<tr>" + "<th>Product Name</th>" + "<th>Description</th>" + "<th>Unit Price</th>"
					+ "<th>Category</th>" + "<th>Product Status</th>" + "<th>Created At</th>" + "<th>Updated At</th>" + "</tr>";
			
			// The query to select the Product records by seller ID
			String query = "SELECT `product_name`, `description`, `unit_price`, `category`, `status`, `created_at`, `updated_at` FROM `productdb`.`product` WHERE `seller_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(sellerID));
			
			ResultSet set = preparedStmt.executeQuery();
			
			// Iterate through all the records in the result set
			while (set.next()) {
				// Reading values from the Result Set - set
				String productName = set.getString("product_name");
				String description = set.getString("description");
				String unitPrice = Double.toString(set.getDouble("unit_price"));
				String category = set.getString("category");
				String productStatus = Integer.toString(set.getInt("status"));
				String createdAt = set.getTimestamp("created_at").toString();
				String updatedAt = set.getTimestamp("updated_at").toString();
				
				// Add the record in to the HTML table
				output += "<tr><td>" + productName + "</td>";
				output += "<td>" + description + "</td>";
				output += "<td>" + unitPrice + "</td>";
				output += "<td>" + category + "</td>";
				output += "<td>" + productStatus + "</td>";
				output += "<td>" + createdAt + "</td>";
				output += "<td>" + updatedAt + "</td></tr>";
				
			}
			
			// Close the database connection
			con.close();
			
			// End of the HTML table
			output += "</table>";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while reading the Product records.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//  The method to update a product record by product ID ---------------------------------------------------------------------------------------------------------
	public String updateProductByID( String productID, String productName, String description, String unitPrice, String category, String productStatus ) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to update the product record
			String query = "UPDATE `productdb`.`product` SET `product_name`=?, `description`=?, `unit_price`=?, `category`=?, `status`=? WHERE `product_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, productName);
			preparedStmt.setString(2, description);
			preparedStmt.setDouble(3, Double.parseDouble(unitPrice));
			preparedStmt.setString(4, category);
			preparedStmt.setString(5, productStatus);
			preparedStmt.setInt(6, Integer.parseInt(productID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Product record has Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the Product record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//  The method to update the category of multiple products owned by a certain Seller/Researcher  ----------------------------------------------------------------
	public String updateCategoryBySellerID( String sellerID, String oldCategory, String newCategory ) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to retrieve all the product records where category = oldCategory & seller ID = sellerID
			String query1 = "SELECT `product_id` FROM `productdb`.`product` WHERE `seller_id`=? AND `category`=?";
			
			PreparedStatement preparedStmt1 = con.prepareStatement(query1);
			
			// binding values
			preparedStmt1.setInt(1, Integer.parseInt(sellerID));
			preparedStmt1.setString(2, oldCategory);
			
			ResultSet set = preparedStmt1.executeQuery();
			
			if(set.next() == true) {
				// Iterate through all the records in the result set
				while (set.next()) {
					// Reading values from the Result Set - set
					int productID = set.getInt("product_id");
					
					// The query to update the product category
					String query2 = "UPDATE `productdb`.`product` SET `category`=? WHERE `product_id`=?";
					
					PreparedStatement preparedStmt2 = con.prepareStatement(query2);
					
					// binding values
					preparedStmt2.setString(1, newCategory);
					preparedStmt2.setInt(2, productID);
					
					// execute the statement
					preparedStmt2.execute();
					
				}
				
			} else {
				output = "There are NO records available for the given category.";
				
			}
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Category pf all the product records has Updated successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the Product record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//  The method to remove a product using product ID  ------------------------------------------------------------------------------------------------------------
	public String removeProductByID( String productID ) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to delete the product record
			String query = "DELETE FROM `productdb`.`product` WHERE `product_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(productID));
			
			// execute the statement
			preparedStmt.execute();
			
			// Close the database connection
			con.close();
			
			// Success
			output = "Product record has deleted successfully!...";
			
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the Product record.";
			System.err.println(e.getMessage());
			
		}
		
		return output;

	}
	
//	-------------------------------------------------------------------------------------------------------------------------------------------------------------

//  The method to get seller ID & unit price => for the Order Management microservice ---------------------------------------------------------------------------
	public String getPriceSellerID(String productID) {
		String output = "";
		
		try {
			Connection con = DatabaseConnectivity.connect();
			
			if (con == null) {
				return "An error has occurred while connecting to the database.";
				
			}
			
			// The query to retrieve unit price & seller ID
			String query = "SELECT `unit_price`, `seller_id` FROM `productdb`.`product` WHERE `product_id`=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(productID));
			
			ResultSet set = preparedStmt.executeQuery();
			
			if(set.next() == true) {
				String unitPrice = Double.toString(set.getDouble("unit_price"));
				String sellerID = Double.toString(set.getDouble("seller_id"));
				
				output = "{'unitPrice' : " + unitPrice + ", 'sellerId' : " + sellerID + "}";
				
			} else {
				output = "Ther are NO record for the give product.";
				
			}
			
			// Close the database connection
			con.close();
		
		} catch (Exception e) {
			// Failure
			output = "An error has occurred while updating the Product record.";
			System.err.println(e.getMessage());
			
		}
			
		return output;
		
	}
	
}
