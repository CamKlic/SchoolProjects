package edu.iastate.cs2280.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author Camden Klicker
 *
 */

/**
 * 
 * This class implements the version of the quicksort algorithm presented in the lecture.   
 *
 */

public class QuickSorter extends AbstractSorter
{
	
	// Other private instance variables if you need ... 
		
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *   
	 * @param pts   input array of integers
	 */
	public QuickSorter(Point[] pts)
	{
		super(pts); //Calls super class constructor
		super.setAlgo("Quick Sorter");
		// TODO 
	}
		

	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.  
	 * 
	 */
	@Override 
	public void sort()
	{
		quickSortRec(0, points.length - 1); 
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last)
	{
		if(first < last)
		{
			int p = partition(first, last);
			quickSortRec(first, p-1);
			quickSortRec(p+1, last);
		}
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last)
	{
		Point pivot = points[last]; //Pivot for last element
		int f = first - 1; //first element to be incremented
			
		for(int i = first; i < last; i++)
		{
			if(points[i].compareTo(pivot) < 0) //Compares
			{
				f++; //Increment f
				
				swap(f, i); //Swaps elements
			}
		}
		swap(f+1, last); //swaps again
		
		return f+1; 
	}	
	
		


	
	// Other private methods if needed ...
}
