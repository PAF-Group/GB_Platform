/* 
 * @author  IT19160580 Maddumage T.A.R
 * 
 * */
package com;

import model.Project;

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

@Path("/Projects")
public class ProjectService {
	Project itemObj = new Project();
//------------------------------------------------------------------------------------------------------------------------
	@GET
	@Path("/view")
	@RolesAllowed({"Funder", "Researcher"})
	@Produces(MediaType.TEXT_HTML)
	public String readprojects() {
		return itemObj.readProjects();
	}
//------------------------------------------------------------------------------------------------------------------------
	@POST
	@Path("/insert")
	@RolesAllowed({"Researcher"})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertproject(@FormParam("Name") String name, @FormParam("Description") String description,
			@FormParam("Field") String field, @FormParam("Project_Report_Url") String url,@FormParam("Researcher_ID") int researcher_id) {
		String output = itemObj.insertProject( name, description,  field, url, researcher_id);
		return output;
	}
//------------------------------------------------------------------------------------------------------------------------
	@PUT
	@Path("/update")
	@RolesAllowed({"Researcher"})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateprojects(String projectData) {
		// Convert the input string to a JSON object
		JsonObject itemObject = new JsonParser().parse(projectData).getAsJsonObject();
		// Read the values from the JSON object
		String projectID = itemObject.get("Project_ID").getAsString();
		String name = itemObject.get("Name").getAsString();
		String description = itemObject.get("Description").getAsString();
		String filed = itemObject.get("Filed").getAsString();
		String url = itemObject.get("Project_Report_Url").getAsString();
		String researcherid = itemObject.get("Researcher_ID").getAsString();
		String output = itemObj.updateProject(projectID, name, description, filed, url,researcherid);
		return output;
	}
//------------------------------------------------------------------------------------------------------------------------
	@DELETE
	@Path("/delete")
	@RolesAllowed({"Researcher"})
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteproject(String projectData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(projectData, "", Parser.xmlParser());

		// Read the value from the element <itemID>
		String projectID = doc.select("Project_ID").text();
		String output = itemObj.deleteProject(projectID);
		return output;
	}

}
