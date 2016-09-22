/*
 * 
Degree centrality
-Numero delle connessioni di un nodo
Betweenness centrality
-Quanti shortest paths passano attraverso un nodo
Closeness centrality
-Quanto un nodo e' "vicino" agli altri nodi
 * 
 */

package it.uniroma3.gta.algorithms;
import it.uniroma3.gta.algorithms.util.DijkstraSP;
import it.uniroma3.gta.algorithms.util.EdgeWeightedDigraph;
import it.uniroma3.gta.algorithms.util.In;
import it.uniroma3.gta.utils.io.GtaInit;
import it.uniroma3.gta.utils.io.NetworkLoader;
import it.uniroma3.gta.utils.io.ShortestPathsLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;

import cytoscape.CyNetwork;
import cytoscape.CyNode;

public abstract class Centrality {
	
	/*
	 * abstract class extended by all centrality classes
	 */
	
	private final static long _limitFileSize = 52428800;
	public abstract double execute(CyNetwork network, String string, ArrayList<LinkedList<CyNode>> arr);
	private static ArrayList<LinkedList<CyNode>> result;
	
	public static ArrayList<LinkedList<CyNode>> getShortestPaths(CyNetwork network) {
		
		FileOutputStream fos = null;
		PrintStream out = null;
		
		FileOutputStream fosToResume = null;
		PrintStream  outToResume = null;
		
		File toResume = new File(GtaInit.getTempFolderPath() + "/toResumeSP.txt");
		
		File dir = new File(GtaInit.getTempFolderPath());		
		
		HashMap<String, Integer> netToInt = NetworkLoader.getNetToInt();
		HashMap<Integer, String> intToNet = new HashMap<Integer, String>();
		for (String s: netToInt.keySet()) {
			intToNet.put(netToInt.get(s), s);
		}
		In in = new In(NetworkLoader.getNetToIntPath());
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
        
        ArrayList<Integer> computed = alreadyComputed(toResume);
        
        try {
			
        	File shortestTemp = new File(GtaInit.getTempFolderPath() + "/sp_temp_" + getFileFormatDate() + ".txt");
			fos = new FileOutputStream(shortestTemp.getAbsoluteFile());
			out = new PrintStream(fos);
			
			fosToResume = new FileOutputStream(toResume.getAbsoluteFile());
			outToResume = new PrintStream(fosToResume); 
			
			int s = computed.get(0);
					
			for (int k=computed.get(1); k<G.V(); k++) {
				if (s != k) {
						
				        DijkstraSP sp = new DijkstraSP(G, s);
				        if (sp.hasPathTo(k)) {
				        	if (shortestTemp.length() < _limitFileSize)
				        		out.println(sp.getStringPath(sp.pathTo(k), intToNet));
				        	else {
				        		out.close();
				        		fos.close();
				        		shortestTemp = new File(GtaInit.getTempFolderPath() + "/sp_temp_" + getFileFormatDate() + ".txt");
				        		fos = new FileOutputStream(shortestTemp.getAbsoluteFile());
				    			out = new PrintStream(fos);
				    			out.println(sp.getStringPath(sp.pathTo(k), intToNet));
				        	}
			                outToResume.print(s + " " + k + " ");
			            }
				        else {
							outToResume.print(s + " " + k + " ");
						}
					
					}			
					
				
			}
			if (computed.get(0)+1 <= G.V()) {
				for (int j=computed.get(0)+1; j<G.V(); j++) {
					for (int k=0; k<G.V(); k++) {
						if (j != k) {
							DijkstraSP sp = new DijkstraSP(G, j);
						        if (sp.hasPathTo(k)) {
						        	if (shortestTemp.length() < _limitFileSize)
						        		out.println(sp.getStringPath(sp.pathTo(k), intToNet));
						        	else {
						        		out.close();
						        		fos.close();
						        		shortestTemp = new File(GtaInit.getTempFolderPath() + "/sp_temp_" + getFileFormatDate() + ".txt");
						        		fos = new FileOutputStream(shortestTemp.getAbsoluteFile());
						    			out = new PrintStream(fos);
						    			out.println(sp.getStringPath(sp.pathTo(k), intToNet));
						        	}
					                outToResume.print(j + " " + k + " ");
					            }
						        else {
									outToResume.print(j + " " + k + " ");
								}
								
							}
						}
					}
				}
	
			
			if (fos!=null && fosToResume!=null) {
				out.close();
				fos.close();
				outToResume.close();
				fosToResume.close();
				
			}
		} catch (IOException e) {}
		
		File[] allFiles = dir.listFiles();
		result = completed(allFiles);
		boolean completed = false;
		while (!completed) {
			if (result != null) {
				if (result.size()>0)
					completed = true;
				else
					result = getShortestPaths(network);
			}
		}
		return result;
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
	
	private static ArrayList<Integer> alreadyComputed(File toResume) {
		ArrayList<Integer> result = null;
			try {
				
				String input ="", tmp; 
				FileInputStream fos = new FileInputStream(toResume.getAbsolutePath());
				InputStreamReader in = new InputStreamReader(fos);
				BufferedReader reader = new BufferedReader(in); 
				do { 
					input = reader.readLine(); 
					if (input!=null && reader.readLine()==null) {
						result = new ArrayList<Integer>();
						tmp = input;
						String[] splitLast = tmp.split(" ");
						result.add(Integer.valueOf(splitLast[splitLast.length-2]));
						result.add(Integer.valueOf(splitLast[splitLast.length-1]));
					}
				} while (input!=null);
				
				reader.close();
				in.close();
				fos.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		return result;
	}
	
	private static ArrayList<LinkedList<CyNode>> completed(File[] files) {
		ArrayList<LinkedList<CyNode>> result = new ArrayList<LinkedList<CyNode>>();
		File shortestFile = new File(GtaInit.getDataFolderPath() + "/shortestPaths.txt");
		
		try {
			shortestFile.createNewFile();
		
			for (File file: files)
				if (file.getName().startsWith("sp")) {
					copyFile(file.getAbsolutePath(), shortestFile.getAbsolutePath());
				}
			
			
			ShortestPathsLoader loader = new ShortestPathsLoader();
			loader.load(shortestFile.getAbsolutePath());
			result = ShortestPathsLoader.getShortestPaths();
			
		} catch(IOException e) {}
		return result;
	}
	
	
	public static void copyFile(String pathFrom, String pathTo) {
		try {
			File f1 = new File(pathFrom);
			File f2 = new File(pathTo);
			InputStream in = new FileInputStream(f1);
			OutputStream out = new FileOutputStream(f2, true);
			byte[] buf = new byte[51200];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} 
		catch (FileNotFoundException ex) {} 
		catch (IOException e) {}
		
	}
	
	public static ArrayList<LinkedList<CyNode>> shortestPaths() {
		return result;
	}
}
