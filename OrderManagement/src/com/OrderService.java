package com;
/* 
 * @author W.G. YASIRU RANDIKA 
 * IT19131184
 * 
 * */

import model.Orders;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

@Path("/Orders")
public class OrderService {
	Orders orderModel = new Orders();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getItems() {
		return orderModel.getAllOrders();
	}
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String addOrder(String itemData) {
		// Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		// Read the values from the JSON object
		String buyerId = itemObject.get("buyerId").getAsString();
		String shippingAddress = itemObject.get("shippingAddress").getAsString();
		JsonArray orderDetails = itemObject.get("orderDetails").getAsJsonArray();
		
		String output = orderModel.addOrder(Integer.parseInt(buyerId), shippingAddress, orderDetails);
		return output;
	}
}

