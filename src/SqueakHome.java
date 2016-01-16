import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

/**
 * The "SqueakHome" Class; Also known as the menus and menu bars
 * 
 * @author Alexander Leung
 * @version January 2014
 */
public class SqueakHome extends JFrame implements ActionListener
{
	private JMenuItem pauseGame, exitGame, rulesGame, aboutGame, missionMode,
			survivalMode;
	private SqueakHomeGame game;

	public SqueakHome()
	{
		super("Squeak Home!");
		setResizable(false);
		setSize(1520, 810);
		setIconImage(Toolkit.getDefaultToolkit().getImage("mouseIcon.png"));

		game = new SqueakHomeGame();
		Container contentPaneMission = getContentPane();
		contentPaneMission.add(game, BorderLayout.CENTER);

		// Game Menu
		JMenu gameMenu = new JMenu("Game");

		// New Game Menu
		JMenu newGame = new JMenu("New");
		missionMode = new JMenuItem("Mission Mode");
		missionMode.addActionListener(this);
		newGame.add(missionMode);
		survivalMode = new JMenuItem("Survival Mode");
		survivalMode.addActionListener(this);
		newGame.add(survivalMode);

		// Game Menu Items
		gameMenu.add(newGame);
		pauseGame = new JMenuItem("Pause");
		pauseGame.setAccelerator(KeyStroke.getKeyStroke('p'));
		pauseGame.addActionListener(this);
		gameMenu.add(pauseGame);
		exitGame = new JMenuItem("Exit");
		exitGame.addActionListener(this);
		gameMenu.add(exitGame);

		// Help Menu Items
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic('H');
		rulesGame = new JMenuItem("Rules", 'R');
		rulesGame.addActionListener(this);
		helpMenu.add(rulesGame);
		aboutGame = new JMenuItem("About", 'A');
		aboutGame.addActionListener(this);
		helpMenu.add(aboutGame);

		// Add Menu Items to Game Menu
		JMenuBar menu = new JMenuBar();
		menu.add(gameMenu);
		menu.add(helpMenu);

		// Set Menu Bar
		setJMenuBar(menu);
	}

	/**
	 * Responds to a Menu Event or Button, as ActionListener is implemented
	 * 
	 * @param event the event that triggered this method
	 */
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == missionMode)
			game.missionStart("mazefinal.txt");
		else if (event.getSource() == survivalMode)
			game.survivalStart();
		else if (event.getSource() == pauseGame)
		{
			game.pauseGame();
			game.resumeGame();
		}
		else if (event.getSource() == exitGame)
			System.exit(0);
		else if (event.getSource() == rulesGame)
			game.showRules();
		else if (event.getSource() == aboutGame)
			game.showAbout();
	}

	public static void main(String[] args) throws Exception
	{
		SqueakHome game = new SqueakHome();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.pack();
		game.setVisible(true);

	}
}