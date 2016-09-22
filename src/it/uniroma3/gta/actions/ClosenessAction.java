//Richiano gli algorirmi e ritorna il risultato

package it.uniroma3.gta.actions;

import it.uniroma3.gta.algorithms.ClosenessCentrality;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;

public class ClosenessAction extends Action {
	
	public String execute(String string) {
		CyNetwork network = null;
		for (CyNetwork net: Cytoscape.getNetworkSet()) {
			if (net.getTitle().equals("DO_NOT_EDIT"))
				network = net;
		}
		if (Cytoscape.getCyNode(string)!=null) {
			ClosenessCentrality centrality = new ClosenessCentrality();
			double result = centrality.execute(network, string, null);
			if (result!=Double.POSITIVE_INFINITY) {
				return "COMPLETED: closeness centrality for " + string + ": " + String.valueOf(result);
			}
			return "COMPLETED: closeness centrality for " + string + ": INFINITY";
		}
		return "The specified node does not exist!";
	}
}
