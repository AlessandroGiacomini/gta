package it.uniroma3.gta.actions;

import it.uniroma3.gta.algorithms.DegreeCentrality;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;

public class DegreeAction extends Action {
	
	public String execute(String string) {
		CyNetwork network = null;
		for (CyNetwork net: Cytoscape.getNetworkSet()) {
			if (net.getTitle().equals("DO_NOT_EDIT"))
				network = net;
		}
		if (Cytoscape.getCyNode(string)!=null) {
			DegreeCentrality centrality = new DegreeCentrality();
			double result = centrality.execute(network, string, null);
			return "COMPLETED: degree centrality for " + string + ": " + String.valueOf(result);
		}
		return "The specified node does not exist!";
	}
}