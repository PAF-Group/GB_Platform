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

import javax.annotation.security.RolesAllowed;

@Path("/gb-member")
public class GBMemberService {
	GBMember memberObj = new GBMember();

//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin", "Member" })
	@GET
	@Path("/view")
	@Produces(MediaType.TEXT_HTML)
	public String getAllGBMembers() {
		return memberObj.getMembers();
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin" })
	@POST
	@Path("/create-member")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertGBMember(String memberData) {
		// Convert the input string to a JSON object
		JsonObject memberObject = new JsonParser().parse(memberData).getAsJsonObject();
		
		// Read the values from the JSON object
		String empID = memberObject.get("empID").getAsString();
		String firstName = memberObject.get("firstName").getAsString();
		String lastName = memberObject.get("lastName").getAsString();
		String memberType = memberObject.get("memberType").getAsString();
		String userPhone = memberObject.get("userPhone").getAsString();
		String email = memberObject.get("email").getAsString();
		String password = memberObject.get("password").getAsString();
		String userRole = memberObject.get("userRole").getAsString();
		String accStatus = memberObject.get("accStatus").getAsString();
		
		String output = memberObj.createMember(empID, firstName, lastName, memberType, userPhone, email, password, userRole, accStatus);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin", "Member" })
	@PUT
	@Path("/update-member")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateGBMember(String memberData) {
		// Convert the input string to a JSON object
		JsonObject memberObject = new JsonParser().parse(memberData).getAsJsonObject();
		
		// Read the values from the JSON object
		String memberID = memberObject.get("memberID").getAsString();
		String empID = memberObject.get("empID").getAsString();
		String firstName = memberObject.get("firstName").getAsString();
		String lastName = memberObject.get("lastName").getAsString();
		String memberType = memberObject.get("memberType").getAsString();
		String userPhone = memberObject.get("userPhone").getAsString();
		
		String output = memberObj.updateMember(memberID, empID, firstName, lastName, memberType, userPhone);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Member" })
	@PUT
	@Path("/update-user-member")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateMemberEmailPassword(String memberData) {
		// Convert the input string to a JSON object
		JsonObject memberObject = new JsonParser().parse(memberData).getAsJsonObject();
		
		// Read the values from the JSON object
		String userID = memberObject.get("userID").getAsString();
		String userEmail = memberObject.get("userEmail").getAsString();
		String password = memberObject.get("password").getAsString();
		
		String output = memberObj.updateMemberEmailPassword(userID, userEmail, password);
		
		return output;
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin" })
	@DELETE
	@Path("/disable-member")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String disableGBMember(String memberData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(memberData, "", Parser.xmlParser());

		// Read the value from the element <userID> & <accStatus>
		String adminID = doc.select("userID").text();
		String accStatus = doc.select("accStatus").text();
		
		String output = memberObj.disableMember(adminID, accStatus);
		
		return output;
		
	}

}
