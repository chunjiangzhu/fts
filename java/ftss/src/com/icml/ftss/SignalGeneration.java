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

package com.uconn.ftss;

import org.jgrapht.*;
import org.jgrapht.alg.spanning.GreedyFTSparsifier;
import org.jgrapht.generate.*;
import org.jgrapht.graph.*;
import org.jgrapht.graph.builder.*;
import org.jgrapht.io.CSVFormat;
import org.jgrapht.io.CSVImporter;
import org.jgrapht.io.ComponentNameProvider;
import org.jgrapht.io.ExportException;
import org.jgrapht.io.GraphExporter;
import org.jgrapht.io.ImportException;
import org.jgrapht.io.IntegerComponentNameProvider;
import org.jgrapht.io.MatrixExporter;
import org.jgrapht.traverse.MyRandomWalkIterator;
import org.jgrapht.traverse.RandomWalkIterator;
import org.jgrapht.util.*;
import org.junit.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Tests for the {@link RandomWalkIterator} class.
 * 
 * @author Assaf Mizrachi
 *
 */
public class SignalGeneration
{

	public <E> CSVImporter<String, E> createImporter(
	        Graph<String, E> g, CSVFormat format, Character delimiter)
	    {
	        return new CSVImporter<>(
	            (l, a) -> l, (f, t, l, a) -> g.getEdgeSupplier().get(), format, delimiter);
	    }
	
	public <E> Graph<String, E> readGraphfromFile(
            File input, CSVFormat format, Character delimiter, Class<? extends E> edgeClass,
            boolean directed, boolean weighted)
            throws ImportException, FileNotFoundException
        {
            Graph<String, E> g;
            if (directed) {
                if (weighted) {
                    g = new DirectedWeightedPseudograph<>(edgeClass);
                } else {
                    g = new DirectedPseudograph<>(edgeClass);
                }
            } else {
                if (weighted) {
                    g = new WeightedPseudograph<>(edgeClass);
                } else {
                    g = new Pseudograph<>(edgeClass);
                }
            }

            CSVImporter<String, E> importer = createImporter(g, format, delimiter);

            if ((format == CSVFormat.EDGE_LIST || format == CSVFormat.ADJACENCY_LIST) && weighted) {
                importer.setParameter(CSVFormat.Parameter.EDGE_WEIGHTS, true);
            }

            importer.importGraph(g, new FileReader(input));

            return g;
        }
   
    // Chunjiang added 20190109
    /**
     * Generate signal and y for a given graph.
     * @throws ExportException 
     * @throws ImportException 
     * @throws IOException 
     */
    @Test
    public void generate() throws ExportException, ImportException, IOException
    {
    	
    	String dir = "C:\\Users\\cjz18001\\Downloads\\";
    	String inputFilename = "facebook_combined_weighted.txt";
    	// parameter for standard deviation of noise added to the signal
        double sigma = 1;
        
        // Facebook
        String filePath = dir + inputFilename;
    	File file = new File(filePath);

        Graph<String, DefaultWeightedEdge> graph =
            readGraphfromFile(file, CSVFormat.EDGE_LIST, ' ', DefaultWeightedEdge.class, false, true);
        

        System.out.println(graph.vertexSet().size());
        System.out.println(graph.edgeSet().size());
        
        
        
        
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
        	System.out.println("signal " + node + ":" + signal.get(node));
        }
        
        
        // add zero-centered Gaussian perturbation to form the y
        Map<String, Double> y = new HashMap<String,Double>();
        Random gaussian = new Random();
        
        for(String node: signal.keySet()) {
        	y.put(node, signal.get(node) + gaussian.nextGaussian() * sigma);
        	System.out.println("y " + node + ":" + y.get(node));
        }
        
        // tilde_f = (lambda * L_G + I)^{-1} * y
        
        // output y
        FileWriter fw = new FileWriter(new File(dir + "y-0.txt"));
        exportVector(graph, y, fw);
        fw.close();
        
        // output signal
        fw = new FileWriter(new File(dir + "signal-0.txt"));
        exportVector(graph, signal, fw);
        fw.close();
        
        
    }

    public void exportVector(Graph<String, DefaultWeightedEdge> g, Map<String, Double> vector, Writer writer)
    {
        PrintWriter out = new PrintWriter(writer);

        // assign ids in vertex set iteration order
        for (String from : g.vertexSet()) {
            double value = vector.get(from);
            out.println(Double.toString(value));
        }

        out.flush();
    }
}
