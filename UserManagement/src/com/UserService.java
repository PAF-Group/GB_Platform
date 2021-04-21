/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package com;

import model.User;

//For REST Services
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.annotation.security.RolesAllowed;

@Path("/user")
public class UserService {
	User userObj = new User();
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@RolesAllowed(value = { "Admin", "Member" })
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getAllUsers() {
		return userObj.getUsers();
		
	}
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------------
	@GET
	@Path("/authentication")
//	@QueryParam("userEmail"), @QueryParam("password")
	public Response getUserRoleForAuthentication( @Context UriInfo uriInfo ) {
		// Read user-email & password
		String userEmail = uriInfo.getQueryParameters().getFirst("userEmail");
		String password = uriInfo.getQueryParameters().getFirst("password");
		
		String output = userObj.getUserRole(userEmail, password);
		
		if(output.equals("Unregistered User!...")) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Unauthorized Access!...").build();
			
		} else if(output.equals("An error has occurred while connecting to the database.") || output.equals("An error has occurred while reading the User records.")){
			return Response.status(Response.Status.BAD_GATEWAY).entity("Bad Gateway ...").build();
			
		} else {
			return Response.status(Response.Status.OK).entity(output).build();
			
		}
		
	}

}
