package com;


//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document; 

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


import org.jsoup.Jsoup;


import model.Research;
@Path("/Research") 
public class ResearchService {

	Research itemObj = new Research();
//	@GET
//	@Path("/")
//	@Produces(MediaType.TEXT_HTML)
//	public String readItems()
//	 {
//	 return "Hello";
//	 } 
//	
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems()
	 {
	 return itemObj.readItems();
	 }
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertItem(@FormParam("id") String id,
	 @FormParam("name") String name,
	 @FormParam("description") String description,
	 @FormParam("leader") String leader, 
	 @FormParam("date") String date,
	 @FormParam("campus") String campus)
	{
	 String output = itemObj.insertItem(id, name, description, leader,date,campus);
	return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String itemData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());

	//Read the value from the element <itemID>
	 String itemID = doc.select("itemID").text();
	 String output = itemObj.deleteItem(itemID);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String itemData)
	{
	//Convert the input string to a JSON object
	 JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
	//Read the values from the JSON object
	 String id = itemObject.get("id").getAsString();
	 String name = itemObject.get("name").getAsString();
	 String description = itemObject.get("description").getAsString();
	 String leader = itemObject.get("leader").getAsString();
	 String date = itemObject.get("date").getAsString();
	 String campus = itemObject.get("campus").getAsString();
	 String output = itemObj.updateItem(id, name, description, leader, date,campus);
	return output;
	}
	
	
	
	}
	

