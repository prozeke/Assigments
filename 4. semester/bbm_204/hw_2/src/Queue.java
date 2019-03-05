
public class Queue {

	Data[] q;
	int front;
	int back;
	int len;

	public Queue(int x){
		this.q = new Data[x];
		for(int i = 0; i < x ; i++)
			this.q[i] = null;
		this.len = x ;
		this.front = 0;
		this.back = 0;
	}
	
	
	
	public boolean isEmpty(){
		if(this.q[this.front] == null)
			return true;
		else
			return false;
		
	}
	
	public boolean isFull(){
		if(this.isEmpty())
			return false;
		if( this.back  == this.front)
			return true;
		else
			return false;
		
	}
	
	public boolean enqueue(Data d){
		if(this.isFull())
			return false;
		this.q[this.back] = d;
		this.back = (this.back + 1) % this.len;
		return true;
		
	}
	
	public Data dequeue(){
		if(this.isEmpty())
			return null;
		Data ret = this.q[this.front];
		this.front = (this.front + 1) % this.len;
		return ret;
		
		
	}
	
	public void requeue(){
		Data d = this.dequeue();
		d.resetChance();
		this.enqueue(d);
		return;
			
	}
		
	public Data getFront(){
		if(this.isEmpty())
			return null;
		return this.q[this.front];
	}
	
	public Data fifoAdd(Data d){
		if(!this.isFull()){
			this.enqueue(d);
			return null;
		}
		Data del = this.dequeue();
		this.enqueue(d);
		
		return del;
	
	}
	
	public Data scaAdd(Data d, Node n){
		if(!this.isFull()){
			this.enqueue(d);
			return null;
		}
		if( (this.q[this.front]).getChance()){
			n.add( (this.q[this.front]).getC() );
			this.requeue();
			return this.scaAdd(d, n);
			
		}
		Data del = this.dequeue();
		this.enqueue(d);
		return del;
		
	}
	
	public String toString(){
		String ret = "";
		int i = 0;
		if(this.isEmpty())
			return ret;
		if(this.isFull())
			i = (this.back + 1) % this.len;
		for(i = 0; i < this.len; i = (i + 1)){
			if(this.q[i] == null)
				return ret;
			
			ret = ret + " " + this.q[i].getC();
			//if(i == this.back)
				//return ret;
		}
		
		return ret;
	}
	


}






