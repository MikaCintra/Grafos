package grafos.algorithms;

import grafos.model.Edge;
import grafos.model.Graph;

import java.util.PriorityQueue;

public class PrimMST {

    public static long mstCost(Graph g, int start) {
        int n = g.n;
        boolean[] inMST = new boolean[n + 1];

        class Node implements Comparable<Node> {
            int v; int w; Node(int v, int w) { this.v = v; this.w = w; }
            @Override public int compareTo(Node o) { return Integer.compare(this.w, o.w); }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, 0));
        long cost = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int u = cur.v;
            if (inMST[u]) continue;
            inMST[u] = true;
            cost += cur.w;
            for (Edge e : g.adjUndir.get(u)) {
                if (!inMST[e.v]) pq.add(new Node(e.v, e.w));
            }
        }
        return cost;
    }
}
