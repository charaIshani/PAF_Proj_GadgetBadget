package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Research {

	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gb_research", "root", "");
	 }
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
	 }
	
	
	public String insertItem(String code, String name, String desc,String leader,String date,String campus)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for inserting."; }
	 // create a prepared statement
	 String query = " INSERT INTO paper (`id`, `name`, `description`, `leader`, `date`, `campus`) VALUES (?, ?, ?, ?, ?, ?);";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setString(1, code);
	 preparedStmt.setString(2, name);
	 preparedStmt.setString(3, desc);
	 preparedStmt.setString(4, leader);
	 preparedStmt.setString(5, date);
	 preparedStmt.setString(6, campus);
	// execute the statement
	
	 preparedStmt.execute();
	 con.close();
	 output = "Inserted successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while inserting the item.";
	 System.err.println(e.getMessage());
	 e.printStackTrace();
	 }
	 return output;
	 }
	
	
	public String readItems()
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for reading."; }
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Item Code</th><th>Research Title</th>" +
	 "<th>Research Description</th>" +
	 "<th>Member Name</th>" +
	 "<th>Published Date</th>" +
	 "<th>University</th>" +
	 "<th>Update</th><th>Remove</th></tr>";

	 String query = "select * from paper";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
	 String id = Integer.toString(rs.getInt("id"));
	 String name= rs.getString("name");
	 String des = rs.getString("description");
	 String leader = rs.getString("leader");
	 String date = rs.getString("date");
	 String campus = rs.getString("campus");
	 // Add into the html table
	 output += "<tr><td>" + id+ "</td>";
	 output += "<td>" + name + "</td>";
	 output += "<td>" + des + "</td>";
	 output += "<td>" + leader + "</td>";
	 output += "<td>" + date + "</td>";
	 output += "<td>" + campus + "</td>";
	 // buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"
	 + "<td><form method='post' action='items.jsp'>"
	 + "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
	 + "<input name='itemID' type='hidden' value='" + id
	 + "'>" + "</form></td></tr>";
	 }
	 con.close();
	 // Complete the html table
	 output += "</table>";
	 }
	 catch (Exception e)
	 {
	 output = "Error while reading the items.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	
	
	public String updateItem(String ID, String name, String des, String leader, String date, String campus)
	
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for updating."; }
	 // create a prepared statement
	 String query = "UPDATE paper SET name=?,description=?,leader=?,date=?,campus=? WHERE id=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	
	 //preparedStmt.setInt(1,Integer.parseInt(ID));
	 preparedStmt.setString(1, name);
	 preparedStmt.setString(2, des);
	 preparedStmt.setString(3, leader);
	 preparedStmt.setString(4, date);
	 preparedStmt.setString(5, campus);
	 preparedStmt.setInt(6,Integer.parseInt(ID));
	 System.out.println("no error1");
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	
	 output = "Updated successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while updating the item.";
	 System.err.println(e.getMessage());
	 e.printStackTrace();
	 }
	 return output;
	 }
	
	
	public String deleteItem(String itemID)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for deleting."; }
	 // create a prepared statement
	 String query = "delete from paper where id=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setString(1,itemID);
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Deleted successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while deleting the item.";
	 System.err.println(e.getMessage());
	 e.printStackTrace();
	 }
	 return output;
	 } 
}
