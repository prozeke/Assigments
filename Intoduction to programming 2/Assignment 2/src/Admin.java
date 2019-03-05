import java.util.ArrayList;

public class Admin extends Employee {
	private String password;

	/**
	 * Constructor for Technican
	 * 
	 * @param inputList
	 *            is a list. Its order is {name,email,birthday,salary,password}.
	 */
	Admin(String[] inputList) {
		super(inputList[1], inputList[2], inputList[3], inputList[4]);
		this.password = inputList[5];

	}

	/**
	 * getter method
	 * 
	 * @return password
	 */
	public String getpassword() {
		return this.password;
	}

	/**
	 * adds a new customer to the system.Also adds it to the given arraylists.
	 * 
	 * @param i
	 *            is the input list that is needed to creat new Customer
	 * @param customers
	 *            contains all customers
	 * @param persons
	 *            contains all persons
	 */
	public void addCustomer(String[] i, ArrayList<Customer> customers, ArrayList<Person> persons) {
		Customer c = new Customer(i);
		customers.add(c);
		persons.add(c);

	}

	/**
	 * adds a new Admin to the system.Also adds it to the given arraylists.
	 * 
	 * @param i
	 *            is the input list that is needed to creat new Admin
	 * @param admins
	 *            contains all admins
	 * @param persons
	 *            contains all persons
	 */
	public void addAdmin(String[] i, ArrayList<Admin> admins, ArrayList<Person> persons,
			ArrayList<Employee> employees) {
		Admin a = new Admin(i);
		admins.add(a);
		persons.add(a);
		employees.add(a);

	}

	/**
	 * adds a new Technican to the system.Also adds it to the given arraylists.
	 * 
	 * @param i
	 *            is the input list that is needed to creat new Technican
	 * @param technicans
	 *            contains all technicans
	 * @param persons
	 *            contains all persons
	 */
	public void addTech(String[] inputList, ArrayList<Technican> technicans, ArrayList<Person> persons,
			ArrayList<Employee> employees) {
		Technican t = new Technican(inputList);
		technicans.add(t);
		persons.add(t);
		employees.add(t);
	}

	/**
	 * displays datas of all customers
	 * 
	 * @param customers
	 *            ArrayList of all customers
	 */
	public void showcustomers(ArrayList<Customer> customers) {
		for (Customer c : customers)
			c.displayData();
	}

	/**
	 * displays information of a specific customer with help of customerID. if
	 * can't match id to any customer prints a text to inform
	 * 
	 * @param customers
	 *            ArrayList of all customers
	 * @param customerID
	 *            id
	 */
	public void showcustomer(String customerID, ArrayList<Customer> customers) {
		boolean noid = true;
		for (Customer c : customers) {
			if (customerID.equals(c.getcustomerID()))
				c.displayData();
			noid = false;

		}
		if (noid)
			System.out.printf("No customer with ID number %s exists!\n", customerID);
	}

	/** creates a campaign for a specific item type.
	 * does not create a campaign if discount rate is bigger than %50
	 * @param start start date
	 * @param end end date
	 * @param item item type that the campaign is for
	 * @param disrate discount rate of the campaign
	 * @param campaigns all campaigns that exists*/
	public void createCampaign(String start, String end, String item, String disrate, ArrayList<Campaign> campaigns) {
		String[] stypes = { "DESKTOP", "LAPTOP", "TABLET", "TV", "SMARTPHONE", "BOOK", "CDDVD", "HAIRCARE", "SKINCARE",
				"PERFUME" };
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
		Item[] types = { desktop, laptop, tablet, tv, phone, book, cd, hair, skin, perfume };
		for (int i = 0; i < types.length; i++) {
			if (item.equals(stypes[i])) {
					Campaign c = new Campaign(start, end, item, disrate);
					Item type = types[i];
					type.setdisrate(c.getdisrate());
					campaigns.add(c);
				
			
			}
		}

	}

	/**	prints admins own info*/
	public void showadminInfo() {
		System.out.printf(
				"----------- Admin info -----------\nAdmin name: %s\nAdmin e-mail: %s\nAdmin date of birth: %s\n",
				this.getname(), this.geteMail(), this.getbirthDay());

	}

}