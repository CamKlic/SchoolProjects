package edu.iastate.cs2280.hw1;

//@author <<Camden Klicker>>
public class Streamer extends TownCell{

	public Streamer(Town town, int row, int col) {
		super(town, row, col);
		// TODO Auto-generated constructor stub
	}

	@Override
	public State who() {
		// TODO Auto-generated method stub
		return State.STREAMER;
	}

	@Override
	public TownCell next(Town tNew) {
		// TODO Auto-generated method stub
		int population[] = new int[NUM_CELL_TYPE]; //Creates an array for the population.
		census(population);
		population[STREAMER]--; //Account for current cell count
		
		if(population[EMPTY] + population[OUTAGE] <= 1)
		{
			return new Reseller(tNew, row, col);
		}
		else if(population[RESELLER] > 0)
		{
			return new Outage(tNew, row, col);
		}
		else if(population[OUTAGE] > 0)
		{
			return new Empty(tNew, row, col);
		}
		else if(population[CASUAL] >= 5)
		{
			return new Streamer(tNew, row, col);
		}
		else
		{
			return new Streamer(tNew, row, col);
		}
	}

}
