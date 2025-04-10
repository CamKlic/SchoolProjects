package edu.iastate.cs2280.hw1;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResellerTest 
{
	@Test
	void testA()
	{
		Town t = new Town(2,2);
		t.grid[0][0] = new Reseller(t, 0, 0);
		t.grid[0][1] = new Casual(t, 0, 1); 
		t.grid[1][0] = new Reseller(t, 1, 0);
		t.grid[1][1] = new Reseller(t, 1, 1);
		
		assertEquals(State.EMPTY, t.grid[0][0].next(t).who()); //Checks if value is equal to expected(empty).
	}
}