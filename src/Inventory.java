import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * The "Inventory" Class; Controls the mouse's inventory
 * 
 * @author Siddharth Vaknalli, edited by Alexander Leung
 * @version January 2014
 */
public class Inventory extends JPanel
{
	private final int EMPTY = 0;
	private final int MAX_ITEMS = 12;

	private Image[] inventoryImages;

	private int[][] items;
	private int noOfItems;

	public Inventory()
	{
		inventoryImages = new Image[3];
		inventoryImages[1] = new ImageIcon("sticks.png").getImage();
		inventoryImages[2] = new ImageIcon("milk.png").getImage();
		items = new int[4][3];
		noOfItems = 0;
	}

	/**
	 * Adds an item to the inventory
	 * 
	 * @param imageNo the image to be put in the inventory
	 */
	public void addItem(int imageNo)
	{
		// Goes through the items array until an open slot is found
		if (noOfItems < MAX_ITEMS)
		{
			int col = 0;
			int row = 0;
			while (row < items.length && items[row][col] > EMPTY)
			{
				while (col < items[row].length && items[row][col] > EMPTY)
					col++;
				if (col == items[row].length)
				{
					row++;
					col = 0;
				}
			}
			// Puts the item in the open slot
			items[row][col] = imageNo;
			noOfItems++;
		}
		else
			JOptionPane.showMessageDialog(this, "Inventory is full.",
					" Inventory Full", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Drops an item into the maze
	 * 
	 * @param row the row the item is in
	 * @param column the column the item is in
	 */
	public void dropItem(int row, int column)
	{
		if (items[row][column] != 0)
		{
			noOfItems--;
			items[row][column] = EMPTY;
		}
	}

	/**
	 * Checks to see if there is an item in the inventory spot
	 * 
	 * @param row the row check for
	 * @param column the item check for
	 * @return
	 */
	public boolean isItemHere(int row, int column)
	{
		return (items[row][column] != 0);
	}

	/**
	 * Resets the inventory
	 */
	public void resetInventory()
	{
		for (int row = 0; row < items.length; row++)
			for (int col = 0; col < items[row].length; col++)
				items[row][col] = EMPTY;
	}

	/**
	 * Draws the inventory
	 * 
	 * @param g the Graphics context
	 */
	public void drawInventory(Graphics g)
	{
		for (int row = 0; row < items.length; row++)
			for (int column = 0; column < items[row].length; column++)
			{
				// Find the x and y positions for each row and column
				int xPos = column * 80 + 5;
				int yPos = row * 80 + 425;

				// Draw the current image in the square
				int imageNo = items[row][column];
				g.drawImage(inventoryImages[imageNo], xPos, yPos, this);
			}
	}

}
