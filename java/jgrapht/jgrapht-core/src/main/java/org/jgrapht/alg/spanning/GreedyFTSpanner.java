package org.jgrapht.alg.spanning;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.SpannerAlgorithm.Spanner;
import org.jgrapht.alg.spanning.GreedyMultiplicativeSpanner;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.AsWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.graph.WeightedMultigraph;
import org.jgrapht.util.SupplierUtil;

public class GreedyFTSpanner<V> {
	private final Graph<V, DefaultWeightedEdge> graph;
    private final int k;
    private final int f;
    private static final int MAX_K = 1 << 29;

    /**
     * Constructs instance to compute a $(2k-1)$-spanner of an undirected graph.
     * 
     * @param graph an undirected graph
     * @param k positive integer.
     * 
     * @throws IllegalArgumentException if the graph is not undirected
     * @throws IllegalArgumentException if k is not positive
     */
    public GreedyFTSpanner(Graph<V, DefaultWeightedEdge> graph, int k, int f)
    {
        this.graph = Objects.requireNonNull(graph, "Graph cannot be null");
        if (!graph.getType().isUndirected()) {
            throw new IllegalArgumentException("graph is not undirected");
        }
        if (k <= 0) {
            throw new IllegalArgumentException(
                "k should be positive in (2k-1)-spanner construction");
        }
        this.k = Math.min(k, MAX_K);
        this.f = f;
    }

    public Set<DefaultWeightedEdge> getFTSpanner()
    {
    	Set<DefaultWeightedEdge> ftSpanner = new LinkedHashSet<>();
    	Graph<V,DefaultWeightedEdge> curGraph = (Graph<V, DefaultWeightedEdge>) ((AbstractBaseGraph)this.graph).clone();
    	GreedyMultiplicativeSpanner<V,DefaultWeightedEdge> spanner = new GreedyMultiplicativeSpanner<V,DefaultWeightedEdge>(curGraph, k);
    	
    	System.out.println("num of itrs: " + (f+1));
    	for(int i=1; i<=f+1; i++)
		{
//    		for(DefaultWeightedEdge e : curGraph.edgeSet()) {
//            	System.out.println(curGraph.getEdgeSource(e) + ", " +  curGraph.getEdgeTarget(e) + ", " + curGraph.getEdgeWeight(e));
//            }
    		Spanner<DefaultWeightedEdge> spannerEdges = spanner.getSpanner();
    		System.out.println("itr " + i + ": graph vertex size: " + curGraph.vertexSet().size() + ": graph edge size: " + curGraph.edgeSet().size() + ", spanner size: " + spannerEdges.size());
    		ftSpanner.addAll(spannerEdges);
    		curGraph.removeAllEdges(spannerEdges);
    		
    		if(curGraph.edgeSet().size() == 0)
    			break;
		}
    	return ftSpanner;
    }
}
