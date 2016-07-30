package pt314.just4fun.minesweeper;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pt314.just4fun.minesweeper.game.Game;
import pt314.just4fun.minesweeper.game.MineField;
import pt314.just4fun.minesweeper.game.MineFieldGenerator;
import pt314.just4fun.minesweeper.gui.MineFieldPanel;

public class Minesweeper extends JFrame implements ActionListener {

	private Game game;

	private int numRows = 10;
	private int numCols = 10;
	private int numMines = 20;

	// menu bar and menus
    private JMenuBar menuBar;
    private JMenu gameMenu;
    
    // game menu items
    private JMenuItem newGameMI;
    private JMenuItem exitMI;

    public Minesweeper() {
		super("Just for fun Minesweeper game!");
		
		setResizable(false);
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

        newGameMI = new JMenuItem("New");
        newGameMI.addActionListener(this);
        gameMenu.add(newGameMI);

        gameMenu.addSeparator();

        exitMI = new JMenuItem("Exit");
        exitMI.addActionListener(this);
        gameMenu.add(exitMI);

        // set menu bar
        setJMenuBar(menuBar);
	}
	
	// TODO: set mines after first space is cleared???
	private void startNewGame() {
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
