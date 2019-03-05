import java.util.ArrayList;

public class Employee extends Person {
	private String salary;

	public Employee(String name, String eMail, String birthDay, String salary) {
		super(name, eMail, birthDay);
		this.salary = salary;
	}

	/**	shows every customer with GOLDEN status	*/
	public void showVIP(ArrayList<Customer> customers) {
		for (Customer c : customers) {
			if ("GOLDEN".equals(c.getstat()))
				c.displayData();
		}
	}

	/**
	 * shows every item
	 * @param items all items*/
	public void listItems(ArrayList<Item> items) {
		for (Item i : items) {
			System.out.println("-----------------------");
			i.displayItem();
			
		}
	}

	/**
	 * prints every type and every types stock*/
	public void showstock() {
		String[] stypes = { "Perfume", "SkinCare", "HairCare", "Tablet", "Laptop", "Desktop", "Tv", "SmartPhone",
				"CdDvd", "Book" };

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
		for (int index = 0; index < stypes.length; index++)
			System.out.printf("%s : %d\n", stypes[index], types[index].getstock());
	}

	/**
	 * prints  type and every types stock if types stock is less then maxstoc
	 * @param maxstock maxstock*/
	public void showstock(int maxstock) {
		String[] stypes = { "Perfume", "SkinCare", "HairCare", "Tablet", "Laptop", "Desktop", "Tv", "SmartPhone",
				"CdDvd", "Book" };

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
		for (int index = 0; index < stypes.length; index++)
			if(maxstock > types[index].getstock())
				System.out.printf("%s : %d\n", stypes[index], types[index].getstock());
	}
}
