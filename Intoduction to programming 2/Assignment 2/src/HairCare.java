
public class HairCare extends Cosmetic {
	private static int stock = 0;
	private boolean isMedicated;
	private static double disrate = 0;
	public HairCare(){}
	/**
	 * @param HairCare inputList{HairCare,double cost, String manufacturer,String brand,boolean isOrganic,String expiration, String weight,String isMedicated}
	 */
	public HairCare(String[] inputList) {
		/*
		 * super(double cost, String manufacturer,String brand,String isOrganic
		 * ,String expiration, String weight)
		 */
		super(Double.parseDouble(inputList[1]), inputList[2], inputList[3], inputList[4], inputList[5], inputList[6]);
		switch (inputList[7]) {
		case ("0"):
			this.isMedicated = false;
		case ("1"):
			this.isMedicated = true;
		}
		stock++;

	}
	/**
	 * display items all info*/
	public void displayItem(){
		System.out.println("Type: HairCare");
		super.displayItem();
		System.out.printf("Medicated: %s\n", isit(this.isMedicated));
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
