package edu.iastate.cs2280.hw1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


/**
 *  @author <<Camden Klicker>>
 *
 */
public class Town {
	
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		//TODO: Write your code here.
		this.length = length;
		this.width = width;
		grid = new TownCell[length][width];
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File file = new File(inputFileName); //Creates a file object
		Scanner scnr = new Scanner(file); //Creates scanner object that reads file
		
		int length = scnr.nextInt(); //length
		int width = scnr.nextInt(); //width
		this.length = length; //assigns length
		this.width = width; //assigns width
		scnr.nextLine();

		grid = new TownCell[length][width]; //Creates a TownCell object
		for(int i=0; i<length; i++)
		{
			String[] arr = scnr.nextLine().split("\\s+"); //Deletes spaces in between
			for(int j =0; j<arr.length; j++) //loops through arr
			{
				String cell = arr[j].trim();
				switch(cell) //switch statement that creates cells from a file
				{
				case "E":
					grid[i][j] = new Empty(this, i, j);
					break;
					
				case "R":
					grid[i][j] = new Reseller(this, i, j);
					break;
				
				case "C":
					grid[i][j] = new Casual(this, i, j);
					
					break;
					
				case "O":
					grid[i][j] = new Outage(this, i, j);
					
					break;
					
				case "S":
					grid[i][j] = new Streamer(this, i, j);
					
					break;
					default:
						throw new IllegalArgumentException("Unrecognized cell type: " + cell); //Throws exception if case not met
				}
			}
		}		
		 
			scnr.close(); //closes scanner object

		
		
	}
	
	/**
	 * Returns width of the grid.
	 * @return
	 */
	public int getWidth() {
		
		return width; //returns width
	}
	
	/**
	 * Returns length of the grid.
	 * @return
	 */
	public int getLength() {
		return length; //returns length
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed); //creates a random object with the given seed
		
		for(int i=0; i<grid.length; i++)
		{
			for(int j=0; j<grid[i].length; j++) //Loops through entire array
			{
				int newRandomValue = rand.nextInt(5); //generates a random value between 0-4
				
				TownCell cell; //declaration of cell
				switch(newRandomValue) //Assigns values to the cell
				{
					case TownCell.CASUAL: 
						cell = new Casual(this, i, j);
						break;
			
					case TownCell.EMPTY:
						cell = new Empty(this, i, j);
						break;
			
					case TownCell.OUTAGE:
						cell = new Outage(this, i, j);
						break;
			
					case TownCell.STREAMER:
						cell = new Streamer(this, i, j);
						break;
			
					case TownCell.RESELLER:
						cell = new Reseller(this, i, j);
						break;
						
					default:
						throw new IllegalStateException("Unexpected value");
				}
				grid[i][j] = cell;
			}
		}
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 */
	@Override
	public String toString() {
		String s = "";
		for(int i=0; i<grid.length; i++) //Loops through rows
		{
			s+=" \n"; //Goes to next line for each row.
			for(int j=0; j<grid[i].length; j++) //Loops through columns
			{
				TownCell index = grid[i][j];
				if(index instanceof Casual) //Converts TownCell objects into strings
				{
					s+="C ";
				}
				else if(index instanceof Streamer)
				{
					s+="S ";
				}
				else if(index instanceof Reseller)
				{
					s+="R ";
				}
				else if(index instanceof Empty)
				{
					s+="E ";
				}
				else if(index instanceof Outage)
				{
					s+="O ";
				}
				else
				{
					throw new IllegalStateException("Unexpected value");
				}
			}
		}
		return s;
	}
}
