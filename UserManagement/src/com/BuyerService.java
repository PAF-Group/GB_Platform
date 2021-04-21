/* 
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package com;

import model.Buyer;

//For REST Services
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.Jsoup;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import javax.annotation.security.RolesAllowed;

@Path("/buyer")
public class BuyerService {
	Buyer buyerObj = new Buyer();

//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin", "Member" })
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getAllBuyers() {
		return buyerObj.getBuyers();
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin", "Buyer" })
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBuyer(String buyerData) {
		// Convert the input string to a JSON object
		JsonObject buyerObject = new JsonParser().parse(buyerData).getAsJsonObject();
		
		// Read the values from the JSON object
		String name = buyerObject.get("name").getAsString();
		String userPhone = buyerObject.get("userPhone").getAsString();
		String address = buyerObject.get("address").getAsString();
		String userAgreement = buyerObject.get("userAgreement").getAsString();
		String email = buyerObject.get("email").getAsString();
		String password = buyerObject.get("password").getAsString();
		String userRole = buyerObject.get("userRole").getAsString();
		String accStatus = buyerObject.get("accStatus").getAsString();
		
		String output = buyerObj.createBuyer(name, userPhone, address, userAgreement, email, password, userRole, accStatus);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin", "Buyer" })
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBuyer(String buyerData) {
		// Convert the input string to a JSON object
		JsonObject buyerObject = new JsonParser().parse(buyerData).getAsJsonObject();
		
		// Read the values from the JSON object
		String buyerID = buyerObject.get("buyerID").getAsString();
		String name = buyerObject.get("name").getAsString();
		String userPhone = buyerObject.get("userPhone").getAsString();
		String address = buyerObject.get("address").getAsString();
		String userAgreement = buyerObject.get("userAgreement").getAsString();
		
		String output = buyerObj.updateBuyer(buyerID, name, userPhone, address, userAgreement);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Buyer" })
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBuyerEmailPassword(String buyerData) {
		// Convert the input string to a JSON object
		JsonObject buyerObject = new JsonParser().parse(buyerData).getAsJsonObject();
		
		// Read the values from the JSON object
		String userID = buyerObject.get("userID").getAsString();
		String userEmail = buyerObject.get("userEmail").getAsString();
		String password = buyerObject.get("password").getAsString();
		
		String output = buyerObj.updateBuyerEmailPassword(userID, userEmail, password);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin" })
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String disableBuyer(String buyerData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(buyerData, "", Parser.xmlParser());

		// Read the value from the element <userID> & <accStatus>
		String buyerID = doc.select("buyerID").text();
		String accStatus = doc.select("accStatus").text();
		
		String output = buyerObj.disableBuyer(buyerID, accStatus);
		
		return output;
		
	}
	
}
