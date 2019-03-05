
public class Union {

	public int[] lis;
	
	public Union(int len){
		
		this.lis = new int[len];
		for(int i = 0; i < len; i++){
			this.lis[i] = -1;
		}
		
		return;
	}
	
	
	public int parent(int i){
		int ret = -1;
		while(this.hasParent(i)){
			if(this.lis[i] == i){

				System.out.println(i + " " + this.lis[i]);
				System.out.println("same parent error in union");
				System.out.println(this.lis[i]);
				System.exit(1);
			}
			i = this.lis[i];
			ret = i;
		}
		return ret;
	}
	
	public boolean hasParent(int i){
		
		return this.lis[i] != -1;
	}
	
	
	public void union(int i, int j){
		int len = this.lis.length;
		if(i == j){
			System.out.println("Error i == j in union");
			return;
		}
		boolean iHas = this.hasParent(i);
		boolean jHas = this.hasParent(j);

		if(i >= len || j >= len){
			System.out.println("one of the vertecies is not in union lis");
			return;
		}
		if(!iHas && !jHas){
			this.lis[j] = i;
			return;
		}
		else if(!iHas){
			this.lis[i] = this.parent(j);
			return;
		}
		else if(!jHas){
			this.lis[j] = this.parent(i);
			return;
		}
		else{
			this.lis[this.parent(i)] = this.parent(j);
			return;
		}
	
	}
	
	public boolean isCycle(int i, int j){
		if(i == j){
			System.out.println("Same vertices in isCycle in union");
			return false;
		}

		if(this.parent(i) == j)
			return true;
		if(this.parent(j) == i)
			return true;
		if(!this.hasParent(i) || !this.hasParent(j))
			return false;
		return this.parent(i) == this.parent(j);
	
	}
	
	
	
}
