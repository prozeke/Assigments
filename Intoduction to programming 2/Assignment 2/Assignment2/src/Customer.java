import java.util.ArrayList;

public class Customer extends Person {
	private static int customerNumber = 0;
	private int customerID;
	private String password;
	private double balance;
	private String stat;
	private ArrayList<Item> cart = new ArrayList<Item>();
	private double moneySpend = 0;
	public static final double goldenDis = 0.15;
	public static final double silverDis = 0.10;

	/**
	 * Constructor for Customer
	 * 
	 * @param inputList
	 *            is a list. Its order is
	 *            {name,email,birthday,balance,password}.
	 */
	public Customer(String[] inputList) {
		super(inputList[1], inputList[2], inputList[3]);
		this.balance = Double.parseDouble(inputList[4]);
		this.password = inputList[5];
		this.customerNumber++;
		this.customerID = customerNumber;
		this.stat = "CLASSIC";
	}

	/** @return customerID */
	public String getcustomerID() {
		return Integer.toString(this.customerID);
	}

	// public String getcustomerID() {return Integer.toString(this.customerID);}
	/**
	 * changes password of the custoemer only if input can provide customers
	 * current password it prints a message that says whether it could change it
	 * or not
	 * 
	 * @param password1
	 *            old password
	 * @param password2
	 *            new password
	 */
	public void changepassword(String password1, String password2) {
		if (password1.equals(this.password)) {
			this.password = password2;
			System.out.println("The password has been successfully changed.");
		} else
			System.out.println("The given password does not match the current password. Please try again.");
	}

	/** @return password */
	public String getpassword() {
		return this.password;
	}

	/** @return stat */
	public String getstat() {
		return this.stat;
	}

	/** Displays its data */
	public void displayData() {
		String name = this.getname();
		String birth = this.getbirthDay();
		String eMail = this.geteMail();
		System.out.printf("Customer name: %s\tID: %d\te-mail:%s\tDate of Birth: %s\tStatus: %s\n", name,
				this.customerID, eMail, birth, this.stat);

	}

	/**
	 * Adds money to balance
	 * 
	 * @param balance money that is added to balance           
	 */
	public void deployMoney(String balance) {
		double dbalance = Double.parseDouble(balance);
		this.balance = dbalance + this.balance;
	}

	/** @return balance */
	public double getbalance() {// will be deleted
		return this.balance;
	}

	/**	Adds item to customers cart only if items stock is bigger than zero.
	 * Prints a massage that informs user	*/
	public void addTocart(Item item) {
		Perfume perfume = new Perfume();
		SkinCare skin = new SkinCare();
		HairCare hair = new HairCare();
		Tablet tablet = new Tablet();
		Laptop laptop = new Laptop();
		Desktop desktop = new Desktop();
		Tv tv = new Tv();
		SmartPhone phone = new SmartPhone();
		CdDvd cd = new CdDvd();
		Book book = new Book();
		Item[] types = { perfume, skin, hair, tablet, laptop, desktop, tv, phone, cd, book };
		String[] stypes = { "Perfume", "SkinCare", "HairCare", "Tablet", "Laptop", "Desktop", "Tv", "SmartPhone",
				"CdDvd", "Book" };

		for (int index = 0; index < types.length; index++) {
			boolean result;
			result = item.getClass().equals(types[index].getClass());

			if (result) {
				if (item.getstock() == 0)
					System.out.println("We are sorry. The item is temporarily unavailable.");
				else {
					this.cart.add(item);
					System.out.printf("The item %s has been successfully added to your cart.\n", stypes[index]);
				}
			}
		}
	}

	/**	emtptys customers cart	*/
	public void emptycart() {
		this.cart = new ArrayList<Item>();
	}

	/**	@return cart	*/
	public ArrayList<Item> getcart() {
		return this.cart;
	}

	/**	Shows every campaign
	 * @param 	campaigns all campaigns*/
	public void showcampaigns(ArrayList<Campaign> campaigns) {
		if (campaigns.size() > 0) {
			System.out.println("Active campaigns:");
			for (Campaign c : campaigns)
				c.displayCampaign();
		} else
			System.out.println("No campaign has been created so far!");
	}

	/**	displays a message that informs customer.
	 * message says how much money customer need to spend for his status upgrade
	 * @param spend money spend so far 	*/
	public void statneed(double spend) {
		if (!"GOLDEN".equals(this.stat)) {
			double money = 0;
			String stat = "";
			switch (this.stat) {
			case ("CLASSIC"): {
				money = 1000 - spend;
				stat = "SILVER";
			}
			case ("SILVER"): {
				money = 5000 - spend;
				stat = "GOLDEN";
			}

			}
			System.out.printf("You need to spend %f more TL to become a %s MEMBER!\n", money, stat);
		}
	}
	/**	calculates the money that customer needs to pay in order to perform order	*/
	public double finalcost() {
		double finalcost = 0;
		ArrayList<Item> cart = this.cart;
		for (Item i : cart) {
			double cost = i.getcost();
			cost = cost * (1 - i.getdisrate());
			finalcost = finalcost + cost;
		}
		switch (this.stat) {
		case ("SILVER"): {
			finalcost = finalcost * (1 - this.silverDis);
		}
		case ("GOLDEN"): {
			finalcost = finalcost * (1 - this.goldenDis);
		}
		}

		return finalcost;
	}

	/**
	 * @return true if there is enough stock for every item in cart*/
	public boolean hasstock() {
		ArrayList<Item> cart = this.cart;
		boolean check = true;
		int haircount = 0;
		int skincount = 0;
		int perfumecount = 0;
		int laptopcount = 0;
		int desktopcount = 0;
		int tabletcount = 0;
		int tvcount = 0;
		int phonecount = 0;
		int bookcount = 0;
		int cdcount = 0;
		for (Item i : cart) {
			if (i instanceof HairCare) {
				haircount++;
				if (i.getstock() < haircount)
					check = false;
			}
			if (i instanceof SkinCare) {
				skincount++;
				if (i.getstock() < skincount)
					check = false;
			}
			if (i instanceof Perfume) {
				perfumecount++;
				if (i.getstock() < perfumecount)
					check = false;
			}
			if (i instanceof Laptop) {
				laptopcount++;
				if (i.getstock() < laptopcount)
					check = false;
			}
			if (i instanceof Desktop) {
				desktopcount++;
				if (i.getstock() < desktopcount)
					check = false;
			}
			if (i instanceof Tablet) {
				tabletcount++;
				if (i.getstock() < tabletcount)
					check = false;
			}
			if (i instanceof Tv) {
				tvcount++;
				if (i.getstock() < tvcount)
					check = false;
			}
			if (i instanceof SmartPhone) {
				phonecount++;
				if (i.getstock() < phonecount)
					check = false;
			}
			if (i instanceof Book) {
				bookcount++;
				if (i.getstock() < bookcount)
					check = false;
			}
			if (i instanceof CdDvd) {
				cdcount++;
				if (i.getstock() < cdcount)
					check = false;
			}

		}

		return check;
	}

	/**	upgrade stat if needed.also displays a message that congrats customer yay	*/
	public void setstatus(double oldspend) {
		if (oldspend < 1000) {

			if (this.moneySpend > 5000) {
				System.out.println(
						"Congratulations! You have been upgraded to a GOLDEN MEMBER! You have earned adiscount of 15% on all purchases.");
				this.stat = "GOLDEN";
			} else if (this.moneySpend > 1000) {
				System.out.println(
						"Congratulations! You have been upgraded to a SILVER MEMBER! You have earned adiscount of 10% on all purchases.");
				this.stat = "SILVER";
			}

		} else if (this.moneySpend < 5000) {
			if (this.moneySpend > 5000) {
				System.out.println(
						"Congratulations! You have been upgraded to a GOLDEN MEMBER! You have earned adiscount of 15% on all purchases.");
				this.stat = "GOLDEN";
			}
		}
	}

	/**
	 * perform order for cart
	 * also print messages if order takes place. if can't order, prints why it cant
	 * @param password customers password
	 * @param orders in order to add order to orders*/
	public void order(String password, ArrayList<Order> orders) {
		String id = Integer.toString(this.customerID);
		double finalcost;
		ArrayList<Item> cart = this.cart;
		double oldspend;
		if (password.equals(this.password)) {
			if (cart.size() != 0) {
				finalcost = this.finalcost();
				if (finalcost < this.balance) {
					if (this.hasstock()) {
						for (Item i : cart) {
							i.setstock();
						}
						Order order = new Order(finalcost, cart, id);
						orders.add(order);
						this.emptycart();
						this.balance = this.balance - finalcost;
						oldspend = this.moneySpend;
						this.moneySpend = this.moneySpend + finalcost;
						double spend = this.moneySpend;
						System.out.println("Done! Your order will be delivered as soon as possible. Thank you!");
						this.setstatus(oldspend);
						statneed(spend);
					}
				} else
					System.out.println("Order could not be placed. Insufficient funds.");
			} else
				System.out.println("You should add some items to your cart before order request!");
		} else
			System.out.println("Order could not be placed. Invalid password.");
	}

}
