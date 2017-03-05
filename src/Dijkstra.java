import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * This class implements Dijkstra's algorithm for a adjacency list representation of a graph
 *
 * @author dardeshna
 */
public class Dijkstra {

	public HashMap<String, String> path;
	public HashMap<String, Integer> distance;
	public String startingNode;

	/**
	 * Creates a new Dijkstra object from a Graph
	 * @param g the Graph
	 * @param startingNode the starting node
	 */
	public Dijkstra(Graph g, String startingNode) {
		this(g.graph, startingNode);
	}
	
	/**
	 * Creates a new Dijkstra object from a HashMap representing a graph
	 * @param graph the HashMap representing a graph
	 * @param startingNode the starting node
	 */
	public Dijkstra(HashMap<String, HashMap<String, Integer>> graph, String startingNode) {

		if (!graph.containsKey(startingNode)) throw new RuntimeException("The graph does not have the starting node!");
		if (!validGraph(graph)) throw new RuntimeException("Invalid graph");
		
		this.startingNode = startingNode;
		
		//previous node
		path = new HashMap<String, String>();

		//distance from starting node
		distance = new HashMap<String, Integer>();

		//set distance to -1 as placeholder for infinity
		for (String s: graph.keySet()) distance.put(s, -1);

		//set the distance of the starting node to 0
		distance.put(startingNode, 0);

		//create an array of nodes to mess around with
		ArrayList<String> nodes = new ArrayList<String>(graph.keySet());

		//while not all the nodes have been messed with
		while (nodes.size() > 0) {

			//find closest node
			String closestNode = null;
			for (String node : nodes) {
				if (closestNode == null || (distance.get(closestNode) > distance.get(node) && distance.get(node) >= 0)) {
					closestNode = node;
				}
			}

			//remove it
			nodes.remove(closestNode);

			//get array of the adjacent nodes
			String[] adjacentNodes = new String[graph.get(closestNode).keySet().size()];
			graph.get(closestNode).keySet().toArray(adjacentNodes);

			//for each adjacent node, if the distance through the selected node is less, switch the previous node and distance
			for (String adjacent: adjacentNodes) {				
				if (distance.get(closestNode) + graph.get(closestNode).get(adjacent) < distance.get(adjacent) || distance.get(adjacent) == -1) {
					path.put(adjacent, closestNode);
					distance.put(adjacent, distance.get(closestNode) + graph.get(closestNode).get(adjacent));
				}
			}
		}
	}

	/**
	 * Returns whether a HashMap represents a valid graph
	 * @param graph the HashMap
	 * @return whether it represents a valid graph
	 */
	public boolean validGraph(HashMap<String, HashMap<String, Integer>> graph) {
		for (String node : graph.keySet()) {
			for (Entry<String, Integer> entry : graph.get(node).entrySet()) {
				if (!graph.containsKey(entry.getKey())) return false;
			}
		}
		return true;
	}
	
}
