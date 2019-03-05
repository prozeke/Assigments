import java.util.HashMap;

public class Assignment4 {

	private static HashMap<String, Integer> map = new HashMap<String, Integer>();
	private static LinkedNode vertices = null;
	private static Graph g = new Graph();
	public static float swcTime;
	private static String topology;
	private static String distances;
	private static String commands;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		topology = args[0];
		distances = args[1];
		commands = args[2];
		swcTime = Float.parseFloat(args[3]);
		vertices = InputTaker.makeGraph(topology, map,g);
		InputTaker.getLens(distances, map, g);
		InputTaker.executeCommands(commands, map, vertices, g);

	}

}
