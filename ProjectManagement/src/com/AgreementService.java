package com;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Agreement;
import model.Item;

public class AgreementService {
	
	@Path("/Items")
	public class ItemService {
		Item itemObj = new Item();

		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
		public String readItems() {
			return itemObj.readItems();
		}

		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String insertItem(@FormParam("Name") String name, @FormParam("Description") String description,
				@FormParam("Field") String field, @FormParam("Project_Report_Url") String url,@FormParam("Researcher_ID") int researcher_id) {
			String output = itemObj.insertItem( name, description,  field, url, researcher_id);
			return output;
		}

		@PUT
		@Path("/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		public String updateItem(String projectData) {
			// Convert the input string to a JSON object
			JsonObject itemObject = new JsonParser().parse(projectData).getAsJsonObject();
			// Read the values from the JSON object
			String projectID = itemObject.get("Project_ID").getAsString();
			String name = itemObject.get("Name").getAsString();
			String description = itemObject.get("Description").getAsString();
			String filed = itemObject.get("Filed").getAsString();
			String url = itemObject.get("Project_Report_Url").getAsString();
			String researcherid = itemObject.get("Researcher_ID").getAsString();
			String output = itemObj.updateItem(projectID, name, description, filed, url,researcherid);
			return output;
		}

		@DELETE
		@Path("/")
		@Consumes(MediaType.APPLICATION_XML)
		@Produces(MediaType.TEXT_PLAIN)
		public String deleteItem(String projectData) {
			// Convert the input string to an XML document
			Document doc = Jsoup.parse(projectData, "", Parser.xmlParser());

			// Read the value from the element <itemID>
			String projectID = doc.select("Project_ID").text();
			String output = itemObj.deleteItem(projectID);
			return output;
		}


}
}
