import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
public class InputTaker {

	private static float[] makeFloatArr(String[] arr){
		float[] ret = new float[arr.length - 1];
		for(int i = 1; i < arr.length; i++){
			ret[i - 1] = Float.parseFloat(arr[i]);
		}
		return ret;
		
	}
	
	private static int lineNum(String str){
		File inp = new File(str);
		int lineNum = 0;
		try{
			Scanner sc = new Scanner(inp);
			while(sc.hasNextLine()){
				if(sc.nextLine().length() != 0)
					lineNum++;
				else{
					sc.close();
					break;
				}
			}
			sc.close();
		
		}
		catch(FileNotFoundException fex){
			System.out.println("FileNotFoundException in lineNum");
			System.exit(1);
		}
		catch(Exception ex){
			System.out.println("Exception in lineNum");
			System.exit(1);
		}
		return lineNum;
	} 
	
	public static Trie createTrie(String path, ArrayList<String> words){
		Trie root = new Trie();
		File inp = new File(path);
		int index = 0;
		String[] linp;
		String line;
		float[] vec;
		WordVec v;
		try{
			Scanner sc = new Scanner(inp);
			while(sc.hasNextLine()){
				line = sc.nextLine();
				line = line.trim();
				linp = line.split(" ");
				linp[0] = linp[0].replaceAll("\"", "");
				words.add(linp[0]);
				vec = makeFloatArr(linp);
				v = new WordVec(index,vec);
				Trie.put(root, linp[0], v);
				index++;
			}
			
			sc.close();
		}
		catch(FileNotFoundException fex){
			System.out.println("FileNotFoundException in createTrie");
			System.exit(1);
		}
		catch(Exception ex){
			System.out.println("Exception in createTrie");
			System.exit(1);
		}
	
		return root;
	}


	public static MinHeap createEdgeHeap(String path, Trie root){
		int len = lineNum(path);
		String line;
		String[] linp;
		WordVec v1,v2;
		MinHeap ret = new MinHeap(len);
		File inp = new File(path);
		try{
			Scanner sc = new Scanner(inp);
			while(sc.hasNextLine()){
				line = sc.nextLine();
				line = line.trim();
				if(line.length() != 0){
					linp = line.split("-");
					v1 = Trie.get(root, linp[0]);
					v2 = Trie.get(root, linp[1]);
					if(v1 == null || v2 == null){
						System.out.println("no string has found when creating EdgeHeap!!");
						continue;
					}
					Edge e = new Edge(v1.getIndex(), v2.getIndex(), v1.cosSim(v2));
					ret.add(e);
					
				}
				else{
					sc.close();
					break;
				}
				
			}
			sc.close();
		}
		catch(FileNotFoundException fex){
			System.out.println("FileNotFoundException in createEdgeHeap");
			System.exit(1);
		}
		catch(Exception ex){
			System.out.println("Exception in createEdgeHeap");
			System.exit(1);
		}
		return ret;
	}

	
	
	public static MinHeap createCompEdgeHeap(String path, Trie root){
		WordVec v1,v2;
		MinHeap ret;
		File inp = new File(path);
		String line;
		String[] linp;
		ArrayList<String> words = new ArrayList<String>();
		try{
			Scanner sc = new Scanner(inp);
			while(sc.hasNextLine()){
				line = sc.nextLine();
				line = line.trim();
				if(line.length() != 0){
					linp = line.split("-");
					if(!words.contains(linp[0]))
						words.add(linp[0]);
					if(!words.contains(linp[1]))
						words.add(linp[1]);
				}
				else{
					sc.close();
					break;
				}
			}
			sc.close();
		}
		catch(FileNotFoundException fex){
			System.out.println("FileNotFoundException in createEdgeHeap");
			System.exit(1);
		}
		catch(Exception ex){
			System.out.println("Exception in createEdgeHeap");
			System.exit(1);
		}
		int len = words.size();
		ret = new MinHeap(len*len - ((len+ 1) * len) / 2 );
		String s1,s2;
		for(int i = 0; i < len-1; i++){
			for(int j = i+1; j < len; j++){
				s1 = words.get(i);
				s2 = words.get(j);
				v1 = Trie.get(root, s1);
				v2 = Trie.get(root, s2);
				Edge e = new Edge(v1.getIndex(), v2.getIndex(), v1.cosSim(v2));
				ret.add(e);
			}
		}
		
		return ret;
		
	}
	
	
	public static ArrayList<String> reIndex(Trie root, MinHeap edgeList, ArrayList<String> words){
		ArrayList<String> ret = new ArrayList<String>();
		Edge[] lis = edgeList.getLis();
		int i = 0,f,s;
		WordVec v;
		String str;
		for(Edge e : lis){
			f = e.getFirst();
			s = e.getSec();
			str = words.get(f);
			v = Trie.get(root, str);
			if(v.getIndex() >= i){
				ret.add(str);
				v.setIndex(i);
				e.setFirst(i++);
			}
			else if(!words.get(f).equals(ret.get(v.getIndex()))){
				ret.add(str);
				v.setIndex(i);
				e.setFirst(i++);
			}
			else{
				e.setFirst(v.getIndex());
			}
			str = words.get(s);
			v = Trie.get(root, str);
			if(v.getIndex() >= i){
				ret.add(str);
				v.setIndex(i);
				e.setSec(i++);
			}
			else if(!words.get(s).equals(ret.get(v.getIndex()))){
				ret.add(str);
				v.setIndex(i);
				e.setSec(i++);
			}
			else{
				e.setSec(v.getIndex());
			}
		}
		return ret;
	
	}






}
