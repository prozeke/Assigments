
public class Desktop extends Computer {
	private static int stock = 0;
	private String color;
	private static double disrate = 0;
	public Desktop(){}
	/**@param inputList{Desktop,double cost,String manufacturer,String brand,String maxVoltage,String maxPowConsumption,
	/String opSystem,String cpuType,String ram, String hdd,String color}*/
	public Desktop(String[] inputList){
		//super(cost, manufacturer, brand, maxVoltage, maxPowConsumption, opSystem, cpuType, ram, hdd)
		super(Double.parseDouble(inputList[1]), inputList[2], inputList[3], inputList[4], inputList[5], inputList[6], inputList[7], inputList[8], inputList[9]);
		this.color = inputList[10];
		this.stock++;
	}
	/**
	 * display items all info*/
	public void displayItem(){
		System.out.println("Type: Desktop");
		super.displayItem();
		System.out.printf("Box Color: %s\n",this.color);
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
