package knapsack;
import java.util.Scanner;

/**
  * Test program for the problem problem.
  * @author Sophie Quigley
  * @author PUT YOUR NAMES HERE
  * 
  */
public class Test {
    
    /**
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Problem problem;

        for (int i=10; i<=200; i+=20) {
            problem = new Problem(i,i);
            process(problem);
        }
        
        Scanner in = new Scanner(System.in);
        while (in.hasNext())    {
            // Read and print input Kanpsack
            problem = new Problem(in);
            process(problem);
        }
    }
    
    /**
     * Processes a knapsack Problem: prints it, solves it, and prints the solution
     * <br>DO NOT MODIFY
     * @param problem Knapsack Problem
     */
    private static void process(Problem problem) {
        int solution;
        System.out.println(problem);
        long time = System.nanoTime();
        solution = problem.solve();
        time = System.nanoTime() - time;
        System.out.println("The greatest value you can achieve is: " + solution);
        System.out.println("was calculated in " + time + "nanoseconds.\n");   
    }
    
      
}
