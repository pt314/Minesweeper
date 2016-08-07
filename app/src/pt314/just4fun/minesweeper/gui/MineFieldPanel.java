package pt314.just4fun.minesweeper.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pt314.just4fun.minesweeper.GameOptions;
import pt314.just4fun.minesweeper.game.Game;
import pt314.just4fun.minesweeper.game.MineField;
import pt314.just4fun.minesweeper.game.MineFieldCell;

public class MineFieldPanel extends JPanel {

	private static final int CELL_SIZE = 32;

	private Game game = null;
	private MineField mineField = null;
	
	private GameOptions options = null;
	
	private MineFieldButton[][] mineFieldButtons = null;
	
	public MineFieldPanel(Game game, GameOptions options) {
		this.game = game;
		this.mineField = game.mineField;
		this.options = options;
		int numRows = mineField.getRows();
		int numCols = mineField.getCols();
		
		GridLayout layout = new GridLayout(numRows, numCols);
		setLayout(layout);
		
		mineFieldButtons = new MineFieldButton[numRows][numCols];
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				//JButton button = new JButton(r + " " + c);
				MineFieldButton button = new MineFieldButton(mineField, r, c);
				mineFieldButtons[r][c] = button;
				
				button.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
				button.addActionListener(new GridButtonHandler());
				
				add(button);
			}
		}
	}

	private void toggleMark(int row, int col) {
		game.toggleMark(row, col);
		if (!options.isAllowQuestionMarks())
			if (mineField.getCell(row, col).isQuestionMarked())
				game.toggleMark(row, col);
		mineFieldButtons[row][col].updateUI();
	}

	private void clear(int row, int col) {
		// clear cell
		Set<MineFieldCell> cells = game.clear(row, col);
		
		// update ui (only cells that changed)
		for (MineFieldCell cell : cells) {
			if (cell.isCleared()) {
				int r = cell.getRow();
				int c = cell.getCol();
				cell.setEnabled(false);
				mineFieldButtons[r][c].updateUI();
			}
		}
	}

	private void clearSurrounding(int row, int col) {
		// clear cells
		Set<MineFieldCell> cells = game.clearSurrounding(row, col);
		
		// update ui (only cells that changed)
		for (MineFieldCell cell : cells) {
			if (cell.isCleared()) {
				int r = cell.getRow();
				int c = cell.getCol();
				cell.setEnabled(false);
				mineFieldButtons[r][c].updateUI();
			}
		}
	}

	private void removeMine(int row, int col) {
		// remove mine
		Set<MineFieldCell> cells = game.removeMine(row, col);
		
		// update ui (only cells that changed)
		for (MineFieldCell cell : cells) {
			if (cell.isCleared()) {
				int r = cell.getRow();
				int c = cell.getCol();
				cell.setEnabled(false);
				mineFieldButtons[r][c].updateUI();
			}
		}
	}

	private void disableButtons() {
		for (int row = 0; row < mineField.getRows(); row++) {
			for (int col = 0; col < mineField.getCols(); col++) {
				MineFieldCell cell = mineField.getCell(row, col);
				cell.setEnabled(false);
				mineFieldButtons[row][col].setEnabled(false);
				mineFieldButtons[row][col].updateUI();
			}
		}
	}

	private void showMines() {
		for (int row = 0; row < mineField.getRows(); row++) {
			for (int col = 0; col < mineField.getCols(); col++) {
				MineFieldCell cell = mineField.getCell(row, col);
				if (cell.isMined()) {
					cell.clear();
					mineFieldButtons[row][col].clear();
				}
				mineFieldButtons[row][col].updateUI();
			}
		}
	}

	private class GridButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			MineFieldButton button = (MineFieldButton) e.getSource();
			int row = button.getRow();
			int col = button.getCol();
			
			// Ctrl + Shift + Click -> remove mine (experimental)
			if ((e.getModifiers() & 
					(ActionEvent.SHIFT_MASK + ActionEvent.CTRL_MASK)) 
					== (ActionEvent.SHIFT_MASK + ActionEvent.CTRL_MASK)) {
				removeMine(row, col);
			}
			// Alt + Click -> toggle mark (flag/question mark)
			else if ((e.getModifiers() & (ActionEvent.ALT_MASK)) == (ActionEvent.ALT_MASK)) {
				toggleMark(row, col);
			}
			// Ctrl + Click -> clear surrounding
			else if ((e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
				clearSurrounding(row, col);
			}
			// Click -> clear cell
			else {
				clear(row, col);
			}

			// Check if game is over
			if (game.isOver()) {
				disableButtons();
				if (game.isWin()) {
					JOptionPane.showMessageDialog(MineFieldPanel.this,
							"Congratulations!  :)", "You won!",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					showMines();
					JOptionPane.showMessageDialog(MineFieldPanel.this,
							"Sorry :(", "You lost...",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
