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

@Path("/researcher")
public class ResearcherService {
	Researcher researcherObj = new Researcher();

//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getAllResearchers() {
		return researcherObj.getResearchers();
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertResearcher(@FormParam("firstName") String firstName, @FormParam("lastName") String lastName, @FormParam("userPhone") String userPhone, 
			@FormParam("userAgreement") String userAgreement, @FormParam("email") String email, @FormParam("password") String password, @FormParam("accStatus") String accStatus) {
		String output = researcherObj.createResearcher(firstName, lastName, userPhone, userAgreement, email, password, accStatus);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateResearcher(String researcherData) {
		// Convert the input string to a JSON object
		JsonObject researcherObject = new JsonParser().parse(researcherData).getAsJsonObject();
		
		// Read the values from the JSON object
		String userID = researcherObject.get("userID").getAsString();
		String firstName = researcherObject.get("firstName").getAsString();
		String lastName = researcherObject.get("lastName").getAsString();
		String userPhone = researcherObject.get("userPhone").getAsString();
		String userAgreement = researcherObject.get("userAgreement").getAsString();
		String email = researcherObject.get("email").getAsString();
		
		String output = researcherObj.updateResearcher(userID, firstName, lastName, userPhone, userAgreement, email);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String disableResearcher(String researcherData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(researcherData, "", Parser.xmlParser());

		// Read the value from the element <userID> & <accStatus>
		String userID = doc.select("userID").text();
		String accStatus = doc.select("accStatus").text();
		
		String output = researcherObj.disableResearcher(userID, accStatus);
		
		return output;
		
	}

}
