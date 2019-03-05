
public class MinVertexHeap {
	private int len;
	private int pos;
	private Vertex[] lis;
	private float[] distTo;
	
	public MinVertexHeap(int len,float[] distTo){
		this.pos = -1;
		this.lis = new Vertex[len];
		this.len = len;
		this.distTo = distTo;
	}
	private int cmpEdge(int e1, int e2){
		if(this.distTo[this.lis[e1].getInd()] > this.distTo[this.lis[e2].getInd()])
			return 1;
		if(this.distTo[this.lis[e1].getInd()] ==  this.distTo[this.lis[e2].getInd()])
			return 0;
		else
			return -1;
	}
	
	private void swap(int i, int j){
		Vertex temp = this.lis[i];
		this.lis[i] = this.lis[j];
		this.lis[j] = temp;
	}
	
	private int getLen(){
		return this.pos + 1;
	}
	
	private void sink(int i){
		int k = 2 * i + 1;
		while(k < this.getLen()){
			if(k + 1 < this.getLen() && this.cmpEdge(k + 1, k) == -1){
				k = k + 1;
			}
			if(this.cmpEdge(k , i) == -1){
				this.swap(i, k);
				i = k;
				k = 2 * i + 1;
			}
			else{
				break;
			}
			
		}
		return;
	}
	
	private void swim(int i){
		if(i > this.getLen())
			return;
		while(i > 0){
			int k = (i - 1) / 2;
			if(this.cmpEdge(i, k) == -1){
				this.swap(i, k);
				i = k;
			}
			else{
				break;
			}
		}
		return;
	}
	
	public void add(Vertex e){
		this.lis[++this.pos] = e;
		this.swim(this.pos);
	}
	
	public Vertex remove(){
		this.swap(0, this.pos--);
		this.sink(0);
		return this.lis[pos + 1];
	}
	
	public boolean isEmpty(){
		return this.pos == -1;
	}
	
	public void reSwim(Edge e){
		int i = 0;
		while(i < this.getLen()){
			if(this.lis[i].getInd() ==  e.getTo().getInd()){
				break;
			}
			i++;
		}
		this.swim(i);
	}
	
	public boolean isIn(Vertex v){
		for(int i = 0; i < this.getLen(); i++){
			if(this.lis[i].getInd() == v.getInd()){
				return true;
			}
		}
		return false;
	}
	
	public String toString(){
		String ret = "";
		for(int i = 0; i < this.getLen(); i++){
			ret = ret + "\n" + this.distTo[this.remove().getInd()];
		}
		return ret;
	}
	
}
