package it.uniroma3.gta.utils;

import it.uniroma3.gta.algorithms.Centrality;
import it.uniroma3.gta.utils.io.GtaInit;
import it.uniroma3.gta.utils.io.NetworkLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;

public class MakeShortestPathsUtil extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<LinkedList<CyNode>> shortestPaths;
	
	public MakeShortestPathsUtil() {}
	
	public boolean makeShortestPaths() {
		String shortestPath = GtaInit.getDataFolderPath();
		if (shortestPath!=null && !shortestPath.equals("")) {
			File dir = new File(shortestPath);
			if (dir.isDirectory()) {
				if (NetworkLoader.getNetwork() != null && NetworkLoader.getNetwork().size() > 0) {
					shortestPaths = computeShortestPaths();
					if (shortestPaths!=null && shortestPaths.size()>0) {
						if ((new File(GtaInit.getDataFolderPath() + "/shortestPaths.txt")).exists()) {
							return true;
						}						
						else {
							JOptionPane.showMessageDialog(this, "An error has occurred during 'shortestPaths.txt' making!\nThey already exist in the specified location???\nREMEMBER TO LOAD NETWORK FIRST!", "ERROR!", JOptionPane.ERROR_MESSAGE);
							return false;
						}
					}
					else {
						JOptionPane.showMessageDialog(this, "An error has occurred while attempting to compute centralities!\nREMEMBER TO LOAD NETWORK FIRST!", "ERROR!", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
				else {
					JOptionPane.showMessageDialog(this, "Load Network first!", "ERROR!", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "You have entered an invalid path!", "ERROR!", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "You have entered an invalid path!", "ERROR!", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	private ArrayList<LinkedList<CyNode>> computeShortestPaths() {
		CyNetwork network = null;
		for (CyNetwork net: Cytoscape.getNetworkSet()) {
			if (net.getTitle().equals("DO_NOT_EDIT"))
				network = net;
		}
		
		return Centrality.getShortestPaths(network);
	}
}
