import java.lang.Comparable;

public class Vertex implements Comparable<Vertex>{

	private int ind;
	private String name;
	private boolean av;
	private int numPass;
	private int swc;
	private LinkedNode root;



	public Vertex(String name, int ind, int swc){
	
		this.av = true;
		this.numPass = 0;
		this.name = name;
		this.ind = ind;
		this.swc = swc;
		this.root = null;
		return;
	
	}

	public void setAv(boolean set){
		this.av = set;
		return;
	}

	public void passed(){
		this.numPass++;
	}
	
	public void setSwc(int swc){
		this.swc = swc;
	}

	public void addEdge(Edge edge){
		this.root = LinkedNode.add(this.root, edge);
	}
	
	public String getName(){
		return this.name;
	}

	public int getInd(){
		return this.ind;
	}
	
	public boolean getAv(){
		return this.av;
	}
	
	public int getSwc(){
		return this.swc;
	}
	
	
	public int getNumPass(){
		return this.numPass;
	}
	
	public LinkedNode getRoot(){
		return this.root;
	}
	
	@Override
	public int compareTo(Vertex v){
		return (this.name).compareTo(v.name);
		
	}
	
	public boolean noEdge(){
		return this.root == null;
	}

	public String toString(){
		String ret = "";
		LinkedNode r = this.root;
		if(r != null){
			ret = ret + " " + r.toString();
		}
		ret = ret.trim();
		return ret;
	}

	public void changeLen(int to, float len){
		if(this.root == null)
			return;
		this.root.changeLen(to, len);
		return;
	}


	public void breakEdge(int to){
		LinkedNode node = this.root;
		while(node != null){
			if(node.getEdge().getTo().getInd() == to){
				node.getEdge().setBroken(true);
				return;
			}
			else
				node = node.getNext();
		}
		return;
	}
	public void repair(int to){
		LinkedNode node = this.root;
		while(node != null){
			if(node.getEdge().getTo().getInd() == to){
				node.getEdge().setBroken(false);
				return;
			}
			else
				node = node.getNext();
		}
		return;
	}


}