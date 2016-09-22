package it.uniroma3.gta.algorithms;

import java.util.ArrayList;
import java.util.LinkedList;
import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;

public class ClosenessCentrality extends Centrality {
	
	public ClosenessCentrality() {}
	
	public double execute(CyNetwork network, String string, ArrayList<LinkedList<CyNode>> shortest) {
		CyNode node = Cytoscape.getCyNode(string);
		
		ArrayList<LinkedList<CyNode>> shortestPaths = new ArrayList<LinkedList<CyNode>>();
		
		if (shortest == null) {
			for (int k=0; k<network.nodesList().size(); k++) {
				if (!string.equals(((CyNode)network.nodesList().get(k)).getIdentifier())) {
					LinkedList<CyNode> shortestPath = null;
						DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(network);
						dijkstra.execute(node);
						shortestPath = dijkstra.getPath((CyNode)network.nodesList().get(k));
					
					if (shortestPath != null)
						shortestPaths.add(shortestPath);
				}
			}
		}
		else {
			for (LinkedList<CyNode> list: shortest) {
				if ((list.get(0)).getIdentifier().equals(string))
					shortestPaths.add(list);
			}
		}
		
		
		int count = 0;
		for (LinkedList<CyNode> list: shortestPaths) {
			count += list.size()-1;
		}		
		
		double dCount = (double)count;
		try {
			double result = (1.0/dCount);
			return result;
		} catch (ArithmeticException e) {
			return Double.POSITIVE_INFINITY;
		}
	}

}