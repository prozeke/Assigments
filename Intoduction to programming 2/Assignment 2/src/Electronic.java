
public class Electronic extends Item{
	private String manufacturer;
	private String brand;
	private String maxVoltage;
	private String maxPowConsumption;

	Electronic(){}
	public Electronic(double cost,	String manufacturer,String brand,String maxVoltage,String maxPowConsumption){
	super(cost);
	this.brand = brand;
	this.manufacturer = manufacturer;
	this.maxPowConsumption = maxPowConsumption;
	this.maxVoltage = maxVoltage;
	
	
	}
	/**
	 * displays item information*/
	public void displayItem(){
		String m = this.manufacturer;
		String b = this.brand;
		String maxp = this.maxPowConsumption;
		String maxv = this.maxVoltage;
		super.displayItem();
		System.out.printf("Manufacturer: %s\nBrand: %s\nMax Volt: %s V.\nMax Watt: %s W.\n",m,b,maxv,maxp);
	}
}
