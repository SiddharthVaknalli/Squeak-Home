import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * The "Alligator" Class; Sets up the alligator
 * 
 * @author Siddharth Vaknalli, edited by Alexander Leung
 * @version January 2014
 */
public class Alligator extends JPanel
{
	// Images
	private Image[] alligatorImages = new Image[4];

	// Variables
	private int alligatorRow;
	private int alligatorCol;
	private int startRow;
	private int startCol;
	private int lastRow;
	private int lastCol;
	private int alligatorDir;

	public Alligator(int row, int col, int endRow, int endCol)
	{
		// Load the different alligator images into the alligator images array
		alligatorImages[0] = new ImageIcon("alligatorUp.png").getImage();
		alligatorImages[1] = new ImageIcon("alligatorRight.png").getImage();
		alligatorImages[2] = new ImageIcon("alligatorDown.png").getImage();
		alligatorImages[3] = new ImageIcon("alligatorLeft.png").getImage();

		// Sets the places that the alligator starts and ends
		startRow = row;
		startCol = col;
		alligatorRow = startRow;
		alligatorCol = startCol;
		lastRow = endRow;
		lastCol = endCol;

		// Find the direction in which the alligator initially faces
		if (row < endRow)
			alligatorDir = 2;
		else if (row > endRow)
			alligatorDir = 0;
		else if (col < endCol)
			alligatorDir = 1;
		else
			alligatorDir = 3;
	}

	/**
	 * Finds out if the alligator eats the mouse
	 * 
	 * @param mouseRow the row the mouse is currently in
	 * @param mouseCol the column the mouse is currently in
	 * @return true if the rows and columns are the same, false if not
	 */
	public boolean eatsMouse(int mouseRow, int mouseCol)
	{
		return (mouseRow == alligatorRow && mouseCol == alligatorCol);
	}

	/**
	 * Moves the alligator
	 */
	public void move()
	{
		if (startCol == lastCol)
		{
			// North South
			if (startRow < lastRow)
			{
				if (alligatorRow == startRow)
					alligatorDir = 2;
				else if (alligatorRow == lastRow)
					alligatorDir = 0;

				if (alligatorDir == 0)
					alligatorRow--;
				else if (alligatorDir == 2)
					alligatorRow++;
			}

			else
			{
				if (alligatorRow == startRow)
					alligatorDir = 0;
				else if (alligatorRow == lastRow)
					alligatorDir = 2;

				if (alligatorDir == 0)
					alligatorRow--;
				else if (alligatorDir == 2)
					alligatorRow++;
			}
		}

		else
		{
			// East West
			if (startCol < lastCol)
			{
				if (alligatorCol == startCol)
					alligatorDir = 1;
				else if (alligatorCol == lastCol)
					alligatorDir = 3;

				if (alligatorDir == 3)
					alligatorCol--;
				else if (alligatorDir == 1)
					alligatorCol++;
			}

			else
			{
				if (alligatorCol == startCol)
					alligatorDir = 3;
				else if (alligatorRow == lastRow)
					alligatorDir = 1;

				if (alligatorDir == 3)
					alligatorCol--;
				else if (alligatorDir == 1)
					alligatorCol++;
			}
		}
	}

	/**
	 * Finds the row the alligator is currently in
	 * 
	 * @return the row the alligator is currently in
	 */
	public int getRow()
	{
		return alligatorRow;
	}

	/**
	 * Finds the row the alligator is currently in
	 * 
	 * @return the column the alligator is currently in
	 */
	public int getColumn()
	{
		return alligatorCol;
	}

	/**
	 * Draws the alligator
	 * 
	 * @param g the Graphics Context
	 * @param startRow the starting row of the alligator
	 * @param startCol the starting column of the alligator
	 */
	public void draw(Graphics g, int startRow, int startCol)
	{
		int xPos = (alligatorCol - startCol) * 90 + 5 + 350;
		int yPos = (alligatorRow - startRow) * 90 + 5;
		g.drawImage(alligatorImages[alligatorDir], xPos, yPos, null);
	}
}
