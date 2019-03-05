
public class Edge implements Comparable<Edge>{
	private Vertex from;
	private Vertex to;
	private float len;
	private boolean broken;
	
	public Edge(Vertex from, Vertex to, float len){
		
		this.broken = false;
		this.len = len;
		this.to= to;
		this.from = from;
		
	}
	
	public void setBroken(boolean set){
		this.broken = set;
	}
	
	public void setLen(float len){
		this.len = len;
	}
	
	public boolean getBroken(){
		return this.broken;
	}

	public Vertex getTo(){
		return this.to;
	}

	public Vertex getFrom(){
		return this.from;
	}

	public float getLen(){
		return this.len;
	}
	
	public int compareTo(Edge e){		
		return this.to.compareTo(e.to);		
	}

	public String toString(){
		String ret = from.getName() + ">"+ to.getName();
		return ret;
		
	}
	
	
}
