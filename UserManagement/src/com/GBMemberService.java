/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package com;

import model.GBMember;

//For REST Services
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

//For JSON
import com.google.gson.*;

//For XML
import org.jsoup.Jsoup;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/gb-member")
public class GBMemberService {
	GBMember memberObj = new GBMember();

//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getAllGBMembers() {
		return memberObj.getMembers();
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertGBMember(@FormParam("empID") String empID, @FormParam("firstName") String firstName, @FormParam("lastName") String lastName, 
			@FormParam("memberType") String memberType, @FormParam("userPhone") String userPhone, @FormParam("email") String email, @FormParam("password") String password, @FormParam("accStatus") String accStatus) {
		String output = memberObj.createMember(empID, firstName, lastName, memberType, userPhone, email, password, accStatus);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateGBMember(String memberData) {
		// Convert the input string to a JSON object
		JsonObject memberObject = new JsonParser().parse(memberData).getAsJsonObject();
		
		// Read the values from the JSON object
		String userID = memberObject.get("userID").getAsString();
		String empID = memberObject.get("empID").getAsString();
		String firstName = memberObject.get("firstName").getAsString();
		String lastName = memberObject.get("lastName").getAsString();
		String memberType = memberObject.get("memberType").getAsString();
		String userPhone = memberObject.get("userPhone").getAsString();
		String email = memberObject.get("email").getAsString();
		
		String output = memberObj.updateMember(userID, empID, firstName, lastName, memberType, userPhone, email);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String disableGBMember(String memberData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(memberData, "", Parser.xmlParser());

		// Read the value from the element <userID> & <accStatus>
		String userID = doc.select("userID").text();
		String accStatus = doc.select("accStatus").text();
		
		String output = memberObj.disableMember(userID, accStatus);
		
		return output;
		
	}

}
