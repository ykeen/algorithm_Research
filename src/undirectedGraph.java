
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.util.List;
import javax.swing.JFrame;
import org.apache.commons.collections15.Transformer;

public class undirectedGraph extends graph {

    Graph<String, String> graph = new UndirectedSparseGraph();

    @Override
    void generateRepMatrix(String[] Edges, String[] weight) {

        adjMatrix = new boolean[numVertices][numVertices];
        repMatrix = new int[numVertices][numVertices];
        // Convert Names to integer
        int m = 0, fromVertex, toVertex;;
        for (int j = 0; j < Edges.length; j += 2) {

            fromVertex = ListVertex.indexOf(Edges[j]);
            toVertex = ListVertex.indexOf(Edges[j + 1]);

            adjMatrix[fromVertex][toVertex] = true;
            repMatrix[toVertex][fromVertex] = Integer.parseInt(weight[m]);
            repMatrix[fromVertex][toVertex] = Integer.parseInt(weight[m]);
            addEdge(weight[m], (m++), Edges[j], Edges[j + 1]);

        }

    }

    @Override
    void addEdge(String edgeWeight, int edgeNumber, String edge1, String edge2) {

        graph.addEdge("Edge- " + (edgeNumber+1) + " (" + edgeWeight + " )", edge1, edge2);
        edges.add(edge1);
        edges.add(edge2);
    }

   @Override
    void addEdges(List<String> FinalGraphEdges,String[] inputGraphEdges,String frameTitle) {
        int fromVertex, toVertex;
        int EdgeNumber = 0;
        for (int j = 0; j < FinalGraphEdges.size(); j += 2) {
            fromVertex = ListVertex.indexOf(FinalGraphEdges.get(j));
            toVertex = ListVertex.indexOf(FinalGraphEdges.get(j + 1));
            EdgeNumber=getEdgeName(inputGraphEdges,FinalGraphEdges.get(j),FinalGraphEdges.get(j + 1),frameTitle);
            addEdge(Integer.toString(repMatrix[fromVertex][toVertex]), EdgeNumber, FinalGraphEdges.get(j), FinalGraphEdges.get(j + 1));
            EdgeNumber++;
        }
    }


    
 @Override
    void drawGraph(String frameTitle, int[] result) {

        Layout<String, String> layout = new CircleLayout(graph);
        layout.setSize(new Dimension(300, 300));

        BasicVisualizationServer<String, String> vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(300, 300));

        Transformer<String, Font> edgeFont = (String node) -> {
            Font font = new Font("Sans Serif", Font.PLAIN, 15);
            return font;
        };

        Transformer<String, Paint> vertexPaint = new Transformer<String, Paint>() {
            private final Color[] palette = {Color.PINK,  Color.MAGENTA, Color.CYAN, Color.ORANGE, Color.GREEN, Color.BLUE, Color.RED,
                Color.LIGHT_GRAY, Color.ORANGE, Color.black, Color.darkGray, Color.YELLOW, Color.DARK_GRAY, Color.magenta};
            int index;
            int vertixIndex;

            @Override
            public Paint transform(String j) {

                if (result == null) {
                    return Color.CYAN;

                } else {

                    vertixIndex = ListVertex.indexOf(j);
                    index = result[vertixIndex];
                    return palette[result[vertixIndex]];

                }

            }

        };

        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeFontTransformer(edgeFont);
        vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);

        
        JFrame frame = new JFrame(frameTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }

}
