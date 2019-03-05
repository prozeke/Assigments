
public class Heap {
	HeapData[] lis;
	int len;
	int pos;

	public Heap(int len){
		this.len = len;
		this.lis = new HeapData[len];
		for(int i = 0; i < len; i++)
			this.lis[i] = null;
		this.pos = -1;
		
	}
	
	
	public boolean isFull(){
		return this.pos == (this.len - 1);
		
	}
	
	public boolean isEmpty(){
		return this.pos == -1;
	}
	
	public int maxIndex(){
		if(this.isEmpty())
			return -1;
		int max = this.lis[0].getIndex();
		for(int i = 0; i < this.len && this.lis[i] != null; i++){
			if(this.lis[i].getIndex() > max)
				max = this.lis[i].getIndex();
		}
		return max;
	}
	
	public void swap(int i, int j){
		HeapData temp = this.lis[i];
		this.lis[i] = this.lis[j];
		this.lis[j] = temp;
		return;
		
	}
	
	public void sink(int i){
		int k = 2*i + 1;
		while(k < this.len){
			if(k + 1 <= this.pos && this.lis[k + 1].getC() > this.lis[k].getC())
				k = k + 1;
			if(this.lis[k].getC() > this.lis[i].getC()){
				this.swap(i, k);
				i = k;
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
			if(this.lis[i].getC() > this.lis[k].getC()){
				this.swap(i, k);
				i = k;
			}
			else
				break;
		
		}
		
	}


	public void add(HeapData h){
		if(!this.isFull()){
			this.pos++;
			this.lis[this.pos] = h;
			this.swim(this.pos);
		}
		else
			return;
	}


	public HeapData deleteMax(){
		if(!this.isEmpty()){
			this.swap(0, this.pos);
			this.pos--;
			return this.lis[ (this.pos) + 1 ];
		}
		else
			return null;
	
	}

	public String toString(){
		HeapData h;
		String ret = "";
		char[] clis = new char[this.len];
		for(int i = 0; i < this.len;i++ )
			clis[i] = '!';
		for(int i = 0; i < this.len; i++){
			h = this.lis[i];
			if(h == null)
					break;
			clis[h.getIndex()] = h.getC();
		}
		for(int i = 0; i < this.len; i++){
			if(clis[i] == '!')
				break;
			ret = ret + " " + clis[i]; 
		}
		ret = ret.trim();
		return ret;
	}

	
	public Data pAdd(Data d){
		if(!this.isFull()){
			int max = this.maxIndex();
			max++;
			HeapData h = new HeapData(d, max);
			this.add(h);
			return null;
			
		}
		else{
			HeapData hdel = this.deleteMax();
			HeapData n = new HeapData(d.getC(), hdel.getIndex());
			this.add(n);
			return new Data(hdel);
			
		}
			
	
		
	}
	
	
	
}
