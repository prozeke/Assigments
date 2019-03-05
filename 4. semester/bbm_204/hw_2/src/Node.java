
public class Node {
	private char c;
	private Node next;
	

	
	public Node(char c){
		this.c = c;
		this.next = null;
		
	}
	
	public void add(char c){
		if(this == null)
			return;
		Node last = this;
		while(last.next != null)
			last = last.next;
		Node n = new Node(c); 
		last.next = n;
		return;
	}
	
	public char getC(){
		return this.c;
	}
	
	public Node getNext(){
		return this.next;
	}
		
	
	public void display(){
		if(this == null)
			return;
		System.out.format("%c-", this.c);
		if(this.next == null){
			System.out.println();
			return;
		}
		this.next.display();
		return;
	}
	
	public static String toString(Node n){
		String ret = "";
		if(n == null)
			return ret;
		while( n != null ){
			ret =  ret + " " + n.c; 
			n = n.next;
		}
		ret = ret.trim();
		return ret;
	}
	
	




}


