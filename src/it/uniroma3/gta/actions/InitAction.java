package it.uniroma3.gta.actions;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

import it.uniroma3.gta.Console;
import it.uniroma3.gta.Controller;
import it.uniroma3.gta.utils.io.GtaInit;
import it.uniroma3.gta.utils.io.NetworkLoader;
import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.Cytoscape;

public class InitAction extends Action {
	
	//create a copy of the network named 'DO_NOT_EDIT'
	@SuppressWarnings("unchecked")
	public String execute(String string) {
		
		if (string.equals("yes")) {
			
			if (Cytoscape.getNetworkSet().size()!=0) {
				String current = Cytoscape.getCurrentNetwork().getIdentifier();
				CyNetwork donotedit = Cytoscape.createNetwork(Cytoscape.getCurrentNetwork().nodesList(), Cytoscape.getCurrentNetwork().edgesList(), "DO_NOT_EDIT");
				Cytoscape.destroyNetworkView(donotedit);
				donotedit.setTitle("DO_NOT_EDIT");
				
				Cytoscape.setCurrentNetwork(current);
				Cytoscape.setCurrentNetworkView(current); //true if there is network view, false if not
				String columnName=Controller.getColumnWeight();
				//Scrivo File networkFile = new File(GtaInit.getDataFolderPath() + "/network_" + getFileFormatDate() + ".txt");
				NetworkLoader.initNetwork(new ArrayList<CyEdge>(Cytoscape.getCurrentNetwork().edgesList()), Cytoscape.getEdgeAttributes(), current, columnName);
				Console.getSPbutton().setEnabled(true);
				Console.getSTARTbutton().setBackground(Color.GREEN);
				Console.getSTARTbutton().setEnabled(false);
				return "INITIALIZATION COMPLETED!";
			}
			return "NOTHING TO INITIALIZE!";
		}
		return "NOTHING TO INITIALIZE!";
	}
}
