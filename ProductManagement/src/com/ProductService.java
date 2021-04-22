/* 
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package com;

import model.Product;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

//For JSON
import com.google.gson.*;

////For XML
//import org.jsoup.*;
//import org.jsoup.parser.*;
//import org.jsoup.nodes.Document;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

@Path("/product")
public class ProductService {
	Product productObj = new Product();

//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@PermitAll
	@GET
	@Path("/products-by-category")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String getProductsByCategory(String productData) {
		// Convert the input string to a JSON object
		JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject();
		
		// Read the values from the JSON object
		String category = productObject.get("category").getAsString();
		
		String output = productObj.getProductsByCategory(category);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@PermitAll
	@GET
	@Path("/product-by-pid")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String getProductByID(String productData) {
		// Convert the input string to a JSON object
		JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject();
		
		// Read the values from the JSON object
		String productID = productObject.get("productID").getAsString();
		
		String output = productObj.getProductByID(productID);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------	
	@RolesAllowed(value = { "Admin", "Researcher" })
	@GET
	@Path("/products-by-sid")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String getProductsBySellerID(String productData) {
		// Convert the input string to a JSON object
		JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject();
		
		// Read the values from the JSON object
		String sellerID = productObject.get("sellerID").getAsString();
		
		String output = productObj.getProductsBySellerID(sellerID);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Researcher" })
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertProduct(String productData) {
		// Convert the input string to a JSON object
		JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject();
		
		// Read the values from the JSON object
		String productName = productObject.get("productName").getAsString();
		String description = productObject.get("description").getAsString();
		String unitPrice = productObject.get("unitPrice").getAsString();
		String category = productObject.get("category").getAsString();
		String productStatus = productObject.get("productStatus").getAsString();
		String sellerID = productObject.get("sellerID").getAsString();
		
		String output = productObj.createProduct(productName, description, unitPrice, category, productStatus, sellerID);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Researcher" })
	@PUT
	@Path("/update-product")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateProductByID(String productData) {
		// Convert the input string to a JSON object
		JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject();
		
		// Read the values from the JSON object
		String productID = productObject.get("productID").getAsString();
		String productName = productObject.get("productName").getAsString();
		String description = productObject.get("description").getAsString();
		String unitPrice = productObject.get("unitPrice").getAsString();
		String category = productObject.get("category").getAsString();
		String productStatus = productObject.get("productStatus").getAsString();
		
		String output = productObj.updateProductByID(productID, productName, description, unitPrice, category, productStatus);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Researcher" })
	@PUT
	@Path("/update-category")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCategoryBySellerID(String productData) {
		// Convert the input string to a JSON object
		JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject();
		
		// Read the values from the JSON object
		String sellerID = productObject.get("sellerID").getAsString();
		String oldCategory = productObject.get("oldCategory").getAsString();
		String newCategory = productObject.get("newCategory").getAsString();
		
		String output = productObj.updateCategoryBySellerID(sellerID, oldCategory, newCategory);
		
		return output;
		
	}

//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin", "Researcher" })
	@DELETE
	@Path("/remove")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String removeProductByID(String productData) {
		// Convert the input string to a JSON object
		JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject();

		// Read the values from the JSON object
		String productID = productObject.get("productID").getAsString();
		
		String output = productObj.removeProductByID(productID);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@PermitAll
	@GET
	@Path("/read")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPriceSellerID( @Context UriInfo uriInfo ) {
		// Read product ID from the request URI info
		String productID = uriInfo.getQueryParameters().getFirst("productID");
		
		String output = productObj.getPriceSellerID(productID);
		
		return output;
		
	}
	

}
