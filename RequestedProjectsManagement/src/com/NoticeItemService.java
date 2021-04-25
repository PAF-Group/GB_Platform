package com;

import model.NoticeItem;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Notices")
public class NoticeItemService {
	NoticeItem itemObj = new NoticeItem();

	@GET                             //add a get method to get data 
	@Path("/view")
	@RolesAllowed({"Funder", "Researcher"})
	@Produces(MediaType.TEXT_HTML)
	public String readItems() {
		return itemObj.readItems();
	}

	@POST                  //add data insert annotation
	@Path("/insert")
	@RolesAllowed({"Funder"})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertItem(@FormParam("name") String name, @FormParam("feild") String feild,
			@FormParam("description") String description, @FormParam("submission_link") String submission_link, @FormParam("fundingbody_id") String fundingbody_id) {
		String output = itemObj.insertItem(name, feild, description, submission_link,fundingbody_id);
		return output;
	}

	@PUT                           //add update annotation
	@Path("/update")
	@RolesAllowed({"Funder"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String itemData) {
		// Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
		// Read the values from the JSON object
		
		String pRequest_id= itemObject.get("pRequest_id").getAsString();
		String name = itemObject.get("name").getAsString();
		String feild = itemObject.get("feild").getAsString();
		String description = itemObject.get("description").getAsString();
		String submission_link = itemObject.get("submission_link").getAsString();
		String output = itemObj.updateItem(pRequest_id,name, feild, description, submission_link);
		return output;
	}

	@DELETE                     //delete annotation
	@Path("/delete")
	@RolesAllowed({"Funder"})
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String itemData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

		// Read the value from the element <pRequest_id>
		String pRequest_id = doc.select("pRequest_id").text();
		String output = itemObj.deleteItem(pRequest_id);
		System.out.println(pRequest_id);
		return output;
	}

}
