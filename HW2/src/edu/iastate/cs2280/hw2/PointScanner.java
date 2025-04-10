package edu.iastate.cs2280.hw2;


/**
 * 
 * @author 
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;


/**
 *  @author Camden Klicker
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;  
	
	protected String outputFileName; //Filepath for output file.
	
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  points2  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] points2, Algorithm algo) throws IllegalArgumentException
	{
		if(points2 == null || points2.length == 0)
		{
			throw new IllegalArgumentException("No value in pts"); //Throws exception if pts is null or empty
		}
		sortingAlgorithm = algo;
		points = new Point[points2.length]; //Creates new point array with equal length to pts
		//TODO possibly change how points is assigned.
		for(int i = 0; i<points2.length; i++) //Loops through pts array
		{
			points[i] = new Point(points2[i]); //Copies pts array to points array
		}
		
		if(algo.toString() == "SelectionSort")
		{
			outputFileName = "select.txt";
		}
		else if(algo.toString() == "InsertionSort")
		{
			outputFileName = "insertion.txt";
		}
		else if(algo.toString() == "MergeSort")
		{
			outputFileName = "merge.txt";
		}
		else if(algo.toString() == "QuickSort")
		{
			outputFileName = "quick.txt";
		}
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		sortingAlgorithm = algo;
		if(algo.toString() == "SelectionSort")
		{
			outputFileName = "select.txt";
		}
		else if(algo.toString() == "InsertionSort")
		{
			outputFileName = "insertion.txt";
		}
		else if(algo.toString() == "MergeSort")
		{
			outputFileName = "merge.txt";
		}
		else if(algo.toString() == "QuickSort")
		{
			outputFileName = "quick.txt";
		}
		
		File file = new File(inputFileName); //Creates file object for inputFileName
		if(!(file.exists()))
			{
				throw new FileNotFoundException("File not found"); //Throws exception if file isn't found.
			}
		Scanner scnr = new Scanner(file); //Creates scanner object for file.
		int intCount = 0; //counts number of integers in a file
		while(scnr.hasNextInt())//Loops through file to count number of integers.
		{
			scnr.nextInt(); //Goes to next int
			intCount++; //scans for the amount of integers in the file
		}
		scnr.close(); //Closes old scanner object
		if(intCount%2!=0) //Throws exception if theres an odd number of integers found in the file.
		{
			throw new InputMismatchException("Odd number of integers in file");
		}
		
		scnr = new Scanner(file); //Starts scanner over at beginning of file
		int index = 0; //Index of points array to assign values to
		int x = 0; //Stores x coord
		int y = 0; //Stores y coord
		while(scnr.hasNextInt())//Loops through file
		{
			x = scnr.nextInt(); //Scans next int for x coord
			y = scnr.nextInt(); //Scans next int for y coord
			
			points[index] = new Point(x, y); //Assigns points to x and y coordinate pair at index
			
			index++; //moves to next array index
		}
		scnr.close(); //Closes scanner object
		
		
		// TODO
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.     
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		// TODO  
		AbstractSorter aSorter; 
		if(sortingAlgorithm == Algorithm.SelectionSort)
		{
			aSorter = new SelectionSorter(points);
		}
		else if(sortingAlgorithm == Algorithm.InsertionSort)
		{
			aSorter = new InsertionSorter(points);
		}
		else if(sortingAlgorithm == Algorithm.MergeSort)
		{
			aSorter = new MergeSorter(points);
		}
		else
		{
			aSorter = new QuickSorter(points);
		}
		long startTime, endTime;
		int x, y;
		
		aSorter.setComparator(0);
		
		startTime = System.nanoTime(); //Sets start time
		aSorter.sort(); //sorts
		endTime = System.nanoTime(); //Sets end time
		scanTime += endTime-startTime; //Gets total scan time
		x = aSorter.getMedian().getX(); //gets median x value
		
		aSorter.setComparator(1);
		
		startTime = System.nanoTime(); //Sets start time
		aSorter.sort(); //sorts
		endTime = System.nanoTime(); //Gets end time
		scanTime += endTime-startTime; //Gets total scan time
		y = aSorter.getMedian().getY(); //gets median y value
		
		medianCoordinatePoint = new Point(x,y);
		
		for(int i = 0; i < aSorter.points.length; i++) //Create a copy
		{
			points[i] = new Point(aSorter.points[i]);
		}
		// create an object to be referenced by aSorter according to sortingAlgorithm. for each of the two 
		// rounds of sorting, have aSorter do the following: 
		// 
		//     a) call setComparator() with an argument 0 or 1. 
		//
		//     b) call sort(). 		
		// 
		//     c) use a new Point object to store the coordinates of the medianCoordinatePoint
		//
		//     d) set the medianCoordinatePoint reference to the object with the correct coordinates.
		//
		//     e) sum up the times spent on the two sorting rounds and set the instance variable scanTime. 
		
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{
		if(sortingAlgorithm.toString().equals("SelectionSort") || sortingAlgorithm.toString().equals("InsertionSort") )
		{
			String stats = sortingAlgorithm.toString() + " " + points.length + " " + scanTime;
			return stats; 
		}
		else
		{
			String stats = sortingAlgorithm.toString() + "     " + points.length + " " + scanTime;
			return stats; 
		}
	}
	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		String result = "MCP: " + medianCoordinatePoint.toString();
		
		return result; 
		// TODO
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException
	{
		File file = new File(outputFileName);
		if(!(file.exists()))
		{
			throw new FileNotFoundException("File not found");
		}
		PrintWriter pw = new PrintWriter(file);
		pw.write(medianCoordinatePoint.toString());
		pw.close();
	}	

	

		
}
