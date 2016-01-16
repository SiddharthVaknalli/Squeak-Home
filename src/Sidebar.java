import java.awt.*;
import java.util.Timer;

import javax.swing.*;

/**
 * The "Sidebar" Class; Draws the sidebar on the left of the screen
 * 
 * @author Siddharth Vaknalli, Alexander Leung
 * @version January 2014
 */
public class Sidebar extends JPanel
{
	// Constants
	private final int MAX_ENERGY = 50;

	// Variables
	private int noOfLives;
	private int energy;
	// Images
	private Image[] lives = new Image[4];
	public Image body;
	private Image energyLevel;
	private Image lowEnergyLevel;

	public Sidebar()
	{
		// Images
		lives[0] = new ImageIcon("0lives.png").getImage();
		lives[1] = new ImageIcon("1life.png").getImage();
		lives[2] = new ImageIcon("2lives.png").getImage();
		lives[3] = new ImageIcon("3lives.png").getImage();
		body = new ImageIcon("sidebar.png").getImage();
		energyLevel = new ImageIcon("energylevel.png").getImage();
		lowEnergyLevel = new ImageIcon("lowenergylevel.png").getImage();
		// Set the number of lives and amount of energy
		noOfLives = 3;
		energy = MAX_ENERGY;
	}

	/**
	 * Draws the sidebar
	 * 
	 * @param g the Graphics Context
	 */
	public void draw(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(body, 0, 0, this);
		g.drawImage(lives[noOfLives], 0, 90, this);
		for (int energyUnit = 0; energyUnit < energy; energyUnit++)
		{
			if (energy < 10)
				g.drawImage(lowEnergyLevel, 4 + (energyUnit * 5), 238, this);
			else
				g.drawImage(energyLevel, 4 + (energyUnit * 5), 238, this);
		}
	}

	// No need to find the sidebar height 
	/**
	 * Finds the sidebar width
	 * 
	 * @return the width of the sidebar
	 */
	public int getWidth()
	{
		return body.getWidth(this);
	}

	/**
	 * Decreases the number of lives
	 */
	public void decreaseLife()
	{
		if (noOfLives > 0)
			noOfLives--;
	}

	/**
	 * Decreases the amount of energy
	 */
	public void decreaseEnergy()
	{
		if (energy > 0)
			energy--;
	}

	/**
	 * Gets the amount of energy in the sidebar
	 * 
	 * @return the amount of energy in the sidebar
	 */
	public int getEnergy()
	{
		return energy;
	}

	/**
	 * Resets the amount of energy
	 */
	public void resetEnergy()
	{
		energy = MAX_ENERGY;
	}

	/**
	 * Increases the amount of energy
	 */
	public void increaseEnergy()
	{
		if (energy + 10 <= MAX_ENERGY)
			energy += 10;
	}

	/**
	 * Resets the amount of lives
	 */
	public void resetLives()
	{
		noOfLives = 3;
	}

}