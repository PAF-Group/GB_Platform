/* 
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package com;

import model.Funder;

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

@Path("/funder")
public class FunderService {
	Funder funderObj = new Funder();

//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin", "Member" })
	@GET
	@Path("/view")
	@Produces(MediaType.TEXT_HTML)
	public String getAllFunders() {
		return funderObj.getFunders();
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@POST
	@Path("/create-funder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertFunder(String funderData) {
		// Convert the input string to a JSON object
		JsonObject funderObject = new JsonParser().parse(funderData).getAsJsonObject();
		
		// Read the values from the JSON object
		String name = funderObject.get("name").getAsString();
		String userPhone = funderObject.get("userPhone").getAsString();
		String userType = funderObject.get("userType").getAsString();
		String address = funderObject.get("address").getAsString();
		String userAgreement = funderObject.get("userAgreement").getAsString();
		String email = funderObject.get("email").getAsString();
		String password = funderObject.get("password").getAsString();
		String userRole = funderObject.get("userRole").getAsString();
		String accStatus = funderObject.get("accStatus").getAsString();
		
		String output = funderObj.createFunder(name, userPhone, userType, address, userAgreement, email, password, userRole, accStatus);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin", "Funder" })
	@PUT
	@Path("/update-funder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateFunder(String funderData) {
		// Convert the input string to a JSON object
		JsonObject funderObject = new JsonParser().parse(funderData).getAsJsonObject();
		
		// Read the values from the JSON object
		String funderID = funderObject.get("funderID").getAsString();
		String name = funderObject.get("name").getAsString();
		String userPhone = funderObject.get("userPhone").getAsString();
		String userType = funderObject.get("userType").getAsString();
		String address = funderObject.get("address").getAsString();
		String userAgreement = funderObject.get("userAgreement").getAsString();
		
		String output = funderObj.updateFunder(funderID, name, userPhone, userType, address, userAgreement);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Funder" })
	@PUT
	@Path("/update-user-funder")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateFunderEmailPassword(String funderData) {
		// Convert the input string to a JSON object
		JsonObject funderObject = new JsonParser().parse(funderData).getAsJsonObject();
		
		// Read the values from the JSON object
		String userID = funderObject.get("userID").getAsString();
		String userEmail = funderObject.get("userEmail").getAsString();
		String password = funderObject.get("password").getAsString();
		
		String output = funderObj.updateFunderEmailPassword(userID, userEmail, password);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin" })
	@DELETE
	@Path("/disable-funder")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String disableFunder(String funderData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(funderData, "", Parser.xmlParser());

		// Read the value from the element <userID> & <accStatus>
		String funderID = doc.select("funderID").text();
		String accStatus = doc.select("accStatus").text();
		
		String output = funderObj.disableFunder(funderID, accStatus);
		
		return output;
		
	}
	
}
