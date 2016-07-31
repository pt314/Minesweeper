package pt314.just4fun.minesweeper;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

import pt314.just4fun.minesweeper.game.Game;
import pt314.just4fun.minesweeper.game.MineField;
import pt314.just4fun.minesweeper.game.MineFieldGenerator;
import pt314.just4fun.minesweeper.gui.MineFieldPanel;

public class Minesweeper extends JFrame implements ActionListener {

	private Game game;

	// board size and number of mines
	private int numRows;
	private int numCols;
	private int numMines;

	// menu bar and menus
    private JMenuBar menuBar;
    private JMenu gameMenu;
    
    // game menu items
    private JMenuItem newGameMI;
    private JRadioButtonMenuItem smallFieldMI;
    private JRadioButtonMenuItem mediumFieldMI;
    private JRadioButtonMenuItem largeFieldMI;
    private JMenuItem exitMI;

    public Minesweeper() {
		super("Just for fun Minesweeper game!");
		
		// TODO: set resizable to false (true now for testing)
		//setResizable(false);
		initMenus();
		
		startNewGame();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	private void initMenus() {
    	menuBar = new JMenuBar();

    	// create game menu
        gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);

        newGameMI = new JMenuItem("New Game");
        newGameMI.addActionListener(this);
        gameMenu.add(newGameMI);

        gameMenu.addSeparator();
        
        ButtonGroup group = new ButtonGroup();
        smallFieldMI = new JRadioButtonMenuItem("Beginner");
        group.add(smallFieldMI);
        gameMenu.add(smallFieldMI);
        mediumFieldMI = new JRadioButtonMenuItem("Intermediate");
        group.add(mediumFieldMI);
        gameMenu.add(mediumFieldMI);
        largeFieldMI = new JRadioButtonMenuItem("Expert");
        group.add(largeFieldMI);
        gameMenu.add(largeFieldMI);
        smallFieldMI.setSelected(true); // default to small

        gameMenu.addSeparator();

        exitMI = new JMenuItem("Exit");
        exitMI.addActionListener(this);
        gameMenu.add(exitMI);

        // set menu bar
        setJMenuBar(menuBar);
	}
	
	/**
	 * Sets the size of the board and the number of mines.
	 */
	private void setGameDifficulty() {
		if (smallFieldMI.isSelected()) {
			numRows = 8;
			numCols = 8;
			numMines = 10;
		}
		else if (mediumFieldMI.isSelected()) {
			numRows = 16;
			numCols = 16;
			numMines = 40;
		}
		else if (largeFieldMI.isSelected()) {
			numRows = 24;
			numCols = 24;
			numMines = 99;
		}
	}

	// TODO: set mines after first space is cleared???
	private void startNewGame() {
		setGameDifficulty();
		game = new Game(numRows, numCols, numMines);
		JPanel board = new MineFieldPanel(game);
		Container contentPane = getContentPane();
		contentPane.removeAll();
		contentPane.add(board);
		//contentPane.revalidate();
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem mItem = (JMenuItem) e.getSource();
		
		if (mItem == newGameMI) {
			startNewGame();
		}
		else if (mItem == exitMI) {
			System.exit(0);
		}		
	}
	
    public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
				new Minesweeper();
		    }
		});
	}
}
