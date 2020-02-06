package cps616Assignment;

public class Sort {
	private static int N;
	public static void slowsort(int array[])
	{
		N = array.length - 1;
		for(int i = 0; i < N; i++){
			for(int j = 0; j < (N - i); j++){
				if (array[j] > array[j+1]){
				swap(array, j, j+1);
				}
			}
		}
	}
	public static void fastsort(int array[]){
		heapify(array);        
        for (int i = N; i > 0; i--)
        {
            swap(array,0, i);
            N = N-1;
            maxheap(array, 0);
        }
	}
	public static void heapify(int array[]){
		N = array.length-1;
        for (int i = N/2; i >= 0; i--)
            maxheap(array, i);
	}
	public static void maxheap(int array[], int i)
    { 
        int left = 2*i ;
        int right = 2*i + 1;
        int max = i;
        if (left <= N && array[left] > array[i])
            max = left;
        if (right <= N && array[right] > array[max])        
            max = right;
 
        if (max != i)
        {
            swap(array, i, max);
            maxheap(array, max);
        }
    }    
	public static void swap(int array[], int i, int j)
    {
        int swap = array[i];
        array[i] = array[j];
        array[j] = swap; 
    } 
}
