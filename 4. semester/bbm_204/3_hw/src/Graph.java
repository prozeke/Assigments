
public class Graph {

	public LinkedNode[] adjList;
	private int V;
	private int E;

	public Graph(int V, MinHeap edgeHeap){
		this.E = edgeHeap.getLen();
		this.V = V;
		this.adjList = new LinkedNode[V];
		int f,s;
		float weight;
		for(Edge e : edgeHeap.getLis()){
			f = e.getFirst();
			s = e.getSec();
			weight = e.getWeight();
			this.adjList[f] = LinkedNode.add(this.adjList[f], s, weight);
			this.adjList[s] = LinkedNode.add(this.adjList[s], f, weight);
		}
		return;
	}
	
	public int getV(){
		return this.V;
	}
	
	public int getE(){
		return this.E;
	}
	
	private void dfsCompenent(int v, int comp, int[] lis){
		if(lis[v] != -1)
			return;
		lis[v] = comp;
		for(LinkedNode l = this.adjList[v]; l != null; l = l.getNext()){
			this.dfsCompenent(l.getVertex(), comp, lis);
			
		}
		
	}
	
	public int[] findComponent(){
		int[] ret = new int[this.V];
		int comp = 0;
		for(int i = 0; i < this.V; i++ ){
			ret[i] = -1;
		}
		
		for(int i = 0; i < this.V; i++){
			if(ret[i] == -1){
				this.dfsCompenent(i, comp, ret);
				comp++;
			} 
			
		}
		return ret;
	}
	
	
	public String toString(){
		
		String ret = "Adj list:\n";
		for(LinkedNode l : this.adjList){
			ret = ret + LinkedNode.toString(l)+ "\n";
			
		} 
		
	
		return ret;
	}
	



}
