package pt314.just4fun.minesweeper;

import javax.swing.JFrame;
import javax.swing.JPanel;

import pt314.just4fun.minesweeper.game.MineField;
import pt314.just4fun.minesweeper.game.MineFieldGenerator;
import pt314.just4fun.minesweeper.gui.MineFieldPanel;

public class Minesweeper extends JFrame {

	private int numRows = 10;
	private int numCols = 25;
	private int numMines = 50;

	private MineField mineField;

	public Minesweeper() {
		super("Just for fun Minesweeper game!");
		
		startNewGame();
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	// TODO: set mines after first space is cleared???
	private void startNewGame() {
		mineField = new MineFieldGenerator().generate(numRows, numCols, numMines);
		JPanel board = new MineFieldPanel(mineField);
		getContentPane().add(board);
		pack();
	}
	
    public static void main(String[] args) {
		new Minesweeper();
	}
}
