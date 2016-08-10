package pt314.just4fun.minesweeper.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;

import pt314.just4fun.minesweeper.GameOptions;
import pt314.just4fun.minesweeper.game.Game;
import pt314.just4fun.minesweeper.game.MineFieldCell;

public class MineFieldButtonHandler implements ActionListener {
	
	private Game game;
	
	private GameOptions options;
	
	private MineFieldPanel mineFieldPanel;

	public MineFieldButtonHandler(Game game,
								  GameOptions options,
								  MineFieldPanel mineFieldPanel) {
		this.game = game;
		this.options = options;
		this.mineFieldPanel = mineFieldPanel;
	}

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
			mineFieldPanel.disableButtons();
			if (game.isWin()) {
				JOptionPane.showMessageDialog(mineFieldPanel,
						"Congratulations!  :)", "You won!",
						JOptionPane.ERROR_MESSAGE);
			}
			else {
				mineFieldPanel.showMines();
				JOptionPane.showMessageDialog(mineFieldPanel,
						"Sorry :(", "You lost...",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void toggleMark(int row, int col) {
		game.toggleMark(row, col);
		// Ignore question mark if not allowed
		if (!options.isAllowQuestionMarks())
			if (game.mineField.getCell(row, col).isQuestionMarked())
				game.toggleMark(row, col);
		Set<MineFieldCell> cells = new HashSet<>();
		cells.add(game.mineField.getCell(row, col));
		mineFieldPanel.update(cells);
	}

	private void clear(int row, int col) {
		// clear cell
		Set<MineFieldCell> cells = game.clear(row, col);
		
		// update ui (only cells that changed)
		for (MineFieldCell cell : cells)
			if (cell.isCleared())
				cell.setEnabled(false);
		mineFieldPanel.update(cells);
	}

	private void clearSurrounding(int row, int col) {
		// clear cells
		Set<MineFieldCell> cells = game.clearSurrounding(row, col);

		// update ui (only cells that changed)
		for (MineFieldCell cell : cells)
			if (cell.isCleared())
				cell.setEnabled(false);
		mineFieldPanel.update(cells);
	}

	private void removeMine(int row, int col) {
		// Ignore if option is disabled
		if (!options.isAllowRemovingMines())
			return;

		// remove mine
		Set<MineFieldCell> cells = game.removeMine(row, col);

		// update ui (only cells that changed)
		for (MineFieldCell cell : cells)
			if (cell.isCleared())
				cell.setEnabled(false);
		mineFieldPanel.update(cells);
	}
}