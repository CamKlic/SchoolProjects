package edu.iastate.cs2280.hw1;

//@author <<Camden Klicker>>

public class Reseller extends TownCell {

	public Reseller(Town town, int row, int col) {
		super(town, row, col);
		// TODO Auto-generated constructor stub
	}

	@Override
	public State who() {
		// TODO Auto-generated method stub
		return State.RESELLER;
	}

	@Override
	public TownCell next(Town tNew) {
		// TODO Auto-generated method stub
		int population[] = new int[NUM_CELL_TYPE]; //Creates an array for the population.
		census(population);
		population[RESELLER]--; //Account for current cell count
		
		if(population[CASUAL] <= 3)
		{
			return new Empty(tNew, row, col);
		}
		else if(population[EMPTY] >= 3)
		{
			return new Empty(tNew, row, col);
		}
		else if(population[CASUAL] >= 5)
		{
			return new Streamer(tNew, row, col);
		}
		else
		{
			return new Reseller(tNew, row, col);
		}
	}

}
