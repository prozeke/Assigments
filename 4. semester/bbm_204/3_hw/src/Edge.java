import java.lang.Comparable;

public class Edge implements java.lang.Comparable{
	private int first;
	private int sec;
	private float weight;
	
	public Edge(int first, int sec, float weight){
		this.first = first;
		this.sec = sec;
		this.weight = weight;
	}
	
	public int getFirst(){
		return this.first;
	}

	public void setFirst(int i){
		this.first = i;
	}
	
	public void setSec(int i){
		this.sec = i;
	}
	
	public int getSec(){
		return this.sec;
	}

	public float getWeight(){
		
		return this.weight;
	}
	
	public int compareTo(Object o){
		Edge e = (Edge)o;
		if(this.weight < e.weight)
			return -1;
		if(this.weight == e.weight)
			return 0;
		return 1;
	
	}
	public String toString(){
		String ret = "";
		ret = "first: " + Integer.toString(this.first) + "\tsec: " + Integer.toString(this.sec) + "\tweight: " + Float.toString(this.weight);
		return ret;
		
	}


	
}
