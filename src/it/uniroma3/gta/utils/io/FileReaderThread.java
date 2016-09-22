package it.uniroma3.gta.utils.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import cytoscape.CyNode;
import cytoscape.Cytoscape;

public class FileReaderThread extends Thread {
	
	private File fileToRead;

	public FileReaderThread(File file) {
		super(file.getName());
		this.fileToRead = file;
	}
	
	public void run() {
		try {			
			ArrayList<LinkedList<CyNode>> sp = null;
			LinkedList<CyNode> list = null;

			InputStream fstream = new FileInputStream(fileToRead.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
            	list = new LinkedList<CyNode>();
				for (String s: line.split(" ")) {
					if (!s.equals("")) {
						if (Cytoscape.getCyNode(s)!=null) {
							if (sp == null)
								sp = new ArrayList<LinkedList<CyNode>>();
							list.add(Cytoscape.getCyNode(s));
						}
					}
				}		
				
				if (list.size()>1) 
					sp.add(list);
				
            }
            br.close();
			in.close();
			fstream.close();
			
			if (sp != null)
				ShortestPathsLoader.setShortestPaths(sp);
						
			while (ShortestPathsLoader.getDoneSignal() == null) {}
			ShortestPathsLoader.doneCountDown();
			System.out.println("Thread Completed!");
						
		} catch (IOException e) {}
	}
	
}
