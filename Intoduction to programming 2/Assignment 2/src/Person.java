import java.util.ArrayList;

public class Person {
	private String name;
	private String eMail;
	private String birthDay;
	

	Person(String name,String eMail,String birthDay ){
		this.name = name;
		this.eMail = eMail;
		this.birthDay =birthDay;
	}
	
	public String getname(){return this.name;}
	public String geteMail(){return this.eMail;}
	public String getbirthDay(){return this.birthDay;}

	/** checks if a specific type has a campaign
	 * @param type item type
	 * @param campaigns all campaigns in a list
	 * @return true if type has a campaign. else false*/
	public boolean hasCampain(String type,ArrayList<Campaign> campaigns){
		boolean check = false;

			for(Campaign c : campaigns){
				if(type.equals(c.gettype())) 
					check =true;}
		
		return check;
	}
}
