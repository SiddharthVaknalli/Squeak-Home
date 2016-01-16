import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

/**
 * The "Room Class Draws and sets all of the different room graphics
 * 
 * @author Siddharth Vaknalli, Alexander Leung, Daniel Kula
 * @version January 2014
 */
public class Room
{
	// The constant room width and height
	private static final int ROOM_WIDTH = 1370;
	private static final int ROOM_HEIGHT = 750;
	// The constant number of obstacles and arrows
	static final int NO_OF_OBSTACLES = 6;
	static final int NO_OF_ARROWS = 2;

	public String BackGround;
	public int Height;
	public int Width;

	public Image Picture;

	// Creates an array of RoomGraphic Obstacles and NextRoomArrows
	public RoomGraphic[] Obstacles;
	public RoomGraphic[] NextRoomArrows;

	/**
	 * Constructs a new room
	 * 
	 * @param backGround the room background file name
	 * @param ObstacleFileNames the array of file names for the obstacles
	 * @param ObstacleFilePositions the array of positions for the obstacles
	 * @param ObstacleFileNames the array of file names for the obstacles
	 * @param ArrowPositions the array of positions for the arrows
	 */
	public Room(String backGround, String[] ObstacleFileNames,
			Point[] ObstaclePositions, String[] ArrowFileNames,
			Point[] ArrowPositions)
	{
		BackGround = backGround;
		Height = ROOM_HEIGHT;
		Width = ROOM_WIDTH;
		// Creates the background picture
		Picture = new ImageIcon(BackGround).getImage();

		// Declares an array of obstacles
		Obstacles = new RoomGraphic[NO_OF_OBSTACLES];
		// Declares an array of arrows
		NextRoomArrows = new RoomGraphic[NO_OF_ARROWS];

		// Runs through each obstacle file name and position, and sets it the
		// Obstacles array
		for (int i = 0; i < NO_OF_OBSTACLES; i++)
		{
			Obstacles[i] = new RoomGraphic("Obstacle", ObstacleFileNames[i],
					ObstaclePositions[i]);
		}

		// Runs through each obstacle file name and position, and sets it the
		// Obstacles array
		for (int i = 0; i < NO_OF_ARROWS; i++)
		{
			NextRoomArrows[i] = new RoomGraphic("Arrow", ArrowFileNames[i],
					ArrowPositions[i]);
		}
	}

	/**
	 * Draws the room
	 * 
	 * @param g The Graphics context
	 * @param observer The Image Observer
	 * 
	 */

	public void Draw(Graphics g, ImageObserver observer)
	{
		//Draws the background image
		g.drawImage(Picture, 0, 0, observer);

		//Runs through the NextRoomAroows array
		for (int i = 0; i < NO_OF_ARROWS; i++)
		{
			//Draws the arrow given it's file name and position
			NextRoomArrows[i].Draw(g, observer);
		}

		//Runs through the Obstacles array
		for (int i = 0; i < NO_OF_OBSTACLES; i++)
		{
			//Draws the obstacle given it's file name and position
			Obstacles[i].Draw(g, observer);
		}
	}

}
