
/*Important Note: Each instance constructors input will be taken from the text 
 *text file will be splitted with (\t) and the resulting list will be input to constructors  */

import java.util.ArrayList;

public class Item {
	private static int itemNumber = 0;
	private int itemID;
	private double cost;
	
	public Item() {

	}

	public Item(double cost) {
		this.itemNumber++;
		this.itemID = this.itemNumber;
		this.cost = cost;

	}

	/**
	 * @return cost*/
	public double getcost() {
		return this.cost;
	}
	/**
	 * @return id*/
	public int getitemID() {
		return this.itemID;
	}

	/**
	 * prints item information*/
	public void displayItem() {
		System.out.printf("Item ID: %d\nPrice: %.1f $\n", this.itemID, this.cost);
	}

	
	public int getstock() {
		// just for not getting compile errors in func showlowstock which is in
		// employee
		return 0;
	}

	
	public void setstock() {

	}

	public int getnumber() {
		return this.itemNumber;
	}

	public void setdisrate(double x) {

	}

	public double getdisrate() {
		return 0;

	}
}
