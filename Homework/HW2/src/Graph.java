import java.util.*;

public class Graph {
    //Construction function
    int N , M, P; // N-># of nodes, M-># of edges
    Edge edges[];//store all edges

    //Create a edge class to represent the graph edge
    class Edge implements Comparable<Edge> {
        int stn, des, weight, id;

        Edge(){};
        Edge(int s, int d, int w, int no) {
            stn = s;
            des = d;
            weight = w;
            id = no;
        }
        public int compareTo(Edge compareEdge) {
            return this.weight-compareEdge.weight;
        }
    };

    //Create subset class
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

    private ArrayList<Edge> kruskalMST() {
        //Create the result's edge list and initial it
        ArrayList<Edge> mst = new ArrayList<>();

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
            Edge next = edges[i++];
            int u = find(subsets, next.stn);
            int v = find(subsets, next.des);
            if ( u != v) {
                mst.add(next);
                e++;
                union(subsets, u, v);
            }
        }
        return mst;
    }

    //***************************************//
    public ArrayList<ArrayList<Edge>> createGraph(Edge[] edges) {
        ArrayList<ArrayList<Edge>> G = new ArrayList<>();
        int len = edges.length * 2;
        //Initialize the arrayList
        for (int i = 0; i < len; i++) {
            G.add(new ArrayList<>());
        }
        for(Edge e : edges) {
            Edge one = new Edge(e.des, e.stn, e.weight, e.id);
            G.get(e.stn).add(e);
            G.get(e.des).add(one);
        }
        return G;
    }

    //
    private ArrayList<Edge> prim(ArrayList<ArrayList<Edge>> G) {
        if(G.isEmpty()) throw new NullPointerException("The Graph is empty");

        ArrayList<Edge> mst = new ArrayList<>();

        PriorityQueue<Edge> pq = new PriorityQueue<>((Object x, Object y) -> {
            Edge first = (Edge) x;
            Edge second = (Edge) y;
            if(first.weight < second.weight) return -1;
            else if(first.weight > second.weight) return 1;
            else return 0;
        });

        for(Edge e : G.get(1)) {
            pq.add(e);
           // System.out.println("The id of e is " + e.id);
        }

        boolean[] marked = new boolean[G.size() + 1];
        marked[1] = true;

       // System.out.println("while begins!");

        while(!pq.isEmpty()) {
            Edge e = pq.poll(); //get the first edge and remove it
            if(marked[e.stn] && marked[e.des])
                continue;
            marked[e.stn] = true;
            for(Edge edge : G.get(e.des)) {
                if(!marked[edge.des]) {
                    pq.add(edge);
                }
            }
            marked[e.des] = true;
            mst.add(e);
            //System.out.println("Should add the edge " + e.id);
        }
        return mst;
    }

    //main function
    public static void main(String[] Args) {
        //define variables
        Scanner scan = new Scanner(System.in);
        System.out.println("Please type the graph's information: ");

        //Get the first line to  initialize the graph
        int n = scan.nextInt();
        int m = scan.nextInt();
        int p = scan.nextInt();
        Graph graph = new Graph(n, m);

        /*//Get the edges information
        for (int i = 0; i < m; i++) {
            graph.edges[i].stn = scan.nextInt();
            graph.edges[i].des = scan.nextInt();
            graph.edges[i].weight = scan.nextInt();
            graph.edges[i].id = i+1;
        }*/

        graph.edges[0].stn = 1;
        graph.edges[0].des = 2;
        graph.edges[0].weight = 100;
        graph.edges[0].id = 1;

        // add edge 0-2
        graph.edges[1].stn = 3;
        graph.edges[1].des = 2;
        graph.edges[1].weight = 200;
        graph.edges[1].id = 2;

        // add edge 0-3
        graph.edges[2].stn = 1;
        graph.edges[2].des = 3;
        graph.edges[2].weight = 150;
        graph.edges[2].id = 3;

        ArrayList<Edge> mst = new ArrayList<>();

        if (p == 0){
            mst = graph.kruskalMST();
        }
        if (p == 1){
            ArrayList<ArrayList<Edge>> G = graph.createGraph(graph.edges);
            mst = graph.prim(G);
        }

        for (int i = 0; i < mst.size(); i++) {
            System.out.println(mst.get(i).id);
        }
    }
}
