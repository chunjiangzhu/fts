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
import org.jgrapht.io.CSVExporter;
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

import java.io.BufferedReader;
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
public class SparsifierGeneration
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
     * Generate.
     * @throws ExportException 
     * @throws ImportException 
     * @throws IOException 
     */
    @Test
    public void generate() throws ExportException, ImportException, IOException
    {
    	// working directory, input graph file name, flag to generate faults, fault file name and log file name
    	String dir = "C:\\Users\\cjz18001\\Downloads\\";
    	String inputFilename = "facebook_combined_weighted.txt";
    	boolean bGenFaults = false;
    	String faultFilename = "faults.txt";
    	String logFilename = "ftsslog.txt";
    	// number of faults allowed (e.g., 1,3,5,7), epsilon and rho
        int f = 1;
		double eps = 0.2;
		int rho = 20;
    	
    	
        // Facebook
        String filePath = dir + inputFilename;
    	File file = new File(filePath);

        Graph<String, DefaultWeightedEdge> graph =
            readGraphfromFile(file, CSVFormat.EDGE_LIST, ' ', DefaultWeightedEdge.class, false, true);
        System.out.println("loaded weighted graph " + filePath);
        
        
        // whether to generate the faults
        if(bGenFaults) {
	        Random gaussian = new Random();
	        // output random faulty edges over time
	        int numOfFaults = 0;
	        int times = 10;
	        int maxf = 100;
	        int m = graph.edgeSet().size();
	        FileWriter fw1 = new FileWriter(new File(dir + faultFilename));
	        PrintWriter out = new PrintWriter(fw1);
	        for(int i=1; i<= times; i++) {
	        	// for each time point, starts by number of faults, followed by each fault in a line
	        	numOfFaults = gaussian.nextInt(maxf+1);
	        	out.println(String.valueOf(numOfFaults));
	        	int[] faults = gaussian.ints(0, m).distinct().limit(numOfFaults).toArray();
	        	Arrays.sort(faults);
	        	for(int fault: faults)
	        		out.println(String.valueOf(fault));
	        	out.flush();
	        }
	        fw1.close();
        }
        
        

        System.out.println("# vertices: " + graph.vertexSet().size());
        System.out.println("# edges: " + graph.edgeSet().size());
        
        FileWriter fw1 = new FileWriter(new File(dir + logFilename));
        PrintWriter out = new PrintWriter(fw1);
        
        // output L_G_SPA, the sparse format of the original graph
        String exportFilePath = dir + "L_G_SPA.txt";
        GraphExporter<String, DefaultWeightedEdge> exporter1 =
                new MatrixExporter<>(MatrixExporter.Format.SPARSE_NORMALIZED_LAPLACIAN_MATRIX);
        FileWriter fw = new FileWriter(new File(exportFilePath));
        exporter1.exportGraph(graph, fw);
        fw.close();
        System.out.println("exported graoh to " + exportFilePath);
        out.println("exported graph to " + exportFilePath);
        
        // ftspa
		System.out.println("f: " + f + ", eps: " + eps + ", rho: " + rho);
		out.println("f: " + f + ", eps: " + eps + ", rho: " + rho);
		
		long tStart = System.nanoTime();
		GreedyFTSparsifier<String> ftSparsifier = new GreedyFTSparsifier<String>(graph, 0, eps, rho);
		Graph<String, DefaultWeightedEdge> ftspa = ftSparsifier.GetFTSparsifierAsGraph();
		long tEnd = System.nanoTime();
        double elapsedSeconds = (tEnd - tStart) / 1000000000.0;
		System.out.println("ftspa size: " +ftspa.edgeSet().size());
		out.println("ftspa size: " +ftspa.edgeSet().size());
		System.out.println("ftspa in seconds: " + elapsedSeconds);
		out.println("ftspa in seconds: " + elapsedSeconds);
        
		// output L_FTSPA_SPA, the sparse format of the ft sparsifier before updates
		exportFilePath = dir + "L_FTSPA_SPA.txt";
        exporter1 =
                new MatrixExporter<>(MatrixExporter.Format.SPARSE_NORMALIZED_LAPLACIAN_MATRIX);
        fw = new FileWriter(new File(exportFilePath));
        exporter1.exportGraph(ftspa, fw);
        fw.close();
        System.out.println("exported sparsifer to " + exportFilePath);
        out.println("exported sparsifer to " + exportFilePath);
        
        // G, spa and ftspa under updates
        List<DefaultWeightedEdge> edgeList = new ArrayList<DefaultWeightedEdge>(graph.edgeSet());
        BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(dir + faultFilename));
			String line = reader.readLine();
			int i=1;
			while (line != null) {
				int numOfFaults = Integer.valueOf(line);
				System.out.println(i + ". # faults: " + numOfFaults);
				out.println(i + ". # faults: " + numOfFaults);
				// update
				List<Integer> faults = new ArrayList<Integer>();
				int j=0;
				line = reader.readLine();
				while (line != null) {
					faults.add(Integer.valueOf(line));
					if(++j == numOfFaults)
						break;
					line = reader.readLine();
				}
				Graph<String,DefaultWeightedEdge> curGraph = (Graph<String, DefaultWeightedEdge>) ((AbstractBaseGraph)graph).clone();
				tStart = System.nanoTime();
				for(int fault : faults)
					if(!curGraph.removeEdge(edgeList.get(fault)))
						System.out.println("error in removing edge " + fault);
				tEnd = System.nanoTime();
	            elapsedSeconds = (tEnd - tStart) / 1000000000.0;
	    		System.out.println("G update time in seconds: " + elapsedSeconds);
	    		out.println("G update time in seconds: " + elapsedSeconds);
		    	
				// output L_G_SPA_ti, the sparse format of the graph at time point i
	    		exportFilePath = dir + "L_G_SPA_t" + i + ".txt";
	            exporter1 =
	                    new MatrixExporter<>(MatrixExporter.Format.SPARSE_NORMALIZED_LAPLACIAN_MATRIX);
	            fw = new FileWriter(new File(exportFilePath));
	            exporter1.exportGraph(curGraph, fw);
	            fw.close();
	            System.out.println("exported sparsifer to " + exportFilePath);
	            out.println("exported sparsifer to " + exportFilePath);
	            
		        
	            // sparsifier (from the scratch)
	        	tStart = System.nanoTime();
	    		ftSparsifier = new GreedyFTSparsifier<String>(curGraph, 0, eps, rho);
	    		Graph<String, DefaultWeightedEdge> spa = ftSparsifier.GetFTSparsifierAsGraph();
	    		tEnd = System.nanoTime();
	            elapsedSeconds = (tEnd - tStart) / 1000000000.0;
	    		System.out.println("spa size: " +spa.edgeSet().size());
	    		out.println("spa size: " +spa.edgeSet().size());
	    		System.out.println("spa update time in seconds: " + elapsedSeconds);
	    		out.println("spa update time in seconds: " + elapsedSeconds);
	    		
	    		// output L_SPA_ti, SPARsifier at time point i
	    		exportFilePath = dir + "L_SPA_t" + i + ".txt";
	            exporter1 =
	                    new MatrixExporter<>(MatrixExporter.Format.SPARSE_NORMALIZED_LAPLACIAN_MATRIX);
	            fw = new FileWriter(new File(exportFilePath));
	            exporter1.exportGraph(spa, fw);
	            fw.close();
	            System.out.println("exported sparsifer to " + exportFilePath);
	            out.println("exported sparsifer to " + exportFilePath);
	            
	            
	        	// FTsparsifier
	            Graph<String,DefaultWeightedEdge> curftspa = (Graph<String, DefaultWeightedEdge>) ((AbstractBaseGraph)ftspa).clone();
	        	tStart = System.nanoTime();
	        	for(int fault : faults) {
	        		DefaultWeightedEdge e = edgeList.get(fault);
	        		if(null == curftspa.removeEdge(graph.getEdgeSource(e), graph.getEdgeTarget(e)))
						System.out.println("not in ftspa: " + fault);
	        	}
	    		tEnd = System.nanoTime();
	            elapsedSeconds = (tEnd - tStart) / 1000000000.0;
	    		System.out.println("ftspa size: " +curftspa.edgeSet().size());
	    		out.println("ftspa size: " +curftspa.edgeSet().size());
	    		System.out.println("ftspa update time in seconds: " + elapsedSeconds);
	    		out.println("ftspa update time in seconds: " + elapsedSeconds);
	            
	    		// output L_FTSPA_ti, FTSPAsifier at time point i
	    		exportFilePath = dir + "L_FTSPA_t" + i + ".txt";
	            exporter1 =
	                    new MatrixExporter<>(MatrixExporter.Format.SPARSE_NORMALIZED_LAPLACIAN_MATRIX);
	            fw = new FileWriter(new File(exportFilePath));
	            exporter1.exportGraph(curftspa, fw);
	            fw.close();
	            System.out.println("exported sparsifer to " + exportFilePath);
	            out.println("exported sparsifer to " + exportFilePath);
	            
	            out.flush();
		            
				// read next line
				line = reader.readLine();
				i++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fw1.close();
        
        
    }
}
