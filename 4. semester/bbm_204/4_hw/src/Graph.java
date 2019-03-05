import java.util.ArrayList;

public class Graph {

	private ArrayList<Vertex> adjList;
	private int E;
	private int V;
	
	public Graph(){
		adjList = new ArrayList<Vertex>();
		this.E = 0;
		this.V = 0;
	}
	
	public int getV(){
		return this.V;
	}
	
	public int getE(){
		return this.E;
	}

	public Vertex getVertex(int ind){
		return this.adjList.get(ind);
	}
	
	public ArrayList<Vertex> getAdjList(){
			return this.adjList;
	}

	public void addVertex(Vertex v){
		this.adjList.add(v);
		this.V++;
	}
	
	
	
	public void addEdge(int from, int to , float len){
		Vertex source = this.adjList.get(from);
		Edge e = new Edge(source,this.adjList.get(to),len  );
		source.addEdge(e);
		this.E++;	
	}
	
	public void changeEdgeLen(int from, int to, float len){
		assert(from < this.V && to < this.V);
		this.adjList.get(from).changeLen(to, len);
		this.adjList.get(to).changeLen(from, len);
	}

	public void breakEdge(int from, int to){
		if(from > this.getV()){
			System.out.println("Cannot break edge. Source does not exist");
			return;
		}
		Vertex f = this.getVertex(from);
		f.breakEdge(to);
		return;
	}
	
	public void repair(int from, int to){
		if(from > this.getV()){
			System.out.println("Cannot break edge. Source does not exist");
			return;
		}
		Vertex f = this.getVertex(from);
		f.repair(to);
		return;
	}
	
	public int followPath(Dijsktra path, int to){
		Edge e;
		Edge[] edgeTo = path.getEdgeTo();
		int swcNum = 0;
		this.getVertex(to).passed();
		while(edgeTo[to] != null){
			e = edgeTo[to];
			e.getFrom().passed();
			to = e.getFrom().getInd();
			if(e.getFrom().getSwc() != e.getTo().getInd()){
				e.getFrom().setSwc(e.getTo().getInd());
				swcNum++;
			}
		}
		return swcNum;
	}
	
	
	public String toString(){
		String ret = "";
		for(Vertex v : this.adjList){
			ret = ret + v.toString();
		}
		return ret;
	}


}
