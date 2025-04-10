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
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if needed
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts); //Calls super class constructor
		super.setAlgo("Merge Sorter");
		// TODO  
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points); //Calls mergesort
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param points	point array 
	 */
	private void mergeSortRec(Point[] points)
	{
		if(points.length <= 1) //If empty or one(sorted)
		{
			return;
		}
		int mid = points.length / 2;
		
		Point[] left = new Point[mid];
		Point[] right = new Point[points.length - mid];
		
		
		left = new Point[points.length/2];
		right = new Point[points.length/2];
			
		//left
		for(int i = 0; i<left.length; i++)
		{
			left[i] = points[i];
		}
			
		//right
		for(int j = 0; j < right.length; j++)
		{
			right[j] = points[left.length + j]; //j++
		}
		//call recursively
		mergeSortRec(left);
		mergeSortRec(right);
		
		//merge sorted halves
		merge(points, left, right);
		
		
		
		
	}
	
	private void merge(Point[] pts, Point[] left, Point[] right)
	{
		int i = 0, j = 0, k = 0; //Index for right, left, and merged array
		
		while(i < left.length && j < right.length) //Merges elements from right and left
		{
			if(left[i].compareTo(right[j]) < 0)
			{
				pts[k++] = left[i++]; //If left is smaller add to merged array
			}
			else
			{
				pts[k++] = right[j++]; //Otherwise add right to merged array
			}
		}
		
		while(i < left.length) //Copy the rest of left
		{
			pts[k++] = left[i++];
		}
		
		while(j < right.length) //Copy the rest of right
		{
			pts[k++] = right[j++];
		}
	}

	
	// Other private methods if needed ...

}
