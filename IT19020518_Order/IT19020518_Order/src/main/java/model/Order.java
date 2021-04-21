package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Order {
	
	// model class definition
	
	
	
	
	
	// db method definition
	
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			 
			con = DriverManager.getConnection("jdbc:mysql://localhost/gb_order?useTimezone=true&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertOrder(String buyerID, String productID, int qty) {
		String output = "";
		int quantity_product = 0;
		boolean validate = false;
		
	
			
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for inserting.";
				}
				
				String query2 = " select qty from product where pID = ?";
				PreparedStatement preparedStmt2 = con.prepareStatement(query2);
				preparedStmt2.setString(1, productID);
				ResultSet rs = preparedStmt2.executeQuery();
				
				while(rs.next()) {
					 quantity_product = rs.getInt("qty");
					
				}
				
				
				if(qty > quantity_product) {
					validate = false;
					output = "product has only " + quantity_product + " quantity available" ;
				}
				else {
					validate = true;
				}
				
				
				if(validate) {
				// create a prepared statement
				String query = " insert into orders(`buyerID`,`productID`,`qty`)"+ "values (?, ?, ?)";
				PreparedStatement preparedStmt = con.prepareStatement(query);
				// binding values
				
				preparedStmt.setString(1, buyerID);
				preparedStmt.setString(2, productID);
				preparedStmt.setInt(3, qty);			
				// execute the statement
				preparedStmt.execute();
				con.close();
				output = "Inserted successfully";
				}
			} catch (Exception e) {
				output = "Error while inserting the Orders.";
				System.err.println(e.getMessage());
			}
			
		
		return output;
		}
			
		
		
		
	
	
	public String readOrders() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Order ID</th><th>Buyer ID</th>" + "<th>Product ID</th>"
					+ "<th>Quantity</th>" +"<th>Order Date</th>"+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from orders";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String orderID = Integer.toString(rs.getInt("orderID"));
				String buyerID = rs.getString("buyerID");
				String productID = rs.getString("productID");
				String qty = Double.toString(rs.getDouble("qty"));
				String orderDate = rs.getString("orderDate");
				

				// Add into the html table
				output += "<tr><td>" + orderID + "</td>";
				output += "<td>" + buyerID + "</td>";
				output += "<td>" + productID + "</td>";
				output += "<td>" + qty + "</td>";
				output += "<td>" + orderDate + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
						+ "<input name='orderID' type='hidden' value='" + orderID + "'>" + "</form></td></tr>";
			}
			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the orders.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String readOrdersbuyer(String ID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Order ID</th><th>Buyer ID</th>" + "<th>Product ID</th>"
					+ "<th>Quantity</th>" +"<th>Order Date</th>"+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from orders where buyerID =?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, ID);
			ResultSet rs = preparedStmt.executeQuery();

			// iterate through the rows in the result set
			while (rs.next()) {
				String orderID = Integer.toString(rs.getInt("orderID"));
				String buyerID = rs.getString("buyerID");
				String productID = rs.getString("productID");
				String qty = Double.toString(rs.getDouble("qty"));
				String orderDate = rs.getString("orderDate");
				

				// Add into the html table
				output += "<tr><td>" + orderID + "</td>";
				output += "<td>" + buyerID + "</td>";
				output += "<td>" + productID + "</td>";
				output += "<td>" + qty + "</td>";
				output += "<td>" + orderDate + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
						+ "<input name='orderID' type='hidden' value='" + orderID + "'>" + "</form></td></tr>";
			}
			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the orders.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updateOrderQuantity(String ID, int qty) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE orders SET qty=? WHERE orderID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, qty);
			preparedStmt.setString(2, ID);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
		} catch (Exception e) {
			output = "Error while updating the order.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	public String deleteOrders(String ID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from orders where orderID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the order.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	


}

