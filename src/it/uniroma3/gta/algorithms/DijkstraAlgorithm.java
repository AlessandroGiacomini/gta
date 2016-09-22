package it.uniroma3.gta.algorithms;

/**************************************************************************
 * File: Dijkstra.java
 * Author: Keith Schwarz (htiek@cs.stanford.edu)
 *
 * An implementation of Dijkstra's single-source shortest path algorithm.
 * The algorithm takes as input a directed graph with non-negative edge
 * costs and a source node, then computes the shortest path from that node
 * to each other node in the graph.
 *
 * The algorithm works by maintaining a priority queue of nodes whose
 * priorities are the lengths of some path from the source node to the
 * node in question.  At each step, the algortihm dequeues a node from
 * this priority queue, records that node as being at the indicated
 * distance from the source, and then updates the priorities of all nodes
 * in the graph by considering all outgoing edges from the recently-
 * dequeued node to those nodes.
 *
 * In the course of this algorithm, the code makes up to |E| calls to
 * decrease-key on the heap (since in the worst case every edge from every
 * node will yield a shorter path to some node than before) and |V| calls
 * to dequeue-min (since each node is removed from the prioritiy queue
 * at most once).  Using a Fibonacci heap, this gives a very good runtime
 * guarantee of O(|E| + |V| lg |V|).
 *
 * This implementation relies on the existence of a FibonacciHeap class, also
 * from the Archive of Interesting Code.  You can find it online at
 *
 *         http://keithschwarz.com/interesting/code/?dir=fibonacci-heap
 */

import it.uniroma3.gta.utils.io.NetworkLoader;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import cytoscape.CyEdge;
import cytoscape.CyNetwork;
import cytoscape.CyNode;

public final class DijkstraAlgorithm {
	
	private final ArrayList<CyEdge> edges;
	private Set<CyNode> settledNodes;
	private Set<CyNode> unSettledNodes;
	private Map<CyNode, CyNode> predecessors;
	private Map<CyNode, Double> distance;
	
	@SuppressWarnings("unchecked")
	public DijkstraAlgorithm(CyNetwork graph) {
		// Create a copy of the array so that we can operate on this array
		this.edges = new ArrayList<CyEdge>(graph.edgesList());
	}
	
	public void execute(CyNode source) {
		settledNodes = new HashSet<CyNode>();
		unSettledNodes = new HashSet<CyNode>();
		distance = new HashMap<CyNode, Double>();
		predecessors = new HashMap<CyNode, CyNode>();
		distance.put(source, 0.0);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			CyNode node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}
	
	private void findMinimalDistances(CyNode node) {
		ArrayList<CyNode> adjacentNodes = getNeighbors(node);
		for (CyNode target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
				distance.put(target, getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}

	}

	private double getDistance(CyNode node, CyNode target) {
		for (CyEdge edge : edges) {
			if (edge.getSource().equals(node) && edge.getTarget().equals(target)) {
				return NetworkLoader.getEdgeWeight((CyNode)edge.getSource(), (CyNode)edge.getTarget());
			}
		}
		throw new RuntimeException("Should not happen");
	}
	
	private ArrayList<CyNode> getNeighbors(CyNode node) {
		ArrayList<CyNode> neighbors = new ArrayList<CyNode>();
		for (CyEdge edge : edges) {
			if (edge.getSource().equals(node) && !isSettled((CyNode)edge.getTarget())) {
				neighbors.add((CyNode)edge.getTarget());
			}
		}
		return neighbors;
	}

	private CyNode getMinimum(Set<CyNode> vertexes) {
		CyNode minimum = null;
		for (CyNode vertex : vertexes) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}
	
	private boolean isSettled(CyNode vertex) {
		return settledNodes.contains(vertex);
	}

	private double getShortestDistance(CyNode destination) {
		Double d = distance.get(destination);
		if (d == null) {
			return Double.MAX_VALUE;
		} else {
			return d;
		}
	}
	
	public LinkedList<CyNode> getPath(CyNode target) {
		LinkedList<CyNode> path = new LinkedList<CyNode>();
		CyNode step = target;
		// Check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		return path;
	}
	
}