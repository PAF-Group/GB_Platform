package com;
/* 
 * @author W.G. YASIRU RANDIKA 
 * IT19131184
 * 
 * */

import model.Orders;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


//For JSON
import com.google.gson.*;

@Path("/Orders")
public class OrderService {
	Orders orderModel = new Orders();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String getItems() {
		System.out.println("working");
		//return Response.status(200).entity(orderModel.getAllOrders()).build();
		return orderModel.getAllOrders();
	}
}