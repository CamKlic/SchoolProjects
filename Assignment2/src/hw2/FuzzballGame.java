
package hw2;

/**
 * Models a simplified baseball-like game called Fuzzball.
 * 
 * @author Camden_Klicker
 */
public class FuzzballGame {
  /**
   * Number of strikes causing a player to be out.
   */
  public static final int MAX_STRIKES = 2;

  /**
   * Number of balls causing a player to walk.
   */
  public static final int MAX_BALLS = 5;

  /**
   * Number of outs before the teams switch.
   */
  public static final int MAX_OUTS = 3;

  /**
   * Determines whether the game is over or not
   */
  private boolean gameHasEnded = false;
  
  /**
   * Used to check if someone is on base 1
   */
  private boolean runOnBase1 = false;
  /**
   * Used to check if someone is on base 2
   */
  private boolean runOnBase2 = false;
  /**
   * Used to check if someone is on base 3
   */
  private boolean runOnBase3 = false;
  /**
   * Used to check if it is the top of the inning. 
   */
  private boolean isTopOfInning = true;
  
  /**
   * Counts the number of strikes the given team has
   */
  private int numStrikes;
  /**
   * Counts the number of outs the given team has
   */
  private int numOuts;
  /**
   * Counts the number of balls the given team has
   */
  private int numBalls;
  /**
   * The max number of innings in the game. Assigned in the constructor
   */
  private int numInnings;
  /**
   * Holds the value for the current inning
   */
  private int currentInning;
  
  /**
   * Counts the score of team 0
   */
  private int team0Score;
  /**
   * Counts the score of team 1
   */
  private int team1Score;


  // TODO: EVERTHING ELSE
  // Note that this code will not compile until you have put in stubs for all
  // the required methods.
  /**
   * Constructor for the class. Assigns numInnings to givenNumInnings
   * @param givenNumInnings
   */
  public FuzzballGame(int givenNumInnings)
  {
	  numInnings = givenNumInnings;
	  currentInning = 1;
	  numOuts = 0;
  }
  
  
  /**
   *  Adds a ball or calls walk function if there are MAX_BALLS(5) balls
   */
  public void ball()
  {
	  if(!gameHasEnded) //While game is going
	  {
		  numBalls++;
		  if(numBalls>=MAX_BALLS)
		  {
			  walk(isTopOfInning); //Walks for given team
			  numBalls = 0;
			  numStrikes = 0;
		  }
	  }
  }
  /**
   * Calls out() function if a ball is caught and the game isn't over
   */
  public void caughtFly()
  {
	  if(!gameHasEnded)
	  {
		  out();
	  }
  }
  /**
   * This function is called whenever an out occurs. It resets the number of balls and strikes,
   * clears the bases, and switches isTopOfInning to true or false depending on which team is up
   * to bat.
   */
  private void out()
  {
	  numOuts++;
	  numBalls = 0;
	  numStrikes = 0;
	  if(numOuts==MAX_OUTS)
	  {
		  numOuts = 0;
		  runOnBase1= false;
		  runOnBase2 = false;
		  runOnBase3 = false;
		  if(isTopOfInning)
		  {
			  isTopOfInning = false;
		  }
		  else
		  {
			  if(currentInning+1>numInnings)
			  {
				  gameHasEnded = true;
			  }
			  else
			  {
				  isTopOfInning = true;
			  	  currentInning++;
			  }
		  }  
	  }
  }
  /**
   * Returns whether the game has ended or not
   * @return gameHasEnded
   */
  public boolean gameEnded()
  {
	  return gameHasEnded;
  }
  /**
   * Returns the number of balls
   * @return numBalls
   */
  public int getBallCount()
  {
	  return numBalls;
  }
  
 /**
  * Returns the number of strikes
  * @return numStrikes
  */
  public int getCalledStrikes()
  {
	  return numStrikes;
  }
  /**
   * Returns the number of outs
   * @return numOuts
   */
  public int getCurrentOuts()
  {
	  return numOuts;
  }
  /**
   * Returns the score of team 0.
   * @return team0Score
   */
  public int getTeam0Score()
  {
	  return team0Score;
  }
  /**
   * Returns the score of team 1.
   * @return team1Score
   */
  public int getTeam1Score()
  {
	  return team1Score;
  }
  /**
   * Causes the batter to walk to first base. If a base a player would walk to is full that player
   * would also walk to the next base
   * @param team
   */
  private void walk(boolean team)
  {
	  if(runOnBase1) //Runner on base 1
	  {
		  if(runOnBase2) //Runner on 2 bases
		  {
			  if(runOnBase3) //Runner on all 3 bases
			  {
				  shiftBases(team, 1);
				  runOnBase1 = true;
			  }
			  else
			  {
				  runOnBase3 = true;
			  }
		  }
		  else
		  {
			  runOnBase2 = true;
		  }
	  }
	  else
	  {
		  runOnBase1 = true;
	  }
  }
  /**
   * Causes players to move bases. The method takes in team which determines what team can score 
   * and t which is how many times the players will move bases. If a player on 3rd base moves their
   * team is awarded a point.
   * @param team, t
   */
  private void shiftBases(boolean team, int t)
  {
	  numBalls = 0;
	  numStrikes = 0;
	  while(t > 0)
	  {
		  if(runOnBase3)
		  {
			  if(team)
			  {
				  team0Score++;
			  }
			  else
			  {
				  team1Score++;
			  }
			  runOnBase3 = false;
		  }
		  if(runOnBase2)
		  {
			  runOnBase3 = true;
			  runOnBase2 = false;
		  }
		  if(runOnBase1)
		  {
			  runOnBase2 = true;
			  runOnBase1 = false;
		  }
		  t--;
	  }
	  
  }
  /**
   * Simulates the batter hitting the ball. If they hit it less than 15 they are out and the out
   * method is called. If they hit it less than 150 and more than 15 the hitting team's players
   * shift one base. If they hit it less than 200 and more than 150 they shift bases twice.
   * If they hit it less than 250 and more than 200 they shift three bases. If they hit it more
   * than 250 its a home run and all runners shift bases until they all score.
   * @param distance
   */
  public void hit(int distance)
  {
	  if(!gameHasEnded)
	  {
		if(distance < 15)
	  	{
		  out();
	  	}
	  	else if(distance < 150)
	  	{
	  		shiftBases(isTopOfInning, 1);
	  		runOnBase1 = true;
	  		
	  	}
	  	else if(distance < 200)
	  	{
	  		shiftBases(isTopOfInning, 2);
	  		runOnBase2 = true;
	  	}
	  	else if(distance < 250)
	  	{
	  		shiftBases(isTopOfInning, 3);
	  		runOnBase3 = true;
	  	}
	  	else
	  	{
	  		shiftBases(isTopOfInning, 3);
	  		runOnBase3 = true;
	  		shiftBases(isTopOfInning, 1);
		  
	  	}
	  }
  }
  /**
   * Returns isTopOfInning 
   * @return isTopOfInning
   */
  public boolean isTopOfInning()
  {
	  
	  return isTopOfInning;
  }
  /**
   * Which is used to determine what base to check. Returns true if a runner is on which base
   * and false if the base is empty.
   * @param which
   * @return true or false
   */
  public boolean runnerOnBase(int which)
  {
	  if(which == 1)
	  {
		  if(runOnBase1)
		  {
			  return true;
		  }
		  else
		  {
			  return false;
		  }
	  }
	  else if(which == 2)
	  {
		  if(runOnBase2)
		  {
			  return true;
		  }
		  else
		  {
			  return false;
		  }
	  }
	  else if(which == 3)
	  {
		  if(runOnBase3)
		  {
			  return true;
		  }
		  else
		  {
			  return false;
		  }
	  }
	  else
	  {
		  return false;
	  }
  }
  /**
   * If swung is false out() is called. If true, numStrikes goes up by one and if there are MAX_STRIKES
   * out() is called
   * @param swung
   */
  public void strike(boolean swung)
  {
	  if(!gameHasEnded)
	  {
		if(swung)
	  	{
		  	out();
	  	}
	  	else
	  	{
	  		numStrikes++;
	  		if(numStrikes == MAX_STRIKES)
	  		{
	  			out();
	  		}
	  	}
	  }
  }
  /**
   * Returns the current inning or the currentInning+1 if the game is over.
   * @return currentInning, currentInning+1
   */
  public int whichInning()
  {
	  if(gameHasEnded)
	  {
		  return currentInning+1;
	  }
	  else
	  {
		  return currentInning;
	  }
  }


  
  // The methods below are provided for you and you should not modify them.
  // The compile errors will go away after you have written stubs for the
  // rest of the API methods.
  /**
   * Returns a three-character string representing the players on base, in the
   * order first, second, and third, where 'X' indicates a player is present and
   * 'o' indicates no player. For example, the string "oXX" means that there are
   * players on second and third but not on first.
   * 
   * @return three-character string showing players on base
   */
  public String getBases()
  {
    return (runnerOnBase(1) ? "X" : "o") + (runnerOnBase(2) ? "X" : "o")
        + (runnerOnBase(3) ? "X" : "o");
  }

  /**
   * Returns a one-line string representation of the current game state. The
   * format is:
   * <pre>
   *      ooo Inning:1 [T] Score:0-0 Balls:0 Strikes:0 Outs:0
   * </pre>
   * The first three characters represent the players on base as returned by the
   * <code>getBases()</code> method. The 'T' after the inning number indicates
   * it's the top of the inning, and a 'B' would indicate the bottom. The score always
   * shows team 0 first.
   * 
   * @return a single line string representation of the state of the game
   */
  public String toString()
  {
    String bases = getBases();
    String topOrBottom = (isTopOfInning() ? "T" : "B");
    String fmt = "%s Inning:%d [%s] Score:%d-%d Balls:%d Strikes:%d Outs:%d";
    return String.format(fmt, bases, whichInning(), topOrBottom, getTeam0Score(),
        getTeam1Score(), getBallCount(), getCalledStrikes(), getCurrentOuts());
  }
}
