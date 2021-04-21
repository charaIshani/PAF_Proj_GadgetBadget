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

import model.Payment;

@Path("/payment")

public class PaymentService {

	Payment payobj = new Payment();	
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayments()
	 {
		 return payobj.readPayments();
	 }
	 
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertItem(@FormParam("orderid") int orderid,
	 @FormParam("amount") float amount)
	 
	{
	 String output = payobj.insertPayment(orderid, amount);
	return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(String payData)
	{
	//Convert the input string to a JSON object
	 JsonObject itemObject = new JsonParser().parse(payData).getAsJsonObject();
	//Read the values from the JSON object
	 String payID = itemObject.get("payID").getAsString();
	 String orderID = itemObject.get("orderID").getAsString();
	 String total = itemObject.get("totalAmount").getAsString();
	 String output = payobj.updatePayment(payID, total, orderID);
	return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String payData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(payData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String payID = doc.select("payID").text();
	 String output = payobj.deletePayment(payID);
	return output;
	}
	
	
}
