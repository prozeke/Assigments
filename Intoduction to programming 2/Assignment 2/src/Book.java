import java.util.ArrayList;

public class Book extends OfficeSupplies {
	private static int stock = 0;
	private String publisher;
	private String authors;
	private String pagenum;
	private static double disrate = 0;
	Book(){
		
	}
	/**
	 * Constructor book
	 * @param inputList {book,double cost,String releaseDate,String covertittle,String publisher,String authors,String pagenum}*/
	/*Book(double cost,String releaseDate,String covertittle,String publisher,String authors,String pagenum)	*/
	Book(String[] inputList){
		//super(double cost,String releaseDate,String covertittle)
		super(Double.parseDouble(inputList[1]),inputList[2],inputList[3]);
		this.publisher = inputList[4];
		this.authors = inputList[5];
		this.pagenum = inputList[6];
		this.stock++;
	}
	/**
	 * display items all info*/
	public void displayItem(){
		System.out.println("Type: Book");
		super.displayItem();
		System.out.printf("Publisher: %s\nAuthor: %s \nPage: %s pages\n",this.publisher,this.authors,this.pagenum);
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
	 * */
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
