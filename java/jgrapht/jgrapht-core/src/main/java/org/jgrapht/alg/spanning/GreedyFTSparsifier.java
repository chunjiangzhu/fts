package org.jgrapht.alg.spanning;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class GreedyFTSparsifier<V> {

	private final Graph<V, DefaultWeightedEdge> graph;
    private final int f;
    private final double eps;
    private final double rho;

    /**
     * Constructs instance to compute a $(2k-1)$-spanner of an undirected graph.
     * 
     * @param graph an undirected graph
     * @param k positive integer.
     * 
     * @throws IllegalArgumentException if the graph is not undirected
     * @throws IllegalArgumentException if k is not positive
     */
    public GreedyFTSparsifier(Graph<V, DefaultWeightedEdge> graph, int f, double eps, double rho)
    {
        this.graph = Objects.requireNonNull(graph, "Graph cannot be null");
        if (!graph.getType().isUndirected()) {
            throw new IllegalArgumentException("graph is not undirected");
        }
        this.f = f;
        this.eps = eps;
        this.rho = rho;
    }

    
    private void Light(Graph<V, DefaultWeightedEdge> graph, int logn, int f) {
    	Set<DefaultWeightedEdge> H = new GreedyFTSpanner<V>(graph, Integer.MAX_VALUE, f).getFTSpanner();
    	
    	System.out.println("edges in H size: " + H.size());
    	System.out.println("edges in G size: " + graph.edgeSet().size());
    	Set<DefaultWeightedEdge> addWeightEdges = new HashSet<DefaultWeightedEdge>();
    	Set<DefaultWeightedEdge> removeEdges = new HashSet<DefaultWeightedEdge>();
    	
    	
    	Random random = new Random();
    	for(DefaultWeightedEdge e: graph.edgeSet()) {
    		if(H.contains(e)) {
//    			System.out.println("skip " +e.toString());
    			continue;
    		}
    		// for an edge not in spanner, random sampling
    		if(random.nextDouble() <= 0.25)
    			addWeightEdges.add(e);
    		else
    			removeEdges.add(e);
    	}
    	
    	for(DefaultWeightedEdge e: addWeightEdges)
    		graph.setEdgeWeight(e, 4*graph.getEdgeWeight(e));
    	for(DefaultWeightedEdge e: removeEdges)
    		// remove the edge e from the current spasifier
			if(!graph.removeEdge(e))
				System.out.println("error in removing edge " + e);
    }
    
    
    public Set<DefaultWeightedEdge> GetFTSparsifier() {
    	
    	int logn = (int)Math.log10(this.graph.vertexSet().size());
    	int logrho = (int)Math.ceil(Math.log10(this.rho));
    	
    	double lightEps = this.eps/logrho;
    	//int f = (int)(this.f+24*Math.pow(logn,2)/Math.pow(lightEps,2));
    	// relax f+log^2n/eps^2 times of spanners to logn/eps^2
//    	int f = this.f+(int)(logn/Math.pow(lightEps,2));
    	int f = this.f + 1;
    	
    	System.out.println("logn: " + logn);
    	System.out.println("logrho: " + logrho);
    	System.out.println("lighteps: " + lightEps);
    	System.out.println("f: " + f);
    	
    	Graph<V, DefaultWeightedEdge> G = this.graph;
    	for(int i=1; i<=logrho; i++) {
    		Light(G, logn, f);
    	}
    	
    	return G.edgeSet();
    }
    
    public Graph<V, DefaultWeightedEdge> GetFTSparsifierAsGraph() {
    	
    	int logn = (int)Math.log10(this.graph.vertexSet().size());
    	int logrho = (int)Math.ceil(Math.log10(this.rho));
    	
    	double lightEps = this.eps/logrho;
    	//int f = (int)(this.f+24*Math.pow(logn,2)/Math.pow(lightEps,2));
    	// relax f+log^2n/eps^2 times of spanners to logn/eps^2
//    	int f = this.f+(int)(logn/Math.pow(lightEps,2));
    	int f = this.f + 1;
    	
    	System.out.println("logn: " + logn);
    	System.out.println("logrho: " + logrho);
    	System.out.println("lighteps: " + lightEps);
    	System.out.println("f: " + f);
    	
    	Graph<V, DefaultWeightedEdge> G = (Graph<V, DefaultWeightedEdge>) ((AbstractBaseGraph)this.graph).clone();
    	for(int i=1; i<=logrho; i++) {
    		Light(G, logn, f);
    	}
    	
    	return G;
    }
}
