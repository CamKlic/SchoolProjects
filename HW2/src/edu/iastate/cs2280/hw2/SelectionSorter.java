package edu.iastate.cs2280.hw2;

//import java.awt.Point;
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
 * This class implements selection sort.   
 *
 */

public class SelectionSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param points  
	 */
	public SelectionSorter(Point[] points)  
	{
		super(points); //Calls super class constructor
		super.setAlgo("Selection Sorter");
		// TODO 
	}	

	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 */
	@Override 
	public void sort()
	{
		int l = points.length;
		for (int i = 0; i < l - 1; i++) 
		{
            int index = i;
            for (int j = i + 1; j < l; j++) 
            {
                if (points[j].compareTo(points[index]) < 0) 
                {
                    index = j; // searching for the lowest index
                }
            }
            Point smallerNumber = points[index];
            points[index] = points[i];
            points[i] = smallerNumber;
        }
    }
}
