
public class Tv extends Electronic {
	private static int stock = 0;
	private String size;// in which unit
	private static double disrate = 0;
	public Tv(){}
	/** @param inputList{Tv,double cost,String manufacturer,String brand,String maxVoltage,String maxPowConsumption,String size}*/

	public Tv(String [] inputList) {
		//super(cost, manufacturer, brand, maxVoltage, maxPowConsumption);
		super(Double.parseDouble(inputList[1]), inputList[2], inputList[3], inputList[4], inputList[5]);
		this.size = inputList[6];
		stock++;
	}

	/**
	 * display items all info*/
	public void displayItem(){
		System.out.println("Type: TV");
		super.displayItem();
		System.out.printf("Screen size: %s\"\n", this.size);
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
	}
}
