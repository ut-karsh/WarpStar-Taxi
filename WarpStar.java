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
 * The WarpStar class is responsible for drawing WarpStars and 
 * having the taxi travel at WarpSpeed towards a warp star.
 * @author Jared Akers
 * @author Utkarsh Maheshwari
 */

public class WarpStar {

	Graphic graphic;

	/**
	 * This constructor initializes a new warp-star object. The level class 
	 * passes two random values to the constructor and these values are used
	 * as the position of the new WarpStar graphic.
	 * @param startX is the x position of the warp-star.
	 * @param startY is the y position of the warp-star. 
	 */
	public WarpStar(float startX, float startY){
		graphic = new Graphic("WARP_STAR"); // Creates new WarpStar Graphic object.
		graphic.setPosition(startX, startY); //Sets position of above warp-star.
	}

	/**
	 * The update() method draws the WarpStar object at its current position.
	 * @return "CONTINUE" will tell the GameEngine to continue operating.
	 */
	public String update(){
		graphic.draw();
		return "CONTINUE";
	}

	/**
	 * This method detects whether both 1) the player's taxi has fuel, and 2) 
	 * if the player is clicking on this WarpStar object. When both are detected
	 * this method sets the taxi to travel at warp speed toward this WarpStar.
	 * @param taxi is the taxi that is both checked for fuel, and then set to
	 * travel at warp speed when it has fuel and the player clicks this object.
	 */
	public void handleNavigation(Taxi taxi)
	{
		if ( taxi.getFuel() > 0)
		{
			if (GameEngine.isKeyPressed("MOUSE"))
			{
				if(graphic.isCoveringPosition(GameEngine.getMouseX(), 
						GameEngine.getMouseY()))
					//True if the warp-star and mouse are at the same position.
				
				{
					// Position of warp-star is passed to setWarp method
					// in class Taxi.
					taxi.setWarp(graphic.getX(), graphic.getY());
				}

			}
		}

	}

}

