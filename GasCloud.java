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
 * Instances of this class represent gas clouds that the player can 
 * collide their taxi into in order to collect additional fuel. 
 * @author Jared Akers
 * @author Utkarsh Maheshwari
 */

public class GasCloud {
	private Graphic graphic;
	private float rotationSpeed;
	private boolean shouldRemove; // If true this gas-cloud should be removed. 
	private float direction;
	/**
	 * Initializes a new GasCloud object to be displayed at the 
	 * specified initial position and orientation.
	 * @param x is the x position of the gas-cloud.
	 * @param y is the y position of the gas-cloud.
	 * @param direction is the initial orientation for this object's graphic.
	 */
	public GasCloud(float x, float y, float direction){
		graphic = new Graphic("GAS"); // Creates new GasCloud Graphic object.
		this.direction = direction; // Sets orientation of above gas-cloud.
		rotationSpeed = .001f; // Sets rotation of above gas-cloud.
		graphic.setPosition(x, y); //Sets position of above gas-cloud.
		shouldRemove = false; 
	}
	/**
	 * This method detects whether the player's taxi is currently colliding
	 * with this gas cloud object or not. If it is, that taxi will get more
	 * fuel and this gas cloud will be marked for removal from the level.
	 * @param taxi is the taxi that will get additional fuel if it is
	 *  colliding with this GasCloud object
	 */
	public void handleFueling(Taxi taxi)
	{ 
		if (taxi.checkCollision(graphic))
		{
			taxi.addFuel(20);
			// Gas-cloud should be removed after increasing the taxi's fuel.
			shouldRemove = true; 
		} 
	}
	/**
	 * This accessor method retrieves whether this object should 
	 * be removed from the level or not.
	 * @return the state of shouldRemove.
	 */
	public boolean shouldRemove()
	{
		return shouldRemove;
	}
	/**
	 * This method rotates the gas cloud before drawing in its new orientation.
	 * @param time is the number of milliseconds that has
	 *  elapsed since the last update.
	 * @return The GameEngine should continue running.
	 */
	public String update(int time){
		direction = direction + (rotationSpeed * time); // Rotates GasCloud.
		graphic.setDirection(direction); // Setting new direction of rotation.
		graphic.draw(); // Drawing new directional position.
		return "CONTINUE";

	}
}
