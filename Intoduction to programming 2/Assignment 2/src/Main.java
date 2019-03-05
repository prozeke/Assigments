import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	/**
	 * @author Zekeriya Onur Yakiskan - 21527539*/
	
	public static String[] types = { "DESKTOP", "LAPTOP", "TABLET", "TV", "SMARTPHONE", "BOOK", "CDDVD", "HAIRCARE",
			"SKINCARE", "PERFUME" };
	// Item instances
	public static ArrayList<Item> items = new ArrayList<Item>();
	public static ArrayList<Perfume> perfumes = new ArrayList<Perfume>();
	public static ArrayList<SkinCare> skincares = new ArrayList<SkinCare>();
	public static ArrayList<HairCare> haircares = new ArrayList<HairCare>();
	public static ArrayList<Tv> tvs = new ArrayList<Tv>();
	public static ArrayList<SmartPhone> smartphones = new ArrayList<SmartPhone>();
	public static ArrayList<Desktop> desktops = new ArrayList<Desktop>();
	public static ArrayList<Laptop> laptops = new ArrayList<Laptop>();
	public static ArrayList<Tablet> tablets = new ArrayList<Tablet>();
	public static ArrayList<Book> books = new ArrayList<Book>();
	public static ArrayList<CdDvd> cdDvds = new ArrayList<CdDvd>();
	// Person instances
	public static ArrayList<Person> persons = new ArrayList<Person>();
	public static ArrayList<Customer> customers = new ArrayList<Customer>();
	public static ArrayList<Admin> admins = new ArrayList<Admin>();
	public static ArrayList<Technican> technicans = new ArrayList<Technican>();
	public static ArrayList<Employee> employees = new ArrayList<Employee>();
	// Campaign and Order instances
	public static ArrayList<Campaign> campaigns = new ArrayList<Campaign>();
	public static ArrayList<Order> orders = new ArrayList<Order>();

	// returns a list which starts from xth member of input list to last member
	/**
	 * returns the list back but makes x number of member from start dissapear.
	 * lists length will be x times less
	 * @param l firstlist
	 * @param x how much less
	 * @return lesser list*/
	public static String[] makeless(String[] l, int x) {
		String[] rlist = new String[l.length - x];
		for (int i = 1; i < l.length; i++)
			rlist[i - x] = l[i];
		return rlist;
	}

	/**
	 * prints commands*/
	public static void printcommand(String command) {
		command = " <" + command + ">";
		System.out.println();
		System.out.println("COMMAND TEXT:" + command);
		System.out.println();
	}

	/**
	 * gets every items input in a list format.
	 * format would be {type,cost...} which is taken from item text*/
	public static void getItemInput(String[] inputList) {

		switch (inputList[0]) {
		case ("DESKTOP"): {
			Desktop o = new Desktop(inputList);
			items.add(o);
			desktops.add(o);
			break;
		}

		case ("LAPTOP"): {
			Laptop o = new Laptop(inputList);
			items.add(o);
			laptops.add(o);
			break;
		}
		case ("TABLET"): {
			Tablet o = new Tablet(inputList);
			items.add(o);
			tablets.add(o);
			break;
		}
		case ("TV"): {
			Tv o = new Tv(inputList);
			items.add(o);
			tvs.add(o);
			break;
		}
		case ("SMARTPHONE"): {
			SmartPhone o = new SmartPhone(inputList);
			items.add(o);
			smartphones.add(o);
			break;
		}
		case ("BOOK"): {
			Book o = new Book(inputList);
			items.add(o);
			books.add(o);
			break;
		}
		case ("CDDVD"): {
			CdDvd o = new CdDvd(inputList);
			items.add(o);
			cdDvds.add(o);
			break;
		}
		case ("HAIRCARE"): {
			HairCare o = new HairCare(inputList);
			items.add(o);
			haircares.add(o);
			break;
		}
		case ("SKINCARE"): {
			SkinCare o = new SkinCare(inputList);
			items.add(o);
			skincares.add(o);
			break;
		}
		case ("PERFUME"): {
			Perfume o = new Perfume(inputList);
			items.add(o);
			perfumes.add(o);
			break;
		}
		}
	}

	
	/**
	 * gets every persons input in a list format.
	 * format would be {type,name...} which is taken from person text*/
	public static void getPersonInput(String[] inputList) {

		switch (inputList[0]) {
		case ("CUSTOMER"): {
			Customer o = new Customer(inputList);
			persons.add(o);
			customers.add(o);
			break;
		}

		case ("ADMIN"): {
			Admin o = new Admin(inputList);
			persons.add(o);
			admins.add(o);
			employees.add(o);
			break;
		}
		case ("TECH"): {
			Technican o = new Technican(inputList);
			technicans.add(o);
			persons.add(o);
			employees.add(o);
			break;
		}
		}
	}

	/**
	 * adds customer if one admins name is list[0]
	 * if not prints a message
	 * @param inputList {name,customerinput1,...}*/
	public static void addCustomer(String[] inputList) {
		boolean x = true;
		for (Admin a : admins) {
			if (a.getname().equals(inputList[0])) {
				a.addCustomer(inputList, customers, persons);
				x = false;
			}

		}
		if (x)
			System.out.println("No admin person named " + inputList[0] + " exists!");
	}

	/**
	 * adds customer if one admins name is list[0]
	 * if not prints a message
	 * @param inputList {name,addmininput1,...}*/
	public static void addAdmin(String[] inputList) {
		boolean x = true;
		for (int index = 0; index < admins.size(); index++) {
			Admin a = admins.get(index);
			if (a.getname().equals(inputList[0])) {
				a.addAdmin(inputList, admins, persons, employees);
				x = false;

			}

		}

		if (x)
			System.out.println("No admin person named " + inputList[0] + " exists!");
	}

	/**
	 * adds customer if one admins name is list[0]
	 * if not prints a message
	 * @param inputList {name,technicaninput1,...}*/
	public static void addTech(String[] inputList) {
		boolean x = true;
		for (Admin a : admins) {
			if (a.getname().equals(inputList[0])) {
				a.addTech(inputList, technicans, persons, employees);
				x = false;
			}

		}
		if (x)
			System.out.println("No admin person named " + inputList[0] + " exists!");
	}

	/**
	 * shows all customers info if one admins name is inputList[0]
	 * @param inputList {name}*/
	public static void showCustomers(String[] inputList) {
		boolean x = true;
		for (Admin a : admins) {
			if (a.getname().equals(inputList[0])) {
				a.showcustomers(customers);
				x = false;
			}

		}
		if (x)
			System.out.println("No admin person named " + inputList[0] + " exists!");
	}

	/**
	 * shows a specific customers info with given customer id if one admins name is inputList[0]
	 * @param inputList {name,id}*/
	public static void showCustomer(String[] inputList) {
		boolean x = true;
		for (Admin a : admins) {
			if (a.getname().equals(inputList[0])) {
				a.showcustomer(inputList[1], customers);
				x = false;
			}

		}
		if (x)
			System.out.println("No admin person named " + inputList[0] + " exists!");
	}

	/**
	 * shows customers with GOLDEN stat*/
	public static void showVIP(String employee) {
		boolean x = true;
		for (Employee e : employees)
			if (employee.equals(e.getname())) {
				e.showVIP(customers);
				x = false;
			}
		if (x)
			System.out.printf("No admin or technician person named %s exists!\n", employee);
	}

	/**
	 * shows info about given admin
	 * @param inputList {name}*/
	public static void showadmin(String[] inputList) {
		boolean x = true;
		for (Admin a : admins) {
			if (a.getname().equals(inputList[0])) {
				a.showadminInfo();
				x = false;
			}

		}
		if (x)
			System.out.println("No admin person named " + inputList[0] + " exists!");
	}

	/**changes password of the given customerid
	 * @param inputList {id,oldpass,newpass}*/
	public static void changepassword(String[] inputList) {
		boolean x = true;
		String id = inputList[0];
		for (Customer c : customers) {
			if (id.equals(c.getcustomerID())) {
				c.changepassword(inputList[1], inputList[2]);
				x = false;
			}
		}
		if (x)
			System.out.printf("No customer with ID number %s exists!\n", id);
	}

	/**
	 * adds money to given customer id
	 * @param inputList {id,money}*/
	public static void deployMoney(String[] inputList) {
		boolean x = true;
		String id = inputList[0];
		for (Customer c : customers) {
			if (id.equals(c.getcustomerID())) {
				c.deployMoney(inputList[1]);
				x = false;
			}
		}
		if (x)
			System.out.printf("No customer with ID number %s exists!\n", id);
	}

	/**
	 * display all items info*/
	public static void listItems(String[] inputList) {
		boolean x = true;
		String name = inputList[0];
		for (Employee e : employees) {
			if (name.equals(e.getname())) {
				e.listItems(items);
				x = false;
			}
		}
		if (x)
			System.out.println("No admin or technician person named " + name + " exists!");
	}

	/**
	 * display given itemtypes all info
	 * @param inputList {name,type1:type2:...}*/
	public static void dispItemsOf(String[] inputList) {
		boolean check = true;
		String name = inputList[0];
		for (Technican t : technicans) {
			if (name.equals(t.getname())) {
				String[] newinp = inputList[1].split(":");

				t.dispItemsOf(newinp, items);

				check = false;
			}
		}
		if (check)
			System.out.println("No technician person named " + inputList[0] + " exists!");
	}

	/**
	 * show every itemtypes stock*/
	public static void showstock(String[] inputList) {
		boolean x = true;
		String employee = inputList[0];
		for (Employee e : employees)
			if (employee.equals(e.getname())) {
				x = false;
				if (inputList.length > 1) {
					int maxstock = Integer.parseInt(inputList[1]);
					e.showstock(maxstock);
				} else
					e.showstock();
			}
		if (x)
			System.out.printf("No admin or technician person named %s exists!\n", employee);
	}

	/**
	 * adds item to system
	 * @param inputList {name,type:info1:info2:...}*/
	public static void additem(String[] inputList) {
		boolean check = true;
		String name = inputList[0];
		for (Technican t : technicans) {
			if (name.equals(t.getname())) {
				check = false;
				String[] newinp = inputList[1].split(":");
				String typename = newinp[0];
				check = false;
				if (Arrays.asList(types).contains(typename))
					getItemInput(newinp);
				else
					System.out.println("No item type " + typename + " found");
			}
		}
		if (check)
			System.out.println("No technician person named " + name + " exists!");

	}

	/**
	 * ads item to customers cart if possible
	 * inform user whether added or not
	 * @param inputList {customerid,itemid}*/
	public static void addTocart(String[] inputList) {
		boolean check = true;
		boolean check2 = true;

		String name = inputList[0];
		for (Customer c : customers) {
			if (name.equals(c.getcustomerID())) {
				check = false;
				for (Item item : items) {
					String id = Integer.toString(item.getitemID());
					if (id.equals(inputList[1])) {
						c.addTocart(item);
						check2 = false;
					}

				}

			}
		}
		if (check)
			System.out.printf("No customer with ID number %s exists!\n", inputList[0]);
		if (check2 && !check)
			System.out.println("Invalid item ID");

	}

	/**
	 * emptys customers cart
	 * @param inputList {customerid}*/
	public static void emptycart(String[] inputList) {
		boolean check = true;
		String id = inputList[0];
		for (Customer c : customers) {
			if (id.equals(c.getcustomerID())) {
				check = false;
				c.emptycart();
				System.out.println("The cart has been emptied.");
			}
		}
		if (check)
			System.out.printf("No customer with ID number %s exists!\n", id);

	}

	/**
	 * creates campaign for a type.
	 * cant create if there is already.
	 * cant create if discount rate is bigger than %50
	 * @param inputList {name,start,end,type,discountrate}*/
	public static void createCampaign(String[] inputList) {
		boolean check = true;
		String name = inputList[0];
		int disrate = Integer.parseInt(inputList[4]);
		String start = inputList[1];
		String end = inputList[2];
		String item = inputList[3];
		String idisrate = inputList[4];
		for (Admin a : admins) {
			if (name.equals(a.getname())) {
				check = false;
				if (disrate <= 50) {
					if (!a.hasCampain(inputList[3], campaigns)) {
						a.createCampaign(start, end, item, idisrate, campaigns);

					}
				} else
					System.out.println("Campaign was not created. Discount rate exceeds maximum rate of 50%.");
			}

		}
		if (check)
			System.out.printf("No admin person named Alper exists!\n", name);

	}

	/**
	 * prints info about all created campaigns*/
	public static void showcampaigns(String[] inputList) {
		boolean check = true;
		String id = inputList[0];
		for (Customer c : customers) {
			if (id.equals(c.getcustomerID())) {
				check = false;
				c.showcampaigns(campaigns);
			}
		}
		if (check)
			System.out.printf("No customer with ID number %s exists!\n", id);
	}

	/**
	 * place orders for customers cart.
	 * doesnt order if id false.
	 * doesnt order if cart empty.
	 * doesnt order if item stocks are low.
	 * doesnt order if passwords doesnt match.
	 * doesnt order if balance is not enough
	 * @param inputList {customerid,password}*/
	public static void order(String[] inputList){
		boolean check = true;
		String id = inputList[0];
		for (Customer c : customers) {
			if (id.equals(c.getcustomerID())) {
				check = false;
				c.order(inputList[1],orders);
			}
		}
		if (check)
			System.out.printf("No customer with ID number %s exists!\n", id);
	}
	
	/**
	 * shows all orders*/
	public static void showorders(String[] inputList){
		String name = inputList[0];
		boolean check = true;
		for(Technican t:technicans){
			if(name.equals(t.getname())){
				check = false;
				if(t.isSenior()){
					t.showorders(orders);
					
				}
				else System.out.println(name+" is not authorized to display orders!");
			}
		}
		
	if(check) System.out.println("No technician person named " + name + " exists!");
	}
	
	/**
	 * decides which commands need which methods*/
	public static void performCommands(String[] inputList) {
		switch (inputList[0]) {
		case ("ADDCUSTOMER"): {
			String[] newinp = makeless(inputList, 1);
			addCustomer(newinp);
			break;
		}
		case ("SHOWCUSTOMERS"): {
			String[] newinp = makeless(inputList, 1);
			showCustomers(newinp);
			break;
		}

		case ("SHOWCUSTOMER"): {
			String[] newinp = makeless(inputList, 1);
			showCustomer(newinp);
			break;
		}
		case ("SHOWADMININFO"): {
			String[] newinp = makeless(inputList, 1);
			showadmin(newinp);
			break;
		}
		case ("ADDADMIN"): {
			String[] newinp = makeless(inputList, 1);
			addAdmin(newinp);
			break;
		}
		case ("ADDTECH"): {
			String[] newinp = makeless(inputList, 1);
			addTech(newinp);
			break;
		}
		case ("SHOWVIP"): {
			showVIP(inputList[1]);
			break;
		}
		case ("CHPASS"): {
			String[] newinp = makeless(inputList, 1);
			changepassword(newinp);
			break;
		}
		case ("DEPOSITMONEY"): {
			String[] newinp = makeless(inputList, 1);
			deployMoney(newinp);
			break;
		}
		case ("LISTITEM"): {
			String[] newinp = makeless(inputList, 1);
			listItems(newinp);
			break;

		}
		case ("DISPITEMSOF"): {
			String[] newinp = makeless(inputList, 1);
			dispItemsOf(newinp);
			break;
		}
		case ("ADDITEM"): {
			String[] newinp = makeless(inputList, 1);
			additem(newinp);
			break;

		}
		case ("SHOWITEMSLOWONSTOCK"): {
			String[] newinp = makeless(inputList, 1);
			showstock(newinp);
			break;

		}
		case ("ADDTOCART"): {
			String[] newinp = makeless(inputList, 1);
			addTocart(newinp);
			break;

		}
		case ("EMPTYCART"): {
			String[] newinp = makeless(inputList, 1);
			emptycart(newinp);
			break;

		}
		case ("CREATECAMPAIGN"): {
			String[] newinp = makeless(inputList, 1);
			createCampaign(newinp);
			break;

		}
		case ("SHOWCAMPAIGNS"): {
			String[] newinp = makeless(inputList, 1);
			showcampaigns(newinp);
			break;

		}
		case ("ORDER"): {
			String[] newinp = makeless(inputList, 1);
			order(newinp);
			break;

		}
		case ("SHOWORDERS"): {
			String[] newinp = makeless(inputList, 1);
			showorders(newinp);
			break;

		}
		}
	}

	public static void main(String[] args) {

		try {
			Scanner scanner = new Scanner(new File("item.txt"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] inputList = line.split("\t");

				getItemInput(inputList);

			}
			scanner.close();
		} catch (FileNotFoundException ex) {
			System.out.println("No File Found!");
			return;
		}

		try {
			Scanner scanner = new Scanner(new File("users.txt"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] inputList = line.split("\t");

				getPersonInput(inputList);

			}
			scanner.close();
		} catch (FileNotFoundException ex) {
			System.out.println("No File Found!");
			return;
		}

		try {
			Scanner scanner = new Scanner(new File("commands.txt"));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				printcommand(line);
				String[] inputList = line.split("\t");

				performCommands(inputList);

			}
			scanner.close();
		} catch (FileNotFoundException ex) {
			System.out.println("No File Found!");
			return;
		}

		
	
	}

}
