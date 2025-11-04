package grafos.io;

import grafos.model.Edge;
import grafos.model.Graph;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public class GraphReader {

    public static boolean VERBOSE = true;

    public static Graph readGraph(String filename) throws IOException {
        if (VERBOSE) System.out.println("Tentando abrir arquivo: " + filename);
        InputStream is = new FileInputStream(filename);
        if (filename.endsWith(".gz")) {
            if (VERBOSE) System.out.println("Arquivo é GZip, descompactando...");
            is = new GZIPInputStream(is);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        Graph g = null;
        Map<Long, Integer> undirectedMin = null;

        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) continue;
            char type = line.charAt(0);

            if (type == 'c') {
                continue;
            } else if (type == 'p') {
                String[] parts = line.trim().split("\\s+");
                int n = Integer.parseInt(parts[2]);
                int m = Integer.parseInt(parts[3]);
                g = new Graph(n);
                undirectedMin = new HashMap<>(Math.max(16, m / 2));
            } else if (type == 'a') {
                String[] parts = line.trim().split("\\s+");
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                int w = Integer.parseInt(parts[3]);
                if (g == null) continue;
                // dirigido
                g.adjDir.get(u).add(new Edge(u, v, w));
                g.arcCount++;
                // não dirigido (peso mínimo entre qualquer direção)
                int a = Math.min(u, v);
                int b = Math.max(u, v);
                long key = (((long)a) << 32) | (b & 0xffffffffL);
                Integer cur = undirectedMin.get(key);
                if (cur == null || w < cur) undirectedMin.put(key, w);
            }
        }

        br.close();

        if (g != null && undirectedMin != null) {
            for (Map.Entry<Long, Integer> e : undirectedMin.entrySet()) {
                long key = e.getKey();
                int a = (int)(key >> 32);
                int b = (int)(key & 0xffffffffL);
                int w = e.getValue();
                g.edgesUndir.add(new Edge(a, b, w));
                g.adjUndir.get(a).add(new Edge(a, b, w));
                g.adjUndir.get(b).add(new Edge(b, a, w));
            }
        }

        return g;
    }
}
