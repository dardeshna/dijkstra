import java.util.HashMap;
import java.util.Map.Entry;

/**
 * This class is a basic implementation of a Graph with methods to create and delete
 * nodes and edges, as well as return the distances between nodes
 * 
 * @author dardeshna
 */
public class Graph {

	public HashMap<String, HashMap<String, Integer>> graph;
	private boolean directed;

	/**
	 * Creates a new Graph
	 * @param directed whether the graph is directed or undirected
	 */
	public Graph(boolean directed) {
		this.directed = directed;
		graph = new HashMap<String, HashMap<String, Integer>>();
	}
	
	/**
	 * Returns whether a node exists
	 * @param node the node
	 * @return whether it exists
	 */
	public boolean nodeExists(String node) {
		return graph.containsKey(node);
	}

	/**
	 * Adds a new node
	 * @param node the node
	 */
	public void addNode(String node) {
		if (!graph.containsKey(node))
			graph.put(node, new HashMap<String, Integer>());
	}
	
	/**
	 * Deletes a node if it exists
	 * @param node the node
	 */
	public void deleteNode(String node) {
		if (nodeExists(node)) {
			if (!directed) {
				for(String toDelete : graph.get(node).keySet()) {
					graph.get(toDelete).remove(node);
				}
			}
			graph.get(node).clear();
		}
		else throw new RuntimeException("Nonexistent node");
	}
	
	/**
	 * Returns whether an edge between two nodes exists
	 * @param node1 the first node
	 * @param node2 the second node
	 * @return whether there is an edge
	 */
	public boolean edgeExists(String node1, String node2) {
		if (nodeExists(node1) && nodeExists(node2))
			return graph.get(node1).containsKey(node2);
		else throw new RuntimeException("Nonexistent node");
	}

	/**
	 * Adds an edge between two nodes
	 * @param node1 the first node
	 * @param node2 the second node
	 * @param distance the distance between the nodes
	 */
	public void addEdge(String node1, String node2, int distance) {
		if (nodeExists(node1) && nodeExists(node2) && distance > 0) {
			graph.get(node1).put(node2, distance);
			if (!directed)
				graph.get(node2).put(node1, distance);
		}
		else if (distance <= 0) throw new RuntimeException("Distance is not a positive integer");
		else throw new RuntimeException("Nonexistent node");
	}

	/**
	 * Deletes an edge between two nodes if it exists
	 * @param node1 the first node
	 * @param node2 the second node
	 */
	public void deleteEdge(String node1, String node2) {
		if (edgeExists(node1, node2)) {
			graph.get(node1).remove(node2);
			if (!directed)
				graph.get(node2).remove(node1);
		}
		else throw new RuntimeException("Nonexistent edge");
	}	
	
	/**
	 * Returns the distance between two nodes linked by an edge, otherwise -1
	 * @param node1 the first node
	 * @param node2 the second node
	 * @return the distance
	 */
	public int distance(String node1, String node2) {
		if (edgeExists(node1, node2))
			return graph.get(node1).get(node2);
		else return -1;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for (String node : graph.keySet()) {
			
			s.append(node + ": ");
			
			for (Entry<String, Integer> entry : graph.get(node).entrySet()) {
				s.append(entry.getKey() + " (" + entry.getValue() + "), ");
			}
			s.append("\n");
		}

		return s.toString();
	}

	public static void main(String args[]) {
		Graph g = new Graph(false);
		g.addNode("a");
		g.addNode("b");
		g.addNode("c");
		g.addNode("d");
		g.addNode("e");
		g.addNode("f");
		g.addNode("g");

		g.addEdge("a", "b", 3);
		g.addEdge("a", "c", 5);
		g.addEdge("a", "d", 6);
		g.addEdge("b", "d", 2);
		g.addEdge("c", "d", 2);
		g.addEdge("c", "e", 6);
		g.addEdge("c", "f", 3);
		g.addEdge("c", "g", 7);
		g.addEdge("d", "f", 9);
		g.addEdge("e", "f", 5);
		g.addEdge("e", "g", 2);
		g.addEdge("f", "g", 1);
		
		System.out.println("Test Graph: \n" + g.toString());
		System.out.println("Distance from d to f: " + g.distance("d", "f"));
		Dijkstra dijkstra = new Dijkstra(g.graph, "a");
		System.out.println("Shortest path: " + dijkstra.path);
		System.out.println("Shortest path from a to g: " + DijkstraMethods.next("g", dijkstra));
	}

}
