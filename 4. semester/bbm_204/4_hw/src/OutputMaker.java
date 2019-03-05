import java.util.Stack;

public class OutputMaker {
	public static void maintain(Graph g, int ind){
		if(ind > g.getV()){
			System.out.print("\tCannot maintain that vertex. It does not exist" + "\n");
			return;
		}
		Vertex v = g.getVertex(ind);
		v.setAv(false);
		return;
	}

	public static void service(Graph g, int ind){
		if(ind > g.getV()){
			System.out.print("\tCannot service that vertex. It does not exist" + "\n");
			return;
		}
		Vertex v = g.getVertex(ind);	
		v.setAv(true);
		return;
	}

	public static void breakEdge(Graph g, int from, int to){
		if(from > g.getV()){
			System.out.print("\tCannot break edge. Source does not exist" + "\n");
			return;
		}
		g.breakEdge(from, to);
		return;
	}
	
	
	public static void repair(Graph g, int from, int to){
		if(from > g.getV()){
			System.out.print("\tCannot break edge. Source does not exist" + "\n");
			return;
		}
		g.repair(from, to);
		return;
	}

	public static void route(Graph g, int source, int to, float swcTime, float velocity){
		int end = to;
		if(source > g.getV() || to > g.getV()){
			System.out.print("\tone of the vertices is not in the graph" + "\n");
			return;
		}
		Dijsktra path = new Dijsktra(g,source,to,swcTime, velocity);
		if(!path.reached(to)){
			System.out.print("\tNo route from " + g.getVertex(source).getName() + " to " + g.getVertex(to).getName() + " found currently!" + "\n");
		}
		else{
			String ret = "";
			Edge e;
			Edge[] edgeTo;
			float time = path.getDist(to) / velocity;
			time = 60 *  time;
			int swcNum = g.followPath(path,end);
			ret = String.format("\tTime (in min): %.3f\n",time);
			ret = ret + "\tTotal # of switch changes: " + swcNum + "\n";
			ret = ret + "\tRoute from " + g.getVertex(source).getName() + " to " +g.getVertex(to).getName() + ":";

			edgeTo = path.getEdgeTo();
			Stack<Vertex> s = new Stack<Vertex>();
			s.add(g.getVertex(to));
			while(edgeTo[to] != null){
				e = edgeTo[to];
				s.add(e.getFrom());
				to = e.getFrom().getInd();
			}
			while(!s.isEmpty()){
				ret = ret + " " + s.pop().getName();
			}
			System.out.print(ret + "\n");
		}
	}

	public static LinkedNode addVertex(Graph g, LinkedNode vertices ,String name, int ind){
		Vertex add = new Vertex(name, ind,-1);
		g.addVertex(add);
		vertices = LinkedNode.add(vertices, add);
		return vertices;
	}

	public static void link(Graph g, int from, int[] tos, float[] lens, int swc){
		for(int i = 0; i < tos.length; i++){
			g.addEdge(from, tos[i], lens[i]);
			g.addEdge(tos[i], from, lens[i]);
		}
		g.getVertex(from).setSwc(swc);
	
	}
	
	public static void listRoutesFrom(Graph g, int from){
		String ret = String.format("\tRoutes from %s:",g.getVertex(from).getName());
		LinkedNode root = g.getVertex(from).getRoot();
		while(root != null){
			ret = ret + " " + root.getEdge().getTo().getName();
			root = root.getNext();
		}
		System.out.print(ret + "\n");
	}
	
	public static void listMaintains(LinkedNode vertices){
		String ret = "\tIntersections under maintenance:";
		while(vertices!= null){
			if(!vertices.getVertex().getAv()){
				ret = ret + " " + vertices.getVertex().getName();
			}
			vertices = vertices.getNext();
		}
		System.out.print(ret + "\n");
	}

	public static void listActiveRails(LinkedNode vertices){
		String ret = "\tActive Rails:";
		LinkedNode root;
		while(vertices != null){
			root = vertices.getVertex().getRoot();
			while(root != null){
				if(root.getEdge().getFrom().getSwc() == root.getEdge().getTo().getInd()){
					ret = ret + " " + root.getEdge();
				}
				root = root.getNext();
			}
				
			vertices = vertices.getNext();
		}		
		System.out.print(ret + "\n");
	}
	
	public static void listBrokenRails(LinkedNode vertices){
		String ret = "\tBroken rails:";
		LinkedNode root;
		while(vertices != null){
			root = vertices.getVertex().getRoot();
			while(root != null){
				if(root.getEdge().getBroken()){
					ret = ret + " " + root.getEdge();
				}
				root = root.getNext();
			}
				
			vertices = vertices.getNext();
		}		
		System.out.print(ret + "\n");
	}
	
	public static void listCrossTimes(LinkedNode vertices){
		String ret = "\t# of cross times:";
		while(vertices != null){
			if(vertices.getVertex().getNumPass() > 0)
				ret = ret + " " + String.format("%s:%d", vertices.getVertex().getName(), vertices.getVertex().getNumPass());
			vertices = vertices.getNext();
		}
		System.out.print(ret + "\n");
	}
	
	
}
