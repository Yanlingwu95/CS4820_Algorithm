import java.util.*;

public class Graph {
    //Construction function
    int N , M; // N-># of nodes, M-># of edges
    Edge edge[];//store all edges
    //Create a edge class to represent the graph edge
    class Edge implements Comparable<Edge> {
        int stn, des, weight;

        public int compareTo(Edge compareEdge) {
            return this.weight-compareEdge.weight;
        }
    };

    //Create a graph with N nodes and M edges
    public Graph(int n, int m) {
        N = n;
        M = m;
        edge = new Edge[M];
        //Initial the edge[]
        for (int i = 0; i < M; i++) {
            edge[i] = new Edge();
        }
    }


    //main function
    public static void main(String[] Args) {
        //define variables
        int N = 4; //# nodes
        int M = 5;;//# edges
        Graph graph = new Graph(N, M);




    }

}
