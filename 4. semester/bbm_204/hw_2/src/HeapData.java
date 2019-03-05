
public class HeapData {
	private char c;
	private int index;
	
	public HeapData(Data d, int index){
		this.index = index;
		this.c = d.getC(); 
		
	}


	public HeapData(char c, int index){
		this.index = index;
		this.c = c; 
	}
	
	
	public char getC(){
		return this.c;
	}
	
	public int getIndex(){
		return this.index;
		
	}
	
	
	







}