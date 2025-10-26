Student: Talgatov Ramazan
University: Astana IT University
Course: Design and Analysis of Algorithms
Group: SE-2405
Date: October 2025
 Project Overview

This project implements Prim’s and Kruskal’s algorithms to find the Minimum Spanning Tree (MST) of weighted graphs.
It simulates optimizing a transportation network by connecting all vertices (cities) with minimal total edge cost, ensuring connectivity with no cycles.
 Project Structure

src/main/java/org/ → Java source code input.json → Input graph data output.json → Output results (JSON format) results.csv → Summary results for Excel chart pom.xml → Maven configuration file
 How to Run

    Open the project in IntelliJ IDEA (as a Maven project).
    Run the class:
    org.Main
    The program will automatically generate:
        output.json — detailed MST results
        results.csv — summary table for Excel visualization

 Experimental Results
Graph 	Vertices 	Edges 	Prim Cost 	Kruskal Cost 	Prim Time (ms) 	Kruskal Time (ms)
graph1 	5 	4 	45 	45 	0.34 	0.10
graph2 	13 	14 	79 	79 	0.22 	0.19
graph3 	27 	28 	281 	281 	0.45 	0.41
 Analysis

     Both algorithms produced identical MST total costs, confirming correctness.
     Prim’s algorithm is slightly faster on dense graphs due to efficient use of a priority queue.
     Kruskal’s algorithm performs better on sparse graphs, though it performs more edge comparisons.

Time Complexity:

    Prim — O(E log V)
    Kruskal — O(E log E)

 Excel Visualization

The results.csv file can be imported into Microsoft Excel to plot the performance comparison graph.
Use columns: Graph | PrimTimeMs | KruskalTimeMs and create a Clustered Column Chart to visualize the difference in execution times.
 Final Report

(Report Talgatov Ramazan.pdf)

The report contains:

    Algorithm explanations
    Implementation details
    Experimental analysis
    Comparison graph (from Excel)

Example Output:

--- BONUS SECTION: Graph Designed in Java ---
Graph edges:
0 - 1 (3)
0 - 2 (1)
1 - 3 (4)
2 - 3 (2)
3 - 4 (5)

=== Designed Graph Results ===
Prim total cost: 10.0
Kruskal total cost: 10.0
 Both algorithms produced identical MST costs.
 This confirms algorithmic correctness on custom graphs.
