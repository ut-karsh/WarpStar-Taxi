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

import java.io.*;
import java.util.*;

/**
 * This Level class is responsible for managing all of the objects in your game
 * The GameEngine creates a new Level object for each level, and then calls 
 * that Level object's update() method repeatedly until it returns either 
 * "ADVANCE" (to proceed to the next level) or "QUIT" (to end the entire game).
 * <br/><br/>
 * This class should contain and make use of the following private fields:
 * <tt><ul>
 * <li>private Random rng</li>
 * <li>private Taxi taxi</li>
 * <li>private ArrayList<WarpStar> warpStars</li>
 * <li>private ArrayList<GasCloud> gasClouds</li>
 * <li>private ArrayList<Planet> planets</li>
 * <li>private int destinationPlanetIndex</li>
 * </ul></tt>
 */
public class Level
{                                
	// Below are variable declarations of game objects used in the game
	private Taxi taxi;
	private ArrayList<WarpStar> warpStars; 	
	private ArrayList<GasCloud> gasClouds;
	private ArrayList<Planet> planets; 
	private int destinationPlanetIndex;	// Stores index of taxi's destination.
	// Saving the instance of Random to be used later in the program.
	private Random rng;

	/**
	 * This constructor initializes a new level object, so that the GameEngine
	 * can begin calling its update() method to advance the game's play.  In
	 * the process of this initialization, all of the objects in the current
	 * level should be instantiated and initialized to their starting states.
	 * @param rng is the ONLY Random number generator that should be used by 
	 * throughout this level and by any of the objects within it.
	 * @param levelFilename is either null (when a random level should be 
	 * loaded) or a reference to the custom level file that should be loaded.
	 */
	public Level(Random rng, String levelFilename) 
	{ 
		this.rng = rng;
		// Creates a Taxi in the center of the screen.
		taxi = new Taxi (GameEngine.getWidth()/2, GameEngine.getHeight()/2); 
		destinationPlanetIndex = 0;

		warpStars = new ArrayList<WarpStar>(6);//Initializes warpStars ArrayList.
		gasClouds = new ArrayList<GasCloud>(6);//Initializes gasClouds ArrayList.
		planets = new ArrayList<Planet>(6); // Initializes planets ArrayList.

		if(levelFilename == null)	// If there are no command-line arguments,
			// a random level is displayed.
		{
			loadRandomLevel(rng);

		}
		else
		{			
			if(!loadCustomLevel(levelFilename))
			{
				loadRandomLevel(rng);
			}
		}
	} 

	/**
	 * The GameEngine calls this method repeatedly to update all of the objects
	 * within your game, and to enforce all of your game's rules.
	 * @param time is the time in milliseconds that have elapsed since the last
	 * time this method was called (or your constructor was called). This can 
	 * be used to help control the speed of moving objects within your game.
	 * @return "CONTINUE", "ADVANCE", or "QUIT".  When this method returns
	 * "CONTINUE" the GameEngine will continue to play your game by repeatedly
	 * calling it's update() method.  Returning "ADVANCE" instructs the 
	 * GameEngine to end the current level, create a new level, and to start
	 * updating that new level object instead of the current one. Returning 
	 * "QUIT" instructs the GameEngine to end the entire game.  In the case of
	 * either "QUIT" or "ADVANCE" being returned, the GameEngine presents a
	 * short pause and transition message to help the player notice the change.
	 */
	public String update(int time) 
	{
		// Calls Taxi update() on taxi object and returns true if game is over.
		boolean quitLevel = taxi.update(time);  

		// Parsing through ArrayList warpStars.
		for (int i=0; i<warpStars.size(); i++) { 
			//Calls WarpStar's update method on individual WarpStar objects.
			warpStars.get(i).update(); 
			// Calls WarpStar handleNavigation on each WarpStar object.
			warpStars.get(i).handleNavigation(taxi); 
		}
		// Parsing through ArrayList gasClouds.
		for (int i=0; i<gasClouds.size(); i++)  {
			//Calls GasCloud's update method on individual GasCloud objects.
			gasClouds.get(i).update(time); 
			gasClouds.get(i).handleFueling(taxi); //Handles fueling of taxi.
			//Removes gasCloud if it has fueled the taxi.
			if(gasClouds.get(i).shouldRemove()) 
			{
				gasClouds.remove(i);
				--i;  /*Since an ArrayList object was removed, the next object's
				 *index is now i, thus i is decremented to prevent skipping of  
				 *the next object in the ArrayList.
				 */
			}
		}
		// Parses through ArrayList planets.
		for (int i=0; i<planets.size(); i++) { 
			//Calls planet's update method on individual Planet objects.
			planets.get(i).update(time);
			// Changes destination planet.
			planets.get(destinationPlanetIndex).setDestination(true);
			if (destinationPlanetIndex>0)
				// Sets old destination to false.
				planets.get(destinationPlanetIndex-1).setDestination(false);
			if (planets.get(i).handleLanding(taxi))
				// Increments if destination is successfully reached.
				destinationPlanetIndex++; 
		}

		if (destinationPlanetIndex >= planets.size())
		{ // Advances to next level if all destinations have been reached.
			return "ADVANCE" ;
		}

		if (quitLevel) 
			return "QUIT"; // Quits game if taxi crashes or is out of fuel.

		return "CONTINUE";
	} 

	/**
	 * This method returns a string of text that will be displayed in the upper
	 * left hand corner of the game window.  Ultimately this text should convey
	 * the taxi's fuel level, and their progress through the destinations.
	 * However, this may also be useful for temporarily displaying messages
	 * that help you to debug your game.
	 * @return a string of text to be displayed in the upper-left hand corner
	 * of the screen by the GameEngine.
	 */
	public String getHUDMessage() 
	{  
		if (taxi.getFuel()<=0)
		{ // Prints below message if taxi runs out of fuel.
			return ("You've run out of fuel!\nPress the SPACEBAR to end this "
					+ "game.");
		}
		else if (taxi.hasCrashed())
		{ // Prints below message if taxi crashes.
			return ("You've crashed into a planet!\nPress the SPACEBAR to end"
					+ " this game.");
		}
		// Prints amount of fuel left and destinations successfully reached.
		else return ("Fuel: " + taxi.getFuel() + "\nFares: " + 
				destinationPlanetIndex + "/" + planets.size());
	}

	/**
	 * This method initializes the current level to contain a single taxi in 
	 * the center of the screen, along with 6 randomly positioned objects of 
	 * each of the following types: warp stars, gasClouds, and planets.
	 */
	private void loadRandomLevel(Random rng) 
	{ 

		for (int i=0; i<6; i++)  {

			// Populating warpStars and gasClouds ArrayLists
			// with their respective objects.

			warpStars.add(new WarpStar (rng.nextFloat()*GameEngine.getWidth(),   
					rng.nextFloat()*GameEngine.getHeight())); 

			gasClouds.add(new GasCloud (rng.nextFloat()*GameEngine.getWidth(),  
					rng.nextFloat()*GameEngine.getHeight(), rng.nextFloat()));
		}

		for (int i=0; i<6; i++) {

			planets.add(new Planet (rng, planets));

		}

		// Setting destination planet.
		planets.get(destinationPlanetIndex).setDestination(true);
	}

	/**
	 * This method initializes the current level to contain each of the objects
	 * described in the lines of text from the specified file.  Each line in
	 * this file contains the type of an object followed by the position that
	 * it should be initialized to start the level.
	 * @param levelFilename is the name of the file (relative to the current
	 * working directory) that these object types and positions are loaded from
	 * @return true after the specified file's contents are successfully loaded
	 * and false whenever any problems are encountered related to this loading
	 */
	private boolean loadCustomLevel(String levelFilename) 
	{ 
		File fileLevel = new File(levelFilename);
		Scanner input;

		try{
			input = new Scanner(fileLevel);

			while (input.hasNextLine())
			{
				String [] parts = input.nextLine().split("@"); 
				String [] coords = parts[1].split(","); 

				if (parts[0].trim().equals("GAS")) 
				{
					gasClouds.add(new GasCloud (Float.parseFloat(coords[0]), 
							Float.parseFloat(coords[1]), rng.nextFloat()));
				}

				else if (parts[0].trim().equals("PLANET"))
				{
					planets.add(new Planet (Float.parseFloat(coords[0]), 
							Float.parseFloat(coords[1])));
					planets.get(destinationPlanetIndex).setDestination(true);
				}

				else if (parts[0].trim().equals("TAXI"))
				{
					taxi = new Taxi (Float.parseFloat(coords[0]), 
							Float.parseFloat(coords[1]));
				}

				else if (parts[0].trim().equals("WARP_STAR"))
				{
					warpStars.add(new WarpStar (Float.parseFloat(coords[0]), 
							Float.parseFloat(coords[1])));
				}
			}

			return true;
		} catch (FileNotFoundException excpt)
		{
			return false;
			
		} catch (IOException excpt)
		{
			return false;
		} catch (NumberFormatException excpt){
			return false;
		}catch (Exception excpt){
		    // To catch all other exceptions.
			return false;
			}
	}

	/**
	 * This method creates and runs a new GameEngine with its first Level. Any
	 * command line arguments passed into this program are treated as a list of
	 * custom level filenames that should be played in order by the player.
	 * @param args is the sequence of custom level filenames to play through
	 */
	public static void main(String[] args)
	{

		GameEngine.start(null,args);

	} 
}
