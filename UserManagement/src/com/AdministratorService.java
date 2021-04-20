/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package com;

import model.Administrator;

//For REST Services
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.Jsoup;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/administrator")
public class AdministratorService {
	Administrator adminObj = new Administrator();

//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getAllAdministrators() {
		return adminObj.getAdministrators();
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertAdministrator(@FormParam("empID") String empID, @FormParam("firstName") String firstName, @FormParam("lastName") String lastName, 
			@FormParam("userPhone") String userPhone, @FormParam("email") String email, @FormParam("password") String password, @FormParam("accStatus") String accStatus) {
		String output = adminObj.createAdministrator(empID, firstName, lastName, userPhone, email, password, accStatus);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateAdministrator(String adminData) {
		// Convert the input string to a JSON object
		JsonObject adminObject = new JsonParser().parse(adminData).getAsJsonObject();
		
		// Read the values from the JSON object
		String userID = adminObject.get("userID").getAsString();
		String empID = adminObject.get("empID").getAsString();
		String firstName = adminObject.get("firstName").getAsString();
		String lastName = adminObject.get("lastName").getAsString();
		String userPhone = adminObject.get("userPhone").getAsString();
		String email = adminObject.get("email").getAsString();
		
		String output = adminObj.updateAdministrator(userID, empID, firstName, lastName, userPhone, email);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String disableBuyer(String adminData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(adminData, "", Parser.xmlParser());

		// Read the value from the element <userID> & <accStatus>
		String userID = doc.select("userID").text();
		String accStatus = doc.select("accStatus").text();
		
		String output = adminObj.disableAdministrator(userID, accStatus);
		
		return output;
		
	}
	
}
