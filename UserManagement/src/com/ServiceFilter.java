/*
 * @author Vishwa Gunathilake J.D.B. - IT19110158
 * 
 * */

package com;

import java.util.*;
import java.util.Base64.Decoder;
import java.lang.reflect.Method;

import javax.ws.rs.client.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;

import org.glassfish.jersey.client.ClientConfig;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import java.io.IOException;

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
			
			decodedAuthToken = new String(byteString);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		// Getting user-email & password
		StringTokenizer tokenizer = new StringTokenizer(decodedAuthToken, ":");
		String userEmail = tokenizer.nextToken();
		String password = tokenizer.nextToken();
		
		ClientConfig clientC = new ClientConfig();

		Client client = ClientBuilder.newClient(clientC);
		
		String currentUser = "";

		try {
			Response response = client.target("http://localhost:8080/user-management-service/user/authentication")
				      .queryParam("userEmail", userEmail)
				      .queryParam("password", password).request().get();
			
			currentUser = response.readEntity(String.class);
			
			if(response.getStatus() != 200) {
		    	Response unauthoriazedStatus = Response.status(Response.Status.UNAUTHORIZED)
						.entity(currentUser).build();
	
				requestContext.abortWith(unauthoriazedStatus);
				
		    } 
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			
		}
		
		if (method.isAnnotationPresent(RolesAllowed.class)) {
			//Get the allowed user roles from Annotation
			RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);

			Set<String> user_role = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));
			
		    if(!user_role.contains(currentUser)) {
		    	Response unauthoriazedStatus = Response.status(Response.Status.UNAUTHORIZED)
						.entity("Unauthorized User!...").build();

				requestContext.abortWith(unauthoriazedStatus);
		    }  
		    return;
		}
		
		return;
			
	}

}
