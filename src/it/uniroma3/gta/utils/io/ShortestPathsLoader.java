package it.uniroma3.gta.utils.io;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import cytoscape.CyNode;

public class ShortestPathsLoader {
	
	private static ArrayList<LinkedList<CyNode>> shortestPaths;
	private static CountDownLatch doneSignal;
	
	public ShortestPathsLoader() {}

	public boolean load(String path) {
		File shortestPathsFile = new File(path); 
		if (shortestPathsFile.exists() && path.endsWith("shortestPaths.txt")) {
			
			shortestPaths = new ArrayList<LinkedList<CyNode>>();		
			int threadCount = 0;
				
			for (File file: (new File(GtaInit.getTempFolderPath())).listFiles()) {
				if (file.getName().startsWith("sp")) {					
					FileReaderThread thread = new FileReaderThread(file);
					thread.start();
					threadCount++;
				}
			}
			
			
			doneSignal = new CountDownLatch(threadCount);
			try {
				doneSignal.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("shortestPaths size: " + shortestPaths.size());
			
			if (shortestPaths.size()==0) {
				shortestPaths = null;
				return false;
			}
			return true;
		}
		return false;
	}
	
	public static ArrayList<LinkedList<CyNode>> getShortestPaths() {
		return shortestPaths;
	}
	
	public static synchronized void setShortestPaths(ArrayList<LinkedList<CyNode>> list) {
		shortestPaths.addAll(list);
	}
	
	public static synchronized CountDownLatch getDoneSignal() {
		return doneSignal;
	}
	
	public static synchronized void doneCountDown() {
		doneSignal.countDown();
	}
	
}
