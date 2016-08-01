package pt314.just4fun.minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import pt314.just4fun.minesweeper.game.Game;
import pt314.just4fun.minesweeper.game.MineField;
import pt314.just4fun.minesweeper.game.MineFieldGenerator;
import pt314.just4fun.minesweeper.gui.MineFieldPanel;
import pt314.just4fun.minesweeper.util.Time;

public class Minesweeper extends JFrame implements ActionListener {

	private Game game;
	private Timer timer;

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
		// init game
		setGameDifficulty();
		game = new Game(numRows, numCols, numMines);
		
		// clear content pane
		Container contentPane = getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(new BorderLayout());

		// add mine field panel
		JPanel mineFieldPanel = new MineFieldPanel(game);
		contentPane.add(mineFieldPanel, BorderLayout.CENTER);
		
		// add status panel
		JPanel statusPanel = new JPanel();
		statusPanel.setBackground(Color.BLACK);
		final JLabel timeLabel = new JLabel("0");
		timeLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
		timeLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		timeLabel.setForeground(Color.WHITE);
		statusPanel.add(timeLabel);
		contentPane.add(statusPanel, BorderLayout.NORTH);

		//contentPane.revalidate();
		pack();
		
		// TODO: Make sure timer only runs after game starts and stops when game ends
		timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				long time = game.getTime();
				long millis = Time.timeMillis(time);
				long seconds = Time.timeSeconds(time);
				long minutes = Time.timeMinutes(time);
				String timeString = String.format("%02d:%02d.%02d", minutes, seconds, millis / 10);
				timeLabel.setText(timeString);
				timeLabel.revalidate();
			}
		});
		timer.start();
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
