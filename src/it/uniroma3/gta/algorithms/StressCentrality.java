package it.uniroma3.gta.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;

public class StressCentrality extends Centrality {

	private static ArrayList<LinkedList<CyNode>> shortestPaths = null;

	public StressCentrality() {}

	public double execute(CyNetwork network, String string, ArrayList<LinkedList<CyNode>> shortest) {
		CyNode node = Cytoscape.getCyNode(string);
	
		shortestPaths = shortest;
		if (shortestPaths == null) {
			setShortestPaths(new ArrayList<LinkedList<CyNode>>());
		
			for (int j=0; j<network.nodesList().size(); j++) {
				for (int k=0; k<network.nodesList().size(); k++) {
					if (!((CyNode)network.nodesList().get(j)).getIdentifier().equals(((CyNode)network.nodesList().get(k)).getIdentifier())) {
						LinkedList<CyNode> shortestPath = null;
							DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(network);
							dijkstra.execute((CyNode)network.nodesList().get(j));
							shortestPath = dijkstra.getPath((CyNode)network.nodesList().get(k));
						
						if (shortestPath != null)
							shortestPaths.add(shortestPath);
					}
				}
			}
		}
	
		ArrayList<LinkedList<CyNode>> shortestPathsInN = new ArrayList<LinkedList<CyNode>>();
		for (LinkedList<CyNode> linked: shortestPaths) {
			if (linked.contains(node))
				shortestPathsInN.add(linked);
		}
		
		double result = (double)shortestPathsInN.size();
		return result;
	}

	public static void setShortestPaths(ArrayList<LinkedList<CyNode>> shortest) {
		shortestPaths = shortest;
	}

	public static ArrayList<LinkedList<CyNode>> getShortestPaths() {
		return shortestPaths;
	}

}
