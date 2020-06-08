
import java.util.*;

public class Dijkstra {

    graph finalGraph;

    int minDistance(int dist[], Boolean sptSet[], int V) {
        int min = Integer.MAX_VALUE, min_index = -1;
        for (int v = 0; v < V; v++) {
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        }
        return min_index;
    }

    boolean isBidirectionalEdge(String vertex1, String vertex2) {
        for (int i = 0; i < finalGraph.edges.size(); i += 2) {
            if (finalGraph.edges.get(i).equals(vertex1) && finalGraph.edges.get(i + 1).equals(vertex2)) {
                return true;
            }
        }
        return false;
    }

    int getEdgeName(String[] edges, String vertex1, String vertex2, String frameTitle) {

        for (int i = 0; i < edges.length; i += 2) {

            if ((frameTitle.equals(" directed input Graph View") && edges[i].equals(vertex1) && edges[i + 1].equals(vertex2))
                    || (frameTitle.equals(" undirected input Graph View") && ((edges[i].equals(vertex1) && edges[i + 1].equals(vertex2))
                    || edges[i + 1].equals(vertex1) && edges[i].equals(vertex2)))) {
                return i / 2;
            }
        }
        return -1;
    }

    int[] dijkstra(int representative[][], int src, int V, List<String> NameOfVertices,
            String frameTitle2, String[] Edges) {
        int edgeIndex;

        int dist[] = new int[V]; //  will hold the shortest distance from src to  other vertices
        Boolean sptSet[] = new Boolean[V]; //sptSet[i] will true if vertex i is included in shortest path

        // Initialize all distances as INFINITE and stpSet[] as false 
        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }

        // Distance of source vertex from itself is 0 
        dist[src] = 0;

        // Find shortest path for all vertices 
        for (int count = 0; count < V; count++) {
            graph g;
            int result[] = new int[V];
            Arrays.fill(result, 0);
            if (frameTitle2.equals(" directed input Graph View")) {
                g = new directedGraph();
            } else {
                g = new undirectedGraph();
            }

            g.setListVertix(NameOfVertices);

            int u = minDistance(dist, sptSet, V);  // get the minimum distance vertex from the set of vertices 
            sptSet[u] = true; // Mark the  vertex as visited

            for (int r = 0; r < V; r++) {
                for (int c = 0; c < V; c++) {
                    if (sptSet[r] && representative[r][c] != 0 && dist[c] != Integer.MAX_VALUE //&& !isBidirectionalEdge(NameOfVertices.get(c),NameOfVertices.get(r))
                            && (dist[r] + representative[r][c]) == dist[c]) {
                        edgeIndex = getEdgeName(Edges, NameOfVertices.get(r), NameOfVertices.get(c), frameTitle2);
                        g.addEdge(Integer.toString(representative[r][c]), edgeIndex, NameOfVertices.get(r), NameOfVertices.get(c));
                        result[r] = 0;
                        result[c] = 0;
                        if (!sptSet[c]) {
                            result[c] = 1;
                        }

                    }
                }
            }

            // Update dist value of the adjacent vertices of  u 
            for (int v = 0; v < V; v++) {
                if (!sptSet[v] && representative[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + representative[u][v] < dist[v]) {
                    dist[v] = dist[u] + representative[u][v];
                    edgeIndex = getEdgeName(Edges, NameOfVertices.get(u), NameOfVertices.get(v), frameTitle2);
                    g.addEdge(Integer.toString(representative[u][v]), edgeIndex, NameOfVertices.get(u), NameOfVertices.get(v));
                    result[v] = 1;

                }
            }
            if (count == V - 1) {
                g.drawGraph("Dijkstra output Graph", result);
            } else {
                g.drawGraph("step " + count, result);
            }

        }

        return dist;
    }

}
