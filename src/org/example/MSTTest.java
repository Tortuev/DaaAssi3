package org.example;

import org.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MSTTest {

    // Генерация простого тестового графа
    private Graph createTestGraph() {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 10);
        g.addEdge(0, 2, 5);
        g.addEdge(1, 2, 2);
        g.addEdge(1, 3, 4);
        g.addEdge(2, 3, 3);
        g.addEdge(3, 4, 1);
        return g;
    }

    // Проверяем, что Прим и Краскал дают одинаковую стоимость MST
    @Test
    public void testMSTCostEquality() {
        Graph g = createTestGraph();

        PrimMST.Result prim = PrimMST.run(g, 0);
        KruskalMST.Result kruskal = KruskalMST.run(g);

        assertEquals(prim.totalCost, kruskal.totalCost, "Стоимость MST должна быть одинаковой");
    }

    // Проверяем, что MST имеет правильное количество рёбер
    @Test
    public void testMSTEdgesCount() {
        Graph g = createTestGraph();

        PrimMST.Result prim = PrimMST.run(g, 0);
        KruskalMST.Result kruskal = KruskalMST.run(g);

        assertEquals(g.V - 1, prim.mstEdges.size(), "MST Прима должен иметь V-1 рёбер");
        assertEquals(g.V - 1, kruskal.mstEdges.size(), "MST Краскала должен иметь V-1 рёбер");
    }

    // Проверяем, что MST ацикличен (через UnionFind)
    @Test
    public void testMSTAcyclic() {
        Graph g = createTestGraph();

        KruskalMST.Result kruskal = KruskalMST.run(g);
        UnionFind uf = new UnionFind(g.V);

        for (Edge e : kruskal.mstEdges) {
            int ru = uf.find(e.u);
            int rv = uf.find(e.v);
            assertNotEquals(ru, rv, "MST не должно содержать циклов");
            uf.union(ru, rv);
        }
    }

    // Проверяем, что MST покрывает все вершины (связный)
    @Test
    public void testMSTConnected() {
        Graph g = createTestGraph();

        KruskalMST.Result kruskal = KruskalMST.run(g);
        UnionFind uf = new UnionFind(g.V);

        for (Edge e : kruskal.mstEdges) {
            uf.union(e.u, e.v);
        }

        int root = uf.find(0);
        for (int i = 1; i < g.V; i++) {
            assertEquals(root, uf.find(i), "Все вершины должны быть соединены в MST");
        }
    }

    // Проверяем, что время выполнения и операции не отрицательные
    @Test
    public void testMSTPerformanceStats() {
        Graph g = createTestGraph();

        PrimMST.Result prim = PrimMST.run(g, 0);
        KruskalMST.Result kruskal = KruskalMST.run(g);

        assertTrue(prim.timeMs >= 0, "Время выполнения Прима не должно быть отрицательным");
        assertTrue(kruskal.timeMs >= 0, "Время выполнения Краскала не должно быть отрицательным");

        assertTrue(prim.ops.heapOps >= 0, "Количество операций Прима >= 0");
        assertTrue(kruskal.ops.comparisons >= 0, "Количество сравнений Краскала >= 0");
        assertTrue(kruskal.ops.unions >= 0, "Количество объединений Краскала >= 0");
    }
}
