package edu.iastate.cs2280.hw1;

// @author <<Camden Klicker>>

public class Casual extends TownCell{

	public Casual(Town town, int row, int col) {
		super(town, row, col);
		// TODO Auto-generated constructor stub
		
		
	}

	@Override
	public State who() {
		// TODO Auto-generated method stub
		return State.CASUAL;
	}

	@Override
	public TownCell next(Town tNew) {
		int population[] = new int[NUM_CELL_TYPE]; //Creates an array for the population.
		census(population);
		population[CASUAL]--; //Account for current cell count
		
		if(population[EMPTY] + population[OUTAGE] <= 1)
		{
			return new Reseller(tNew, row, col);
		}
		else if(population[RESELLER] > 0)
		{
			return new Outage(tNew, row, col);
		}
		else if(population[STREAMER] > 0)
		{
			return new Streamer(tNew, row, col);
		}
		else if(population[CASUAL] >= 5)
		{
			return new Streamer(tNew, row, col);
		}
		else
		{
			return new Casual(tNew, row, col);
		}
	}
}
