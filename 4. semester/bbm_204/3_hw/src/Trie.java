
public class Trie {
	private Trie[] next;
	private WordVec val;
	public Trie(){
		this.next = new Trie[36];
		this.val = null;
		
	}

	private static boolean isIn(char[] clis, char c){
		for(char ch : clis){
			if(ch == c){
				return true;
			}
			
		}
		return false;
	}
	
	private static int mod(char c){
		char[] nums = new char[10]; 
		int ret;
		for(int i = 0; i < 10; i++){
			nums[i] = Integer.toString(i).charAt(0);
		}
		if(isIn(nums, c)){
			ret = 26 + c % 10;
			return ret;
		}
		ret = c % 26;
		return ret;
	}
	
	public void newBranch(int mod){
		mod = mod % 26;
		this.next[mod] = new Trie();
		return;
		
	}

	public static void put(Trie root,String s, WordVec val){
		int len = s.length();
		int mod;
		if(len == 0)	return;
		for(int i = 0; i < len; i++){
			mod = mod(s.charAt(i));
			if(root.next[mod] != null){
				root = root.next[mod];
			}
			else{
				root.next[mod] = new Trie();
				root = root.next[mod];
			}
	
			
		}
		root.val = val;
	}

	private static char intToChar(int i){
		if(i >= (int)'a'){
			String alph = "abcdefghijklmnopqrstuvwxyz";
			i = i + 26 - 19;
			i = i % 26;
			return alph.charAt(i);
		}
		else{
			String nums = "0123456789";
			i = i+10-8;
			i = i % 10;
			return nums.charAt(i);
		}
	
	}
	
	
	public void display(){
		for(int i = 0; i < 36; i++)
			if(this.next[i] != null){
				System.out.printf("%d ------ %c\n", i, intToChar(i));
				this.next[i].display();
			}
				
		
		
	}

	public static WordVec get(Trie root, String s){
		int len = s.length();
		int mod;
		for(int i = 0; i < len; i++ ){
			mod = mod(s.charAt(i));
			if(root.next[mod] == null)
				return null;
			root = root.next[mod];
		}
		return root.val;
	
	}
	
	public void delete(String s){
		Trie root = this;
		int len = s.length();
		int mod;
		for(int i = 0; i < len; i++ ){
			mod = mod(s.charAt(i));
			if(root.next[mod] == null)
				return;
			root = root.next[mod];
		}
		root.val = null;
		return;
		
	}


}
