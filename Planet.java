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

import java.util.*;

/**  
 *Instances of this class represent planets that the player will need to avoid 
 *traveling into at warp speed. At any given time, one planet will be marked as
 *the current destination which the taxi must reach and use their thrusters to
 *collide with in order to drop of their next passenger. Doing this will 
 *ultimately change which planet is marked at the destination, or if after all
 *planets have been visited, the player will have beat the current level.
 *@author Jared Akers
 *@author Utkarsh Maheshwari 
 */
public class Planet {

	private Graphic graphic;
	private boolean isDestination; // To check whether a planet is set as 
	// the destination or not. 
	/**
	 * This constructor will initialize this planet to be displayed at a 
	 * specified position. This constructor is called from the 
	 * loadCustomLevel() method of the Level class
	 * to store Planet objects in the ArrayList planets.
	 * @param x is the x position of the planet.
	 * @param y is the y position of the planet.
	 **/
	public Planet(float x, float y)
	{
		graphic = new Graphic("PLANET"); // Creates new Planet Graphic object.
		graphic.setPosition(x, y); //Sets position of above planet.
		isDestination = false; //Planet is set as non-destination by default.
	}

	/**
	 * This constructor will create random positions for planets and ensures
	 * they do not overlap. This constructor is called from the 
	 * loadRandomLevel() method of the Level class to store Planet objects in 
	 * the ArrayList planets.
	 * @param rng is a random number generator.
	 * @param planets: ArrayList from Level which stores Planet objects.
	 **/
	public Planet(Random rng, ArrayList<Planet> planets)
	{
		graphic = new Graphic("PLANET"); // Creates new Planet Graphic object.
		boolean checkOverlap = false;

		/* If there is overlap between new planet and previous planet
		 * the loop will iterate again, setting the planet
		 * to a new position. */
		do  {

			isDestination = false; //Planet is set as non-destination by default.
			checkOverlap = false; // Boolean checker, true if newly created 
			// planet overlaps with another already existing planet.

			// Sets new planet to a random position.
			graphic.setPosition((rng.nextFloat()*GameEngine.getWidth()),
					rng.nextFloat()*GameEngine.getHeight());

			for (int j=0; j<planets.size(); j++)
			{
				// Iterates through ArrayList to check if there is 
				// overlap between planetary positions.
				
				if (planets.get(j).graphic.isCollidingWith(graphic))
				{
					checkOverlap = true; 
				}
			}
		} while (checkOverlap);
	}
	
	/** 
	 * This method draws the current planet at it's current position.
	 */
	public void update(int time)
	{
		graphic.draw();
	}

	/** This method detects and handles collisions between taxis and planets that 
	 * result in either crashing when traveling at warp speed, or when reaching a 
	 * destination and progressing through the current level.
	 * @param taxi the taxi that might be colliding with this planet.
	 * @return true when the taxi safely lands on this planet and this planet is 
	 * marked as the current destination, otherwise it returns false.
	 */
	public boolean handleLanding(Taxi taxi)
	{
		if (taxi.checkCollision(graphic))
		{
			if (taxi.isTravellingAtWarp())
			{ // Returns false if taxi crashes.
				taxi.crash(); // Calls taxi's crash method if taxi crashes.
				return false;
			}
			else
				return isDestination; // Returns true if planet that Taxi 
			// reaches is the destination, otherwise returns false.
		}
		return false;
	}
	
/**
 * This method checks if the current planet is the destination and 
 * and updates the appearance of its graphic accordingly.
 * @param isDestination is true when this planet is being marked as the current 
 * destination, and false when it is being un-marked or returned to its status as 
 * a normal planet.
 */
	public void setDestination(boolean isDestination)
	{
		this.isDestination = isDestination;
		if (isDestination == true)
		{
			graphic.setAppearance("DESTINATION");
		}
		if (isDestination == false)
			graphic.setAppearance("PLANET");
	}
}
