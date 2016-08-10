package pt314.just4fun.minesweeper.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import pt314.just4fun.minesweeper.GameOptions;
import pt314.just4fun.minesweeper.game.Game;
import pt314.just4fun.minesweeper.game.MineField;
import pt314.just4fun.minesweeper.game.MineFieldCell;

public class MineFieldPanel extends JPanel {

	private static final int CELL_SIZE = 32;

	private MineField mineField = null;
	
	private MineFieldButton[][] mineFieldButtons = null;
	
	private MineFieldButtonHandler buttonHandler = null;
	
	public MineFieldPanel(Game game, GameOptions options) {
		this.mineField = game.mineField;
		
		// Color and border
		setBackground(Color.GRAY);
		Border b1 = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border b2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
		Border b3 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border b12 = BorderFactory.createCompoundBorder(b1, b2);
		Border b123 = BorderFactory.createCompoundBorder(b12, b3);
		setBorder(b123);
		
		// Create grid
		int numRows = mineField.getRows();
		int numCols = mineField.getCols();
		GridLayout layout = new GridLayout(numRows, numCols);
		setLayout(layout);
		
		// Add buttons
		buttonHandler = new MineFieldButtonHandler(game, options, this);
		mineFieldButtons = new MineFieldButton[numRows][numCols];
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				//JButton button = new JButton(r + " " + c);
				MineFieldButton button = new MineFieldButton(mineField, r, c, options);
				mineFieldButtons[r][c] = button;
				
				button.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
				button.addActionListener(buttonHandler);
				
				add(button);
			}
		}
	}

	public void disableButtons() {
		for (int row = 0; row < mineField.getRows(); row++) {
			for (int col = 0; col < mineField.getCols(); col++) {
				MineFieldCell cell = mineField.getCell(row, col);
				cell.setEnabled(false);
				mineFieldButtons[row][col].setEnabled(false);
				mineFieldButtons[row][col].update();
			}
		}
	}

	public void showMines() {
		for (int row = 0; row < mineField.getRows(); row++) {
			for (int col = 0; col < mineField.getCols(); col++) {
				MineFieldCell cell = mineField.getCell(row, col);
				if (cell.isMined())
					cell.clear();
				mineFieldButtons[row][col].update();
			}
		}
	}

	// update display - all cells
	public void update() {
		for (int row = 0; row < mineField.getRows(); row++) {
			for (int col = 0; col < mineField.getCols(); col++) {
				mineFieldButtons[row][col].update();
			}
		}
	}

	// update display - specific cells
	public void update(Set<MineFieldCell> cells) {
		for (MineFieldCell cell : cells) {
			int row = cell.getRow();
			int col = cell.getCol();
			mineFieldButtons[row][col].update();
		}
	}
}
