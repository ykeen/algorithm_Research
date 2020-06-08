
import java.util.*;
import java.util.LinkedList;
import javax.swing.JTextField;

class MaxFlow {

    int V; //Number of vertices in graph 

    List<String> convertToEdges(List<Integer> path, List<String> verticesNames) {
        List<String> Edges = new ArrayList<>();
        int j = path.size() - 2;
        for (int i = path.size() - 1; i > 0; i--) {
            //  0 1 1 2 2 3 
            Edges.add(verticesNames.get(path.get(i)));
            Edges.add(verticesNames.get(path.get(j--)));
        }
        return Edges;
    }

    boolean bfs(int rGraph[][], int s, int t, int parent[]) {
        // Create a visited array and mark all vertices as not visited
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; ++i) {
            visited[i] = false;
        }

       
       
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;  // source vertex as visited 
        parent[s] = -1;

        //BFS Loop 
        while (queue.size() != 0) {
            int u = queue.poll();

            for (int v = 0; v < V; v++) {
                if (visited[v] == false && rGraph[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;
                }
            }
        }
        return (visited[t] == true);
    }

    int fordFulkerson(int graph[][], int s, int t, JTextField jfield, List<String> verticesNames, String frameTitle, String[] inputGraphEdges) {
        int u, v;
        List<String> finalGraphEdges = new ArrayList<>();
        V = Integer.parseInt(jfield.getText());
        
        int rGraph[][] = new int[V][V];

        for (u = 0; u < V; u++) {
            for (v = 0; v < V; v++) {
                rGraph[u][v] = graph[u][v];
            }
        }

        
        int parent[] = new int[V]; // This array is filled by BFS to store path

        int max_flow = 0; 
        int count = 0;
        
        while (bfs(rGraph, s, t, parent)) {
            graph g;
            if (frameTitle.equals(" directed input Graph View")) {
                g = new directedGraph();
            } else {
                g = new undirectedGraph();
            }
            List<Integer> path = new ArrayList<>();

            
            int path_flow = Integer.MAX_VALUE;
            int i = 0;
            
            path.add(t);
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                path.add(u);
                path_flow = Math.min(path_flow, rGraph[u][v]);
            }

            
            // update residual weights of the edges 
            for (v = t; v != s; v = parent[v]) {
                u = parent[v];
                rGraph[u][v] -= path_flow;

            }

            List<String> stepGraphEdges = convertToEdges(path, verticesNames);
            finalGraphEdges.addAll(stepGraphEdges);
            g.setVertixNo(V);
            g.setWeights(rGraph);
            g.setListVertix(verticesNames);
            g.addEdges(finalGraphEdges,inputGraphEdges,frameTitle);
            g.drawGraph("step " + count, null);
          
           
            max_flow += path_flow; // Add path flow to overall flow 
            count++;
        }

      
        return max_flow;
    }
}
