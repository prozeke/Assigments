public class MinHeap {
	Edge[] lis;
	int len;
	int pos;

	public MinHeap(int len){
		this.len = len;
		this.lis = new Edge[len];
		for(int i = 0; i < len; i++)
			this.lis[i] = null;
		this.pos = -1;
		
	}
	
	
	public int getLen(){
		return this.pos + 1;
		
	}
	
	public Edge getMin(){
		return this.lis[0];
	}
	
	public Edge[] getLis(){
		if(this.pos == -1)
			return null;
		else{
			Edge[] ret = new Edge[this.pos + 1];
			for(int i = 0; i < this.pos + 1; i++){
				ret[i] = this.lis[i];
			}
			return ret;
		}
	}
	
	public boolean isFull(){
		return this.pos == (this.len - 1);
		
	}
	
	public boolean isEmpty(){
		return this.pos == -1;
	}
	
	public float maxIndex(){
		if(this.isEmpty())
			return -1;
		float max = this.lis[0].getWeight();
		for(int i = 0; i < this.len && this.lis[i] != null; i++){
			if(this.lis[i].getWeight() > max)
				max = this.lis[i].getWeight();
		}
		return max;
	}
	
	public void swap(int i, int j){
		Edge temp = this.lis[i];
		this.lis[i] = this.lis[j];
		this.lis[j] = temp;
		return;
		
	}
	
	public void sink(int i){
		int k = 2*i + 1;
		while(k < this.getLen()){
			if(k + 1 <= this.pos && this.lis[k + 1].getWeight() < this.lis[k].getWeight())
				k = k + 1;
			if(this.lis[k].getWeight() < this.lis[i].getWeight()){
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
	
	public void swim(int i){
		int k;
		while(i > 0){
			k = (i-1) / 2;
			if(this.lis[i].getWeight() < this.lis[k].getWeight()){
				this.swap(i, k);
				i = k;
			}
			else
				return;
		
		}
		
	}


	public void add(Edge h){
		if(!this.isFull()){
			this.pos++;
			this.lis[this.pos] = h;
			this.swim(this.pos);

		}
		else
			return;
	}


	public Edge deleteMin(){
		if(!this.isEmpty()){
			this.swap(0, this.pos);
			this.pos--;
			this.sink(0);
			return this.lis[ (this.pos) + 1 ];
		}
		else
			return null;
	
	}

	public Edge deleteMax(){
		Edge max = this.lis[0];
		Edge e;
		int i = 0;
		int l = 0;
		while( i <  this.getLen()){
			e = this.lis[i];
			if (e.getWeight() > max.getWeight()){
				max = e;
				l = i;
			}
			i++;
		}
		this.swap(l, this.pos );
		this.pos--;
		return max;
}
	
	
	public String toString(){
		String ret = "";
		int num = 0;
		for(int i = 0; i < this.pos + 1; i++){
			ret = ret + this.lis[i].toString() + "\n";
			num++;
		}
		System.out.println("len = " + num);
		return ret;
	}

	public MinHeap makeMST(int v){
		MinHeap ret;
		Union u = new Union(v);
		Edge min;
		int v1, v2,i = 0;
		if(v < 1)
			return null;
		ret = new MinHeap(v - 1);
		while(i < v-1  && !this.isEmpty()){
			if(this.isEmpty())
				System.out.println("EdgeList is EMPTY!!");
			min = this.deleteMin();
			v1 = min.getFirst();
			v2 = min.getSec();
			if(!u.isCycle(v1, v2)){
				//System.out.println("ADDED!!!\t first: "+ min.getFirst() + "\tsec: " + min.getSec() + "\tweight: " + min.getWeight());

				u.union(v1, v2);
				ret.add(min);
				//System.out.printf("%f ----- %f\n",ret.getMin().getWeight(), min.getWeight());
				i++;
			}
			else{
			//	System.out.println("Cycle!!!\t first: "+ min.getFirst() + "\tsec: " + min.getSec() + "\tweight: " + min.getWeight());
			}
		}
		
		return ret;
	}
	
	public void makeClusters(int num){
		for(int i = 0; i < num - 1; i++){
			this.deleteMin();
			
		}		
		
		return;
	}
	
	
	
	
	
	
	
	
	
	
}