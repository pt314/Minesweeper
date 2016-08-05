package pt314.just4fun.minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
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
    private JMenu helpMenu;
    
    // game menu items
    private JMenuItem newGameMI;
    private JRadioButtonMenuItem easyMI;
    private JRadioButtonMenuItem mediumMI;
    private JRadioButtonMenuItem hardMI;
    private JMenuItem exitMI;
    
    // help menu items
    private JMenuItem aboutMI;

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
        easyMI = new JRadioButtonMenuItem("Beginner");
        group.add(easyMI);
        gameMenu.add(easyMI);
        mediumMI = new JRadioButtonMenuItem("Intermediate");
        group.add(mediumMI);
        gameMenu.add(mediumMI);
        hardMI = new JRadioButtonMenuItem("Expert");
        group.add(hardMI);
        gameMenu.add(hardMI);
        easyMI.setSelected(true); // default to small

        gameMenu.addSeparator();

        exitMI = new JMenuItem("Exit");
        exitMI.addActionListener(this);
        gameMenu.add(exitMI);

    	// create help menu
        helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        aboutMI = new JMenuItem("About");
        aboutMI.addActionListener(this);
        helpMenu.add(aboutMI);

        // keyboard shortcuts
        
        // mnemonics for navigating menus
        gameMenu.setMnemonic(KeyEvent.VK_G); // (G)ame
        easyMI.setMnemonic(KeyEvent.VK_B);	 // (B)eginner
        mediumMI.setMnemonic(KeyEvent.VK_I); // (I)ntermediate	
        hardMI.setMnemonic(KeyEvent.VK_E);	 // (E)xpert
        exitMI.setMnemonic(KeyEvent.VK_X);	 // E(x)it
        helpMenu.setMnemonic(KeyEvent.VK_H); // (H)elp
        
        // accelerators to select options without going through the menus
        newGameMI.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.getKeyText(KeyEvent.VK_F2)));	// F2: new game
        aboutMI.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.getKeyText(KeyEvent.VK_F1)));	// F1: help/about

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
		else if (mItem == aboutMI) {
			JOptionPane.showMessageDialog(this,
					"https://github.com/pt314/minesweeper", "About",
					JOptionPane.INFORMATION_MESSAGE);
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
