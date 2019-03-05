import java.util.Queue;
import java.util.LinkedList;

public class Dijsktra {
	private Edge[] edgeTo;
	private float[] distTo;


	public Dijsktra(Graph g, int from, int to, float swcTime, float velocity){
		this.edgeTo = new Edge[g.getV()];
		this.distTo = new float[g.getV()];
		boolean[] visited = new boolean[g.getV()];
		for(int i = 0; i < g.getV(); i++){
			this.edgeTo[i] = null;
			this.distTo[i] = Float.POSITIVE_INFINITY;
			visited[i] = false;
		}
		//Queue<Vertex> q = new LinkedList<Vertex>();
		MinVertexHeap q = new MinVertexHeap(g.getE(), distTo);
		Vertex now = g.getVertex(from);
		q.add(now);
		this.distTo[now.getInd()] = 0;
		visited[now.getInd()] = true;
		float len;
		LinkedNode root;
		Edge e;
		while(!q.isEmpty()){
			now = q.remove();
			if(!now.getAv())
				continue;
			root = now.getRoot();
			while(root != null){
				e = root.getEdge();
				if(!e.getTo().getAv() || e.getBroken()){
					root = root.getNext();
					
				}
				else{
					len = e.getLen() + this.distTo[now.getInd()];
					if(now.getSwc() != e.getTo().getInd())
						len = len + swcTime / 60 * velocity;
					if(len  < this.distTo[e.getTo().getInd()]){
						this.distTo[e.getTo().getInd()] = len ;
						this.edgeTo[e.getTo().getInd()] = e;
						if(visited[e.getTo().getInd()]){
							if(q.isIn(e.getTo())){
								q.reSwim(e);
							}
							else{
								q.add(e.getTo());
							}
						}
						
					}
					if(!visited[e.getTo().getInd()]){
						q.add(e.getTo());
						visited[e.getTo().getInd()] = true;
					}
					root = root.getNext();
				}
			}
		}
		return;
	}

	public Edge[] getEdgeTo(){
		return this.edgeTo;
	}

	public float getDist(int to){
		return this.distTo[to];
	}

	public boolean reached(int to){
		return this.edgeTo[to] != null;
	}
}
