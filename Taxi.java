//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:            WarpStar Taxi
// Files:            Level.java, GasCloud.java, Planet.java, Taxi.java, WarpStar.java
// Semester:         Spring 2017
//
// Author:           Utkarsh Maheshwari
// Email:            umaheshwari@wisc.edu 
// CS Login:         maheshwari 
// Lecturer's Name:  Jim Williams 
// Lab Section:      314
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:     Jared Akers
// Partner Email:    jakers@wisc.edu
// Partner CS Login: akers
// Lecturer's Name:  Gary Dahl
// Lab Section:      344
// 
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//    __X Write-up states that Pair Programming is allowed for this assignment.
//    __X We have both read the CS302 Pair Programming policy.
//    __X We have registered our team prior to the team registration deadline.
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////

/**
 * Instances of this class represent the space taxi that the player controls 
 * using both thrusters and warp stars, to travel between planets delivering
 * fares to their destinations. 
 * 
 * @author Jared Akers
 * @author Utkarsh Maheshwari
 */

public class Taxi 
{
	private Graphic graphic;
	private float thrusterSpeed; // Stores thruster speed of taxi.
	private float fuel; // Stores amount of fuel in taxi.
	private float warpSpeed; // Stores value of warp speed.
	private boolean isTravellingAtWarp; // True when taxi travels at warp.
	private boolean hasCrashed; // True if taxi crashes.

	/**
	 * Initialized new taxi object to the specified position with the fuel
	 * and correct ThrusterSpeed and WarpSpeed.
	 * @param x is the specified x position of taxi.
	 * @param y is the specified y position of taxi.
	 */
	public Taxi(float x, float y) {

		graphic = new Graphic("TAXI"); // Creating the taxi graphic.
		
		thrusterSpeed = 0.01f;
		warpSpeed = 0.2f; 
		fuel = 30; // Fueling taxi to 30 units to begin game with.
		
		graphic.setPosition(x, y); // Sets taxi to specified position.
		
		isTravellingAtWarp = false; // Taxi is not warping by default.
		hasCrashed = false; 
	}
	
	/**
	 * The below method moves taxi according to user key pressed,
	 * and changes direction the taxi is facing according to the
	 * direction of movement. Updated by the Game Engine repeatedly.
	 * @param time: Game counter in milliseconds
	 */
	public boolean update(int time) {

		if (fuel>0 && hasCrashed() == false)
		{ /* Taxi only moves when fuel>0, even when a key is held.
		 * The below if statements check which key is held and moves taxi 
		 * accordingly, and sets direction accordingly.
		 */

			//RIGHT
			if (GameEngine.isKeyHeld("D") || GameEngine.isKeyHeld("RIGHT")) 
			{
				graphic.setX((graphic.getX()) + (thrusterSpeed * time));
				graphic.setDirection(0f);
			}

			//LEFT
			if (GameEngine.isKeyHeld("A") || GameEngine.isKeyHeld("LEFT")) 
			{
				graphic.setX((graphic.getX()) - (thrusterSpeed * time));
				graphic.setDirection((float)Math.PI);
			}

			//UP
			if (GameEngine.isKeyHeld("W") || GameEngine.isKeyHeld("UP")) 
			{
				graphic.setY((graphic.getY()) - (thrusterSpeed * time));
				graphic.setDirection((float)Math.PI/2);
			}

			//DOWN
			if (GameEngine.isKeyHeld("S")|| GameEngine.isKeyHeld("DOWN")) 
			{
				graphic.setY((graphic.getY()) + (thrusterSpeed * time));
				graphic.setDirection((float)Math.PI * 3 / 2);

			}

			// Decreases fuel according to duration of movement of taxi.
			if (GameEngine.isKeyHeld("D")|| GameEngine.isKeyHeld("RIGHT")||
					GameEngine.isKeyHeld("A") || GameEngine.isKeyHeld("LEFT")||
					GameEngine.isKeyHeld("W") || GameEngine.isKeyHeld("UP")||
					GameEngine.isKeyHeld("S")|| GameEngine.isKeyHeld("DOWN"))
			{
				fuel = fuel - (thrusterSpeed * time);
				// If any of the specified keys is held; exits warp.
				isTravellingAtWarp = false;  
			}
		}

		if (isTravellingAtWarp() && !hasCrashed)  
		{ 
			// Stops taxi after it crashes into a planet during warp travel.
			
			graphic.setPosition
			(graphic.getX()+graphic.getDirectionX()*time*warpSpeed,
					graphic.getY()+ graphic.getDirectionY()*time*warpSpeed);
		}

		// Prevents fuel from being negative due to time lapsed between updates.
		if (fuel<0) 
			fuel = 0;

		// below statements enable wrapping of taxi if it goes past screen size.
		if (graphic.getX() > GameEngine.getWidth())
			graphic.setX(0);

		if (graphic.getY() < 0)
			graphic.setY(GameEngine.getHeight());

		if (graphic.getX() < 0)
			graphic.setX(GameEngine.getWidth());

		if (graphic.getY() > GameEngine.getHeight())
			graphic.setY(0);

		// Returns true if space bar is held
		// when is fuel is 0 or if taxi collides with a planet.
		if (fuel==0.0||hasCrashed)
		{
			if (GameEngine.isKeyPressed("SPACE"))
				return true;
		}

		graphic.draw();
		return false;
	}

	/**
	 * Determines whether taxi is overlapping with another object in the game.
	 * @param other - object which the collision is checked with.
	 * @return true if taxi is overlapping with an object. 
	 */
	public boolean checkCollision(Graphic other)
	{
		return graphic.isCollidingWith(other);
	}

	/**
	 * Accessor method to send taxi's fuel to caller.
	 * @return Amount of fuel taxi has at the moment.
	 */
	public float getFuel()
	{
		return fuel;
	}

	/**
	 * This method adds a specific amount of fuel to taxi.
	 * @param fuel is the amount of fuel to be added to taxi. 
	 */
	public void addFuel(float fuel)
	{
		this.fuel = this.fuel + fuel;
	}

	/**
	 * Sets the taxi to warp speed if called.
	 * Ends warp travel if a specified key is pressed.
	 * @param x - x position toward which the taxi needs to warp.
	 * @param y - y position toward which the taxi needs to warp.
	 */
	public void setWarp(float x, float y) 
	{ 
		isTravellingAtWarp = true; // Initializing warp travel.
		graphic.setDirection(x , y); // Sets orientation to the location of warp.
		
		if (GameEngine.isKeyPressed("D")|| GameEngine.isKeyPressed("RIGHT")|| 
				GameEngine.isKeyPressed("A") || GameEngine.isKeyPressed("LEFT")
				||GameEngine.isKeyPressed("W")|| GameEngine.isKeyPressed("UP")||
				GameEngine.isKeyPressed("S")|| GameEngine.isKeyPressed("DOWN"))
		{
			// Checks if a specified key is pressed and ends warp travel if yes.
			isTravellingAtWarp = false; 
		}
	}

	/**
	 * Accessor method which returns Taxi's warp travel state.
	 * @return true if taxi is travelling at warp speed.
	 */
	public boolean isTravellingAtWarp() 
	{
		return isTravellingAtWarp;
	}

	/**
	 * Method is called when taxi crashes.
	 * Changes hasCrashed to true and changes appearance of taxi to an 
	 * explosion. 
	 */
	public void crash()   
	{
		hasCrashed = true; //Changes hasCrashed to true whenever called.      
		// Changes taxi's graphic to that of an explosion.
		graphic.setAppearance("EXPLOSION");
	}

	/**
	 * Accessor method which returns true or false depending on whether the 
	 * taxi has crashed or not.
	 * @return true if taxi has crashed, false otherwise.
	 */
	public boolean hasCrashed()
	{
		return hasCrashed;   
	}




}