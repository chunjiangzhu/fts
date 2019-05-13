/*
 * (C) Copyright 2016-2018, by Assaf Mizrachi and Contributors.
 *
 * JGraphT : a free Java graph-theory library
 *
 * See the CONTRIBUTORS.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the
 * GNU Lesser General Public License v2.1 or later
 * which is available at
 * http://www.gnu.org/licenses/old-licenses/lgpl-2.1-standalone.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR LGPL-2.1-or-later
 */

package org.jgrapht.traverse;

import org.jgrapht.*;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.graph.builder.*;
import org.jgrapht.util.*;
import org.junit.*;

import java.io.StringWriter;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Tests for the {@link RandomWalkIterator} class.
 * 
 * @author Assaf Mizrachi
 *
 */
public class MyRandomWalkIteratorTest
{

    /**
     * Tests empty graph
     */
    @Test
    public void testEmptyGraph()
    {
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        Iterator<String> iter = new RandomWalkIterator<>(graph);
        assertFalse(iter.hasNext());
    }

    /**
     * Tests single node graph
     */
    @Test
    public void testSingleNode()
    {
        Graph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        graph.addVertex("123");
        Iterator<String> iter = new RandomWalkIterator<>(graph);
        assertTrue(iter.hasNext());
        assertEquals("123", iter.next());
        assertFalse(iter.hasNext());
    }

    /**
     * Tests iterator does not have more elements after reaching sink vertex.
     */
    @Test
    public void testSink()
    {
        Graph<String,
            DefaultEdge> graph = GraphTypeBuilder
                .directed().vertexSupplier(SupplierUtil.createStringSupplier())
                .edgeClass(DefaultEdge.class).allowingMultipleEdges(false).allowingSelfLoops(true)
                .buildGraph();
        int graphSize = 10;
        LinearGraphGenerator<String, DefaultEdge> graphGenerator =
            new LinearGraphGenerator<>(graphSize);
        graphGenerator.generateGraph(graph);
        Iterator<String> iter = new RandomWalkIterator<>(graph);
        for (int i = 0; i < graphSize; i++) {
            assertTrue(iter.hasNext());
            assertNotNull(iter.next());
        }
        assertFalse(iter.hasNext());
    }

    /**
     * Tests iterator is exhausted after maxSteps
     */
    @Test
    public void testExhausted()
    {
        Graph<String,
            DefaultEdge> graph = GraphTypeBuilder
                .undirected().vertexSupplier(SupplierUtil.createStringSupplier(1))
                .edgeClass(DefaultEdge.class).allowingMultipleEdges(false).allowingSelfLoops(false)
                .buildGraph();

        RingGraphGenerator<String, DefaultEdge> graphGenerator = new RingGraphGenerator<>(10);
        graphGenerator.generateGraph(graph);
        int maxSteps = 4;
        Iterator<String> iter = new RandomWalkIterator<>(graph, "1", false, maxSteps);
        for (int i = 0; i < maxSteps; i++) {
            assertTrue(iter.hasNext());
            assertNotNull(iter.next());
        }
        assertFalse(iter.hasNext());
    }

    /**
     * Test deterministic walk using directed ring graph.
     */
    @Test
    public void testDeterministic()
    {
        Graph<String,
            DefaultEdge> graph = GraphTypeBuilder
                .directed().vertexSupplier(SupplierUtil.createStringSupplier())
                .edgeClass(DefaultEdge.class).allowingMultipleEdges(false).allowingSelfLoops(true)
                .buildGraph();

        int ringSize = 5;
        RingGraphGenerator<String, DefaultEdge> graphGenerator = new RingGraphGenerator<>(ringSize);
        graphGenerator.generateGraph(graph);
        Iterator<String> iter = new RandomWalkIterator<>(graph, "0", false, 20);
        int step = 0;
        while (iter.hasNext()) {
            step++;
            assertEquals(String.valueOf(step % ringSize), iter.next());
        }
    }
    
    /**
     * Test myRandonWalk.
     * @throws ExportException 
     */
    @Test
    public void testMyRandomWalk()
    {
        Graph<String,
            DefaultEdge> graph = GraphTypeBuilder
                .directed().vertexSupplier(SupplierUtil.createStringSupplier())
                .edgeClass(DefaultEdge.class).allowingMultipleEdges(false).allowingSelfLoops(true)
                .buildGraph();
        
        

        // graph generation
        int ringSize = 50;
        RingGraphGenerator<String, DefaultEdge> graphGenerator = new RingGraphGenerator<>(ringSize);
        graphGenerator.generateGraph(graph);
        
        
        
        Map<String,Integer> count = new HashMap<String,Integer>();
        for(String node: graph.vertexSet())
        	count.put(node, 0);
        
        // uniform randomly select 10 starter nodes
        List<String> starterNodes = new ArrayList<String>();
        List<String> nodeList = new ArrayList<String>(graph.vertexSet());
        
        if (graph.vertexSet().size() > 0) {
        	Random random = new Random();
        	
        	for(int i=0; i<10; i++) {
	        	int j = random.nextInt(graph.vertexSet().size());
	        	starterNodes.add(nodeList.get(j));
        	}
        }
        else {
        	System.out.println("Error empty graph.");
        	return;
        }
        System.out.println("Starter Nodes:" + starterNodes);
        
        // for each starter node, perform m/10 times random walks
        int loop = graph.edgeSet().size()/10;
        for(int i=0; i<10; i++)
	        for(int j=0; j<loop; j++) {
		        Iterator<String> iter = new MyRandomWalkIterator<>(graph, starterNodes.get(i), count, false);
		        while (iter.hasNext())
		        	iter.next();
	        }
        
        // normalize the count by the average count
        int totCount = 0;
        for(String node: count.keySet()) {
        	totCount += count.get(node);
        	System.out.println(node + ":" + count.get(node));
        }
        double avgCount = (double)totCount/graph.vertexSet().size();
        System.out.println("avg count:" + avgCount);
        Map<String,Double> signal = new HashMap<String,Double>();
        for(String node: count.keySet()) {
        	signal.put(node, count.get(node)/avgCount);
        	System.out.println(node + ":" + signal.get(node));
        }
        
        
        // add zero-centered Gaussian perturbation to form the y
        Map<String, Double> y = new HashMap<String,Double>();
        Random gaussian = new Random();
        double sd = 2;
        for(String node: signal.keySet()) {
        	y.put(node, signal.get(node) + gaussian.nextGaussian() * sd);
        	System.out.println(node + ":" + y.get(node));
        }
        
        // tilde_f = (lambda * L_G + I)^{-1} * y
        // output L_G
//        GraphExporter<String, DefaultEdge> exporter1 =
//                new MatrixExporter<>(MatrixExporter.Format.SPARSE_LAPLACIAN_MATRIX);
//        StringWriter w1 = new StringWriter();
//        exporter1.exportGraph(graph, w1);
//        System.out.println("L_G:" + w1.toString());
    }

}
