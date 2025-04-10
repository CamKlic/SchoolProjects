package edu.iastate.cs2280.hw2;

/**
 *  
 * @author Camden Klicker
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		Scanner scnr = new Scanner(System.in);
		System.out.println("Enter 1 to generate random points or 2 to enter a file path");
		int input = scnr.nextInt();
		PointScanner[] scanners = new PointScanner[4]; 
		while(input < 1 || input > 2)
		{
			System.out.println("Please enter 1 or 2");
			input = scnr.nextInt();
		}
		if(input == 1) //If user wants to randomly generate points
		{
			System.out.println("How many points would you like to generate?");
			int numPts = scnr.nextInt(); //gets desired number of points to generate
			System.out.println("Enter an integer seed for generation");
			int seed = scnr.nextInt(); //gets desired seed for generation of points
			Random generator = new Random(seed);
			Point[] points = generateRandomPoints(numPts, generator); //Generates array of points with desired values
			scanners[0] = new PointScanner(points, Algorithm.SelectionSort); //Pointscanner with SelectionSort
			scanners[1] = new PointScanner(points, Algorithm.InsertionSort); //Pointscanner with InsertionSort
			scanners[2] = new PointScanner(points, Algorithm.MergeSort); //Pointscanner with MergeSort
			scanners[3] = new PointScanner(points, Algorithm.QuickSort); //Pointscanner with QuickSort
		}
		else
		{
			System.out.println("Enter in the filepath: ");
			String filepath = scnr.next();
			scanners[0] = new PointScanner(filepath, Algorithm.SelectionSort); //Pointscanner with SelectionSort
			scanners[1] = new PointScanner(filepath, Algorithm.InsertionSort); //Pointscanner with InsertionSort
			scanners[2] = new PointScanner(filepath, Algorithm.MergeSort); //Pointscanner with MergeSort
			scanners[3] = new PointScanner(filepath, Algorithm.QuickSort); //Pointscanner with QuickSort
		}
		
		System.out.println("\nalgorithm size time (ns)");
		System.out.println("--------------------------------------------------");
		for(int i = 0; i<scanners.length; i++) //loops through all scanners
		{
			scanners[i].scan(); //Scans scanner
			System.out.println(scanners[i].stats()); //Prints stats of scan 
		}
		System.out.println("--------------------------------------------------");
		// TODO 
		// 
		// Conducts multiple rounds of comparison of four sorting algorithms.  Within each round, 
		// set up scanning as follows: 
		// 
		//    a) If asked to scan random points, calls generateRandomPoints() to initialize an array 
		//       of random points. 
		// 
		//    b) Reassigns to the array scanners[] (declared below) the references to four new 
		//       PointScanner objects, which are created using four different values  
		//       of the Algorithm type:  SelectionSort, InsertionSort, MergeSort and QuickSort. 
		// 
		// 	
		
		
		// For each input of points, do the following. 
		// 
		//     a) Initialize the array scanners[].  
		//
		//     b) Iterate through the array scanners[], and have every scanner call the scan() 
		//        method in the PointScanner class.  
		//
		//     c) After all four scans are done for the input, print out the statistics table from
		//		  section 2.
		//
		// A sample scenario is given in Section 2 of the project description. 
		scnr.close();
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] � [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	private static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		
		int x = 0;
		int y = 0;
		if(numPts < 1) //Checks if numPts has a value
		{
			throw new IllegalArgumentException("Invalid number of points");
		}
		Point[] points = new Point[numPts];
		
		for(int i=0; i<numPts; i++)
		{
			x = rand.nextInt(101) - 50; //generates a new integer between (-50, 50)
			y = rand.nextInt(101) - 50;//generates a new integer between (-50, 50)
			
			points[i] = new Point(x, y); //Creates new point object
		}
		return points; 
		// TODO 
	}
	
}
