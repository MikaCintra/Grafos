package grafos.algorithms;

import grafos.model.Edge;
import grafos.model.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMST {

    public static long mstCost(Graph g) {
        List<Edge> edges = new ArrayList<>(g.edgesUndir);
        Collections.sort(edges);
        UnionFind uf = new UnionFind(g.n);
        long cost = 0;
        for (Edge e : edges) {
            int ru = uf.find(e.u);
            int rv = uf.find(e.v);
            if (ru != rv) {
                uf.union(ru, rv);
                cost += e.w;
            }
        }
        return cost;
    }
}
