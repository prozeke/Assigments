
public class Perfume extends Cosmetic {
	private static int stock = 0;
	private String lastingDur;
	private static double disrate = 0;
	public Perfume(){}
	/**
	 * @param inputList{Perfume,double cost, String manufacturer,String brand,boolean isOrganic,String expiration, String weight,String lastingDur}
	 */
	public Perfume(String[] inputList) {
		/*
		 * super(double cost, String manufacturer,String brand,String isOrganic
		 * ,String expiration, String weight)
		 */
		super(Double.parseDouble(inputList[1]), inputList[2], inputList[3], inputList[4], inputList[5], inputList[6]);
		this.lastingDur = inputList[7];
		stock++;

	}
	/**
	 * display items all info*/
	public void displayItem(){
		System.out.println("Type: Perfume");
		super.displayItem();
		System.out.printf("Lasting Duration: %s min.\n", this.lastingDur);
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
