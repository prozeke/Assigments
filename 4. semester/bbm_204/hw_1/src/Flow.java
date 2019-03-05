
public class Flow {

	private int index;
	private double data;	
	
	public Flow(int index, double data){
		
		this.data = data;
		this.index = index;
	}
	
	public int getIndex(){
		return this.index;
		
	}
	public double getData(){
		
		return this.data;
	}
	
	public void display(){
		
		System.out.printf("index : %d\tdata : %f\n",this.index, this.data);
	}
	
	public boolean less(Flow j){
		if(this.data < j.data)
			return true;
		else
			return false;
	}
	public boolean equals(Flow j){
		return this.data == j.data;
		
		
	}

}