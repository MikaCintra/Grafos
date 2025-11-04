package grafos.model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    public final int n;                 // número de vértices
    public int arcCount;                // número de arcos dirigidos (entrada)
    public final List<Edge> edgesUndir; // arestas não dirigidas (para Kruskal)
    public final List<List<Edge>> adjDir;   // adjacência dirigida (para Dijkstra)
    public final List<List<Edge>> adjUndir; // adjacência não dirigida (para Prim)

    public Graph(int n) {
        this.n = n;
        this.arcCount = 0;
        this.edgesUndir = new ArrayList<>();
        this.adjDir = new ArrayList<>(n + 1);
        this.adjUndir = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            adjDir.add(new ArrayList<>());
            adjUndir.add(new ArrayList<>());
        }
    }

    // Utilitário para testes: adiciona uma aresta não dirigida
    public void addUndirectedEdge(int u, int v, int w) {
        edgesUndir.add(new Edge(Math.min(u, v), Math.max(u, v), w));
        adjUndir.get(u).add(new Edge(u, v, w));
        adjUndir.get(v).add(new Edge(v, u, w));
        // também reflete nas estruturas dirigidas bidirecionalmente
        adjDir.get(u).add(new Edge(u, v, w));
        adjDir.get(v).add(new Edge(v, u, w));
    }
}
