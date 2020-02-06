package knapsack;
import java.util.Random;
import java.util.Scanner;
import java.lang.Math;
/**
  * Knapsack Problems consist of a knapsack capacity,
  * and a list of Items that can possibly be included in the knapsack, 
  * each with a weight and value.
  * @author Sophie Quigley
  * @author PUT YOUR NAMES HERE
  * 
  */

public class Problem {
    /**
     * Capacity of knapsack in Problem
     */
     int capacity;
    /**
    * Total number of Items to try to fit into knapsack
    */
    int totalItems;
    /**
     * Items to try to fit into knapsack
     */
    Item[] items;
    int[][] mfk;
    /**
     * 
     * Creates a new knapsack Problem whose parameters will be read from the Scanner.
     * <br>Input format consists of non-negative integers separated by white space as follows:
     * <ul>
     * <li>First positive integer specifies the knapsack capacity
     * <li>Next positive integer specifies the number of items n
     * <li>Next 2Xn integers specify the items, one by one,
     *      listing the weight and value of each item.
     * </ul>
     * Incorrect knapsack information will simply be skipped.
     * @param in Scanner used to read knapsack problem description
     */
    
    Problem(Scanner in) {
        // Read knapsack capacity
        capacity = in.nextInt();
        while (capacity<0)  {
            System.out.println("Error: knapsack capacity must be on-negative");
            capacity = in.nextInt(); 
        }
            
        // Get total number of items
        totalItems = in.nextInt();
        while (totalItems < 0) {
            System.out.println("Error: number of items must be non-negative");
            totalItems = in.nextInt();
            return;
        }
        items = new Item[totalItems];
        mfk = new int[totalItems][totalItems];
        // Read weights and values
        for (int i=0; i<totalItems; i++) 
            items[i] = new Item(in);
        for(int i=0; i<totalItems; i++){
        	for(int j=0; j<totalItems; j++){
        		if (i== 0 || j == 0) mfk[i][j] = 0;
        		else mfk[i][j] = -1;
        	}
        }
    }

    /**
     * Creates a randomly generated knapsack Problem according to specifications.
     * or a trivial Problem (zero capacity or no totalItems) if the specifications are faulty.
     * @param capacity Capacity of knapsack
     * @param totalItems Total number of Items to try to fit in knapsack 
     */
     Problem( int capacity, int totalItems) {
        if (capacity < 0) {
            System.out.println("Error: knapsack capacity must be non-negative");
            this.capacity = 0;       
        }
        else
            this.capacity = capacity;
     
        if (totalItems<0) {
            System.out.println("Error: number of items must be non-negative");
            this.totalItems = 0;       
        }
        else
            this.totalItems = totalItems;
        if (this.totalItems == 0)  return;

        // Create arrays based on number of totalItems    
        this.items = new Item[this.totalItems]; 
        
        // Create weights randomly
        Random rand = new Random();
        int randmax = capacity;
        for (int i=0; i<this.totalItems; i++) {
            this.items[i] = new Item(rand,randmax); 
        }
        mfk = new int[totalItems][capacity];
        for(int i=0; i<totalItems; i++){
        	for(int j=0; j<totalItems; j++){
        		if (i== 0 || j == 0) mfk[i][j] = 0;
        		else mfk[i][j] = -1;
        	}
        }
    }
     
    /**
     * Returns a string describing the Problem
     * @return Description of Problem
     */    
    @Override
    public String toString() {
        String result = "Problem: try to fit " + totalItems + " items:\n";
        for (int i=0; i<totalItems; i++)
            result += "- " + items[i]+"\n";
        result += "into knapsack of capacity " + capacity;
        return result;
    }
    
    /**
     * Returns the Solution to the knapsack Problem
     * @return Solution for knapsack Problem
     */
    public int solve() {
        return MFKnapsack(totalItems - 1, capacity - 1);     
    }
    
    /**
     * Finds best combination of items 0 to n that fits in capacity
     * @param capacity Remaining capacity of knapsack 
     * @param n Index of new Item to be fitted into knapsack (or not)
     * @return Solution which maximises the value of the knapsack
     */
    private int MFKnapsack (int i, int j){
    	if (mfk[i][j] >= 0) return mfk[i][j];
    	if (items[i].getWeight() > j) return MFKnapsack(i-1,j);
    	else mfk[i][j] = Math.max(MFKnapsack(i-1,j),(MFKnapsack(i - 1, j - items[i].getWeight()) + items[i].getValue()));
    	return mfk[i][j];
    }
}
