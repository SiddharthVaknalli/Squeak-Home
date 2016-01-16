import java.io.*;
import java.util.Scanner;
import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

/**
 * The "SqueakHomeGame" Class; All modes and major events located
 * 
 * @author Siddharth Vaknalli, Alexander Leung, Daniel Kula
 * @version January 2014
 */
public class SqueakHomeGame extends JPanel implements MouseListener,
		KeyListener
{
	// Constants
	private final int IMAGE_WIDTH;
	private final int IMAGE_HEIGHT;
	private static final int SCROLL_WIDTH = 14;
	private static final int SCROLL_HEIGHT = 9;
	private static final int TIMER_INTERVAL = 500;
	private static final int ALLIGATOR_TIMER_INTERVAL = 400;
	private static final int ENERGY_TIMER_INTERVAL = 1000;
	// Buttons for Main Menu Image
	private static final Rectangle missionButton = new Rectangle(220, 375, 325,
			70);
	private static final Rectangle instructionsButton = new Rectangle(760, 375,
			325, 70);
	private static final Rectangle survivalButton = new Rectangle(220, 530,
			325, 70);
	private static final Rectangle aboutButton = new Rectangle(770, 530, 325,
			70);
	// Button for Going Back to Menu
	private static final Rectangle backToMenuButton = new Rectangle(10, 725,
			290, 65);
	// Buttons for Inventory
	private static final Rectangle firstBox = new Rectangle(5, 425, 80, 80);
	private static final Rectangle secondBox = new Rectangle(90, 425, 80, 80);
	private static final Rectangle thirdBox = new Rectangle(175, 425, 80, 80);
	private static final Rectangle fourthBox = new Rectangle(5, 510, 80, 80);
	private static final Rectangle fifthBox = new Rectangle(90, 510, 80, 80);
	private static final Rectangle sixthBox = new Rectangle(175, 510, 80, 80);
	private static final Rectangle seventhBox = new Rectangle(5, 595, 80, 80);
	private static final Rectangle eighthBox = new Rectangle(90, 595, 80, 80);
	private static final Rectangle ninthBox = new Rectangle(175, 595, 80, 80);
	private static final Rectangle tenthBox = new Rectangle(5, 680, 80, 80);
	private static final Rectangle eleventhBox = new Rectangle(90, 680, 80, 80);
	private static final Rectangle twelthBox = new Rectangle(175, 680, 80, 80);
	// Images
	private Image[] gridImages, playerImage;
	private Image backgroundImage, instructionScreen;
	// Inventory
	private Sidebar sidebar;
	private Inventory inventory;
	private Timer energyTimer;
	// Variables to keep track of the grid and the player position in mission
	// mode
	private int[][] grid;
	private boolean[][] mouseTraps;
	private int currentRow, currentColumn, startRow, startCol, playerDir,
			pathNo;

	// Check to see if things have started or not
	private boolean missionStart, mainMenu, survivalStart, gamePaused,
			instructions;

	// Mission Mode Alligator
	private Alligator[] alligators;
	private Timer alligatorTimer;

	// Survival Mode
	// Constants
	private final int CAT_TIME_INTERVAL = 50;
	private final int MOUSE_TIME_INTERVAL = 50;
	private final int CAT_DELAY_TIME = 1000;
	private final int MILK_DELAY_TIME = 5000;

	private static final String CHEESE_IMAGE = "cheese.png";
	private static final String MILK_IMAGE = "milk.png";
	private static final String MOUSETRAP_IMAGE = "mouseTrap.png";
	private static final String POWER_UP_IMAGE = "powerUp.png";
	private static final String CAT_IMAGE = "cat.png";
	private static final String MOUSE_IMAGE = "mouse.png";
	// Variables
	private int cheeseCollected = 0;
	private int mouseSpeed = 20;
	private int catSpeed = 12;
	private int catSpeedTimeIncrement = 150;
	private int catSpeedTime = 0;
	private int time, angle;
	private int catDelayDuration = 0;
	private int milkDelayDuration = 0;
	private int sideBarWidth = 270;
	private boolean upPressed, downPressed, leftPressed, rightPressed,
			checkXLeft, checkXRight, checkYTop, checkYBottom, isCheeseFound,
			isMilkFound, isPowerUpFound, isObstacleHere, isItemHere,
			isMouseHere, isArrowHere, isArrowHereUp, isArrowHereRight,
			drawMilk, drawPowerUp, setCatDelay, setMilkDelay, isPointReached;
	// Timers
	private Timer catTimer, mouseTimer, scoreTimer;
	// House
	private House house;
	// Graphics for rooms
	private RoomGraphic cat, mouse, mouseTrap, cheese, milk, powerUp;

	// Lives for both modes
	private int lives;

	/**
	 * Constructs a new MazeArea object
	 */
	public SqueakHomeGame()
	{
		// Maze Images from Google Images and Microsoft Clip Art
		// Obstacle and Powerup Images by Alex and Daniel
		// Sidebar by Sid and Alex
		// Menu Screen by Alex
		// All Images modified by Alex, Daniel, and Sid
		gridImages = new Image[12];
		playerImage = new Image[4];

		gridImages[0] = new ImageIcon("wall3.gif").getImage();
		gridImages[1] = new ImageIcon("pathupdown.gif").getImage();
		gridImages[2] = new ImageIcon("pathleftright.gif").getImage();
		gridImages[3] = new ImageIcon("pathuprightturn.gif").getImage();
		gridImages[4] = new ImageIcon("pathbottomrightturn.gif").getImage();
		gridImages[5] = new ImageIcon("pathbottomleftturn.gif").getImage();
		gridImages[6] = new ImageIcon("pathupleftturn.gif").getImage();
		gridImages[7] = new ImageIcon("cheese.png").getImage();
		gridImages[8] = new ImageIcon("sticks.png").getImage();
		gridImages[9] = new ImageIcon("pipeopening.gif").getImage();
		gridImages[10] = new ImageIcon("pipeClosing.gif").getImage();
		gridImages[11] = new ImageIcon("mouseTrap.png").getImage();
		playerImage[0] = new ImageIcon("blackmouseup.png").getImage();
		playerImage[1] = new ImageIcon("blackmouseright.png").getImage();
		playerImage[2] = new ImageIcon("blackmousedown.png").getImage();
		playerImage[3] = new ImageIcon("blackmouseleft.png").getImage();
		backgroundImage = new ImageIcon("menuBackground.png").getImage();
		instructionScreen = new ImageIcon("instructionsScreen.png").getImage();
		playerDir = 1;
		// Set the image height and width based on the path image size
		IMAGE_WIDTH = gridImages[0].getWidth(this);
		IMAGE_HEIGHT = gridImages[0].getHeight(this);
		setPreferredSize(new Dimension(1520, 810));
		// Alligator
		alligators = new Alligator[13];
		alligatorTimer = new Timer(ALLIGATOR_TIMER_INTERVAL,
				new AlligatorTimerEventHandler());
		// Sidebar and Inventory
		sidebar = new Sidebar();

		inventory = new Inventory();
		energyTimer = new Timer(ENERGY_TIMER_INTERVAL,
				new TimerEnergyEventHandler());
		lives = 3;

		// Survival Mode
		scoreTimer = new Timer(1000, new TimerEventHandler());
		house = new House();
		cat = new RoomGraphic("Cat", CAT_IMAGE, new Point(sideBarWidth, 0));
		mouse = new RoomGraphic("Mouse", MOUSE_IMAGE,
				new Point(sideBarWidth, 0));
		mouseTrap = new RoomGraphic("Mouse Trap", MOUSETRAP_IMAGE, new Point(
				600, 400));
		angle = 0;
		mouse.Position.x = house.CurrentRoom.Width - 1 - mouse.getWidth();
		mouse.Position.y = house.CurrentRoom.Height - 1 - mouse.getHeight();
		cheese = new RoomGraphic("Cheese", CHEESE_IMAGE, new Point());
		milk = new RoomGraphic("Milk", MILK_IMAGE, new Point());
		powerUp = new RoomGraphic("Power Up", POWER_UP_IMAGE, new Point());
		house.CheeseItemRoom = GetRandomRoom();
		SetItemPosition(cheese, house.CheeseItemRoom);
		catTimer = new Timer(CAT_TIME_INTERVAL, new CatTimerEventHandler());
		mouseTimer = new Timer(MOUSE_TIME_INTERVAL,
				new MouseTimerEventHandler());

		// Sets up for keyboard input and mouse input
		setFocusable(true);
		addKeyListener(this);
		addMouseListener(this);
		requestFocusInWindow();
		mainMenu = true;
		missionStart = false;
		survivalStart = false;
		gamePaused = false;
		instructions = false;

	}

	/**
	 * Inner class for time events for the energy
	 * 
	 */
	private class TimerEnergyEventHandler implements ActionListener
	{
		Component frame = SqueakHomeGame.this;

		/**
		 * Method to handle the energy timer
		 * 
		 * @param event the Timer event
		 */
		public void actionPerformed(ActionEvent event)
		{
			// Decrease energy, and lose a life if all energy is burnt
			sidebar.decreaseEnergy();
			if (sidebar.getEnergy() == 0)
			{
				JOptionPane.showMessageDialog(frame,
						"Oops! Tim ran out of energy! YOU LOST A LIFE!",
						"Game Notification", JOptionPane.INFORMATION_MESSAGE);
				resetMouse();
				lives--;
				if (lives == 0)
					gameOver();
				sidebar.decreaseLife();
				sidebar.resetEnergy();
			}
		}
	}

	/**
	 * Inner class for time events for the cat
	 */
	private class CatTimerEventHandler implements ActionListener
	{
		/**
		 * Method to handle the cat timer
		 * 
		 * @param event the Timer event
		 */
		public void actionPerformed(ActionEvent event)
		{
			// Delays the cat showing on screen
			if (setCatDelay)
			{
				if (catDelayDuration > CAT_DELAY_TIME)
				{
					setCatDelay = false;
					catDelayDuration = 0;
				}
				else
				{
					catDelayDuration += CAT_TIME_INTERVAL;
				}
				return;
			}
			// Delays the milk showing on screen
			if (setMilkDelay)
			{
				if (milkDelayDuration > MILK_DELAY_TIME)
				{
					setMilkDelay = false;
					milkDelayDuration = 0;
				}
				else
				{
					milkDelayDuration += CAT_TIME_INTERVAL;
				}
				return;
			}

			// Increment cat speed
			if (catSpeedTime > catSpeedTimeIncrement)
			{
				catSpeedTime = 0;
				catSpeed++;
			}
			else
				catSpeedTime++;

			// Move the cat based on mouse position and stop when obstacles are
			// in front
			if ((mouse.Position.x > cat.Position.x)
					&& (cat.Position.x < house.CurrentRoom.Width
							- cat.getWidth()))
			{
				cat.Position.x += catSpeed;
				isObstacleHere = IsObstacleHere(cat, house.CurrentRoom);
				if (isObstacleHere)
					cat.Position.x -= catSpeed;
			}
			if ((mouse.Position.x < cat.Position.x)
					&& (cat.Position.x > sideBarWidth))
			{
				cat.Position.x -= catSpeed;
				isObstacleHere = IsObstacleHere(cat, house.CurrentRoom);
				if (isObstacleHere)
					cat.Position.x += catSpeed;
			}
			if ((mouse.Position.y > cat.Position.y)
					&& (cat.Position.y < house.CurrentRoom.Height
							- cat.getHeight()))
			{
				cat.Position.y += catSpeed;
				isObstacleHere = IsObstacleHere(cat, house.CurrentRoom);
				if (isObstacleHere)
					cat.Position.y -= catSpeed;
			}
			if ((mouse.Position.y < cat.Position.y) && (cat.Position.y > 0))
			{
				cat.Position.y -= catSpeed;
				isObstacleHere = IsObstacleHere(cat, house.CurrentRoom);
				if (isObstacleHere)
					cat.Position.y += catSpeed;
			}
			// Checks if cat is caught
			if (IsMouseCaught() || IsItemHere(mouseTrap))
				mouseCaught();
			repaint();
		}
	}

	/**
	 * Inner class for time events for the mouse
	 */
	private class MouseTimerEventHandler implements ActionListener
	{

		/**
		 * Method to handle the mouse timer
		 * 
		 * @param event the Timer event
		 */
		public void actionPerformed(ActionEvent event)
		{
			// Move the mouse and stop when obstacles are in front
			if (upPressed && mouse.Position.y > 0)
			{
				angle = 0;
				mouse.Position.y -= mouseSpeed;
				isObstacleHere = IsObstacleHere(mouse, house.CurrentRoom);
				if (isObstacleHere)
					mouse.Position.y += mouseSpeed;
			}
			if (downPressed
					&& mouse.Position.y < (house.CurrentRoom.Height - mouse
							.getHeight()))
			{
				angle = 180;
				mouse.Position.y += mouseSpeed;
				isObstacleHere = IsObstacleHere(mouse, house.CurrentRoom);
				if (isObstacleHere)
					mouse.Position.y -= mouseSpeed;
			}
			if (leftPressed && mouse.Position.x > sideBarWidth)
			{
				angle = 270;
				mouse.Position.x -= mouseSpeed;
				isObstacleHere = IsObstacleHere(mouse, house.CurrentRoom);
				if (isObstacleHere)
					mouse.Position.x += mouseSpeed;
			}
			if (rightPressed
					&& mouse.Position.x < (house.CurrentRoom.Width - mouse
							.getWidth()))
			{
				angle = 90;
				mouse.Position.x += mouseSpeed;
				isObstacleHere = IsObstacleHere(mouse, house.CurrentRoom);
				if (isObstacleHere)
					mouse.Position.x -= mouseSpeed;
			}

			// Diagonal movement of mouse
			if (upPressed && rightPressed)
				angle = 45;
			if (upPressed && leftPressed)
				angle = 315;
			if (downPressed && rightPressed)
				angle = 135;
			if (downPressed && leftPressed)
				angle = 225;
			isCheeseFound = IsItemHere(cheese);
			isMilkFound = IsItemHere(milk);
			isPowerUpFound = IsItemHere(powerUp);
			MoveMouseTrap(house.CurrentRoom);
			repaint();

		}
	}

	/**
	 * Inner Class for time events for the alligator
	 * 
	 */
	public class AlligatorTimerEventHandler implements ActionListener
	{
		Component frame = SqueakHomeGame.this;

		/**
		 * Method to handle the alligator timer
		 * 
		 * @param event the Timer event
		 */
		public void actionPerformed(ActionEvent event)
		{
			Component frame = SqueakHomeGame.this;
			// Moves the alligator back and forth
			for (int alligator = 0; alligator < alligators.length; alligator++)
			{
				Alligator currentAlligator = alligators[alligator];

				if (currentAlligator.eatsMouse(currentRow, currentColumn))
				{
					JOptionPane
							.showMessageDialog(
									frame,
									"Ouch! Tim was eaten by Al the Alligator! YOU LOST A LIFE! BE CAREFUL NEXT TIME!",
									"Game Notification",
									JOptionPane.INFORMATION_MESSAGE);
					lives--;
					sidebar.decreaseLife();
					sidebar.resetEnergy();
					if (lives == 0)
						gameOver();
					resetMouse();
				}
				currentAlligator.move();
			}
			repaint();
		}
	}

	/**
	 * Inner class for time events to handle the score in survival modes
	 * 
	 */
	private class TimerEventHandler implements ActionListener
	{
		/**
		 * Method to handle the score timer
		 * 
		 * @param event the Timer event
		 */
		public void actionPerformed(ActionEvent event)
		{
			time++;
		}
	}

	// The following are methods for survival mode

	/**
	 * Starts survival mode
	 */
	public void survivalStart()
	{
		catTimer.start();
		mouseTimer.start();
		energyTimer.start();
		scoreTimer.start();
		survivalStart = true;
		missionStart = false;
		repaint();
	}

	/**
	 * Finds random room to start in
	 * 
	 * @return the random room to start in
	 */
	private Room GetRandomRoom()
	{
		// Set a random value from 0 to 2 to decide which room the item will
		// be draw in
		int randomRoom = (int) (Math.random() * (3));

		// If the random number is 0, the item will be draw in livingRoom
		if (randomRoom == 0)
			return house.livingRoom;
		// If the random number is 1, the item will be draw in kitchen
		else if (randomRoom == 1)
			return house.kitchen;
		// If the random number is 2, the item will be draw in bathroom
		else
			return house.bathroom;
	}

	/**
	 * Sets the items in the rooms
	 * 
	 * @param item the item to be put in the room
	 * @param room the room the item is being put in
	 */
	private void SetItemPosition(RoomGraphic item, Room room)
	{
		// Set a random value to the x and y of the cheese
		item.Position.x = (sideBarWidth)
				+ (int) (Math.random()
						* (room.Width - sideBarWidth - item.getWidth()) + (1));
		item.Position.y = (int) (Math.random()
				* (room.Height - item.getWidth()) + (1));

		// Check if this random value is in an obstacle
		isObstacleHere = IsObstacleHere(item, room);

		// Keep creating random cheese positions until it is not created
		// over an obstacle
		while (isObstacleHere)
		{
			// Set a random value to the x and y of the cheese
			item.Position.x = (sideBarWidth)
					+ (int) (Math.random()
							* (room.Width - sideBarWidth - item.getWidth()) + (1));
			item.Position.y = (int) (Math.random()
					* (room.Height - item.getWidth()) + (1));
			isObstacleHere = IsObstacleHere(item, room);
		}
	}

	/**
	 * Moves the mouse trap in the room
	 * 
	 * @param room the room the mousetrap is in
	 */
	public void MoveMouseTrap(Room room)
	{
		// If the room is the kitchen, move the mouse trap back and forth
		// horizontally
		if (room == house.kitchen)
		{
			mouseTrap.Position.y = 400;
			if (isPointReached == false)
			{
				if (mouseTrap.Position.x < 1100)
				{
					mouseTrap.Position.x = mouseTrap.Position.x + 10;
				}
				if (mouseTrap.Position.x == 1100)
					isPointReached = true;
			}
			if (isPointReached)
			{
				if (mouseTrap.Position.x > 600)
				{
					mouseTrap.Position.x = mouseTrap.Position.x - 10;
				}
				if (mouseTrap.Position.x == 600)
					isPointReached = false;
			}
		}
		// If the room is livingRoom or bathroom, move the mouse trap back
		// and forth vertically
		if ((room == house.livingRoom) || (room == house.bathroom))
		{
			mouseTrap.Position.x = 700;
			if (isPointReached == false)
			{
				if (mouseTrap.Position.y > 200)
				{
					mouseTrap.Position.y = mouseTrap.Position.y - 10;
				}
				if (mouseTrap.Position.y == 200)
					isPointReached = true;
			}
			if (isPointReached)
			{
				if (mouseTrap.Position.y < 600)
				{
					mouseTrap.Position.y = mouseTrap.Position.y + 10;
				}
				if (mouseTrap.Position.y == 600)
					isPointReached = false;
			}
		}
	}

	/**
	 * Checks for obstacles in front
	 * 
	 * @param mover the cat or mouse that is moving
	 * @param room the room the cat and mouse are currently in
	 * @return true if there is an obstacle in front, false if not
	 */
	public boolean IsObstacleHere(RoomGraphic mover, Room room)
	{

		for (int i = 0; i < room.Obstacles.length; i++)
		{
			isObstacleHere = IsCollisionHere(room.Obstacles[i], mover);
			if (isObstacleHere)
				return true;
		}
		return false;
	}

	/**
	 * Checks for an arrow to move to another room
	 * 
	 * @param arrowIndex the index of the arrow in the array
	 * @return returns if there is an arrow, false if not
	 */
	public boolean IsArrowHere(int arrowIndex)
	{

		isArrowHere = IsCollisionHere(
				house.CurrentRoom.NextRoomArrows[arrowIndex], mouse);
		return isArrowHere;
	}

	/**
	 * Checks if mouse is caught by the cat
	 * 
	 * @return true if caught, false if not
	 */
	public boolean IsMouseCaught()
	{
		isMouseHere = IsCollisionHere(cat, mouse);
		return isMouseHere;
	}

	/**
	 * Checks if there is an item there
	 * 
	 * @param item the item to check
	 * @return true if there is an item, false if not
	 */
	public boolean IsItemHere(RoomGraphic item)
	{
		isItemHere = IsCollisionHere(item, mouse);
		return isItemHere;
	}

	/**
	 * Checks for collisions
	 * 
	 * @param item the item to check
	 * @param mover the animal being checked
	 * @return
	 */
	public boolean IsCollisionHere(RoomGraphic item, RoomGraphic mover)
	{
		checkXLeft = false;
		checkXRight = false;
		checkYTop = false;
		checkYBottom = false;

		if (item.Position.x <= mover.Position.x)
			if (mover.Position.x - item.Position.x < item.getWidth())
				checkXLeft = true;
		if (item.Position.x > mover.Position.x)
			if (item.Position.x - (mover.Position.x + mover.getWidth()) < 0)
				checkXRight = true;

		if (item.Position.y <= mover.Position.y)
			if (mover.Position.y - item.Position.y < item.getHeight())
				checkYTop = true;
		if (item.Position.y > mover.Position.y)
			if (item.Position.y - (mover.Position.y + mover.getHeight()) < 0)
				checkYBottom = true;

		if (((checkXLeft && checkYTop) || (checkXRight && checkYTop))
				|| ((checkXLeft && checkYBottom) || (checkXRight && checkYBottom)))
		{
			return true;
		}
		return false;
	}

	/**
	 * Catches the mouse. There is only one life in survival mode.
	 */
	private void mouseCaught()
	{
		sidebar.decreaseLife();
		sidebar.decreaseLife();
		sidebar.decreaseLife();
		gameOver();
	}

	// The following methods are for the mission mode
	public void missionStart(String mazeFileName)
	{
		// Initial position of the player
		currentRow = 1;
		currentColumn = 1;
		// Initial position of the scroll
		startRow = 0;
		startCol = 0;
		// Load up the file for the maze
		try
		{
			// Find the size of the file first to size the array
			// Standard Java file input (better than hsa.TextInputFile)
			BufferedReader mazeFile = new BufferedReader(new FileReader(
					mazeFileName));
			String rowStr;

			// Set up the array
			grid = new int[100][50];
			mouseTraps = new boolean[100][50];

			// Load in the file data into the grid
			for (int row = 0; row < grid.length; row++)
			{
				rowStr = mazeFile.readLine();
				for (int column = 0; column < grid[0].length; column++)
				{
					if (Character.isLetter(rowStr.charAt(column)))
					{
						grid[row][column] = (int) (rowStr.charAt(column) - 'a');
						mouseTraps[row][column] = true;
					}
					else
					{
						grid[row][column] = (int) (rowStr.charAt(column) - '0');
					}
				}
			}

			// Read the blank line after the maze for the sewer
			String line;
			mazeFile.readLine();

			// Read in each extra line containing Alligator parameters and
			// create corresponding Alligator objects
			for (int alligatorIndex = 0; alligatorIndex <= 12; alligatorIndex++)
			{
				line = mazeFile.readLine();
				int startRow, startCol, endRow, endCol;

				// Read in the start and end rows and the start and end
				// columns of the alligator
				Scanner alligatorPositions = new Scanner(line);
				startRow = alligatorPositions.nextInt();
				startCol = alligatorPositions.nextInt();
				endRow = alligatorPositions.nextInt();
				endCol = alligatorPositions.nextInt();

				// Create an Alligator object element in the alligators
				// array using the parameters obtained above
				alligators[alligatorIndex] = new Alligator(startRow, startCol,
						endRow, endCol);
			}

			mazeFile.close();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(this, mazeFileName
					+ " not a valid maze file", "Message - Invalid Maze File",
					JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}
		survivalStart = false;
		missionStart = true;
		energyTimer.start();
		alligatorTimer.start();
		repaint();
	}

	/**
	 * Get to the end of the sewer in mission mode. Reset everything.
	 */
	public void beatMission()
	{
		alligatorTimer.stop();
		energyTimer.stop();
		JOptionPane.showMessageDialog(this,
				"Congratulations! You have beaten the mission.",
				" Congratulations!", JOptionPane.INFORMATION_MESSAGE);
		missionStart = false;
		survivalStart = false;
		mainMenu = true;
		inventory.resetInventory();
		sidebar.resetEnergy();
		sidebar.resetLives();
		lives = 3;
	}

	/**
	 * Moves the mouse back to the starting location on the grid
	 */
	public void resetMouse()
	{
		startRow = 0;
		startCol = 0;
		currentRow = 1;
		currentColumn = 1;
	}

	/**
	 * Checks the current path direction the mouse is currently on
	 * 
	 * @param row the row that the grid is at
	 * @param column the column that the grid is at
	 * @return the number to replace an image
	 */
	public int checkPathDirection(int row, int column)
	{
		if (grid[row - 1][column] == 1 && grid[row + 1][column] == 1)
			return 1;
		else if (grid[row][column - 1] == 2 && grid[row][column + 1] == 2)
			return 2;
		else if ((grid[row - 1][column] == 1 || grid[row + 1][column] == 1)
				&& (grid[row][column - 1] == 2 && grid[row][column + 1] == 2))
			return 2;
		else if ((grid[row][column - 1] == 2 || grid[row][column + 1] == 2)
				&& (grid[row - 1][column] == 1 && grid[row + 1][column] == 1))
			return 1;
		else if (grid[row][column - 1] == 2 && grid[row + 1][column] == 1)
			return 3;
		else if (grid[row - 1][column] == 1 && grid[row][column - 1] == 2)
			return 4;
		else if (grid[row][column + 1] == 2 && grid[row - 1][column] == 1)
			return 5;
		else
			return 6;
	}

	/**
	 * Trips the mouse trap (removes from maze)
	 */
	public void tripMouseTrap()
	{
		if ((grid[currentRow + 1][currentColumn] == 11 && mouseTraps[currentRow + 1][currentColumn]))
		{
			mouseTraps[currentRow + 1][currentColumn] = false;
		}
		else if ((grid[currentRow - 1][currentColumn] == 11 && mouseTraps[currentRow - 1][currentColumn]))
		{
			mouseTraps[currentRow - 1][currentColumn] = false;
		}
		else if (grid[currentRow][currentColumn - 1] == 11
				&& mouseTraps[currentRow][currentColumn - 1])
		{
			mouseTraps[currentRow][currentColumn - 1] = false;
		}
		else if (grid[currentRow][currentColumn + 1] == 11
				&& mouseTraps[currentRow][currentColumn + 1])
		{
			mouseTraps[currentRow][currentColumn + 1] = false;
		}
	}

	// The following methods are being used by the entire game
	/**
	 * Repaint the drawing panel
	 * 
	 * @param g The Graphics context
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (missionStart)
		{
			// Scrolling
			if (startRow < grid.length - SCROLL_HEIGHT
					&& currentRow - startRow > SCROLL_HEIGHT / 2)
				startRow++;

			if (startCol < grid[0].length - SCROLL_WIDTH
					&& currentColumn - startCol > SCROLL_WIDTH / 2)
				startCol++;

			if (currentRow < startRow + SCROLL_HEIGHT / 2 && startRow >= 1)
				startRow--;

			if (currentColumn < startCol + SCROLL_WIDTH / 2 && startCol >= 1)
				startCol--;

			// Redraw the grid with current images
			int drawRow = 0;
			for (int row = startRow; row < startRow + SCROLL_HEIGHT; row++)
			{
				int drawCol = 0;
				for (int column = startCol; column < startCol + SCROLL_WIDTH; column++)
				{
					int imageNo = grid[row][column];
					if (imageNo > 6 && imageNo < 9)
					{
						pathNo = checkPathDirection(row, column);
						g.drawImage(gridImages[pathNo], drawCol * IMAGE_WIDTH
								+ sidebar.getWidth(), drawRow * IMAGE_HEIGHT,
								this);
						g.drawImage(gridImages[imageNo], drawCol * IMAGE_WIDTH
								+ sidebar.getWidth() + 5, drawRow
								* IMAGE_HEIGHT + 5, this);
					}
					else if (imageNo == 11)
					{
						if (mouseTraps[row][column])
							g.drawImage(gridImages[11], drawCol * IMAGE_WIDTH
									+ sidebar.getWidth(), drawRow
									* IMAGE_HEIGHT, this);
						else
							g.drawImage(gridImages[2], drawCol * IMAGE_WIDTH
									+ sidebar.getWidth(), drawRow
									* IMAGE_HEIGHT, this);

					}
					else
						g.drawImage(gridImages[imageNo], drawCol * IMAGE_WIDTH
								+ sidebar.getWidth(), drawRow * IMAGE_HEIGHT,
								this);
					drawCol++;

				}
				drawRow++;
			}
			sidebar.draw(g);
			inventory.drawInventory(g);

			// Find the position of the mouse in the sewer in pixels
			int xMousePos = (currentColumn - startCol) * IMAGE_WIDTH + 5
					+ sidebar.getWidth();
			int yMousePos = (currentRow - startRow) * IMAGE_HEIGHT + 5;

			// Draw the moving player on top of the grid
			g.drawImage(playerImage[playerDir], xMousePos, yMousePos, this);

			for (int alligator = 0; alligator < alligators.length; alligator++)
			{
				int row = alligators[alligator].getRow();
				int col = alligators[alligator].getColumn();
				if ((row >= startRow && row < startRow + SCROLL_HEIGHT)
						&& (col >= startCol && col < startCol + SCROLL_WIDTH))
					alligators[alligator].draw(g, startRow, startCol + 1);
			}
		}
		else if (survivalStart)
		{
			// Draw rooms
			if (house.CurrentRoom == house.livingRoom)
			{
				isArrowHere = IsArrowHere(0);
				if (isArrowHere)
				{
					house.CurrentRoom = house.kitchen;
					mouse.Position.x = 900;
					mouse.Position.y = 300;
					setCatDelay = true;
					cat.Position.x = house.CurrentRoom.NextRoomArrows[0].Position.x;
					cat.Position.y = house.CurrentRoom.NextRoomArrows[0].Position.y
							- house.CurrentRoom.NextRoomArrows[0].getWidth();
				}
			}
			else if (house.CurrentRoom == house.kitchen)
			{
				isArrowHereUp = IsArrowHere(0);
				isArrowHereRight = IsArrowHere(1);
				if (isArrowHereUp)
				{
					house.CurrentRoom = house.livingRoom;
					mouse.Position.x = 900;
					mouse.Position.y = 300;
					setCatDelay = true;
					cat.Position.x = house.CurrentRoom.NextRoomArrows[0].Position.x;
					cat.Position.y = house.CurrentRoom.NextRoomArrows[0].Position.y
							- house.CurrentRoom.NextRoomArrows[0].getWidth();
				}
				if (isArrowHereRight)
				{
					house.CurrentRoom = house.bathroom;
					mouse.Position.x = 900;
					mouse.Position.y = 300;
					setCatDelay = true;
					cat.Position.x = house.CurrentRoom.NextRoomArrows[0].Position.x;
					cat.Position.y = house.CurrentRoom.NextRoomArrows[0].Position.y
							- house.CurrentRoom.NextRoomArrows[0].getWidth();
				}
			}
			else if (house.CurrentRoom == house.bathroom)
			{
				isArrowHere = IsArrowHere(0);
				if (isArrowHere)
				{
					house.CurrentRoom = house.kitchen;
					mouse.Position.x = 900;
					mouse.Position.y = 300;
					setCatDelay = true;
					cat.Position.x = house.CurrentRoom.NextRoomArrows[1].Position.x;
					cat.Position.y = house.CurrentRoom.NextRoomArrows[1].Position.y
							- house.CurrentRoom.NextRoomArrows[1].getWidth();
				}
			}
			house.CurrentRoom.Draw(g, this);
			mouse.DrawRotated(g, this, angle);
			if (!setCatDelay)
				cat.Draw(g, this);
			mouseTrap.Draw(g, this);
			if (isCheeseFound)
			{
				cheeseCollected++;
				sidebar.increaseEnergy();
				house.CheeseItemRoom = GetRandomRoom();
				SetItemPosition(cheese, house.CheeseItemRoom);
				if (house.CurrentRoom == house.CheeseItemRoom)
					cheese.Draw(g, this);
			}
			else if (house.CurrentRoom == house.CheeseItemRoom)
				cheese.Draw(g, this);
			// Draw a milk every 10 cheeses collected
			if (((cheeseCollected != 0) && (cheeseCollected % 10 == 0))
					&& (!drawMilk))
			{
				drawMilk = true;
				house.MilkItemRoom = GetRandomRoom();
				SetItemPosition(milk, house.MilkItemRoom);
			}
			if (drawMilk)
			{
				if (isMilkFound)
				{
					inventory.addItem(2);
					cheeseCollected = 0;
					drawMilk = false;
					setMilkDelay = true;
				}
				else if (house.CurrentRoom == house.MilkItemRoom)
					milk.Draw(g, this);
			}
			// Draw a powerUp every 5 cheeses collected
			if (((cheeseCollected != 0) && (cheeseCollected % 5 == 0))
					&& (!drawPowerUp))
			{
				drawPowerUp = true;
				house.PowerUpItemRoom = GetRandomRoom();
				SetItemPosition(powerUp, house.PowerUpItemRoom);
			}
			if (drawPowerUp)
			{
				if (isPowerUpFound)
				{
					cheeseCollected = 0;
					drawPowerUp = false;
					mouseSpeed++;
				}
				else if (house.CurrentRoom == house.PowerUpItemRoom)
					powerUp.Draw(g, this);
			}
			isCheeseFound = false;
			isMilkFound = false;
			isPowerUpFound = false;
			sidebar.draw(g);
			inventory.drawInventory(g);
		}
		else if (instructions)
			g.drawImage(instructionScreen, 0, 0, this);
		else
			g.drawImage(backgroundImage, 0, 0, this);
	}

	/**
	 * Game over
	 */
	public void gameOver()
	{
		energyTimer.stop();
		if (survivalStart)
		{
			// Key is Released
			leftPressed = false;
			rightPressed = false;
			upPressed = false;
			downPressed = false;
			catTimer.stop();
			mouseTimer.stop();
			scoreTimer.stop();
			JOptionPane.showMessageDialog(this,
					"Your time survived in seconds is:\n" + time + " seconds.",
					" Score", JOptionPane.INFORMATION_MESSAGE);
			// Reset the cat and mouse position
			cat = new RoomGraphic("Cat", CAT_IMAGE, new Point(sideBarWidth, 0));
			mouse.Position.x = house.CurrentRoom.Width - 1 - mouse.getWidth();
			mouse.Position.y = house.CurrentRoom.Height - 1 - mouse.getHeight();
			// Reset the Speed of the Cat and Mouse
			catSpeed = 12;
			catSpeedTime = 0;
			mouseSpeed = 20;
			house.CurrentRoom = house.kitchen;
			time = 0;
			cheeseCollected = 0;
			drawPowerUp = false;
			drawMilk = false;
		}
		else
			alligatorTimer.stop();
		JOptionPane.showMessageDialog(this, "Game Over!", " Game Over!",
				JOptionPane.WARNING_MESSAGE);
		inventory.resetInventory();
		sidebar.resetEnergy();
		sidebar.resetLives();
		lives = 3;
		missionStart = false;
		survivalStart = false;
		mainMenu = true;
		repaint();
	}

	/**
	 * Pauses the game
	 */
	public void pauseGame()
	{
		gamePaused = true;
		energyTimer.stop();
		if (missionStart)
			alligatorTimer.stop();
		else if (survivalStart)
		{
			catTimer.stop();
			mouseTimer.stop();
			scoreTimer.stop();
		}
		JOptionPane.showMessageDialog(this,
				"THE GAME HAS BEEN PAUSED. PRESS 'OK' TO CONTINUE",
				"Game Paused", JOptionPane.WARNING_MESSAGE);
		gamePaused = false;
	}

	/**
	 * Resumes the game
	 */
	public void resumeGame()
	{
		if (!gamePaused)
		{
			energyTimer.start();
			if (missionStart)
				alligatorTimer.start();
			else if (survivalStart)
			{
				catTimer.start();
				mouseTimer.start();
				scoreTimer.start();
			}
		}
	}

	/**
	 * Shows the instructions of the game
	 */
	public void showRules()
	{
		instructions = true;
		mainMenu = false;
		repaint();
	}

	/**
	 * Shows information about the game
	 */
	public void showAbout()
	{
		JOptionPane.showMessageDialog(this,
				"by Siddharth Vaknalli, Daniel Kula, \n Alexander Leung"
						+ "\n\u00a9 2014", "About Squeak Home",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Responds to arrow key inputs when a key is pressed
	 */
	public void keyPressed(KeyEvent event)
	{
		int keyEventCode = event.getKeyCode();
		if (missionStart)
		{

			// Change the currentRow and currentColumn of the player
			// based on the key pressed
			if (keyEventCode == KeyEvent.VK_LEFT)
			{
				if (currentColumn >= 1
						&& grid[currentRow][currentColumn - 1] != 0)
				{
					currentColumn--;
					playerDir = 3;
				}
			}
			else if (keyEventCode == KeyEvent.VK_RIGHT)
			{
				if (currentColumn < grid[0].length - 1
						&& grid[currentRow][currentColumn + 1] != 0)
				{
					currentColumn++;
					playerDir = 1;
				}
			}
			else if (keyEventCode == KeyEvent.VK_UP)
			{
				if (currentRow >= 1 && grid[currentRow - 1][currentColumn] != 0)
				{
					currentRow--;
					playerDir = 0;
				}
			}
			else if (keyEventCode == KeyEvent.VK_DOWN)
			{
				if (currentRow < grid.length - 1
						&& grid[currentRow + 1][currentColumn] != 0)
				{
					currentRow++;
					playerDir = 2;
				}
			}
			if (grid[currentRow][currentColumn] > 6
					&& grid[currentRow][currentColumn] < 9)
			{
				// Check if the mouse is currently picking up cheese or a stick
				// and perform the appropriate action
				if (grid[currentRow][currentColumn] == 8)
					inventory.addItem(1);
				else if (grid[currentRow][currentColumn] == 7)
					sidebar.increaseEnergy();
				grid[currentRow][currentColumn] = pathNo;

			}
			if (grid[currentRow][currentColumn] == 11
					&& mouseTraps[currentRow][currentColumn])
			{
				JOptionPane.showMessageDialog(this,
						"Tim was caught in a mousetrap! YOU LOSE A LIFE!",
						"Game Notification", JOptionPane.INFORMATION_MESSAGE);
				lives--;
				sidebar.decreaseLife();
				sidebar.resetEnergy();
				if (lives == 0)
					gameOver();
				resetMouse();
			}
			if (grid[currentRow][currentColumn] == 10)
				beatMission();
		}
		else if (survivalStart)
		{
			if (keyEventCode == event.VK_LEFT)
				leftPressed = true;
			else if (keyEventCode == event.VK_RIGHT)
				rightPressed = true;
			else if (keyEventCode == event.VK_UP)
				upPressed = true;
			else if (keyEventCode == event.VK_DOWN)
				downPressed = true;
			else if (keyEventCode == event.VK_ESCAPE)
				gameOver();
		}
		repaint();
	}

	/**
	 * Responds to arrow key inputs when a key is released
	 */
	public void keyReleased(KeyEvent event)
	{
		int keyCode = event.getKeyCode();
		if (keyCode == event.VK_LEFT)
			leftPressed = false;
		else if (keyCode == event.VK_RIGHT)
			rightPressed = false;
		else if (keyCode == event.VK_UP)
			upPressed = false;
		else if (keyCode == event.VK_DOWN)
			downPressed = false;
	}

	/**
	 * Handles mouse inputs from the user
	 */
	public void mousePressed(MouseEvent event)
	{
		// Main menu screen
		Point pressed = event.getPoint();
		if (mainMenu)
		{
			if (missionButton.contains(pressed))
			{
				mainMenu = false;
				missionStart("mazefinal.txt");
			}
			else if (instructionsButton.contains(pressed))
				showRules();
			else if (survivalButton.contains(pressed))
			{
				mainMenu = false;
				survivalStart();
			}
			else if (aboutButton.contains(pressed))
				showAbout();
		}
		// Instructions
		else if (instructions)
		{
			if (backToMenuButton.contains(pressed))
			{
				instructions = false;
				mainMenu = true;
				repaint();
			}
		}
		else
		{
			// Inventory
			// If milk drinking feature added, the else above would be else if
			// Else would be below, indicating that the mouse is in survival
			// mode instead of mission mode
			if (firstBox.contains(pressed))
			{
				if (inventory.isItemHere(0, 0))
				{
					inventory.dropItem(0, 0);
					tripMouseTrap();
				}
			}
			else if (secondBox.contains(pressed))
			{
				if (inventory.isItemHere(0, 1))
				{
					inventory.dropItem(0, 1);
					tripMouseTrap();
				}
			}
			else if (thirdBox.contains(pressed))
			{
				if (inventory.isItemHere(0, 2))
				{
					inventory.dropItem(0, 2);
					tripMouseTrap();
				}
			}
			else if (fourthBox.contains(pressed))
			{
				if (inventory.isItemHere(1, 0))
				{
					inventory.dropItem(1, 0);
					tripMouseTrap();
				}
			}
			else if (fifthBox.contains(pressed))
			{
				if (inventory.isItemHere(1, 1))
				{
					inventory.dropItem(1, 1);
					tripMouseTrap();
				}
			}
			else if (sixthBox.contains(pressed))
			{
				if (inventory.isItemHere(1, 2))
				{
					inventory.dropItem(1, 2);
					tripMouseTrap();
				}
			}
			else if (seventhBox.contains(pressed))
			{
				if (inventory.isItemHere(2, 0))
				{
					inventory.dropItem(2, 0);
					tripMouseTrap();
				}
			}
			else if (eighthBox.contains(pressed))
			{
				if (inventory.isItemHere(2, 1))
				{
					inventory.dropItem(2, 1);
					tripMouseTrap();
				}
			}
			else if (ninthBox.contains(pressed))
			{
				if (inventory.isItemHere(2, 2))
				{
					inventory.dropItem(2, 2);
					tripMouseTrap();
				}
			}
			else if (tenthBox.contains(pressed))
			{
				if (inventory.isItemHere(3, 0))
				{
					inventory.dropItem(3, 0);
					tripMouseTrap();
				}
			}
			else if (eleventhBox.contains(pressed))
			{
				if (inventory.isItemHere(3, 1))
				{
					inventory.dropItem(3, 1);
					tripMouseTrap();
				}
			}
			else if (twelthBox.contains(pressed))
			{
				if (inventory.isItemHere(3, 1))
				{
					inventory.dropItem(3, 1);
					tripMouseTrap();
				}
			}
		}
	}

	// Dummy Methods that are not in use
	public void keyTyped(KeyEvent e)
	{
	}

	public void mouseClicked(MouseEvent e)
	{
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
	}
}
