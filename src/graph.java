import java.util.ArrayList;
import java.util.List;


abstract class graph {
    
    int numVertices;
    boolean adjMatrix [][];
    int repMatrix[][];
    List<String> ListVertex;
     List<String> edges=new ArrayList<> ();
 
   abstract void generateRepMatrix(String[] Edges, String[] weight );  
   abstract void  drawGraph(String frameTitle, int[] result);

    void setVertixNo(int numVertices){
           
          this.numVertices= numVertices;

       }
        
    void setListVertix(List<String> ListVertex ){

         this.ListVertex = new ArrayList<>(ListVertex);
 
       }
     
       void setWeights(int rGraph[][]){
           
           repMatrix= new int[numVertices][numVertices]; 
           for(int i=0;i<numVertices;i++){
             for(int j=0;j<numVertices;j++){
                 this.repMatrix[i][j]=rGraph[i][j];
           
           }
       
       }
       }
       
     abstract void addEdges(List<String> Edges,String[] inputGraphEdges,String frameTitle);
     abstract void addEdge(String edgeWeight,int edgeNumber,String Edges1,String Edges2);
       
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
    
}
