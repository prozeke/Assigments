
public class OfficeSupplies extends Item{
	private String releaseDate;
	private String covertittle;
	
	OfficeSupplies(){}
	
	OfficeSupplies(double cost,String releaseDate,String covertittle){
		super(cost);
		this.releaseDate = releaseDate;
		this.covertittle=covertittle;
		
		
	} 
	/**
	 * displays item information*/
	public void displayItem(){
		super.displayItem();
		System.out.printf("Release Date: %s\nTitle: %s\n", this.releaseDate,this.covertittle);
	}

}
