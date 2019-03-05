
public class Bnode {
	public Bnode left;
	public Bnode right;
	public Data val;
	


	public Bnode(){
		this.val = null;
		this.right = null;
		this.left = null;
		
	}
		
	public Bnode(Data val){
		
		this.val = val;
		this.left = null;
		this.right = null;
	
	}
	
	
	public Bnode add(Data d){
		Bnode a = this;
		a = add(this, d);
		return a;
	}
	
	
	public static Bnode add(Bnode b, Data d){
		if(b == null)
			return new Bnode(d);
		if(b.val == null)
			return new Bnode(d);
		int cmp = (b.val).compareTo(d);
		if(cmp == 1){
			b.left = add(b.left,d) ;
		}
		if(cmp == -1)
			b.right = add(b.right,d) ;
	
	
		return b;
	}
	
	public void display(){
		display(this);
	}
	
	public static void display(Bnode b){
		if(b == null){
			return;
		}
		System.out.println(b.val.getC());
		display(b.left);
		display(b.right);
	}
	
	
	public Bnode min(){
		if( this == null)
			return null;
		if(this.val == null)
			return null;
		Bnode min = this;
		while(min.left != null)
			min = min.left;
		return min;
	}
	
	
	public Bnode deleteMin(){
		if(this.left == null)
			return this.right;
		this.left = this.left.deleteMin();
		return this;
		
	}
	
	
	
	public Bnode delete(Data d){
		if(this == null)
			return null;
		int cmp = (this.val).compareTo(d);
		if(cmp == 1){
			this.left = this.left.delete(d);
			
		}
		if(cmp == -1){
			this.right = this.right.delete(d);
			
		}
		if(cmp == 0){
			if(this.right == null)
				return this.left;
				
			Bnode m = this.right.min();
			Bnode del = this;
			m.right = this.right.deleteMin();
			m.left = del.left;
			return m;
		}
	
		return this;
	
	}
	
	
	public static boolean isIn(Bnode b, char c){
		if(b == null)
			return false;
		if(b.val == null)
			return false;
		char tc = b.val.getC();
		if(tc == c){
			b.val.setChance();
			return true;
		}
		if(tc > c)
			return isIn(b.left, c);
		else
			return isIn(b.right, c);
	}
	
	
	







}
