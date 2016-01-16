import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;

/**
 * The "House" Class Creates all of the different graphics in room
 * 
 * @author Siddharth Vaknalli, Alexander Leung, Daniel Kula
 * @version January 2014
 */

public class House
{

	// The different public Rooms
	public Room livingRoom;
	public Room bathroom;
	public Room kitchen;
	public Room CurrentRoom;
	public Room ItemRoom;
	public Room CheeseItemRoom;
	public Room MilkItemRoom;
	public Room PowerUpItemRoom;
	public Room testRoom;

	/**
	 * Constructs the different rooms
	 */
	public House()
	{
		// Living Room Graphics
		// Sets all of the obstacle file names in this array
		// LR stands for Living Room
		String LR_obstacleFileNames[] = new String[] { "couch.png",
				"couch.png", "chair.png", "table.png", "plasmaTV.png",
				"table.png" };
		// Sets all of the obstacle point positions in this array
		Point LR_obstaclePositions[] = new Point[] { new Point(400, 500),
				new Point(920, 500), new Point(400, 310), new Point(550, 100),
				new Point(900, 20), new Point(1000, 190) };
		// Sets the arrow file names in this array
		String LR_arrowFileNames[] = new String[] { "arrow_down.png", "" };
		// Sets the arrow positions in the array
		Point LR_arrowPositions[] = new Point[] { new Point(680, 720),
				new Point() };
		// Creates the living room given the background, obstacle and arrow file names, obstacle and arrow positions
		livingRoom = new Room("livingRoomBackground2.png",
				LR_obstacleFileNames, LR_obstaclePositions, LR_arrowFileNames,
				LR_arrowPositions);

		// Kitchen Graphics
		// Sets all of the obstacle file names in this array
		// K stands for Living Room
		String K_obstacleFileNames[] = new String[] { "kitchensink.png",
				"stove.png", "", "thickCounterTop.png",
				"verticalCounterTop.png", "horizontalCounterTop.png" };
		// Sets all of the obstacle point positions in this array
		Point K_obstaclePositions[] = new Point[] { new Point(610, 150),
				new Point(900, 550), new Point(), new Point(750, 550),
				new Point(500, 150), new Point(750, 150) };
		// Sets the arrow file names in this array
		String K_arrowFileNames[] = new String[] { "arrow_up.png",
				"arrow_right.png" };
		// Sets the arrow positions in the array
		Point K_arrowPositions[] = new Point[] { new Point(700, 20),
				new Point(1300, 400) };
		// Creates the kitchen given the background, obstacle and arrow file names, obstacle and arrow positions
		kitchen = new Room("kitchenFloor.png", K_obstacleFileNames,
				K_obstaclePositions, K_arrowFileNames, K_arrowPositions);

		// Bathroom Graphics
		// Sets all of the obstacle file names in this array
		// LR stands for Living Room
		String B_obstacleFileNames[] = new String[] { "toilet.png",
				"bathtub.png", "sink.png", "", "sink.png", "" };
		// Sets all of the obstacle point positions in this array
		Point B_obstaclePositions[] = new Point[] { new Point(500, 550),
				new Point(900, 400), new Point(570, 110), new Point(),
				new Point(720, 110), new Point() };
		// Sets the arrow file names in this array
		String B_arrowFileNames[] = new String[] { "arrow_left.png", "" };
		// Sets the arrow positions in the array 
		Point B_arrowPositions[] = new Point[] { new Point(280, 400),
				new Point() };
		// Creates the bathroom given the background, obstacle and arrow file names, obstacle and arrow positions
		bathroom = new Room("tiledFloor.png", B_obstacleFileNames,
				B_obstaclePositions, B_arrowFileNames, B_arrowPositions);

		//Set CurrentRoom to kitchen so the game starts in the kitchen
		CurrentRoom = kitchen;

	}

	/**
	 * Repaint the drawing panel
	 * 
	 * @param g The Graphics context
	 * @param observer The Image Observer
	 * 
	 */
	public void Draw(Graphics g, ImageObserver observer)
	{
		//Draws the current room
		CurrentRoom.Draw(g, observer);

	}

}