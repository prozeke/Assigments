import java.lang.Comparable;

public class Data implements java.lang.Comparable{
	private char c;
	boolean chance;


	public Data(char c ){
		this.c = c;
		this.chance = false;
	}	
	
	public Data(HeapData d){
		this.c = d.getC();
		this.chance = false;
	}
	
	public char getC(){
		return this.c;
		
	}
	
	public boolean getChance(){
		return this.chance;
	}
	
	public void setChance(){
		this.chance = true;
	}
	public void resetChance(){
		this.chance = false;
	}
	
	public void setChar(char x){
		this.c = x;
		
	}
	
	public int compareTo(Object o){
		Data d = (Data)o;
		if(this.c > d.c)
			return 1;
		if(this.c == d.c)
			return 0;
		return -1;
		
	}


}