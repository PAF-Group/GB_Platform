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

@Path("/buyer")
public class BuyerService {
	Buyer buyerObj = new Buyer();

//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getAllBuyers() {
		return buyerObj.getBuyers();
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBuyer(@FormParam("name") String name, @FormParam("userPhone") String userPhone, @FormParam("address") String address,
			@FormParam("userAgreement") String userAgreement, @FormParam("email") String email, @FormParam("password") String password, @FormParam("accStatus") String accStatus) {
		String output = buyerObj.createBuyer(name, userPhone, address, userAgreement, email, password, accStatus);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateBuyer(String buyerData) {
		// Convert the input string to a JSON object
		JsonObject buyerObject = new JsonParser().parse(buyerData).getAsJsonObject();
		
		// Read the values from the JSON object
		String userID = buyerObject.get("userID").getAsString();
		String name = buyerObject.get("name").getAsString();
		String userPhone = buyerObject.get("userPhone").getAsString();
		String address = buyerObject.get("address").getAsString();
		String userAgreement = buyerObject.get("userAgreement").getAsString();
		String email = buyerObject.get("email").getAsString();
		
		String output = buyerObj.updateBuyer(userID, name, userPhone, address, userAgreement, email);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String disableBuyer(String buyerData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(buyerData, "", Parser.xmlParser());

		// Read the value from the element <userID> & <accStatus>
		String userID = doc.select("userID").text();
		String accStatus = doc.select("accStatus").text();
		
		String output = buyerObj.disableBuyer(userID, accStatus);
		
		return output;
		
	}
	
}
