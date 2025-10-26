package org;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class IOUtils {

    // ✅ JSON writer
    public static void writeResults(String path, List<Map<String, Object>> results) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n  \"results\": [\n");
        for (int i = 0; i < results.size(); i++) {
            Map<String, Object> r = results.get(i);
            sb.append("    {\n");
            sb.append("      \"graphIndex\": ").append(r.get("graphIndex")).append(",\n");
            sb.append("      \"vertices\": ").append(r.get("vertices")).append(",\n");
            sb.append("      \"edgesCount\": ").append(r.get("edgesCount")).append(",\n");
            sb.append("      \"primCost\": ").append(r.get("primCost")).append(",\n");
            sb.append("      \"kruskalCost\": ").append(r.get("kruskalCost")).append(",\n");
            sb.append("      \"primTimeMs\": ").append(r.get("primTimeMs")).append(",\n");
            sb.append("      \"kruskalTimeMs\": ").append(r.get("kruskalTimeMs")).append("\n");
            sb.append("    }");
            if (i < results.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("  ]\n}");
        Files.writeString(Path.of(path), sb.toString());

        // ✅ Write to CSV
        writeCSV("results.csv", results);
    }

    // ✅ CSV writer
    private static void writeCSV(String path, List<Map<String, Object>> results) throws IOException {
        StringBuilder csv = new StringBuilder();
        csv.append("Graph,Vertices,Edges,PrimCost,KruskalCost,PrimTimeMs,KruskalTimeMs\n");
        for (Map<String, Object> r : results) {
            csv.append("graph").append(r.get("graphIndex")).append(",");
            csv.append(r.get("vertices")).append(",");
            csv.append(r.get("edgesCount")).append(",");
            csv.append(r.get("primCost")).append(",");
            csv.append(r.get("kruskalCost")).append(",");
            csv.append(r.get("primTimeMs")).append(",");
            csv.append(r.get("kruskalTimeMs")).append("\n");
        }
        Files.writeString(Path.of(path), csv.toString());
        System.out.println("✅ Results written to results.csv");
    }
}
