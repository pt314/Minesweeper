package pt314.just4fun.minesweeper.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pt314.just4fun.minesweeper.game.Game;
import pt314.just4fun.minesweeper.game.MineField;
import pt314.just4fun.minesweeper.game.MineFieldCell;
import pt314.just4fun.minesweeper.images.ImageLoader;

public class MineFieldPanel extends JPanel {

	private static final int CELL_SIZE = 48;

	private Game game = null;
	private MineField mineField = null;

	private MineFieldButton[][] mineFieldButtons = null;
	
	public MineFieldPanel(Game game) {
		this.game = game;
		this.mineField = game.mineField;
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

	private void clear(int row, int col) {
		if (mineFieldButtons[row][col].isCleared())
			return;
		
		// clear cell
		Set<MineFieldCell> cells = game.clear(row, col);
		
		// update ui (only cells that changed)
		for (MineFieldCell cell : cells) {
			if (cell.isCleared()) {
				int r = cell.getRow();
				int c = cell.getCol();
				mineFieldButtons[r][c].clear();
			}
		}
		
		if (game.isOver()) {
			disableButtons();
			if (game.isWin()) {
				JOptionPane.showMessageDialog(null, 
						"Congratulations!  :)", "You won!",
						JOptionPane.ERROR_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(null, 
						"Sorry :(", "You lost...",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void disableButtons() {
		for (int row = 0; row < mineField.getRows(); row++) {
			for (int col = 0; col < mineField.getCols(); col++) {
				mineFieldButtons[row][col].setEnabled(false);
			}
		}
	}

	private class GridButtonHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			MineFieldButton button = (MineFieldButton) e.getSource();
			int row = button.getRow();
			int col = button.getCol();
			if ((e.getModifiers() & 
					(ActionEvent.SHIFT_MASK + ActionEvent.CTRL_MASK)) 
					== (ActionEvent.SHIFT_MASK + ActionEvent.CTRL_MASK)) {
				button.setIcon(ImageLoader.createImageIcon("red_ball.png"));
			}
			else {
				clear(row, col);
			}
		}
	}
}
