package hw1;

public class Balloon 
{
	/**
	 * @author Camden_Klicker
	 * This class acts as a simulation for a hot air balloon over time.
	 */
	
	private double outsideTemp; //Temperature outside of the hot air balloon
	private double initialOutsideTemp; //initial outside temp
	private double burnRate; //rate of fuel burned
	private double balloonMass; // balloon mass
	
	private double balloonTemp; //temperature inside hot air balloon
	private double altitude; //altitude of balloon
	private double windDirect; //direction of wind
	private double initialWindDirect; //initial direction of wind
	private double time; //time of simulation
	private double remainingFuel; //fuel left in balloon
	private double velocity; //velocity of balloon
	private double tetherLength; //length of tether
	
	private final double heatLossFactor = 0.1; //heat loss factor or H
	private final int balloonAirVolume = 61234; //Volume of air inside of balloon
	private final double acceleration = 9.81; // acceleration due to gravity
	private final double gasConstant = 287.05; //gas constant in J/kgK
	private final double pressure = 1013.25; //pressure in hPa
	private final double Kelvin = 273.15; //Degrees K when degC = 0
	
	private double outsideDensity;
	private double balloonAirDensity;
	private double lift;
	private double gravityForce;
	private double netUpwardForce;
	private double upwardsAcceleration;
	
	/**
	 * Constructs a new balloon simulation. The simulation starts with the given airTemp (outside air
temperature in C) and windDirection (in degrees). It is assumed windDirection is between 0
(inclusive) and 360 (exclusive). The balloon temperature (air inside the balloon) is initialized to
the same temperature as the outside air. The simulation time is initialized to the default value 0.
The balloon’s altitude, remaining fuel, fuel burn rate, balloon mass, velocity, and tether length
are all initialized to 0.
	 * @param airTemp
	 * @param windDirection
	 */
	public Balloon(double airTemp, double windDirection)
	{
		outsideTemp = airTemp;
		initialOutsideTemp = airTemp;
		windDirect = windDirection;
		initialWindDirect = windDirection;
		
		balloonTemp = outsideTemp;
		altitude = 0;
		time = 0;
		remainingFuel = 0;
		velocity = 0;
		tetherLength = 0;
	}
	
	/**
	 * Gets the remaining fuel that can be used to heat the air in the balloon.
	 * @return remainingFuel
	 */
	public double getFuelRemaining()
	{
		return remainingFuel;
	}
	
	/**
	 * Sets the remaining fuel that can be used to heat the air in the balloon.
	 * @param fuel
	 */
	public void setFuelRemaning(double fuel)
	{
		remainingFuel = fuel;
	}
	
	/**
	 * Gets the mass of the balloon (m in the formulas).
	 * @return balloonMass
	 */
	public double getBalloonMass()
	{
		return balloonMass;
	}
	
	/**
	 * Sets the mass of the balloon (m in the formulas).
	 * @param mass
	 */
	public void setBalloonMass(double mass)
	{
		balloonMass = mass;
	}
	
	/**
	 * Gets the outside air temperature (Toutside in the formulas).
	 * @return outsideTemp
	 */
	public double getOutsideAirTemp()
	{
		return outsideTemp;
	}
	
	/**
	 * Sets the outside air temperature (Toutside in the formulas).
	 * @param temp
	 */
	public void setOutsideAirTemp(double temp)
	{
		outsideTemp = temp;
	}
	
	/**
	 * Gets the fuel burn rate (B in the formulas).
	 * @return burnRate
	 */
	public double getFuelBurnRate()
	{
		return burnRate;
	}
	
	/**
	 * Sets the fuel burn rate (B in the formulas).
	 * @param rate
	 */
	public void setFuelBurnRate(double rate)
	{
		burnRate = rate;
	}
	
	/**
	 * Gets the balloon temperature (Tballoon in the formulas).
	 * @return balloonTemp
	 */
	public double getBalloonTemp()
	{
		return balloonTemp;
	}
	
	/**
	 * Sets the balloon temperature (Tballoon in the formulas).
	 * @param temp
	 */
	public void setBalloonTemp(double temp)
	{
		balloonTemp = temp;
	}
	
	/**
	 * Gets the balloon velocity (v in the formulas).
	 * @return velocity
	 */
	public double getVelocity()
	{
		return velocity;
	}
	
	/**
	 * Gets the balloon altitude.
	 * @return altitude
	 */
	public double getAltitude()
	{
		return altitude;
	}
	
	/**
	 * Gets the length of the tether.
	 * @return tetherLength
	 */
	public double getTetherLength()
	{
		return tetherLength;
	}
	
	/**
	 * Gets the length of the tether minus the current altitude of the balloon.
	 * @return tetherLength - altitude
	 */
	public double getTetherRemaining()
	{
		return tetherLength - altitude;
	}
	
	/**
	 * Sets the length of the tether.
	 * @param length
	 */
	public void setTetherLength(double length)
	{
		tetherLength = length;
	}
	
	/**
	 * Gets the direction of the wind in degrees, a number between 0 (inclusive) and 360 (exclusive).
	 * @return windDirect
	 */
	public double getWindDirection()
	{
		return windDirect;
	}
	
	/**
	 * Updates the wind direction by adding the giving value (which is assumed to be between -360 and
360 exclusive) on to the current wind direction. The wind direction must always be between 0
(inclusive) and 360 (exclusive).
	 * @param deg
	 */
	public void changeWindDirection(double deg) //TODO
	{
		windDirect += deg + 360; //WindDirect >=0 but < 360 
		windDirect = windDirect % 360;
	}
	
	/**
	 * Gets the number of full minutes that have passed in the simulation. For example, if the
simulation time is 179, minutes = 2 and seconds = 59.
	 * @return minutes
	 */
	public long getMinutes()
	{
		int minutes = (int) (time / 60);
		return minutes;
	}
	
	/**
	 * Gets the number of seconds passed the number of full minutes. For example, if the simulation
time is 179, minutes = 2 and seconds = 59. The seconds should always be between 0 and 59
inclusive.
	 * @return seconds
	 */
	public long getSeconds()
	{
		int seconds = (int) (time % 60);
		return seconds;
	}
	
	/**
	 * Calling this method represents 1 second of simulated time passing. Specifically, the simulation
time is incremented by 1. The fuel remaining is consumed at the set fuel burn rate, but it can
never drop below 0. If the fuel burn rate is more than the available amount of fuel then consume
as much fuel as is available but no more. The temperature inside of the balloon is updated (see
formulas in the Overview section).
The velocity and position of the balloon is also updated based on the formulas. Note that the
calculations for velocity and position at time n depend on the velocity and position at time n-1
(one second ago). In other words, each calculation of velocity and position depends on the
previous calculation.
There are two exceptions to the calculation of the position of the balloon; the balloon can never
drop below ground level (an altitude of 0) and can never rise above the length of the tether. That
is to say, the ground level and the tether length are the minimum and maximum altitudes
respectively. The velocity and altitude can only be calculated after updating the balloon
temperature.
	 */
	public void update()
	{
		//TODO
		time += 1;
		remainingFuel = Math.max(remainingFuel - burnRate, 0);
		
		balloonTemp += burnRate + (outsideTemp - balloonTemp) * heatLossFactor;
		outsideDensity = ((pressure)/(gasConstant * (outsideTemp + Kelvin)));
		balloonAirDensity = ((pressure)/(gasConstant * (balloonTemp + Kelvin)));
		lift = balloonAirVolume * (outsideDensity - balloonAirDensity) * acceleration;
		gravityForce = balloonMass * acceleration;
		netUpwardForce = lift - gravityForce;
		upwardsAcceleration = netUpwardForce / balloonMass;
		velocity = velocity + upwardsAcceleration;
		altitude = Math.max(0, Math.min(altitude + velocity, tetherLength));
	}
	
	/**
	 * Calling this method resets the simulation to its initial state (the same state it had immediately
after the constructor was called). Pay attention that the outside air temperature and wind
direction are reset to the values that were provided to the constructor. (Hint: How will you do
that when those parameters are not in the scope of this method? You have learned all the tools
necessary; but it will take some planning to implement the solution.)
	 */
	public void reset()
	{
		//TODO
		windDirect = initialWindDirect;
		outsideTemp = initialOutsideTemp;
		time = 0;
		balloonTemp = outsideTemp;
		altitude = 0;
		balloonMass = 0;
		time = 0;
		remainingFuel = 0;
		burnRate = 0;
		velocity = 0;
		tetherLength = 0;
	}
}

