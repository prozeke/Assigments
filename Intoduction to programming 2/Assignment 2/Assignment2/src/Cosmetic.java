
public class Cosmetic extends Item {
	private String manufacturer;
	private String brand;
	private String expirationDate;
	private String weight;
	private boolean isOrganic;

	public Cosmetic(){}
	public Cosmetic(double cost, String manufacturer,String brand,String isOrganic ,String expirationDate,
		String weight){
		super(cost);
		this.manufacturer = manufacturer;
		this.brand = brand;
		this.expirationDate = expirationDate;
		this.weight = weight;
		switch(isOrganic){
			case("0"):
				this.isOrganic = false;
			case("1"):
				this.isOrganic = true;
		}
	
	}
	/**
	 * makes boolean string yes or no
	 * @param itis true or false
	 * @return yes if its true,no if itis false*/
	public String isit(boolean itis){
		if(itis) return "Yes";
		else return "No";
	}
	/**
	 * displays item information*/
	public void displayItem(){
		super.displayItem();
		String m = this.manufacturer;
		String b = this.brand;
		String e = this.expirationDate;
		String w = this.weight;
		String o = this.isit(this.isOrganic);
		System.out.printf("Manufacturer: %s\nBrand: %s\nOrganic: %s\nExpiration Date: %s\nWeight: %s g.\n",m,b,o,e,w);
	}

}
