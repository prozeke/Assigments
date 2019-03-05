import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class InputTaker {
	
	public static LinkedNode makeGraph(String path, HashMap<String, Integer> map, Graph g){
		LinkedNode vertices = null;
		File inp = new File(path);
		String line;
		String[] linp;
		int ind = 0;
		String sfrom;
		String sSwc;
		int from;
		int to;
		int swc;
		Vertex v;
		try{
			Scanner sc = new Scanner(inp);
			while(sc.hasNextLine()){
				line = sc.nextLine();
				line = line.trim();
				if(line.length() == 0)
					break;
				linp = line.split(">");
				sSwc = linp[1];
				if(!map.containsKey(sSwc)){
					map.put(sSwc, ind);
					v = new Vertex(sSwc, ind++, -1);
					vertices = LinkedNode.add(vertices, v);
					g.addVertex(v);
				}
				swc = map.get(sSwc);
				linp = linp[0].split(":");
				sfrom = linp[0];
				if(!map.containsKey(sfrom)){
					map.put(sfrom, ind);
					v = new Vertex(sfrom, ind++,-1);
					vertices = LinkedNode.add(vertices, v);
					g.addVertex(v);
				}
				from = map.get(sfrom);
				g.getVertex(from).setSwc(swc);
				linp = linp[1].split(",");
				for(String s : linp){
					if(!map.containsKey(s)){
						map.put(s, ind);
						v = new Vertex(s, ind++,-1);
						vertices = LinkedNode.add(vertices, v);
						g.addVertex(v);
					}
					to = map.get(s);
					g.addEdge(from, to, 0);
				}
				
			}
			sc.close();

		}
		catch(FileNotFoundException noFile){
			System.out.println("FileNotFound error in InputTaker -> MakeGraph");
			System.exit(1);
		}
/*
		catch(Exception ex){
			System.out.print("Unknown error in InputTaker -> MakeGraph");
			System.out.print(ex);
			System.exit(1);
		}
*/	
		return vertices;
	}
	
	public static void getLens(String path, HashMap<String, Integer> map, Graph g){
		File inp = new File(path);
		String line;
		String[] linp;
		int ind1, ind2;
		float len;
		try{
			Scanner sc = new Scanner(inp);
			while(sc.hasNextLine()){
				line = sc.nextLine();
				line = line.trim();
				if(line.length() == 0)
					break;
				linp = line.split(" ");	
				len = Float.parseFloat(linp[1]);
				linp = linp[0].split("-");
				if(!map.containsKey(linp[0]) || !map.containsKey(linp[1])){
					System.out.println("Nonexisting vertex error in InputMaker in getLens!!!");
				}
				ind1 = map.get(linp[0]);
				ind2 = map.get(linp[1]);
				g.changeEdgeLen(ind1, ind2, len);
				
			}
			sc.close();
		}
		catch(FileNotFoundException noFile){
			System.out.println("FileNotFound error in InputTaker -> getLens");
			System.exit(1);
		}
		catch(Exception ex){
			System.out.println("Unknown error in InputTaker -> getLens");
			System.out.println(ex);
			System.exit(1);
		}
	}
	
	public static void executeCommands(String path, HashMap<String, Integer> map, LinkedNode vertices, Graph g){
		File inp = new File(path);
		String line;
		String[] linp;
		int ind,from,to;
		try{
			Scanner sc = new Scanner(inp);
			while(sc.hasNextLine()){
				line = sc.nextLine();
				line = line.trim();
				linp = line.split(" ");
				switch(linp[0]){
					case "MAINTAIN":
						ind = map.get(linp[1]);
						System.out.print("COMMAND IN PROCESS >> MAINTAIN " + linp[1] + "\n");
						OutputMaker.maintain(g, ind);
						System.out.print("\tCommand \"" + line+ "\"  has been executed successfully!\n");
						break;
					case "SERVICE":
						ind = map.get(linp[1]);
						System.out.print("COMMAND IN PROCESS >> SERVICE " + linp[1] + "\n");
						OutputMaker.service(g, ind);
						System.out.print("\tCommand \"" + line+ "\"  has been executed successfully!" + "\n");
						break;
					case "BREAK":
						linp = linp[1].split(">");
						from = map.get(linp[0]);
						to =  map.get(linp[1]);
						System.out.print("COMMAND IN PROCESS >> BREAK "+ linp[0] + ">" + linp[1] + "\n");
						OutputMaker.breakEdge(g, from, to);
						System.out.print("\tCommand \"" + line+ "\"  has been executed successfully!" + "\n");
						break;
					case "REPAIR":
						linp = linp[1].split(">");
						from = map.get(linp[0]);
						to =  map.get(linp[1]);
						System.out.print("COMMAND IN PROCESS >> REPAIR "+ linp[0] + ">" + linp[1] + "\n");
						OutputMaker.repair(g, from, to);
						System.out.print("\tCommand \"" + line+ "\"  has been executed successfully!" + "\n");
						break;
					case "ROUTE":
						float velocity = Float.parseFloat(linp[2]);
						String vel = linp[2];
						linp = linp[1].split(">");
						from = map.get(linp[0]);
						to =  map.get(linp[1]);
						System.out.print("COMMAND IN PROCESS >> ROUTE "+ linp[0] + ">" + linp[1] + " " + vel + "\n");
						OutputMaker.route(g, from, to, Assignment4.swcTime, velocity);
						System.out.print("\tCommand \"" + line+ "\"  has been executed successfully!" + "\n");
						break;
					case "ADD":
						String name = linp[1];
						map.put(name, g.getV());
						System.out.print("COMMAND IN PROCESS >> ADD "+ name + "\n");
						vertices = OutputMaker.addVertex(g, vertices, name, g.getV());
						System.out.print("\tCommand \"" + line+ "\"  has been executed successfully!" + "\n");
						break;
					case "LINK":
						String ret = linp[1];
						System.out.print("COMMAND IN PROCESS >> LINK " + ret + "\n");
						linp = linp[1].split(">");
						String[] slinp;
						int swc = map.get(linp[1]);
						linp = linp[0].split(":");
						from = map.get(linp[0]);
						linp = linp[1].split(",");
						int[] tos = new int[linp.length];
						float[] lens = new float[linp.length];
						for(int i = 0; i < linp.length; i++){
							slinp = linp[i].split("-");
							tos[i] = map.get(slinp[0]);
							lens[i] = Float.parseFloat(slinp[1]);
						}
						OutputMaker.link(g, from, tos, lens, swc);
						System.out.print("\tCommand \"" + line+ "\"  has been executed successfully!" + "\n");
						break;
					case "LISTROUTESFROM":
						from = map.get(linp[1]);
						System.out.print("COMMAND IN PROCESS >> LISTROUTESFROM " + linp[1] + "\n");
						OutputMaker.listRoutesFrom(g, from);
						System.out.print("\tCommand \"" + line+ "\"  has been executed successfully!" + "\n");
						break;
					case "LISTMAINTAINS":
						System.out.print("COMMAND IN PROCESS >> " + line + "\n");
						OutputMaker.listMaintains(vertices);
						System.out.print("\tCommand \"" + line+ "\"  has been executed successfully!" + "\n");
						break;
					
					case "LISTACTIVERAILS":
						System.out.print("COMMAND IN PROCESS >> " + line + "\n");
						OutputMaker.listActiveRails(vertices);
						System.out.print("\tCommand \"" + line+ "\"  has been executed successfully!" + "\n");
						break;
					case "LISTBROKENRAILS":
						System.out.print("COMMAND IN PROCESS >> " + line + "\n");
						OutputMaker.listBrokenRails(vertices);
						System.out.print("\tCommand \"" + line+ "\"  has been executed successfully!" + "\n");
						break;
					case "LISTCROSSTIMES":
						System.out.print("COMMAND IN PROCESS >> " + line + "\n");
						OutputMaker.listCrossTimes(vertices);
						System.out.print("\tCommand \"" + line+ "\"  has been executed successfully!" + "\n");
						break;
					case "TOTALNUMBEROFJUNCTIONS":
						System.out.print("COMMAND IN PROCESS >> " + line + "\n");
						System.out.print("\tTotal # of junctions: " + g.getV()+ "\n");
						System.out.print("\tCommand \"TOTALNUMBEROFJUNCTIONS\"  has been executed successfully!" + "\n");
						break;
					case "TOTALNUMBEROFRAILS":
						System.out.print("COMMAND IN PROCESS >> " + line + "\n");
						System.out.print("\tTotal # of rails: " + g.getE()+ "\n");
						System.out.print("\tCommand \"TOTALNUMBEROFRAILS\"  has been executed successfully!" + "\n");
						break;
					default:
						System.out.print("COMMAND IN PROCESS >> " + linp[0] + "\n");
						System.out.print("\tUnrecognized command \"" + linp[0] + "\"!" + "\n");
						break;
				}
			}
			
			sc.close();
		}
		catch(FileNotFoundException noFile){
			System.out.println("FileNotFound error in InputTaker -> executeCommands");
			System.exit(1);
		}

	
	}

	
	
	
	
	
	

}
