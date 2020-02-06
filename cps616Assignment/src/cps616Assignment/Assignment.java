
package cps616Assignment;
import java.util.Random;

public class Assignment{
	final static int N = 1000000;			// Size of the array
	final static int MAXVALUE = 10*N;	// maximum value in the array
	final static int AVERAGEOVER = 1000000 / N;	//Number of iterations over which to average performance
	public static void main(String args[]){
	int arrayA[] = new int[N];
	int arrayB[] = new int[N];
	Random rand = new Random();
	for (int i = 0; i< N; i++){ 
		arrayA[i] = rand.nextInt(MAXVALUE);
		arrayB[i] = arrayA[i];
	}
	Sort.slowsort(arrayA);
	Sort.fastsort(arrayB);
	System.out.println("Slowsort array:");
	for (int i = 0; i < N; i++)
        System.out.print(arrayA[i]+" ");
	System.out.println("\nFastsort array:");
	for (int i = 0; i < N; i++)
        System.out.print(arrayB[i]+" ");
	long startTime;
	long estimatedTime1 = 0;
	long estimatedTime2 = 0;
	for (int i = 0; i < AVERAGEOVER; i++){
		for (int j = 0; j< N; j++){ 
			arrayA[j] = rand.nextInt(MAXVALUE);
			arrayB[j] = arrayA[j];
		}
		startTime = System.nanoTime();
		Sort.slowsort(arrayA);
		estimatedTime1 += System.nanoTime() - startTime;
		startTime = System.nanoTime();
		Sort.fastsort(arrayB);
		estimatedTime2 += System.nanoTime() - startTime;
	}
	System.out.println("\nSlowsort array time for " + N + " integers: " + estimatedTime1 );
	System.out.println("Fastsort array time for " + N + " integers: " + estimatedTime2 );
	
}}

