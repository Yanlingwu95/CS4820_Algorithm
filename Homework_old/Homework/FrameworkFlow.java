import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class FrameworkFlow
{
	public class Edge
	{
		int headNode;
		int tailNode;
		int capacity = 0;
		int flow = 0;
		Edge originalEdge;
		boolean isForwardEdge;
		
		public Edge( int tN, int hN, int c )
		{
			tailNode = tN;
			headNode = hN;
			capacity = c;
		}
	}
	
	int n;
	int s; // the number of node s 
	int t; // the number of node t
	Edge adjacencyList[][];
	// for every node numbered i adjacencyList[i][.] contains all edges 
	// that have node numbered i as its tail
	int maxCapacity;

	void input(String input_name)
	{
		File file = new File(input_name);
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));

			String text = reader.readLine();
			n=Integer.parseInt( text );
			s=Integer.parseInt( reader.readLine() );
			t=Integer.parseInt( reader.readLine() );
			adjacencyList = new Edge[n][];
			String [] parts;
			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				parts=text.split(" ");
				adjacencyList[i]=new Edge[parts.length/2];
				for (int j=0;j<parts.length/2;j++)
				{
					adjacencyList[i][j] = new Edge( i, Integer.parseInt(parts[j*2]), Integer.parseInt(parts[j*2+1]) );
					if ( maxCapacity < adjacencyList[i][j].capacity )
						maxCapacity = adjacencyList[i][j].capacity;
				}
			}
			reader.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//writing the output
	void output(String output_name)
	{
		try
		{
			PrintWriter writer = new PrintWriter(output_name, "UTF-8");
			
			writer.println( n );
			writer.println( s );
			writer.println( t );

			for (int i=0;i<n;i++)
			{
				for (int j=0;j<adjacencyList[i].length;j++)
	                writer.print( adjacencyList[i][j].headNode + " " + adjacencyList[i][j].flow + " " );
				writer.println();
			}

			writer.close();
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
		
	public FrameworkFlow(String []Args)
	{
		input(Args[0]);

		//YOUR CODE GOES HERE
		Edge e;
		Edge ef;
		Edge eb;
		int k;
		int[] count = new int[n];
		int[][] forward = new int[n][n];
		int[][] backward = new int[n][n];
		ArrayList<Edge>[] residual = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			residual[i] = new ArrayList<Edge>();
		}

		// initialize the residual graph Gf
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < adjacencyList[i].length; j++) {
				e = adjacencyList[i][j];
				k = e.headNode;
				
				// forward edges
				ef = new Edge(i, k, e.capacity);
				ef.originalEdge = e;
				ef.isForwardEdge = true;
				residual[i].add(ef);
				forward[i][k] = count[i];
				count[i] += 1;
				
				// backward edges
				eb = new Edge(k, i, 0);
				eb.originalEdge = e;
				eb.isForwardEdge = false;
				residual[k].add(eb);
				backward[k][i] = count[k];
				count[k] += 1;
			}   
		}

		boolean find;
		int b;
		HashSet<Integer> visited;
		LinkedList<Integer> queue;
		Edge[] trace;
		ArrayList<Edge> path;
		
		// augment paths in the residual graph Gf
		while (true) {
			find = false;
			
			// breadth first search (BFS)
			visited = new HashSet<Integer>();
			visited.add(s);
			queue = new LinkedList<Integer>();
			queue.add(s);
			trace = new Edge[n];
			
			while (queue.size() > 0) {
				k = queue.remove();
				if (k == t) {
					find = true;
					break;
				} else {
					for (int i = 0; i < residual[k].size(); i++) {
						e = residual[k].get(i);
						if (!visited.contains(e.headNode) && e.capacity > 0) {
							visited.add(e.headNode);
							queue.add(e.headNode);
							trace[e.headNode] = e;
						}
					}
				}
			}
			
			if (find) {
				// recover the path
				path = new ArrayList<Edge>();
				k = t;
				while (k != s) {
					e = trace[k];
					path.add(e);
					k = e.tailNode;
				}
				
				// find the bottleneck
				b = path.get(0).capacity;
				for (int i = 1; i < path.size(); i++) {
					if (path.get(i).capacity < b) {
						b = path.get(i).capacity;
					}
				}
				
				// update the flow in the original graph and the capacity in the residual graph
				for (int i = 0; i < path.size(); i++) {
					e = path.get(i);
					if (e.isForwardEdge == true) {
						e.originalEdge.flow += b; // flow in the original graph
						e.capacity -= b; // forward edge in the residual graph
						residual[e.headNode].get(backward[e.headNode][e.tailNode]).capacity += b; // backward edge in the residual graph
					} else {
						e.originalEdge.flow -= b; // flow in the original graph
						e.capacity -= b; // backward edge in the residual graph
						residual[e.headNode].get(forward[e.headNode][e.tailNode]).capacity += b; // forward edge in the residual graph
					}
				}

			} else {
				break;
			}
		}
		//END OF YOUR CODE

		output(Args[1]);
	}

	public static void main(String [] Args) //Strings in Args are the name of the input file followed by the name of the output file
	{
		new FrameworkFlow(Args);
	}
}
