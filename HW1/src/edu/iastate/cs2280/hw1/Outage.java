package edu.iastate.cs2280.hw1;

//@author <<Camden Klicker>>

public class Outage extends TownCell {

	public Outage(Town town, int row, int col) {
		super(town, row, col);
		// TODO Auto-generated constructor stub
	}

	@Override
	public State who() {
		// TODO Auto-generated method stub
		return State.OUTAGE;
	}

	@Override
	public TownCell next(Town tNew) {
		// TODO Auto-generated method stub
		
			return new Empty(tNew, row, col);
	}

}
