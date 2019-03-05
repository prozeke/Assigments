
import java.util.ArrayList;
import java.util.Date;
public class Order {
	String orderDate;
	double totalCost;
	ArrayList<Item> cart;
	String customerID;
	
	/**
	 * Constructor for Order
	 * @param totalcost is the total cost of the order
	 * @param cart items that are bought on this order
	 * @param item customerID customers id who ordered*/
	public Order(double totalcost,ArrayList<Item> cart,String customerID){
		this.totalCost = totalcost;
		this.cart = cart;
		this.customerID = customerID;
		Date today = new Date(); 
		String date = today.toString();
		this.orderDate = date;
	}
	

	/**
	 * display information about all orders*/
	public void displayorder(){
		String numberOfPurchase = Integer.toString(cart.size());
		String date = this.orderDate;
		String cost = Double.toString(this.totalCost);
		String id = this.customerID;
		System.out.printf("Order date: %s Customer ID: %s Total Cost:%s Number of purchased items: %s\n", date,id,cost,numberOfPurchase);
	}
}
