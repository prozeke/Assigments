
public class LinkedNode implements Comparable<LinkedNode>{

	private LinkedNode next;
	private Object obj;

	public LinkedNode(Edge edge){
		this.next = null;
		this.obj = edge;
	}

	public LinkedNode(Vertex v){
		this.obj = v;
		this.next = null;
	}
	
	
	public LinkedNode getNext(){
		return this.next;
	}
	
	public Edge getEdge(){
		assert(this.obj.getClass().equals(Edge.class));
		return (Edge)this.obj;
	}
	
	public Vertex getVertex(){
		assert(this.obj.getClass().equals(Vertex.class));
		return (Vertex)this.obj;
	}
	
	public float getLen(){
		assert(this.obj.getClass().equals(Edge.class));
		Edge e = (Edge)this.obj;
		return e.getLen();
	}
	
	public static LinkedNode add(LinkedNode root, LinkedNode add){
		if(root == null)
			return add;
		if(add.compareTo(root) < 0){
			add.next = root;
			return add;
		}
		root.next = LinkedNode.add(root.next, add);
		return root;	
	
	}
	
	
	public static LinkedNode add(LinkedNode root, Edge edge){
		LinkedNode add = new LinkedNode(edge);
		return add(root, add);
		
	}
	
	public static LinkedNode add(LinkedNode root, Vertex v){
		LinkedNode add = new LinkedNode(v);
		return add(root, add);
		
	}
	
	public void changeLen(int to, float len){
		assert(this.obj.getClass().equals(Edge.class));
		LinkedNode n = this;
		Edge e;
		while(n!= null){
			e = (Edge)n.obj;
			if(e.getTo().getInd() == to ){
				e.setLen(len);
				return;
			}
			else
				n = n.next;
		}
	}
	
	
/*
	public static String toString(LinkedNode l){
		if(l == null)
			return "\n";
		String ret = "";
		for(LinkedNode temp = l; temp != null; temp = temp.next){
			ret = ret + Integer.toString(temp.getVertex()) + " ";
			
		}
		return ret;
	}
*/
	public int compareTo(LinkedNode n){
		assert(this.obj.getClass().equals(Edge.class) || this.obj.getClass().equals(Vertex.class));
		if(this.obj.getClass().equals(Edge.class)){
			Edge e1 = (Edge)this.obj;
			Edge e2 = (Edge)n.obj;
			return e1.compareTo(e2);
		}
		else{
			Vertex v1 = (Vertex)this.obj;
			Vertex v2 = (Vertex)n.obj;
			return v1.compareTo(v2);
		}	
	}

	public String toString(){		
		assert(this.obj.getClass().equals(Edge.class) || this.obj.getClass().equals(Vertex.class));
		String ret = "";
		LinkedNode r = this;
		while(r != null){
			ret = ret + "\n" + r.obj.toString();
			r = r.next;
		}
		ret = ret + "\n";
		return ret;
	}
	

}
