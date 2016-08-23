package pt314.just4fun.minesweeper.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import pt314.just4fun.minesweeper.GameOptions;
import pt314.just4fun.minesweeper.game.Game;

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
			mineFieldPanel.showMines();
			if (game.isWin()) {
				JOptionPane.showMessageDialog(mineFieldPanel,
						"Congratulations!  :)", "You won!",
						JOptionPane.ERROR_MESSAGE);
			}
			else {
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
	}

	private void clear(int row, int col) {
		game.clear(row, col);
	}

	private void clearSurrounding(int row, int col) {
		game.clearSurrounding(row, col);
	}

	private void removeMine(int row, int col) {
		// Ignore if option is disabled
		if (!options.isAllowRemovingMines())
			return;

		// remove mine
		game.removeMine(row, col);
	}
}