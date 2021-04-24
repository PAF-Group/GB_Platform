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
	@Path("/category")
	@Produces(MediaType.TEXT_HTML)
	public String getProductsByCategory(@Context UriInfo uriInfo) {
		// Read category from the request URI info
		String category = uriInfo.getQueryParameters().getFirst("category");
		
		// Get products by category
		String output = productObj.getProductsByCategory(category);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@PermitAll
	@GET
	@Path("/product")
	@Produces(MediaType.TEXT_HTML)
	public String getProductByID(@Context UriInfo uriInfo) {
		// Read product ID from the request URI info
		String productID = uriInfo.getQueryParameters().getFirst("productID");
		
		// Get products by category
		String output = productObj.getProductByID(productID);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------	
	@RolesAllowed(value = { "Admin", "Researcher" })
	@GET
	@Path("/seller-products")
	@Produces(MediaType.TEXT_HTML)
	public String getProductsBySellerID(@Context UriInfo uriInfo) {
		// Read seller ID from the request URI info
		String sellerID = uriInfo.getQueryParameters().getFirst("sellerID");
		
		// Get products by category
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
	@Produces(MediaType.TEXT_PLAIN)
	public String removeProductByID(@Context UriInfo uriInfo) {
		// Read product ID from the request URI info
		String productID = uriInfo.getQueryParameters().getFirst("productID");
		
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
