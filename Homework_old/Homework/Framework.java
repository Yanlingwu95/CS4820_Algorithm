import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class Framework
{
	int n; // number of men (women)

	int MenPrefs[][]; // preference list of men (n*n)
	int WomenPrefs[][]; // preference list of women (n*n)

	ArrayList<MatchedPair> MatchedPairsList; // your output should fill this arraylist which is empty at start

	public class MatchedPair
	{
		int man; // man's number
		int woman; // woman's number

		public MatchedPair(int Man,int Woman)
		{
			man=Man;
			woman=Woman;
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

			MenPrefs=new int[n][n];
			WomenPrefs=new int[n][n];

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] mList=text.split(" ");
				for (int j=0;j<n;j++)
				{
					MenPrefs[i][j]=Integer.parseInt(mList[j]);
				}
			}

			for (int i=0;i<n;i++)
			{
				text=reader.readLine();
				String [] wList=text.split(" ");
				for(int j=0;j<n;j++)
				{
					WomenPrefs[i][j]=Integer.parseInt(wList[j]);
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
				writer.println(MatchedPairsList.get(i).man+" "+MatchedPairsList.get(i).woman);
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
		/** singlemen is a LinkedList of free men**/		
		LinkedList singleMen = new LinkedList();
		/** numbering the free men in LinkedList**/
		for (int i=0;i<n;i++){
			singleMen.addFirst(i);
		}
		
		/** Array next[i] represents the index of the next woman that man i wants to propose**/
		int[] next = new int[n];
		for (int i=0;i<next.length;i++){
			next[i] = 0;
		}
		
		/** Array current[i] represents the current boyfriend of woman i**/
		/** if woman i is single, current[i]=1314**/
		int[] current = new int[n];
		for (int i=0;i<current.length;i++){
			current[i] = 1314;	//1314 indicates single
		}
		
		/** n square array ranking of women's preference**/
		int[][] ranking = new int[n][n];
		for (int i=0;i<n;i++){
			for (int j=0;j<n;j++){
				ranking[i][WomenPrefs[i][j]] = j;
			}
		}
		
		/** Gale-Shapley algorithm **/
		int man, woman, partner;
		while(!singleMen.isEmpty()){
			man = (int)singleMen.getFirst();
			woman = MenPrefs[man][next[man]];
			next[man] += 1;
			if (current[woman]==1314){
				current[woman] = man;
				singleMen.removeFirst();
			}else{
				partner = current[woman];//current partner of women w
				//the smaller the ranking, the higher the priority
				if (ranking[woman][man]<ranking[woman][partner]){	
					current[woman] = man;
					singleMen.removeFirst();
					singleMen.addFirst(partner);
				}
			}
		}
		// store the matched couples into MatchedPairsList
		for (int i=0;i<n;i++){
			man = current[i];
			woman = i;
			MatchedPair pair=new MatchedPair(man,woman);
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
