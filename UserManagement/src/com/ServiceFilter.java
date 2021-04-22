/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package com;

import java.util.*;
import java.util.Base64.Decoder;
import java.lang.reflect.Method;

import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import java.io.IOException;

import model.User;

@Provider
public class ServiceFilter implements ContainerRequestFilter {
	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	
	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Method method = resourceInfo.getResourceMethod();
		
		// If allowed for all return 
		if (method.isAnnotationPresent(PermitAll.class)) {
			return;
			
		}
		
		// Get the authorization in request header
		List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
		
		// If authHeader is null or its size is not greater than zero will sent not authorized
		if (authHeader == null || authHeader.size() <= 0) {
			Response unauthoriazedStatus = Response.status(Response.Status.UNAUTHORIZED)
					.entity("Authorization Failed!...").build();

			requestContext.abortWith(unauthoriazedStatus);
			
		}
		
		// Store the first element in the List in a string variable
		String authToken = authHeader.get(0);
		
		// Replace the Authorization Header prefix
		authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
		
		// Decode the encoded string
		Decoder decoder = Base64.getDecoder();
		
		String decodedAuthToken = "";
		
		try {
			byte[] byteString = decoder.decode(authToken);
			
			decodedAuthToken = new String(byteString, "UTF-8");
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		// Getting user-email & password
		StringTokenizer tokenizer = new StringTokenizer(decodedAuthToken, ":");
		String userEmail = tokenizer.nextToken();
		String password = tokenizer.nextToken();
		
		User userObj = new User();
		
		String currentUser = userObj.getUserRole(userEmail, password);
		
		if(currentUser.equals("Unregistered User!...")) {
			Response unauthoriazedStatus = Response.status(Response.Status.UNAUTHORIZED)
			.entity("Invalid User!...").build();

			requestContext.abortWith(unauthoriazedStatus);
			
		} else if(currentUser.equals("An error has occurred while reading the User records.") || currentUser.equals("An error has occurred while connecting to the database.")) {
			Response badGatewayStatus = Response.status(Response.Status.BAD_GATEWAY).entity("Bad Gateway ...").build();
			
			requestContext.abortWith(badGatewayStatus);
			
		} else {
			if (method.isAnnotationPresent(RolesAllowed.class)) {
				//Get the allowed user roles from Annotation
				RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);

				Set<String> user_role = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
				
			    if(user_role.contains(currentUser) == false) {
			    	Response unauthoriazedStatus = Response.status(Response.Status.UNAUTHORIZED)
							.entity("Unauthorized User!...").build();

					requestContext.abortWith(unauthoriazedStatus);
			    } 
			    
			    return;
			    
			}
			
		}
				
		return;
			
	}

}
