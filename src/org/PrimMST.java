package org;

import java.util.*;

public class PrimMST {
    public static class Result {
        public List<Edge> mstEdges = new ArrayList<>();
        public long totalCost = 0;
        public double timeMs = 0.0;
        public OperationCounter ops = new OperationCounter();
    }

    public static Result run(Graph g, int start) {
        Result res = new Result();
        long t0 = System.nanoTime();

        boolean[] used = new boolean[g.V];

        class Item {
            int from, to;
            long w;
            Item(int f, int t, long w) { from = f; to = t; this.w = w; }
        }

        PriorityQueue<Item> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a.w));
        used[start] = true;

        for (Edge e : g.adj.get(start)) {
            int to = e.u == start ? e.v : e.u;
            pq.add(new Item(start, to, e.w));
            res.ops.heapOps++;
        }

        while (!pq.isEmpty() && res.mstEdges.size() < g.V - 1) {
            Item cur = pq.poll();
            res.ops.heapOps++;
            if (used[cur.to]) continue;

            used[cur.to] = true;
            res.mstEdges.add(new Edge(cur.from, cur.to, cur.w));
            res.totalCost += cur.w;

            for (Edge e : g.adj.get(cur.to)) {
                int next = e.u == cur.to ? e.v : e.u;
                if (!used[next]) {
                    pq.add(new Item(cur.to, next, e.w));
                    res.ops.heapOps++;
                }
            }
        }

        res.timeMs = (Math.round(((System.nanoTime() - t0) / 1_000_000.0) * 100.0) / 100.0);
        return res;
    }
}

