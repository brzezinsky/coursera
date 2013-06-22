import java.io.*;
import java.util.*;
import java.util.List;
import java.util.ArrayList;

/**
 * The class <code>Solver</code> is an implementation of a greedy algorithm to solve the knapsack problem.
 *
 */
public class Solver {
    
    /**
     * The main class
     */
    public static void main(String[] args) {
        try {
            solve(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

		static class Item implements Comparable<Item>{
			final int cost;
			final int weight;
			final int ID;
			public Item(int cost, int weight, int ID) {
				this.cost = cost;
				this.weight = weight;
				this.ID = ID;
			}

			public int compareTo(Item o) {
				double here = (double)cost / weight;
				double there = (double)o.cost / o.weight;
				if (here > there) return -1;
				if (here < there) return 1;
				return 0;
			}
		}    
    /**
     * Read the instance, solve it, and print the solution in the standard output
     */
    public static void solve(String[] args) throws IOException {
        String fileName = null;
        
        // get the temp file name
        for(String arg : args){
            if(arg.startsWith("-file=")){
                fileName = arg.substring(6);
            } 
        }
        if(fileName == null)
            return;
        
        // read the lines out of the file
        List<String> lines = new ArrayList<String>();

        BufferedReader input =  new BufferedReader(new FileReader(fileName));
        try {
            String line = null;
            while (( line = input.readLine()) != null){
                lines.add(line);
            }
        }
        finally {
            input.close();
        }
        
        
        // parse the data in the file
        String[] firstLine = lines.get(0).split("\\s+");
        int items = Integer.parseInt(firstLine[0]);
        int capacity = Integer.parseInt(firstLine[1]);
				Item []it = new Item[items];

        for(int i=1; i < items+1; i++){
          String line = lines.get(i);
          String[] parts = line.split("\\s+");

          int a = Integer.parseInt(parts[0]);
         	int b = Integer.parseInt(parts[1]);
					it[i - 1] = new Item(a, b, i - 1);
        }
				Arrays.sort(it);
        // a trivial greedy algorithm for filling the knapsack
        // it takes items in-order until the knapsack is full
        int value = 0;
        int weight = 0;
        int[] taken = new int[items];

        for(int i=0; i < items; i++){
            if(weight + it[i].weight <= capacity){
                taken[it[i].ID] = 1;
                value += it[i].cost;
                weight += it[i].weight;
            } else {
                taken[it[i].ID] = 0;
            }
        }
        
        // prepare the solution in the specified output format
        System.out.println(value+" 0");
        for(int i=0; i < items; i++){
            System.out.print(taken[i]+" ");
        }
        System.out.println("");        
    }
}
