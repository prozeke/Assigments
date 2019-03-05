import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.ArrayIndexOutOfBoundsException;
import java.util.Random;
import java.io.PrintWriter;


public class Assignment1 {
	
	private static ArrayList<String> flows = new ArrayList<String>();
	private static Flow[] flowToSort;
	private static int rowNum = 0;
	private static String dataPath;
	private static int featureIndex;
	private static Flow[] control;
	private static String features;
	
	public static void shuffle(Flow[] flows){
		Random rand = new Random();
		int n;
		for(int i = 0; i < flows.length; i++){
			 n = rand.nextInt(flows.length - 1);
			 swap(flows, i ,n);
			
		}
		
		
	}
	
	public static boolean isSorted(Flow[] flows){
		for(int i = 0; i < flows.length - 1; i++){
			if(flows[i].getData() > flows[i+1].getData() )
				return false;
			
		}
		return true;
	}
	
	public static void swap(Flow[] flows, int i, int j){
		
		Flow temp = flows[i];
		flows[i] = flows[j];
		flows[j] = temp;
		
	}
	
	public static void insertionSort(Flow[] flows){
		int len = flows.length;
		for(int i = 1; i < len; i++){
			
			for(int j = i; flows[j].less(flows[j -1]) ; j-- ){
				swap(flows, j, j - 1);
				if(j - 1 == 0) break;
			}
			
		}
	}
	
	public static int[] partition(Flow[] flows, int left, int right){
			int hi,lo,j;
			hi = right -1;
			lo = hi;
			j = left + 1;
			int c = 0;

			while(j < lo){
				c++;
				if(flows[j].less(flows[hi])){
					j++;
					
				}	
				else if(flows[j].equals(flows[hi])){
					swap(flows,j,lo - 1);
					lo--;
				}
				else{
					swap(flows,lo - 1, hi);
					if(lo - 1 != j)
						swap(flows, j, hi);
					hi--;
					lo--;
					
				}
			
			}
		
		int[] arr = new int[2];
		arr[0] = lo;
		arr[1] = hi;
		return arr;
	}
	
	
	public static void quickSort(Flow[] flows, int left, int right){
		int i;
		if( (left + 1) == (right))
				return;
		int[] arr = partition(flows,left,right);
		quickSort(flows,left, arr[0]);
		quickSort(flows, arr[1], right);
	}
	
	public static void qSort(Flow[] flows){
		shuffle(flows);
		int left = -1;
		int right = flows.length;
		quickSort(flows,left,right);
		return;
		
	}
	
	public static void addToHeap(Flow[] arr, int l){
		while(l > 0){
			if(arr[l].getData() > arr[(l - 1)/2].getData()){
				swap(arr, l, (l - 1)/2);
				l = (l - 1)/2;
			}
			else
				break;
			
		}
		
		
	}
	
	
	public static void deleteFromHeap(Flow arr[],int l){
		
			swap(arr, 0, l);
			l--;
			int j = 0;
			int k = 2*j + 1;
			while(k <= l){
				if(k + 1 <= l){
					if(arr[k].less(arr[k +1])){
						k = k + 1;
					}
				}
				if(arr[j].less(arr[k]))
					swap(arr,j,k);
			
				j = k;
				k = 2*j + 1;
			}
	}
	
	
	public static void heapSort(Flow[] arr){
		
		for(int i = 0; i < arr.length; i++){
			addToHeap(arr,i);
			
		}
		for(int i = arr.length - 1; i > 0; i--){
			deleteFromHeap(arr, i);
		}
	
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		
		
		try{
			dataPath = args[0];
			featureIndex = Integer.parseInt(args[1]);
			featureIndex--;
			if(!args[2].equals("T"))
				System.exit(1);
		}
		catch(ArrayIndexOutOfBoundsException indexout){
			
			System.out.println("Error! Not enough argument");
			System.exit(1);
		}
		try{
			File file = new File(args[0]);
			Scanner input = new Scanner(file);
			features = input.nextLine();
			
			for(int i = 0; input.hasNextLine(); i++){

				flows.add(input.nextLine());
				rowNum++;
			}
					
		
		
		
		}
		catch(FileNotFoundException ex1){
			System.out.println("File not found!");
			System.exit(1);
			
			
		}
		catch(Exception ex){
			
			System.out.println("Unknown exception!");
			System.exit(1);

		}
		flowToSort = new Flow[rowNum];
		String str;
		for(int i = 0; i < rowNum ; i++){
			str = flows.get(i);
			flowToSort[i] = new Flow(i,Double.parseDouble(str.split(",")[featureIndex]) );
			
		}
		//long sTime = System.currentTimeMillis();
		//insertionSort(flowToSort);
		qSort(flowToSort);
		//heapSort(flowToSort);
		/*long eTime = System.currentTimeMillis();
		System.out.println(eTime - sTime);
		if(isSorted(flowToSort))
			System.out.println("Done!!!!!!!!!!!!!!!!");
		else
			System.out.println("STILL NOT SORTED!!");
		for(Flow d : flowToSort )
			d.display();*/
		try{
			PrintWriter pw = new PrintWriter(new File(args[0]));
			pw.write(features + "\n" );
			for(Flow d : flowToSort){
				pw.write(flows.get(d.getIndex()) + "\n" );
				
			}
		
			pw.close();
			
		}
		catch(FileNotFoundException second){
			
			System.out.println("File not found for writing");
			
		}
	
	}

	
}
