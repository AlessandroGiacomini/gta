package it.uniroma3.gta.utils;

import it.uniroma3.gta.algorithms.BetweennessCentrality;
import it.uniroma3.gta.algorithms.Centrality;
import it.uniroma3.gta.algorithms.ClosenessCentrality;
import it.uniroma3.gta.algorithms.DegreeCentrality;
import it.uniroma3.gta.algorithms.StressCentrality;
import it.uniroma3.gta.utils.io.GtaInit;
import it.uniroma3.gta.utils.io.NetworkLoader;
import it.uniroma3.gta.utils.io.ShortestPathsLoader;
import it.uniroma3.gta.utils.io.StatisticsUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;

public class MakeStatisticsUtil extends JPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<LinkedList<CyNode>> shortestPaths;
	
	public MakeStatisticsUtil() {}
	
	public boolean makeStatistics() {
		String statsPath = GtaInit.getDataFolderPath();
		if (statsPath!=null && !statsPath.equals("")) {
			File dir = new File(statsPath);
			if (dir.isDirectory()) {
				if (NetworkLoader.getNetwork() != null && NetworkLoader.getNetwork().size() > 0) {
					HashMap<String, ArrayList<String>> statistics = null;
					File shortest = new File(GtaInit.getDataFolderPath() + "/shortestPaths.txt");
					if (shortest.exists()) {
						if (ShortestPathsLoader.getShortestPaths() != null) {
							shortestPaths = ShortestPathsLoader.getShortestPaths();
							statistics = computeStats(shortestPaths);
						}
						else {
							ShortestPathsLoader loader = new ShortestPathsLoader();
							if (loader.load(shortest.getAbsolutePath())) {
								shortestPaths = ShortestPathsLoader.getShortestPaths();
								statistics = computeStats(shortestPaths);
							}
							else { 
								statistics = computeStats(null);
							}
						}
					}
					else {
						statistics = computeStats(null);
					}
					if (statistics!=null && statistics.size()>0) {
						StatisticsUtil util = new StatisticsUtil();
						boolean stats = util.makeStats(statsPath, statistics, shortestPaths);
						if (stats) {
							return true;
						}
						else {
							JOptionPane.showMessageDialog(this, "An error has occurred during 'stats.xlsx' making!", "ERROR!", JOptionPane.ERROR_MESSAGE);
							return false;
						}
					}
					else {
						JOptionPane.showMessageDialog(this, "An error has occurred while attempting to compute centralities!", "ERROR!", JOptionPane.ERROR_MESSAGE);
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
	
	private HashMap<String, ArrayList<String>> computeStats(ArrayList<LinkedList<CyNode>> sp) {
		CyNetwork network = null;
		for (CyNetwork net: Cytoscape.getNetworkSet()) {
			if (net.getTitle().equals("DO_NOT_EDIT"))
				network = net;
		}
		//KEY: NodeName    -    VALUE: {Betweenness - Closeness - Degree - Stress}
		HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
		
		shortestPaths = sp;
		if (shortestPaths == null)
			shortestPaths = Centrality.getShortestPaths(network);
		ArrayList<String> nodesString = new ArrayList<String>();
		
		for (CyNode n: NetworkLoader.retrieveNodesList())
			nodesString.add(n.getIdentifier());
				
		BetweennessCentrality betweenness = new BetweennessCentrality();
		ClosenessCentrality closeness = new ClosenessCentrality();
		DegreeCentrality degree = new DegreeCentrality();
		StressCentrality stress = new StressCentrality();
		
		CyAttributes nodeAtts = Cytoscape.getNodeAttributes();
		CyNode cyNode = null;
		for (String node: nodesString) {
			ArrayList<String> centralities = new ArrayList<String>();
			cyNode = Cytoscape.getCyNode(node);
			double nodeBetweenness = betweenness.execute(network, node, shortestPaths);
			centralities.add(String.valueOf(nodeBetweenness));
			nodeAtts.setAttribute (cyNode.getIdentifier(), "Betweenness", nodeBetweenness);
			double nodeCloseness = closeness.execute(network, node, shortestPaths);
			centralities.add(String.valueOf(nodeCloseness));
			nodeAtts.setAttribute (cyNode.getIdentifier(), "Closeness", nodeCloseness);
			double nodeDegree = degree.execute(network, node, null);
			centralities.add(String.valueOf(nodeDegree));
			nodeAtts.setAttribute (cyNode.getIdentifier(), "Degree", nodeDegree);
			double nodeStress = stress.execute(network, node, shortestPaths);
			centralities.add(String.valueOf(nodeStress));
			nodeAtts.setAttribute (cyNode.getIdentifier(), "Stress", nodeStress);
			
			result.put(node, centralities);
			
		}
		
		return result;
	}	

}
