package org;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        List<Graph> graphs = readGraphs("resources/input.json");
        if (graphs.isEmpty()) {
            System.out.println("⚠️ Не удалось загрузить графы из input.json");
            return;
        }

        List<Map<String, Object>> results = new ArrayList<>();
        int index = 1;

        for (Graph g : graphs) {
            PrimMST.Result prim = PrimMST.run(g, 0);
            KruskalMST.Result kruskal = KruskalMST.run(g);

            System.out.println("Graph " + index + " (" + g.V + " vertices, " + g.edges.size() + " edges)");
            System.out.println("  Prim MST cost: " + prim.totalCost);
            System.out.println("  Kruskal MST cost: " + kruskal.totalCost);
            System.out.println("  Prim time: " + prim.timeMs + " ms");
            System.out.println("  Kruskal time: " + kruskal.timeMs + " ms\n");

            Map<String, Object> one = new LinkedHashMap<>();
            one.put("graphIndex", index++);
            one.put("vertices", g.V);
            one.put("edgesCount", g.edges.size());
            one.put("primCost", prim.totalCost);
            one.put("kruskalCost", kruskal.totalCost);
            one.put("primTimeMs", prim.timeMs);
            one.put("kruskalTimeMs", kruskal.timeMs);
            results.add(one);
        }

        IOUtils.writeResults("resources/output.json", results);
        System.out.println("✅ Results written to output.json");
    }

    // ---------- Чтение графов из input.json ----------
    private static List<Graph> readGraphs(String filename) {
        List<Graph> graphs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line.trim());
            String content = sb.toString().replaceAll("\\s+", "");

            // Разбиваем на блоки графов
            String[] parts = content.split("\\{\"type\":");
            for (int i = 1; i < parts.length; i++) {
                String block = parts[i];

                int vertices = extractInt(block, "\"vertices\":", ",");
                if (vertices <= 0) continue;

                Graph g = new Graph(vertices);

                if (!block.contains("\"edges\":[")) continue;
                String edgesSection = block.split("\"edges\":\\[")[1].split("]")[0];
                String[] edges = edgesSection.split("\\},\\{");

                for (String e : edges) {
                    int u = extractInt(e, "\"u\":", ",");
                    int v = extractInt(e, "\"v\":", ",");
                    int w = extractInt(e, "\"w\":", "[^0-9]");
                    if (u < vertices && v < vertices)
                        g.addEdge(u, v, w);
                }

                graphs.add(g);
            }

        } catch (Exception e) {
            System.out.println("⚠️ Ошибка при чтении JSON: " + e.getMessage());
        }
        return graphs;
    }

    // ---------- Извлечение числа ----------
    private static int extractInt(String src, String key, String end) {
        try {
            int start = src.indexOf(key);
            if (start == -1) return 0;
            start += key.length();
            StringBuilder num = new StringBuilder();
            for (int i = start; i < src.length(); i++) {
                char c = src.charAt(i);
                if (Character.isDigit(c)) num.append(c);
                else break;
            }
            return Integer.parseInt(num.toString());
        } catch (Exception e) {
            return 0;
        }
    }
}