/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package com;

import model.UserAgreement;

//For REST Services
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.Jsoup;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/user-agreement")
public class UserAgreementService {
	UserAgreement agreementObj = new UserAgreement();

//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getAllUserAgreements() {
		return agreementObj.getAgreements();
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertUserAgreement(@FormParam("agreementName") String agreementName, @FormParam("description") String description, 
			@FormParam("fileLocation") String fileLocation) {
		String output = agreementObj.createAgreement(agreementName, description, fileLocation);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateUserAgreement(String agreementData) {
		// Convert the input string to a JSON object
		JsonObject agreementObject = new JsonParser().parse(agreementData).getAsJsonObject();
		
		// Read the values from the JSON object
		String agreementID = agreementObject.get("agreementID").getAsString();
		String agreementName = agreementObject.get("agreementName").getAsString();
		String description = agreementObject.get("description").getAsString();
		String fileLocation = agreementObject.get("fileLocation").getAsString();
		
		String output = agreementObj.updateAgreement(agreementID, agreementName, description, fileLocation);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteUserAgreement(String agreementData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(agreementData, "", Parser.xmlParser());

		// Read the value from the element <agreementID>
		String agreementID = doc.select("agreementID").text();
		
		String output = agreementObj.deleteAgreement(agreementID);
		
		return output;
		
	}
	
}
