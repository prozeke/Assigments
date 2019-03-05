
public class SmartPhone extends Electronic{
	private static int stock = 0;
	private String screenType;
	private static double disrate = 0;
	public SmartPhone(){}
	/**@param inputList{SmartPhone,double cost,String manufacturer,String brand,String maxVoltage,String maxPowConsumption,String screenType}*/
	public SmartPhone(String[] inputList) {		
		super(Double.parseDouble(inputList[1]), inputList[2], inputList[3], inputList[4], inputList[5]);
		this.screenType = inputList[6];
		stock++;
	}
	/**
	 * display items all info*/
	public void displayItem(){
		System.out.println("Type: SmartPhone");
		super.displayItem();
		System.out.printf("Screen Type: %s\n", this.screenType);
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
