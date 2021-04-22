/* 
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package com;

import model.Researcher;

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

@Path("/researcher")
public class ResearcherService {
	Researcher researcherObj = new Researcher();

//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin", "Member" })
	@GET
	@Path("/view")
	@Produces(MediaType.TEXT_HTML)
	public String getAllResearchers() {
		return researcherObj.getResearchers();
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@POST
	@Path("/create-researcher")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertResearcher(String researcherData) {
		// Convert the input string to a JSON object
		JsonObject researcherObject = new JsonParser().parse(researcherData).getAsJsonObject();
		
		// Read the values from the JSON object
		String firstName = researcherObject.get("firstName").getAsString();
		String lastName = researcherObject.get("lastName").getAsString();
		String userPhone = researcherObject.get("userPhone").getAsString();
		String userAgreement = researcherObject.get("userAgreement").getAsString();
		String email = researcherObject.get("email").getAsString();
		String password = researcherObject.get("password").getAsString();
		String userRole = researcherObject.get("userRole").getAsString();
		String accStatus = researcherObject.get("accStatus").getAsString();
		
		String output = researcherObj.createResearcher(firstName, lastName, userPhone, userAgreement, email, password, userRole, accStatus);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin", "Researcher" })
	@PUT
	@Path("/update-researcher")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateResearcher(String researcherData) {
		// Convert the input string to a JSON object
		JsonObject researcherObject = new JsonParser().parse(researcherData).getAsJsonObject();
		
		// Read the values from the JSON object
		String researcherID = researcherObject.get("researcherID").getAsString();
		String firstName = researcherObject.get("firstName").getAsString();
		String lastName = researcherObject.get("lastName").getAsString();
		String userPhone = researcherObject.get("userPhone").getAsString();
		String userAgreement = researcherObject.get("userAgreement").getAsString();
		
		String output = researcherObj.updateResearcher(researcherID, firstName, lastName, userPhone, userAgreement);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Researcher" })
	@PUT
	@Path("/update-user-researcher")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateResearcherEmailPassword(String researcherData) {
		// Convert the input string to a JSON object
		JsonObject researcherObject = new JsonParser().parse(researcherData).getAsJsonObject();
		
		// Read the values from the JSON object
		String userID = researcherObject.get("userID").getAsString();
		String userEmail = researcherObject.get("userEmail").getAsString();
		String password = researcherObject.get("password").getAsString();
		
		String output = researcherObj.updateResearcherEmailPassword(userID, userEmail, password);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin" })
	@DELETE
	@Path("/disable-researcher")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String disableResearcher(String researcherData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(researcherData, "", Parser.xmlParser());

		// Read the value from the element <userID> & <accStatus>
		String researcherID = doc.select("researcherID").text();
		String accStatus = doc.select("accStatus").text();
		
		String output = researcherObj.disableResearcher(researcherID, accStatus);
		
		return output;
		
	}

}
