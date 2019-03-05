import java.io.PrintWriter;
import java.util.ArrayList;

public class Assignment3 {
	private static ArrayList<String> words = new ArrayList<String>();
	private static MinHeap edgeHeap;
	private static Graph g;
	private static int[] compList;
	private static final int clusterNum = 2;
	private static PrintWriter wr;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try{
			wr = new PrintWriter("clusters.txt", "UTF-8");
			
		}
		catch(Exception e){
			System.out.println("Prinwriter exception occured");
			System.exit(1);
		}
		
		Trie root = InputTaker.createTrie("wordVec.txt", words);//Creates a trie that contains all strings and corresponding
// wordVecs

		
		//edgeHeap = InputTaker.createEdgeHeap("sample1_word_pairs.txt", root);//Creates edgeList with respect to input word_pairs
		
		edgeHeap = InputTaker.createCompEdgeHeap("word_pairs.txt", root);//creates complete graph with respect to
// vertices in words		
		words = InputTaker.reIndex(root, edgeHeap, words);//remakes words lists and edgeList so that it only contains
		// vertices that is in edgeList
		int gMaxSize = words.size() * 3;
		int gMinSize = words.size() * 1/10;
		MinHeap inpHeap = new MinHeap(gMaxSize + gMinSize);//Creates new edgeHeap to obtain reasonable output
		
		for(int i = 0; i < gMinSize; i++)
			inpHeap.add(edgeHeap.deleteMin());

		for(int i = 0; i < gMaxSize; i++)
			inpHeap.add(edgeHeap.deleteMax());

		
		edgeHeap = inpHeap;			
		edgeHeap = edgeHeap.makeMST(words.size());//Creates MST.
		edgeHeap.makeClusters(clusterNum);//Makes clusters with respect to clusterNum
		g = new Graph(words.size(), edgeHeap);//Creates Graph
		compList = g.findComponent();//Finds components of the graph(clusters so to say).
		
		//Makes the output file
		for(int j = 0; j < clusterNum; j++){
			for(int i = 0 ; i < compList.length; i++){
				if(compList[i] == j ){
					wr.print(words.get(i) + " ");
				}
			}
			wr.println();
		}
		wr.close();
		
	}



}
