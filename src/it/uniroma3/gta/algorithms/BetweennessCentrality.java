/*Betweenness centrality
Quanti shortest paths passano attraverso un nodo


Betweenness centrality
- Probabilita' per un nodo di trovarsi nello 
shortest path tra due altri nodi

CB(v) = SUM(s!=v,e!=v) (#sp(s,e,v)/#sp(s,e))


- s: nodo iniziale, e: nodo finale
- #sp(s,e,v): numero di shortest paths da s a e che passano 
attraverso il nodo v
- #sp(s,e): numero totale di shortest paths da s a e

 * 
La Betweenness centrality si basa sull'osservazione che se un nodo fa parte di molti cammini 
minimi allora `e un nodo che riveste una posizione importante nel dominio del sistema che stiamo 
rappresentando come rete. Per esempio, in una rete sociale come twitter, dove i nodi sono gli 
utenti di twitter e gli archi sono la possibilita` di scambiarsi informazioni, rimuovere un nodo 
con alto valore di betweenness significa rallentare il flusso di infor- mazioni fra i nodi e in 
alcuni casi puo` significare la creazione di componenti sconnesse. Un secondo esempio puo` essere 
la rete formata dai raccordi stra- dali come nodi e dalle strade come archi. E` chiaro che in una 
rete del genere i raccordi che risiedono sulla maggior parte dei cammini minimi occupano una 
posizione strategica all'interno della rete. La conoscenza di tali punti puo` essere utile in 
vari scenari, per esempio ogni volta che si verificano degli esodi verso un unico luogo. 
I nodi con alto valore di Betweenness possono es- sere visti anche come i punti deboli della rete 
in quanto l'eliminazione di tali punti puo` portare a rendere la rete sconnessa.
 * 
*/

package it.uniroma3.gta.algorithms;

import cytoscape.CyNetwork;
import cytoscape.CyNode;
import cytoscape.Cytoscape;
import java.util.ArrayList;
import java.util.LinkedList;

public class BetweennessCentrality extends Centrality {
	private static ArrayList<LinkedList<CyNode>> shortestPaths = null;
	
	public BetweennessCentrality() {}
	
	public double execute(CyNetwork network, String string, ArrayList<LinkedList<CyNode>> shortest) {
		CyNode node = Cytoscape.getCyNode(string);
		
		shortestPaths = shortest;
		if (shortestPaths == null) {
			setShortestPaths(new ArrayList<LinkedList<CyNode>>());
			
			for (int j=0; j<network.nodesList().size(); j++) {
				for (int k=0; k<network.nodesList().size(); k++) {
					if (!((CyNode)network.nodesList().get(j)).getIdentifier().equals(((CyNode)network.nodesList().get(k)).getIdentifier())) {
						LinkedList<CyNode> shortestPath = null;
						
							DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(network);
							dijkstra.execute((CyNode)network.nodesList().get(j));
							shortestPath = dijkstra.getPath((CyNode)network.nodesList().get(k));
						
						if (shortestPath != null)
							shortestPaths.add(shortestPath);
					}
				}
			}
		}
		
		ArrayList<LinkedList<CyNode>> shortestPathsInN = new ArrayList<LinkedList<CyNode>>();
		for (LinkedList<CyNode> linked: shortestPaths) {
			if (linked.contains(node)) 
				shortestPathsInN.add(linked);
		}
		
		double num = (double)shortestPathsInN.size();//cammini minimi passanti per quel nodo
		double den = (double)shortestPaths.size();//cammini minimi
		
		try {
			double result = num/den;
			return result;
		} 	catch (ArithmeticException e) {
			return Double.POSITIVE_INFINITY;
		}
	}
	
	public static void setShortestPaths(ArrayList<LinkedList<CyNode>> shortest) {
		shortestPaths = shortest;
	}
	
	public static ArrayList<LinkedList<CyNode>> getShortestPaths() {
		if (Centrality.shortestPaths() != null)
			return Centrality.shortestPaths();
		return shortestPaths;
	}

}
