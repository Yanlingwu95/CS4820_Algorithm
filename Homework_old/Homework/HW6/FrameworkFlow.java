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
	// for every node numbered i adjacencyList[i][.] contains all edges that have node numbered i as its tail
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
		
		
		//END OF YOUR CODE

		output(Args[1]);
	}

	public static void main(String [] Args) //Strings in Args are the name of the input file followed by the name of the output file
	{
		new FrameworkFlow(Args);
	}
}
