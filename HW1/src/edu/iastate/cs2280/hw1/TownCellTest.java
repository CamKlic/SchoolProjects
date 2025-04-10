package edu.iastate.cs2280.hw1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

public class TownCellTest 
{
	
	Town t; 
	public void initialize() throws FileNotFoundException
	{
		t= new Town("src/edu.iastate.cs2280.hw1/ISP.txt");
	}
	@Test
	void testA()
	{
		String str = t.grid[1][1].next(t).who().toString();
		assertEquals(State.CASUAL.toString(), str); //checks if value is equal to str
	}
}