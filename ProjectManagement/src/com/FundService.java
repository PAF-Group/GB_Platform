package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Funds;

@Path("/Funds")
public class FundService {

		Funds fundObj = new Funds();

		@GET
		@Path("/")
		@Produces(MediaType.TEXT_HTML)
		public String readItems() {
			return fundObj.readfund();
		}

		@POST
		@Path("/")
		@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
		@Produces(MediaType.TEXT_PLAIN)
		public String insertfund(@FormParam("Date") String date, @FormParam("Amount") float amount,
				 @FormParam("Bank_Slip") String slip) {
			String output = fundObj.insertfund( date, amount, slip);
			return output;
		}
}
