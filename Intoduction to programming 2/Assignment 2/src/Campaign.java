
public class Campaign {
	String startDate;
	String endDate;
	double disrate;
	String type;
	
	/**
	 * Constructor for Campaign
	 * @param start start date of the campaign
	 * @param end end date of the campaign
	 * @param item item type that campaign effects
	 * @param disrate discount rate of the campaign*/
	public Campaign(String start,String end,String item,String disrate){
		this.startDate = start;
		this.endDate = end;
		this.type = item;
		double rate = Double.parseDouble(disrate);
		rate = rate/100.0;
		this.disrate = rate;
		
		
	}
	
	/**
	 * @return disrate*/
	public double getdisrate(){
		return this.disrate;
	}	
	/**
	 * retun type*/
	public String gettype(){
		return this.type;
	}
	
	/**
	 * displays campaign information*/
	public void displayCampaign(){
		double rate = this.disrate;
		rate = rate * 100;
		String percent = "%";
		System.out.printf("%.0f%s sale of %s until %s\n",rate,percent,this.type,this.endDate);
	} 
}
