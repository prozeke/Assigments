import java.util.ArrayList;

public class CdDvd extends OfficeSupplies {
	private static int stock = 0;
	private String composer;
	private String songs;
	private static double disrate = 0;

	CdDvd() {
	}

	/**
	 * @param inputList {CdDvd,double cost,String releaseDate,String covertittle, String composer,songs}
	 */
	CdDvd(String[] inputList) {
		// super(double cost,String releaseDate,String covertittle)
		super(Double.parseDouble(inputList[1]), inputList[2], inputList[3]);
		this.composer = inputList[4];
		this.songs = inputList[5];
		this.stock++;

	}

	/**
	 * display items all info*/
	public void displayItem() {
		System.out.println("Type: CDDVD");
		super.displayItem();
		System.out.printf("Songs: %s \nComposer: %s\n", this.songs, this.composer);
	}

	/**	@return stock	*/
	public int getstock() {
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
