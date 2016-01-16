import java.awt.*;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;

/**
 * The "RoomGraphic" Class is the base class for the graphics
 * 
 * @author Siddharth Vaknalli, Alexander Leung, Daniel Kula
 * @version January 2014
 */
public class RoomGraphic
{
	/**
	 * Constructs a new room
	 * 
	 * @param backGround the room background file name
	 * @param name The name of the graphic
	 * @param fileName the image file name
	 * @param Position The position of the image
	 */
	public RoomGraphic(String name, String fileName, Point position)
	{
		// Sets name to Name
		Name = name;
		// Sets position to Position
		Position = position;
		// Creates the picture given the file name
		Picture = new ImageIcon(fileName).getImage();
	}

	// Creates a public Image, Point, Container, Name
	public Image Picture;
	public Point Position;
	private Container container;
	public String Name;

	/**
	 * Get's the images width
	 */
	public int getWidth()
	{
		return Picture.getWidth(null);
	}

	/**
	 * Get's the images width
	 */
	public int getHeight()
	{
		return Picture.getHeight(null);
	}

	/**
	 * Draws the graphic
	 * 
	 * @param g The Graphics context
	 * @param observer The Image Observer
	 * 
	 */
	public void Draw(Graphics g, ImageObserver observer)
	{
		// Draws the image given it's image and position
		g.drawImage(Picture, Position.x, Position.y, observer);
	}

	/**
	 * Draws the rotating graphics
	 * 
	 * @param g the graphics object to draw this image in
	 * @param angle the angle (in degrees) to rotate this image
	 */

	public void DrawRotated(Graphics g, ImageObserver observer, int angle)
	{
		// uses Graphics2D object to rotate
		Graphics2D g2D = (Graphics2D) g;
		// Used to prevent jagged edges on circle pieces
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Find the x and y position of the centre of the image and gets the
		// angleInRadians
		// the object
		double angleInRadians = Math.toRadians(angle);
		int centreX = Position.x + getWidth() / 2;
		int centreY = Position.y + getHeight() / 2;

		// Rotate the graphic context, draw the image and then rotate back
		g2D.rotate(angleInRadians, centreX, centreY);
		g.drawImage(Picture, Position.x, Position.y, observer);
		g2D.rotate(-angleInRadians, centreX, centreY);
	}

}
