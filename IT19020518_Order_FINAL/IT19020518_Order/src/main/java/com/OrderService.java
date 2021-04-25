package com;
//IT19020518

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Order;

@Path("/Order")
public class OrderService {

	Order ordProduct = new Order();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String read_Orders()
	 {
		return ordProduct.read_Orders(); 
	 } 
	
	
	@GET
	@Path("/{buyerID}")
	@Produces(MediaType.TEXT_PLAIN)
	public String read_Oredrs_Buyer(@PathParam("buyerID") String buyerID)
	 {
		return ordProduct.read_Orders_buyer(buyerID); 
	 }
	
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insert_Order(@FormParam("buyerID") String buyerID,
	 @FormParam("productID") String productID,
	 @FormParam("price") int price,
	@FormParam("orderDate") String orderDate)
	{
	 String output = ordProduct.insert_Order(buyerID, productID, price,orderDate);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String update_Item(String productData)
	{
	//Convert the input string to a JSON object
	 JsonObject itemProduct = new JsonParser().parse(productData).getAsJsonObject();
	//Read the values from the JSON object
	 String orderID = itemProduct.get("orderID").getAsString();
	 int price = Integer.parseInt(itemProduct.get("price").getAsString());
	
	 String output = ordProduct.update_Order_Price(orderID, price);
	return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String delete_Item(String productData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(productData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String orderID = doc.select("orderID").text();
	 String output = ordProduct.delete_Orders(orderID);
	return output;
	}
	
}
