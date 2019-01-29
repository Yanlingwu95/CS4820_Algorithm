import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Framework
{
	int n; // number of applicants (employers)

	int APrefs[][]; // preference list of applicants (n*n)
	int EPrefs[][]; // preference list of employers (n*n)

	ArrayList<MatchedPair> MatchedPairsList; // your output should fill this arraylist which is empty at start

	public class MatchedPair
	{
		int appl; // applicant's number
		int empl; // employer's number

		public MatchedPair(int Appl,int Empl)
		{
			appl=Appl;
			empl=Empl;
		}

		public MatchedPair()
		{
		}
	}

	// reading the input
	void input(String input_name)
	{
		File file = new File(input_name);
		BufferedReader reader = null;

		try
		{
			reader = new BufferedReader(new FileReader(file));

			String text = reader.readLine();

			String [] parts = text.split(" ");
			n=Integer.parseInt(parts[0]);

			APrefs=new int[n][n];
			EPrefs=new int[n][n];

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] aList=text.split(" ");
				for (int j=0;j<n;j++)
				{
					APrefs[i][j]=Integer.parseInt(aList[j]);
				}
			}

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] eList=text.split(" ");
				for(int j=0;j<n;j++)
				{
					EPrefs[i][j]=Integer.parseInt(eList[j]);
				}
			}

			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// writing the output
	void output(String output_name)
	{
		try
		{
			PrintWriter writer = new PrintWriter(output_name, "UTF-8");

			for(int i=0;i<MatchedPairsList.size();i++)
			{
				writer.println(MatchedPairsList.get(i).empl+" "+MatchedPairsList.get(i).appl);
			}

			writer.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public Framework(String []Args)
	{
		input(Args[0]);

		MatchedPairsList=new ArrayList<MatchedPair>(); // you should put the final stable matching in this array list

		/* NOTE
		 * if you want to declare that man x and woman y will get matched in the matching, you can
		 * write a code similar to what follows:
		 * MatchedPair pair=new MatchedPair(x,y);
		 * MatchedPairsList.add(pair);
		*/

		//YOUR CODE STARTS HERE

		//Declare important variables
        //int n = APrefs.length;
		//This LinkedList can help to idenfy a free emolyer.
        LinkedList<Integer> freeE = new LinkedList<>();
		//This next array help to identify the highest-ranked application for a empolyer.
        int[] next = new int[n];
		//This curr array help to mark whether an applicant is hired or which empolyer hire her.
        int[] curr = new int[n];
		// This ranking array is help to decide the rank of a employer in the sorted order of one applicant's preference.
        int[][] ranking = new int[n][n];

        //Initilize the variables
		Arrays.fill(next, 0); //Set the initial value of next array to 1;
		Arrays.fill(curr, -1); //Set the initial values of curr to -1, meaning not hired.
		for(int i = 0; i < n; i++)
			freeE.add(i);
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				ranking[i][APrefs[i][j]] = j;
			}
		}

        //matching code
		int e = -1, ee = -1;
		int a = -1;
		while(freeE.size() != 0) {
		    e = freeE.removeFirst();
		    a = EPrefs[e][next[e]++];
		    if(curr[a] == -1) {
		        curr[a] = e;
            }
            else {
                ee = curr[a]; //The employer who is hired a currently;
                if(ranking[a][ee] < ranking[a][e]) { //applicant prefer ee to e;
                    // e remines free
                    freeE.add(e);//e is still free;
                }
                else {
                    curr[a] = e; // e hire a;
                    freeE.add(ee); //ee becomes free;
                }
            }
		}
		// add the matched pairs to MatchedPairsList;
		for(int i =0; i < n; i++) {
			//MatchedPair pair = new MatchedPair(i, curr[i]);
			MatchedPair pair = new MatchedPair(curr[i],i);
            MatchedPairsList.add(pair);
        }
		//YOUR CODE ENDS HERE

		output(Args[1]);
	}

	public static void main(String [] Args) // Strings in Args are the name of the input file followed by the name of the output file
	{
		new Framework(Args);
	}
}
