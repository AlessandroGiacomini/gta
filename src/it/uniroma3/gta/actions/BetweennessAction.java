package it.uniroma3.gta.actions;
import it.uniroma3.gta.algorithms.BetweennessCentrality;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;

public class BetweennessAction extends Action {

	public String execute(String string) {
		CyNetwork network = null;
		for (CyNetwork net: Cytoscape.getNetworkSet()) {
			if (net.getTitle().equals("DO_NOT_EDIT"))
				network = net;
		}
		if (Cytoscape.getCyNode(string)!=null) {
			BetweennessCentrality centrality = new BetweennessCentrality();
			double result = centrality.execute(network, string, BetweennessCentrality.getShortestPaths());
			return "COMPLETED: betweenness centrality for " + string + ": " + String.valueOf(result);
		}
		return "The specified node does not exist!";
	}
}