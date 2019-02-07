import java.util.*;

public class Graph {
    //Construction function
    int N , M, P; // N-># of nodes, M-># of edges
    Edge edges[];//store all edges
    //Create a edge class to represent the graph edge
    class Edge implements Comparable<Edge> {
        int stn, des, weight, no;

        public int compareTo(Edge compareEdge) {
            return this.weight-compareEdge.weight;
        }
    };

    class Subset {
        int parent, rank;
    };

    //Create a graph with N nodes and M edges
    public Graph(int n, int m) {
        N = n;
        M = m;
        edges = new Edge[M];
        //Initial the edge[]
        for (int i = 0; i < M; i++) {
            edges[i] = new Edge();
        }
    }

    // A method to find a set containing u (path compression)
    public int find(Subset[] subsets, int u){
        if (subsets[u].parent != u) {
            subsets[u].parent = find(subsets, subsets[u].parent);
        }
        return subsets[u].parent;
    }

    // A method that Union two sets
    public void union(Subset[] subsets, int u, int v) {
        int uroot = find(subsets, u);
        int vroot = find(subsets, v);
        //compare the values of this two subset's rank
        //attach smaller rank tree to root of higher rank tree
        if (subsets[uroot].rank < subsets[vroot].rank) {
            subsets[uroot].parent = vroot;
        } else if (subsets[uroot].rank > subsets[vroot].rank){
            subsets[vroot].parent = uroot;
        }
        //If their ranks are same, then make vroot as root and
        // plus its rank by one
        else {
            subsets[uroot].parent = vroot;
            subsets[vroot].rank++;
        }
    }

    private void kruskalMST() {
        //Create the result's edge list and initial it
        Edge[] res = new Edge[N];
        for (int i = 0; i < N; i++) {
            res[i] = new Edge();
        }

        //Sort all edges in ascending order
        Arrays.sort(edges);

        //Allocate the memory for every node at first and initial them
        Subset[] subsets = new Subset[N+1];
        for (int i = 1; i < N+1; i++) {
            subsets[i] = new Subset();
            subsets[i].parent = i;
            subsets[i].rank = 0;
        }

        //
        int i = 0, e = 0;
        while(e < N - 1) {
            Edge next = new Edge();
            next = edges[i++];
            int u = find(subsets, next.stn);
            int v = find(subsets, next.des);
            if ( u != v) {
                res[e++] = next;
                union(subsets, u, v);
            }
        }
        for (i = 0; i < e; i++) {
            System.out.println(res[i].no);
            System.out.println(res[i].stn+" -- " +
                    res[i].des+" == " + res[i].weight + "The edge number is  "
                    + res[i].no);
        }
    }

    //main function
    public static void main(String[] Args) {
        /*//define variables
        Scanner scan = new Scanner(System.in);
        System.out.println("Please type the graph's information: ");

        //Use nextLine to receive the data
        //get the first line
        String str = new String();
        //Get the first line to  initialize the graph
        if (scan.hasNextLine()) {
            str = scan.nextLine();
        }
        String[] str_list = str.split(" ");
        int N = Integer.parseInt(str_list[0]);
        int M = Integer.parseInt(str_list[1]);
        int P = Integer.parseInt(str_list[2]);
        Graph graph = new Graph(N, M);

        //Get the edges information
        for (int i = 0; i < M; i++) {
            if (scan.hasNextLine()) {
                str = scan.nextLine();
            }
            str_list = str.split(" ");
            graph.edges[i].stn = Integer.parseInt(str_list[0]);
            graph.edges[i].des = Integer.parseInt(str_list[1]);
            graph.edges[i].weight = Integer.parseInt(str_list[2]);
            graph.edges[i].no = i+1;
        }

        if (P == 0)
            graph.kruskalMST();
        if (P == 1)
            graph.kruskalMST();*/

        int N = 3; //# nodes
        int M = 3;//# edges
        Graph graph = new Graph(N, M);

        graph.edges[0].stn = 1;
        graph.edges[0].des = 2;
        graph.edges[0].weight = 100;
        graph.edges[0].no = 1;

        // add edge 0-2
        graph.edges[1].stn = 3;
        graph.edges[1].des = 2;
        graph.edges[1].weight = 200;
        graph.edges[1].no = 2;

        // add edge 0-3
        graph.edges[2].stn = 1;
        graph.edges[2].des = 3;
        graph.edges[2].weight = 150;
        graph.edges[2].no = 3;

        graph.kruskalMST();
    }

}


/*
// add edge 1-3
        graph.edges[3].stn = 1;
                graph.edges[3].des = 3;
                graph.edges[3].weight = 15;
                graph.edges[3].no = 4;

                // add edge 2-3
                graph.edges[4].stn = 2;
                graph.edges[4].des = 3;
                graph.edges[4].weight = 4;
                graph.edges[4].no = 5;*/
