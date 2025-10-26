package org;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GraphGenerator {
    private static final Random rand = new Random();

    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"graphs\": [\n");

        sb.append(generateGraphs(4, 6, "small"));
        sb.append(",\n");
        sb.append(generateGraphs(10, 15, "medium"));
        sb.append(",\n");
        sb.append(generateGraphs(20, 30, "large"));

        sb.append("\n  ]\n}");

        try (FileWriter writer = new FileWriter("resources/input.json")) {
            writer.write(sb.toString());
        }

        System.out.println("✅ input.json создан!");
    }

    private static String generateGraphs(int minV, int maxV, String label) {
        int V = rand.nextInt(maxV - minV + 1) + minV;
        Graph g = generateConnectedGraph(V);
        StringBuilder sb = new StringBuilder();
        sb.append("    {\n");
        sb.append("      \"type\": \"").append(label).append("\",\n");
        sb.append("      \"vertices\": ").append(V).append(",\n");
        sb.append("      \"edges\": [\n");
        for (int i = 0; i < g.edges.size(); i++) {
            Edge e = g.edges.get(i);
            sb.append("        {\"u\": ").append(e.u)
                    .append(", \"v\": ").append(e.v)
                    .append(", \"w\": ").append(e.w).append("}");
            if (i < g.edges.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("      ]\n    }");
        return sb.toString();
    }

    private static Graph generateConnectedGraph(int V) {
        Graph g = new Graph(V);
        for (int i = 1; i < V; i++) {
            int parent = rand.nextInt(i);
            g.addEdge(i, parent, 1 + rand.nextInt(20));
        }
        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                if (rand.nextInt(4 * V) == 0)
                    g.addEdge(i, j, 1 + rand.nextInt(50));
            }
        }
        return g;
    }
}
