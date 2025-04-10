package edu.iastate.cs2280.hw1;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author <<Camden Klicker>>
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		//TODO: Write your code here.
		for(int i =0; i<tOld.getLength(); i++)
		{
			for(int j=0; j<tOld.getWidth(); j++)
			{
				tNew.grid[i][j] = tOld.grid[i][j].next(tNew);
			}
		}
		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * @param town
	 * @return
	 */
	public static int getProfit(Town town) {
		//TODO: Write/update your code here.
		int profit = 0;
		for(int i = 0; i<town.getLength(); i++)
		{
			for(int j = 0; j<town.getWidth(); j++)
			{
				if(town.grid[i][j].who().equals(State.CASUAL))
				{
					profit++;
				}
			}
		}
		return profit;
	}
	

	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String []args) {
		//TODO: Write your code here.
		Town town = null;
		Scanner scnr = new Scanner(System.in);
		int input = 0;
//		while(input!=1 || input!=2)
//		{
			System.out.println("How to populate grid (type 1 or 2) : 1: from a file. 2: randomly with seed");
			input = scnr.nextInt();
			if(input==1)
			{
				
				System.out.println("Please enter a file path:");
				String filepath = scnr.next();
				try {
					town = new Town(filepath);
				} catch (FileNotFoundException e) {
					System.out.println("Invalid file");
					e.printStackTrace();
				}
			}
			
			else if(input==2) //Generate random array
			{
				//Gets Rows, columns, and seed into an array
				System.out.println("Provide rows, cols, and seed integer separated by spaces:");
				int rows = scnr.nextInt();
				int cols = scnr.nextInt();
				int seed = scnr.nextInt();
				//TODO create town object based off of entered values.
				town = new Town(rows, cols);
				
				town.randomInit(seed); //Assigns random values to grid based on seed.
			}
			else {System.out.println("Invalid option");
					}
		//}
		
		//System.out.println("Start: \n");
		int totalProfit = 0;
		for(int m=0; m<12; m++)
		{
			int thisCycleProfit = getProfit(town); //profit for this month
			totalProfit+=thisCycleProfit; //Add to total profit
			
			town = updatePlain(town);
		}
		int potential = town.getLength() * town.getWidth();
		double averagePercent = (totalProfit * 100.0) / (potential * 12);
		
		System.out.printf("Profit %%: %.2f\n", averagePercent);
		//System.out.println(averagePercent);
		//System.out.println(town.toString()); //Prints initial grid
			
			
			
		scnr.close();
	}
}
