
public class Laptop extends Computer {
	private static int stock = 0;
	private boolean hasHdmi;
	private static double disrate = 0;
	
	public Laptop(){}
	/**
	 * @param inputList{Laptop,double cost, String manufacturer, String brand, String
	 * maxVoltage, String maxPowConsumption, String opSystem, String cpuType,
	 * String ram, String hdd, String hasHdmi}
	 */

	public Laptop(String[] inputList) {
		//super(cost, manufacturer, brand, maxVoltage, maxPowConsumption, opSystem, cpuType, ram, hdd)
		super(Double.parseDouble(inputList[1]), inputList[2], inputList[3], inputList[4], inputList[5], inputList[6], inputList[7], inputList[8], inputList[9]);
		switch (inputList[10]) {
		case ("0"): {
			this.hasHdmi = false;
		}
		case ("1"):
			this.hasHdmi = true;
		}
		this.stock++;
	}
	/**
	 * display items all info*/
	public void displayItem(){
		System.out.println("Type: Laptop");
		super.displayItem();
		String hasHdmi;
		if(this.hasHdmi) hasHdmi = "Yes";
		else hasHdmi = "No";
		System.out.printf("HDMI support: %s\n", hasHdmi);
		
	}
	/**	@return stock	*/
	public int getstock(){
		return this.stock;
	}
	/**
	 * @return disrate*/
	public double getdisrate(){
		return this.disrate;
	}
	/**
	 * sets disrate to rate
	 * @param rate the wanted disrate*/
	public void setdisrate(double rate){
		this.disrate = rate;
	}
	/**
	 * sets stock to one less*/
	public void setstock(){
		this.stock--;
	}}
