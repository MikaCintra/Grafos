package grafos.algorithms;

import grafos.model.Edge;
import grafos.model.Graph;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra {

    public static long[] dijkstra(Graph g, int source) {
        int n = g.n;
        long[] dist = new long[n + 1];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[source] = 0;

        class Node implements Comparable<Node> {
            int v; long d; Node(int v, long d) { this.v = v; this.d = d; }
            @Override public int compareTo(Node o) { return Long.compare(this.d, o.d); }
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(source, 0));
        boolean[] visited = new boolean[n + 1];

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int u = cur.v;
            if (visited[u]) continue;
            visited[u] = true;
            for (Edge e : g.adjDir.get(u)) {
                int v = e.v;
                long nd = dist[u] + e.w;
                if (nd < dist[v]) {
                    dist[v] = nd;
                    pq.add(new Node(v, nd));
                }
            }
        }
        return dist;
    }

    public static long totalDistance(long[] dist) {
        long sum = 0;
        for (int i = 1; i < dist.length; i++) {
            if (dist[i] < Long.MAX_VALUE) sum += dist[i];
        }
        return sum;
    }
}
