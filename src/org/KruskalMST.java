package org;

import java.util.*;

public class KruskalMST {
    public static class Result {
        public List<Edge> mstEdges = new ArrayList<>();
        public long totalCost = 0;
        public double timeMs = 0.0;
        public OperationCounter ops = new OperationCounter();
    }

    public static Result run(Graph g) {
        Result res = new Result();
        long t0 = System.nanoTime();

        List<Edge> sorted = new ArrayList<>(g.edges);
        sorted.sort((a, b) -> {
            res.ops.comparisons++;
            return Long.compare(a.w, b.w);
        });

        UnionFind uf = new UnionFind(g.V);
        for (Edge e : sorted) {
            int ru = uf.find(e.u);
            int rv = uf.find(e.v);
            res.ops.finds += 2;
            if (ru != rv) {
                uf.union(ru, rv);
                res.ops.unions++;
                res.mstEdges.add(e);
                res.totalCost += e.w;
                if (res.mstEdges.size() == g.V - 1) break;
            }
        }

        res.timeMs =  (Math.round(((System.nanoTime() - t0) / 1_000_000.0) * 100.0) / 100.0);

        return res;
    }
}
