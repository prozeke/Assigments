import java.util.ArrayList;

public class Technican extends Employee {
	private boolean isSenior;
	 /**Constructor for Technican
	  * @param inputList is a list. Its order is {name,email,birthday,salary,isSenior}. */
	Technican(String[] inputList) {
		super(inputList[1], inputList[2], inputList[3], inputList[4]);
		switch (inputList[5]) {
		case ("1"):
			this.isSenior = true;
			break;
		case ("0"):
			this.isSenior = false;
			break;
		}
	}
	/**	display item types for given types
	 * @param inputList inputs of items to display
	 * @param items all items	*/
	public void dispItemsOf(String[] inputList, ArrayList<Item> items) {

		for (String type : inputList) {

			switch (type) {

			case ("DESKTOP"): {
				for (Item item : items) {
					if (item instanceof Desktop) {
						System.out.println("-----------------------");
						item.displayItem();

					}
				}
				break;
			}

			case ("LAPTOP"): {
				for (Item item : items) {
					if (item instanceof Laptop) {
						System.out.println("-----------------------");
						item.displayItem();

					}
				}
				break;
			}
			case ("TABLET"): {
				for (Item item : items) {
					if (item instanceof Tablet) {
						System.out.println("-----------------------");
						item.displayItem();

					}
				}
				break;
			}
			case ("TV"): {
				for (Item item : items) {
					if (item instanceof Tv) {
						System.out.println("-----------------------");
						item.displayItem();

					}
				}
				break;
			}
			case ("SMARTPHONE"): {
				for (Item item : items) {
					if (item instanceof SmartPhone) {
						System.out.println("-----------------------");
						item.displayItem();

					}
				}
				break;
			}
			case ("BOOK"): {
				for (Item item : items) {
					if (item instanceof Book) {
						System.out.println("-----------------------");
						item.displayItem();

					}
				}
				break;
			}
			case ("CDDVD"): {
				for (Item item : items) {
					if (item instanceof CdDvd) {
						System.out.println("-----------------------");
						item.displayItem();

					}
				}
				break;
			}
			case ("HAIRCARE"): {
				for (Item item : items) {
					if (item instanceof HairCare) {
						System.out.println("-----------------------");
						item.displayItem();

					}
				}
				break;
			}
			case ("SKINCARE"): {
				for (Item item : items) {
					if (item instanceof SkinCare) {
						System.out.println("-----------------------");

						item.displayItem();
					}
				}
				break;
			}
			case ("PERFUME"): {
				for (Item item : items) {
					if (item instanceof Perfume) {
						System.out.println("-----------------------");

						item.displayItem();
					}
				}
				break;
			}
			}

		}

	}

	/** @return whether senior or not	*/
	public boolean isSenior() {
		return this.isSenior;
	}

	/**	shows orders that is ordered so far
	 * @param orders all orders list 	*/
	public void showorders(ArrayList<Order> orders) {
		System.out.println("Order History:");
		for (Order o : orders) {
			o.displayorder();
		}
	}

}
