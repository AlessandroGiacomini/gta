package it.uniroma3.gta.actions;

import it.uniroma3.gta.algorithms.StressCentrality;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;

public class StressAction extends Action {
	
	public String execute(String string) {
		CyNetwork network = null;
		for (CyNetwork net: Cytoscape.getNetworkSet()) {
			if (net.getTitle().equals("DO_NOT_EDIT"))
				network = net;
		}
		if (Cytoscape.getCyNode(string)!=null) {
			StressCentrality centrality = new StressCentrality();
			double result = centrality.execute(network, string, StressCentrality.getShortestPaths());
			return "COMPLETED: stress centrality for " + string + ": " + String.valueOf(result);
		}
		return "The specified node does not exist!";
	}

}
