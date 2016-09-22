package it.uniroma3.gta.algorithms;

import it.uniroma3.gta.utils.io.NetworkLoader;

import java.util.ArrayList;
import java.util.LinkedList;

import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;

public class DegreeCentrality extends Centrality {
	
	public DegreeCentrality() {}
	
	public double execute(CyNetwork network, String vertex, ArrayList<LinkedList<CyNode>> arr) {
		CyNode node = Cytoscape.getCyNode(vertex);
		double count = 0;
		double result = 0;
		if (network.nodesList().contains(node) && (network.getAdjacentEdgeIndicesArray(node.getRootGraphIndex(), false, true, true)).length != 0) {
			int[] outgoingEdges = network.getAdjacentEdgeIndicesArray(node.getRootGraphIndex(), false, true, true);
			for (int i=0; i<outgoingEdges.length; i++){
				CyNode source = (CyNode) network.getEdge(outgoingEdges[i]).getSource();
				CyNode target = (CyNode) network.getEdge(outgoingEdges[i]).getTarget();
				double edgeW = NetworkLoader.getEdgeWeight(source, target);
				count = count + edgeW;
			}
			
			result = count;
		}
		return result;
	}

}
