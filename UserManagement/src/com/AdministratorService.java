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

import javax.annotation.security.RolesAllowed;

@Path("/administrator")
public class AdministratorService {
	Administrator adminObj = new Administrator();

//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin" })
	@GET
	@Path("/view")
	@Produces(MediaType.TEXT_HTML)
	public String getAllAdministrators() {
		return adminObj.getAdministrators();
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin" })
	@POST
	@Path("/create-admin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertAdministrator(String adminData) {
		// Convert the input string to a JSON object
		JsonObject adminObject = new JsonParser().parse(adminData).getAsJsonObject();
		
		// Read the values from the JSON object
		String empID = adminObject.get("empID").getAsString();
		String firstName = adminObject.get("firstName").getAsString();
		String lastName = adminObject.get("lastName").getAsString();
		String userPhone = adminObject.get("userPhone").getAsString();
		String email = adminObject.get("email").getAsString();
		String password = adminObject.get("password").getAsString();
		String userRole = adminObject.get("userRole").getAsString();
		String accStatus = adminObject.get("accStatus").getAsString();
		
		String output = adminObj.createAdministrator(empID, firstName, lastName, userPhone, email, password, userRole, accStatus);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin" })
	@PUT
	@Path("/update-admin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateAdministrator(String adminData) {
		// Convert the input string to a JSON object
		JsonObject adminObject = new JsonParser().parse(adminData).getAsJsonObject();
		
		// Read the values from the JSON object
		String adminID = adminObject.get("adminID").getAsString();
		String empID = adminObject.get("empID").getAsString();
		String firstName = adminObject.get("firstName").getAsString();
		String lastName = adminObject.get("lastName").getAsString();
		String userPhone = adminObject.get("userPhone").getAsString();
		
		String output = adminObj.updateAdministrator(adminID, empID, firstName, lastName, userPhone);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin" })
	@PUT
	@Path("/update-user-admin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateAdminEmailPassword(String adminData) {
		// Convert the input string to a JSON object
		JsonObject adminObject = new JsonParser().parse(adminData).getAsJsonObject();
		
		// Read the values from the JSON object
		String userID = adminObject.get("userID").getAsString();
		String userEmail = adminObject.get("userEmail").getAsString();
		String password = adminObject.get("password").getAsString();
		
		String output = adminObj.updateAdminEmailPassword(userID, userEmail, password);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin" })
	@DELETE
	@Path("/disable-admin")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String disableAdministrator(String adminData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(adminData, "", Parser.xmlParser());

		// Read the value from the element <userID> & <accStatus>
		String adminID = doc.select("adminID").text();
		String accStatus = doc.select("accStatus").text();
		
		String output = adminObj.disableAdministrator(adminID, accStatus);
		
		return output;
		
	}
	
}
