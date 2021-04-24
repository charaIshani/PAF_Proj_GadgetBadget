package model;
//IT19020518

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Order {
	
	//databse method definition
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			 
			con = DriverManager.getConnection("jdbc:mysql://localhost/gb_order?useTimezone=true&serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String insert_Order(String buyerID, String productID, int price) {
		String output1 = "";
//		String output2 = "";
		int price_research = 0;
		boolean validate = false;
		
			
			try {
				Connection con = connect();
				if (con == null) {
					return "Error while connecting to the database for inserting.";
				}
				
				if(price > price_research) {
					validate = false;
//					output1 = "Price is more than " + price_research + " value " ;
					output1 = "Inserted successfully";
				}
				else {
					validate = true;
				}		
				
				if(validate) {
				//Entered Data 
				String query = " insert into orders(`buyerID`,`productID`,`price`)"+ "values (?, ?, ?)";
				
				PreparedStatement preparedStmt = con.prepareStatement(query);
				preparedStmt.setString(1, buyerID);
				preparedStmt.setString(2, productID);
				preparedStmt.setInt(3, price);			
				// execute statement
				preparedStmt.execute();
				con.close();
				
				output1 = "Inserted successfully";
				}
			} catch (Exception e) {
				output1 = "Error while inserting the Orders.";
				System.err.println(e.getMessage());
			}
			
		
		return output1 ; 
		}
			
		
	
	public String read_Orders() {
		String output3 = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			//Create Table Header
			output3 = "<table border='1'><tr><th>Order ID</th><th>Buyer ID</th>" + "<th>Product ID</th>"
					+ "<th>Price</th>" +"<th>Ordered Date</th>"+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from orders";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String orderID = Integer.toString(rs.getInt("orderID"));
				String buyerID = rs.getString("buyerID");
				String productID = rs.getString("productID");
				String price = Double.toString(rs.getDouble("price"));
				String orderDate = rs.getString("orderDate");
				

				// Add into the html table
				output3 += "<tr><td>" + orderID + "</td>";
				output3 += "<td>" + buyerID + "</td>";
				output3 += "<td>" + productID + "</td>";
				output3 += "<td>" + price + "</td>";
				output3 += "<td>" + orderDate + "</td>";

				// buttons
				output3 += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
						+ "<input name='orderID' type='hidden' value='" + orderID + "'>" + "</form></td></tr>";
			}
			con.close();

			// Display HTML table
			output3 += "</table>";
		} catch (Exception e) {
			output3 = "Error while reading the orders.";
			System.err.println(e.getMessage());
		}
		return output3;
	}
	
	//Data Retrieve Function
	public String read_Orders_buyer(String ID) {
		String output4 = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output4 = "<table border='1'><tr><th>Order ID</th><th>Buyer ID</th>" + "<th>Product ID</th>"
					+ "<th>Price</th>" +"<th>Ordered Date</th>"+ "<th>Update</th><th>Remove</th></tr>";

			String query = "select * from orders where buyerID =?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, ID);
			ResultSet rs = preparedStmt.executeQuery();

			// iterate through the rows in the result set
			while (rs.next()) {
				String orderID = Integer.toString(rs.getInt("orderID"));
				String buyerID = rs.getString("buyerID");
				String productID = rs.getString("productID");
				String price = Double.toString(rs.getDouble("price"));
				String orderDate = rs.getString("orderDate");
				

				//Shhow data from database
				output4 += "<tr><td>" + orderID + "</td>";
				output4 += "<td>" + buyerID + "</td>";
				output4 += "<td>" + productID + "</td>";
				output4 += "<td>" + price + "</td>";
				output4 += "<td>" + orderDate + "</td>";

				//buttons
				output4 += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
						+ "<td><form method='post' action='items.jsp'>"
						+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
						+ "<input name='orderID' type='hidden' value='" + orderID + "'>" + "</form></td></tr>";
			}
			con.close();

			// Complete the html table
			output4 += "</table>";
		} catch (Exception e) {
			output4 = "Error while reading the orders.";
			System.err.println(e.getMessage());
		}
		return output4;
	}
	
	//Update function
	public String update_Order_Price(String ID, int price) {
		String output5 = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String upd = "UPDATE orders SET price=? WHERE orderID=?";
			PreparedStatement preparedStmt = con.prepareStatement(upd);
			// binding values
			preparedStmt.setInt(1, price);
			preparedStmt.setString(2, ID);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output5 = "Price : Updated successfully";
		} catch (Exception e) {
			output5 = "Error while updating the order.";
			System.err.println(e.getMessage());
		}
		return output5;
	}
	
	//Delete Function
	public String delete_Orders(String ID) {
		String output6 = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			//query for delete 
			String del = "delete from orders where orderID=?";
			PreparedStatement preparedStmt = con.prepareStatement(del);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output6 = "Deleted successfully";
		} catch (Exception e) {
			output6 = "Error while deleting the order.";
			System.err.println(e.getMessage());
		}
		return output6;
	}

}

