import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Main {

	private static boolean fifo;
	private static boolean sca;
	private static boolean priorityQ;
	private static int queueSize = 0;
	private static char page;
	private static Data del;
	private static int faultNum = 0;
	private static Node sChances;
	private static PrintWriter wr;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sca = false;
		fifo = false;
		priorityQ = false;
		Node order = new Node('!');
		try{
			wr = new PrintWriter("output.txt", "UTF-8");
			
		}
		catch(Exception e){
			System.out.println("Prinwriter exception occured");
			System.exit(1);
		}
	
		try{
			String str;
			String[] linp;
			File file = new File(args[0]); 
			Scanner inp = new Scanner(file);
			while(inp.hasNextLine()){
				str = inp.nextLine();
				str = str.trim();
				linp = str.split(" ");
				if(linp[0].equals("Read"))
					order.add( linp[1].charAt(0));
				else if(linp[0].equals("SetMemory")){
					queueSize = Integer.parseInt(linp[1]);
					wr.format("Memory %s", linp[1]);
					wr.println();
				}
				else if(linp[0].equals("setReplacement")){
					if(linp[1].equals("FIFO")){
						fifo = true;
						wr.println("FIFO Page Replacement");
					}
					else if(linp[1].equals("SecondChance")){
						sca = true;
						wr.println("SecondChance Page Replacement");
					}
					else if(linp[1].equals("PriorityQueue")){
						priorityQ = true;
						wr.println("PriorityQueue Page Replacement");
					}
					else{
						fifo = true;
					}
				}
				else if(linp[0].equals("setSearchStructure")){
					if(linp[1].equals("BinarySearch")){
						wr.println("Binary Search Tree");
					}
					else if(linp[1].equals("UnorderedList")){
						wr.println("Unordered Linked List");
					}
					
					else
						wr.println("Binary Search Tree");
				}
			
			
			}
			order = order.getNext();
			inp.close();
		}
		catch(Exception e){
			if(e.getClass().equals(FileNotFoundException.class))
				System.out.println("FileNotFound");
			System.out.println("exception occured");
			System.exit(1);
		}
		
		Bnode root = null;
		Queue q = new Queue(queueSize);
		wr.println();
		
		
		if(fifo){
			while(order != null){
				page = order.getC();
				if(!Bnode.isIn(root, page)){
					Data d = new Data(page);
					del = q.fifoAdd(d);
					if(del != null){
					//	System.out.println(del.getC());
						root = root.delete(del);
					}
					root = Bnode.add(root, d);
					faultNum++;
					wr.format("Page Fault\t%s", q.toString());
					wr.println();
				}
				else{
					wr.format("          \t%s", q.toString());
					wr.println();
				}	
				order = order.getNext();
			}
			
		}
		if(sca){
			while(order != null){
				sChances = new Node('!');
				page = order.getC();
				if(!Bnode.isIn(root, page)){
					Data d = new Data(page);
					del = q.scaAdd(d,sChances);
					if(del != null){
					//	System.out.println(del.getC());
						root = root.delete(del);
					}
					root = Bnode.add(root, d);
					faultNum++;
					if(sChances.getNext() != null){
						wr.format("Page Fault\t%s Second Chance %s", q.toString(), Node.toString(sChances.getNext()));
						wr.println();
					}
					else{
						wr.format("Page Fault\t%s", q.toString());
						wr.println();
					}
				}
				else{
					wr.format("          \t%s", q.toString());
					wr.println();
				}	
				order = order.getNext();
			}			
		}
		int ind = 0;
		if(priorityQ){
			Heap h = new Heap(queueSize);
			while(order != null){
				page = order.getC();
				if(!Bnode.isIn(root, page)){
					Data d = new Data(page);
					del = h.pAdd(d);
					if(del != null){
					//	System.out.println(del.getC());
						root = root.delete(del);
					}
					root = Bnode.add(root, d);
					faultNum++;
					wr.format("Page Fault\t%s", h.toString());
					wr.println();
					
				}
				else{
					wr.format("          \t%s", h.toString());
					wr.println();
				}	
				order = order.getNext();
			}					
									
		}
		
		wr.format("%d",faultNum);
			
			
		
		wr.close();
	
	
	
	}

	


}
