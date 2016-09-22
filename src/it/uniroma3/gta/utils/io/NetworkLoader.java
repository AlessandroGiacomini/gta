package it.uniroma3.gta.utils.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;

import cytoscape.CyEdge;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import cytoscape.data.CyAttributes;
import cytoscape.data.Semantics;

public class NetworkLoader {
	
	private static String networkName;
	private static ArrayList<ArrayList<String>> network;
	private static HashMap<ArrayList<String>, Double> netWithWeights;
	private static HashMap<String, Integer> netToInt;
	private static String netToIntPath;
	
	public NetworkLoader() {}
	
	public static String getNetworkName() {
		return networkName;
	}
	
	public static ArrayList<ArrayList<String>> getNetwork() {
		return network;
	}
	
	public static HashSet<CyEdge> retrieveEdgesList() {		
		if (network != null) {
			HashSet<CyEdge> edges = new HashSet<CyEdge>();
			for (ArrayList<String> arr: network) {
				edges.add(Cytoscape.getCyEdge(Cytoscape.getCyNode(arr.get(0)), Cytoscape.getCyNode(arr.get(1)), Semantics.INTERACTION, "pp", true, true));
			}
			return edges;
		}
		else
			return null;
	}
	
	public static HashSet<CyNode> retrieveNodesList() {		
		if (network != null) {
			HashSet<CyNode> nodes = new HashSet<CyNode>();
			for (ArrayList<String> arr: network) {
				nodes.add(Cytoscape.getCyNode(arr.get(0)));
				nodes.add(Cytoscape.getCyNode(arr.get(1)));
			}
			return nodes;
		}
		else
			return null;
	}
	
	public static HashSet<String> retriveStringNodesList() {		
		if (network != null) {
			HashSet<String> nodes = new HashSet<String>();
			for (ArrayList<String> arr: network) {
				nodes.add(arr.get(0));
				nodes.add(arr.get(1));
			}
			return nodes;
		}
		else
			return null;
	}
	
	public static double getEdgeWeight(CyNode source, CyNode target) {		
		for (ArrayList<String> arr: netWithWeights.keySet()) {
			if (arr.get(0).equals(source.getIdentifier()) && arr.get(1).equals(target.getIdentifier()))
				return netWithWeights.get(arr);
		}
		return 1;
	}
	
	public static HashMap<ArrayList<String>, Double> getNetworkWithWeights() {
		return netWithWeights;
	}
	
	private static String getFileFormatDate() {
		Calendar calendar = new GregorianCalendar();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH)+1;
		int year = calendar.get(Calendar.YEAR);
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		String AM_PM;
		if(calendar.get(Calendar.AM_PM) == 0)
			AM_PM = "AM";
		else
			AM_PM = "PM";
		return day + "." + month + "." + year + "_" + hour + "." + minute + "." + second +  "." + AM_PM;
	}
	
	private static int getNodeOccurrencies(ArrayList<CyEdge> edges) {
		netToInt = new HashMap<String, Integer>();
		ArrayList<String> alreadyCounted = new ArrayList<String>();
		int num = 0;
		for (CyEdge edge: edges) {
					
					String sourceString = edge.getSource().getIdentifier();
					String targetString = edge.getTarget().getIdentifier();
					
					if (!alreadyCounted.contains(sourceString)) {
						alreadyCounted.add(sourceString);
						netToInt.put(sourceString, num);
						num++;
					}
					if (!alreadyCounted.contains(targetString)) {
						alreadyCounted.add(targetString);
						netToInt.put(targetString, num);
						num++;
					}
				}
		return alreadyCounted.size();
	}
		
	public static HashMap<String, Integer> getNetToInt() {
		return netToInt;
	}
	
	public static String getNetToIntPath() {
		return netToIntPath;
	}

	public static void initNetwork(ArrayList<CyEdge> edgesList, CyAttributes edgeAttributes, String name,String column) {
		try {
			if (!edgesList.isEmpty()) {
				File networkFile = new File(GtaInit.getDataFolderPath() + "/network_" + getFileFormatDate() + ".txt");
				netToIntPath = networkFile.getAbsolutePath();
				networkName = name;
				network = new ArrayList<ArrayList<String>>();
				netWithWeights = new HashMap<ArrayList<String>, Double>();
				
				FileOutputStream fosNet = new FileOutputStream(networkFile.getAbsoluteFile());
				PrintStream outNet = new PrintStream(fosNet);
				
				outNet.println(getNodeOccurrencies(edgesList));
				outNet.println(edgesList.size());
				
				boolean isInt = false;
				boolean assignDefaultWeight = false;
				if (column.equals("Default weight (1)")){
					assignDefaultWeight = true;
				}
				
				if (!assignDefaultWeight){
					try {
						@SuppressWarnings("unused")
						double test = (edgeAttributes.getDoubleAttribute(edgesList.get(0).getIdentifier(), column));
					} catch (ClassCastException e) {
						isInt = true;
						for (CyEdge eInt: edgesList) {
							ArrayList<String> edge = new ArrayList<String>();
							edge.add(eInt.getSource().getIdentifier());
							edge.add(eInt.getTarget().getIdentifier());
							network.add(edge);
							int edgeWeight = edgeAttributes.getIntegerAttribute(eInt.getIdentifier(), column);
							double edgeWeightD = (double) edgeWeight;
							netWithWeights.put(edge, edgeWeightD);
							outNet.println(netToInt.get(eInt.getSource().getIdentifier()) + " " + netToInt.get(eInt.getTarget().getIdentifier()) + " " + edgeWeightD);
						}
						
					}
				
					if (!isInt){
						for (CyEdge e: edgesList) {
							ArrayList<String> edge = new ArrayList<String>();
							edge.add(e.getSource().getIdentifier());
							edge.add(e.getTarget().getIdentifier());
							network.add(edge);
							double edgeWeight = edgeAttributes.getDoubleAttribute(e.getIdentifier(), column);
							netWithWeights.put(edge, edgeWeight);
							outNet.println(netToInt.get(e.getSource().getIdentifier()) + " " + netToInt.get(e.getTarget().getIdentifier()) + " " + edgeWeight);
						}
					}
				}
				else {
					for (CyEdge e: edgesList) {
						ArrayList<String> edge = new ArrayList<String>();
						edge.add(e.getSource().getIdentifier());
						edge.add(e.getTarget().getIdentifier());
						network.add(edge);
						double edgeWeight = 1.0;
						netWithWeights.put(edge, edgeWeight);
						outNet.println(netToInt.get(e.getSource().getIdentifier()) + " " + netToInt.get(e.getTarget().getIdentifier()) + " " + edgeWeight);
					}
				}
				
				outNet.close();
	            fosNet.close();
				
			}
		} catch (IOException e) {}
	}
	
}
