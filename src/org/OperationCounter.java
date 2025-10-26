package org;
public class OperationCounter {
    public long comparisons = 0;
    public long heapOps = 0;
    public long unions = 0;
    public long finds = 0;

    public void reset() {
        comparisons = heapOps = unions = finds = 0;
    }
}

