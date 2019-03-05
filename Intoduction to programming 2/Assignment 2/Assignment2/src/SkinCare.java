
public class SkinCare extends Cosmetic {
	private static int stock = 0;
	private boolean isBabySensitive;
	private static double disrate = 0;
	public SkinCare(){}
	/**
	 * @param inputList {SkinCarei,double cost, String manufacturer,String brand,boolean isOrganic,String expiration, String weight,String isBabySensitive}
	 */
	public SkinCare(String[] inputList) {

		/*
		 * super(double cost, String manufacturer,String brand,String isOrganic
		 * ,String expiration, String weight)
		 */
		super(Double.parseDouble(inputList[1]), inputList[2], inputList[3], inputList[4], inputList[5], inputList[6]);
		switch (inputList[7]) {
		case ("0"):
			this.isBabySensitive = false;
		case ("1"):
			this.isBabySensitive = true;
		}
		this.stock++;
	}
	/**
	 * display items all info*/
	public void displayItem(){
		System.out.println("Type: SkinCare");
		super.displayItem();
		System.out.printf("Baby Sensitive: %s\n", isit(this.isBabySensitive));
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
