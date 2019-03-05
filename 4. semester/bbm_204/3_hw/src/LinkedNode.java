
public class LinkedNode {

	private LinkedNode next;
	private int vertex;
	private float weight;

	LinkedNode(int v, float weight){
		this.next = null;
		this.vertex = v;
		this.weight = weight;
	}


	public LinkedNode getNext(){
		
		return this.next;
	}
	
	public int getVertex(){
		
		return this.vertex;
	}
	
	public float getWeight(){
		
		return this.weight;
	}
	
	public static LinkedNode add(LinkedNode root, LinkedNode add){
		add.next = root;
		return add;
		
	}

	public static LinkedNode add(LinkedNode root, int v, float weight){
		LinkedNode add = new LinkedNode(v,weight);
		return add(root, add);
		
	}
	
	public static LinkedNode delete(LinkedNode root, LinkedNode del){
		if(root == null)
			return null;
		if(root.vertex == del.vertex)
			return root.next;
		root.next = delete(root, del);
		return root;
	}

	public static LinkedNode delete(LinkedNode root, int vertex){
		if(root == null)
			return null;
		if(root.vertex == vertex)
			return root.next;
		root.next = delete(root, vertex);
		return root;
		
	}
	
	public static String toString(LinkedNode l){
		if(l == null)
			return "\n";
		String ret = "";
		for(LinkedNode temp = l; temp != null; temp = temp.next){
			ret = ret + Integer.toString(temp.getVertex()) + " ";
			
		}
		return ret;
	}


}
