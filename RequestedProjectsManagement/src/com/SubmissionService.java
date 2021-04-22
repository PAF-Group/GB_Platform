package com;


import model.Submit;

import javax.annotation.security.PermitAll;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Submissions")

public class SubmissionService {
	Submit itemObj = new Submit();

	@GET                             //add a get method to get data 
	@Path("/")
	@PermitAll
	@Produces(MediaType.TEXT_HTML)
	public String readSubmissions() {
		return itemObj.readSubmissions();
	}

	@POST                  //add data insert annotation
	@Path("/")
	@PermitAll
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertSubmission(@FormParam("name") String name, @FormParam("descriptiom") String descriptiom,
			@FormParam("report_url") String report_url, @FormParam("researcher_id") String researcher_id, @FormParam("pRequest_id") String pRequest_id) {
		String output = itemObj.insertSubmission(name, descriptiom, report_url, researcher_id,pRequest_id);
		return output;
	}

	@PUT                           //add update annotation
	@Path("/")
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateSubmission(String itemData) {
		// Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		// Read the values from the JSON object
		
		String project_id= itemObject.get("project_id").getAsString();
		String name = itemObject.get("name").getAsString();
		String descriptiom = itemObject.get("descriptiom").getAsString();
		String report_url = itemObject.get("report_url").getAsString();
		String researcher_id = itemObject.get("researcher_id").getAsString();
		String output = itemObj.updateSubmission(project_id,name, descriptiom, report_url, researcher_id);
		return output;
		
	}

	@DELETE                     //delete annotation
	@Path("/")
	@PermitAll
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteSubmission(String itemData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

		// Read the value from the element <pRequest_id>
		String project_id = doc.select("project_id").text();
		String output = itemObj.deleteSubmission(project_id);
		System.out.println(project_id);
		return output;
	}

}
