package com;
/* 
 * @author W.G. YASIRU RANDIKA 
 * IT19131184
 * 
 * */

import model.Orders;

import javax.annotation.security.RolesAllowed;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

//For JSON
import com.google.gson.*;

@Path("/Orders")
public class OrderService {
	Orders orderModel = new Orders();
	
	@GET
	@Path("/{orderId}")
	@RolesAllowed({"Buyer","Admin", "Researcher"})
	@Produces(MediaType.TEXT_HTML)
	public String getOrder(@PathParam("orderId") String orderId) {
		return orderModel.getOrderById(Integer.parseInt(orderId));
	}
	
	@GET
	@Path("/Buyers/{buyerId}")
	@Produces(MediaType.TEXT_HTML)
	public String getOrdersByBuyer(@PathParam("buyerId") String buyerId) {
		return orderModel.getOrdersByBuyer(buyerId);
	}
	
	@GET
	@Path("/Sellers/{sellerId}")
	@Produces(MediaType.TEXT_HTML)
	public String getOrdersBySeller(@PathParam("sellerId") String sellerId) {
		return orderModel.getOrdersBySeller(sellerId);
	}
	
	@GET
	@Path("/")
	@RolesAllowed(value = { "Admin" })
	@Produces(MediaType.TEXT_HTML)
	public String getOrders() {
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
		
		String output = "";
		//orderModel.addOrder(Integer.parseInt(buyerId), shippingAddress, orderDetails);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String itemData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String orderId = doc.select("OrderId").text();
		String output = orderModel.deleteOrder(orderId);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String updateOrder(String itemData) {
		// Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		// Read the values from the JSON object
		String orderId =  itemObject.get("orderId").getAsString();
		String buyerId =  itemObject.get("buyerId").getAsString();
		String shippingAddress = itemObject.get("shippingAddress").getAsString();
		JsonArray orderDetails = itemObject.get("orderDetails").getAsJsonArray();
		
		String output = orderModel.updateOrder(Integer.parseInt(orderId), Integer.parseInt(buyerId), shippingAddress, orderDetails);
		return output;
	}
	
	
	@PUT
	@Path("/addPayment")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String addPayment(@FormParam("orderId") String orderId, @FormParam("paymentSlipUrl") String paymentSlip) {
		String output = orderModel.addPayment(orderId, paymentSlip);
		return output;
	}
	
	@PUT
	@Path("/acceptPayment")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String acceptPayment(@QueryParam("orderId") int OrderId) {
		String output = orderModel.acceptPayment(OrderId);
		return output;
	}
	
	@PUT
	@Path("/rejectPayment")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String rejectPayment(@QueryParam("orderId") int OrderId) {
		String output = orderModel.rejectPayment(OrderId);
		return output;
	}
	
	@PUT
	@Path("/addShipping")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String addShipping(@FormParam("orderId") int OrderId, @FormParam("productId") int productId, @FormParam("date") String date, @FormParam("shippingCompany") String shippingCompany, @FormParam("trackId") String trackId) {
		String output = orderModel.addShipping(OrderId, productId, date, shippingCompany, trackId);
		return output;
	}
	
	@PUT
	@Path("/confirmOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String confirmOrder(@FormParam("orderId") int OrderId, @FormParam("productId") int productId) {
		String output = orderModel.confirmOrder(OrderId, productId);
		return output;
	}
	
	@POST
	@Path("/Issue")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String openIssue(String issueDetails) {
		// Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(issueDetails).getAsJsonObject();
		// Read the values from the JSON object
		int orderId = itemObject.get("orderId").getAsInt();
		int productId = itemObject.get("productId").getAsInt();
		String issue = itemObject.get("issue").getAsString();
		
		String output = orderModel.openIssue(orderId, productId, issue);
		return output;
	}
	
	
	@PUT
	@Path("/Issue")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String changeIssueStatus(String issueDetails) {
		// Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(issueDetails).getAsJsonObject();
		// Read the values from the JSON object
		int orderId = itemObject.get("issueId").getAsInt();
		String status = itemObject.get("status").getAsString();
		
		String output = orderModel.changeIssueStatus(orderId, status);
		return output;
	}
	
	@DELETE
	@Path("/Issue")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteIssue(String itemData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String issueId = doc.select("IssueId").text();
		String output = orderModel.deleteIssue(issueId);
		return output;
	}
	
	@GET
	@Path("/Issue/{issueId}")
	@Produces(MediaType.TEXT_HTML)
	public String getIssueById(@PathParam("issueId") String issueId) {
		return orderModel.getIssueById(Integer.parseInt(issueId));
	}
	
	@GET
	@Path("/Issue")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_HTML)
	public String getIssuesforOrder(String data) {
		// Convert the input string to an XML document
				Document doc = Jsoup.parse(data, "", Parser.xmlParser());

				// Read the value from the element <itemID>
				String id = doc.select("OrderId").text();
				String output = orderModel.issuesInOrder(id);
				return output;
	}
}

