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

@Path("/funder")
public class FunderService {
	Funder funderObj = new Funder();

//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getAllFunders() {
		return funderObj.getFunders();
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertFunder(@FormParam("name") String name, @FormParam("userPhone") String userPhone, @FormParam("userType") String userType, @FormParam("address") String address,
			@FormParam("userAgreement") String userAgreement, @FormParam("email") String email, @FormParam("password") String password, @FormParam("accStatus") String accStatus) {
		String output = funderObj.createFunder(name, userPhone, userType, address, userAgreement, email, password, accStatus);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateFunder(String funderData) {
		// Convert the input string to a JSON object
		JsonObject funderObject = new JsonParser().parse(funderData).getAsJsonObject();
		
		// Read the values from the JSON object
		String userID = funderObject.get("userID").getAsString();
		String name = funderObject.get("name").getAsString();
		String userPhone = funderObject.get("userPhone").getAsString();
		String userType = funderObject.get("userType").getAsString();
		String address = funderObject.get("address").getAsString();
		String userAgreement = funderObject.get("userAgreement").getAsString();
		String email = funderObject.get("email").getAsString();
		
		String output = funderObj.updateFunder(userID, name, userPhone, userType, address, userAgreement, email);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String disableFunder(String funderData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(funderData, "", Parser.xmlParser());

		// Read the value from the element <userID> & <accStatus>
		String userID = doc.select("userID").text();
		String accStatus = doc.select("accStatus").text();
		
		String output = funderObj.disableFunder(userID, accStatus);
		
		return output;
		
	}
	
}
