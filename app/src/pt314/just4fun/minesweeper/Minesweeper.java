package pt314.just4fun.minesweeper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
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
import pt314.just4fun.minesweeper.gui.MineFieldPanel;
import pt314.just4fun.minesweeper.images.ImageLoader;
import pt314.just4fun.minesweeper.util.Time;

public class Minesweeper extends JFrame implements ActionListener {

	private GameOptions options;
	
	private Game game;
	private Timer timer;

	private MineFieldPanel mineFieldPanel;
	
	// menu bar and menus
    private JMenuBar menuBar;
    private JMenu gameMenu;
    private JMenu optionsMenu;
    private JMenu helpMenu;
    
    // game menu items
    private JMenuItem newGameMI;
    private JRadioButtonMenuItem easyMI;
    private JRadioButtonMenuItem mediumMI;
    private JRadioButtonMenuItem hardMI;
    private JMenuItem exitMI;
    
    // option menu items
    private JCheckBoxMenuItem allowQuestionMarksMI;
    private JCheckBoxMenuItem allowRemovingMinesMI;
    private JCheckBoxMenuItem showHiddenMinesMI;
    
    // help menu items
    private JMenuItem aboutMI;

    public Minesweeper() {
		super("Just for fun Minesweeper game!");
		
		// Init options
		options = new GameOptions();
		options.setAllowQuestionMarks(true);
		options.setAllowRemovingMines(false);
		options.setShowHiddenMines(false);

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
        mediumMI.setSelected(true); // default to medium

        gameMenu.addSeparator();

        exitMI = new JMenuItem("Exit");
        exitMI.addActionListener(this);
        gameMenu.add(exitMI);

    	// create options menu
        optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);

        allowQuestionMarksMI = new JCheckBoxMenuItem("Allow question marks");
        allowQuestionMarksMI.setSelected(options.isAllowQuestionMarks());
        allowQuestionMarksMI.addActionListener(this);
        optionsMenu.add(allowQuestionMarksMI);

        allowRemovingMinesMI = new JCheckBoxMenuItem("Allow removing mines");
        allowRemovingMinesMI.setSelected(options.isAllowRemovingMines());
        allowRemovingMinesMI.addActionListener(this);
        optionsMenu.add(allowRemovingMinesMI);

        showHiddenMinesMI = new JCheckBoxMenuItem("Show hidden mines");
        showHiddenMinesMI.setSelected(options.isShowHiddenMines());
        showHiddenMinesMI.addActionListener(this);
        optionsMenu.add(showHiddenMinesMI);

    	// create help menu
        helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        aboutMI = new JMenuItem("About");
        aboutMI.addActionListener(this);
        helpMenu.add(aboutMI);

        // keyboard shortcuts
        
        // mnemonics for navigating menus
        gameMenu.setMnemonic(KeyEvent.VK_G);	// (G)ame
        easyMI.setMnemonic(KeyEvent.VK_B);		// (B)eginner
        mediumMI.setMnemonic(KeyEvent.VK_I);	// (I)ntermediate	
        hardMI.setMnemonic(KeyEvent.VK_E);		// (E)xpert
        exitMI.setMnemonic(KeyEvent.VK_X);		// E(x)it
        helpMenu.setMnemonic(KeyEvent.VK_H);	// (H)elp
        optionsMenu.setMnemonic(KeyEvent.VK_O);	// (O)ptions
        allowQuestionMarksMI.setMnemonic(KeyEvent.VK_Q);
        allowRemovingMinesMI.setMnemonic(KeyEvent.VK_R);
        
        // accelerators to select options without going through the menus
        newGameMI.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.getKeyText(KeyEvent.VK_F2)));	// F2: new game
        aboutMI.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.getKeyText(KeyEvent.VK_F1)));	// F1: help/about
        allowQuestionMarksMI.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.getKeyText(KeyEvent.VK_F3)));	// F3: allow question marks
        allowRemovingMinesMI.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.getKeyText(KeyEvent.VK_F4)));	// F4: allow removing mines
        showHiddenMinesMI.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.getKeyText(KeyEvent.VK_F5)));	// F5: show hidden mines

        // set menu bar
        setJMenuBar(menuBar);
	}
	
	/**
	 * Sets the size of the board and the number of mines.
	 */
	private void setGameDifficulty() {
		if (easyMI.isSelected())
			options.setDifficulty(GameOptions.DIFFICULTY_EASY);
		else if (mediumMI.isSelected())
			options.setDifficulty(GameOptions.DIFFICULTY_MEDIUM);
		else if (hardMI.isSelected())
			options.setDifficulty(GameOptions.DIFFICULTY_HARD);
	}

	// TODO: set mines after first space is cleared???
	private void startNewGame() {
		// init game
		setGameDifficulty();
		int numRows = options.getNumberOfRows();
		int numCols = options.getNumberOfColumns();
		int numMines = options.getNumberOfMines();
		game = new Game(numRows, numCols, numMines);
		
		// clear content pane
		Container contentPane = getContentPane();
		contentPane.removeAll();
		contentPane.setLayout(new BorderLayout());

		// add mine field panel
		mineFieldPanel = new MineFieldPanel(game, options);
		contentPane.add(mineFieldPanel, BorderLayout.CENTER);
		
		// add status panel
		
		// Time
		JPanel statusPanel = new JPanel();
		statusPanel.setBackground(Color.BLACK);
		final JLabel timeLabel = new JLabel("0");
		timeLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
		timeLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		timeLabel.setForeground(Color.YELLOW);

		// Number of mines
		final JLabel minesLabel = new JLabel("0");
		minesLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
		minesLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		minesLabel.setForeground(Color.GREEN);

		statusPanel.add(new JLabel(ImageLoader.createImageIcon("blue_ball.png")));
		statusPanel.add(timeLabel);
		statusPanel.add(new JLabel(ImageLoader.createImageIcon("red_ball.png")));
		statusPanel.add(minesLabel);
		contentPane.add(statusPanel, BorderLayout.NORTH);

		//contentPane.revalidate();
		pack();
		
		// Timer for updating status bar
		// TODO: Make sure timer only runs after game starts and stops when game ends
		timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				long time = game.getTime();
				long millis = Time.timeMillis(time);
				long seconds = Time.timeSeconds(time);
				long minutes = Time.timeMinutes(time);
				// Max timer right before 1h
				if (minutes > 59) {
					minutes = 59;
					seconds = 59;
					millis = 999;
				}
				String timeString = String.format("%02d:%02d.%02d", minutes, seconds, millis / 10);
				timeLabel.setText(timeString);
				timeLabel.revalidate();
				
				// TODO: move out of here - update only when value changes
				int mines = game.mineField.getMineCount() - game.mineField.getFlagCount();
				String minesString = String.valueOf(mines);
				minesLabel.setText(minesString);
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
		else if (mItem == allowQuestionMarksMI) {
			options.setAllowQuestionMarks(allowQuestionMarksMI.isSelected());
		}
		else if (mItem == allowRemovingMinesMI) {
			options.setAllowRemovingMines(allowRemovingMinesMI.isSelected());
		}
		else if (mItem == showHiddenMinesMI) {
			options.setShowHiddenMines(showHiddenMinesMI.isSelected());
			mineFieldPanel.update();
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
