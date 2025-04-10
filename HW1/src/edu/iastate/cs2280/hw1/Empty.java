package edu.iastate.cs2280.hw1;

//@author <<Camden Klicker>>

public class Empty extends TownCell {

	public Empty(Town town, int row, int col) {
		super(town, row, col);
		// TODO Auto-generated constructor stub
	}

	@Override
	public State who() {
		// TODO Auto-generated method stub
		return State.EMPTY;
	}

	@Override
	public TownCell next(Town tNew) {
		int population[] = new int[NUM_CELL_TYPE]; //Creates an array for the population.
		census(population);
		population[EMPTY]--; //Account for current cell count
		
		if(population[EMPTY] + population[OUTAGE] <= 1)
		{
			return new Reseller(tNew, row, col);
		}
		else
		{
			return new Casual(tNew, row, col);
		}
	}

}
