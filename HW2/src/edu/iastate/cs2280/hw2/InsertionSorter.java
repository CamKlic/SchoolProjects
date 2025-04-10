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
 * This class implements insertion sort.   
 *
 */

public class InsertionSorter extends AbstractSorter 
{
	// Other private instance variables if you need ... 
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 * 
	 * @param pts  
	 */
	public InsertionSorter(Point[] pts) 
	{
		super(pts); //Calls super class constructor
		super.setAlgo("Insertion Sorter");
		// TODO 
	}	

	
	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 */
	@Override 
	public void sort()
	{
		int n = points.length;
        for (int j = 1; j < n; j++) 
        {
            Point key = points[j];
            int i = j - 1;
            while ((i > -1) && (points[i].compareTo(key) < 0)) //If points < key
            {
                points[i + 1] = points[i]; //Change comparison TODO
                i--;
            }
            points[i + 1] = key;
        }
	}		
}
