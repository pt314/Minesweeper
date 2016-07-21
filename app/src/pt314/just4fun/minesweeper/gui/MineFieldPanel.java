package pt314.just4fun.minesweeper.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import pt314.just4fun.minesweeper.game.Game;
import pt314.just4fun.minesweeper.game.MineField;
import pt314.just4fun.minesweeper.images.ImageLoader;

public class MineFieldPanel extends JPanel {

	private static final int CELL_SIZE = 48;

	private Game game = null;

	private MineFieldButton[][] mineFieldButtons = null;
	
	public MineFieldPanel(Game game) {
		this.game = game;
		MineField mineField = game.mineField;
		int numRows = mineField.getNumRows();
		int numCols = mineField.getNumCols();
		
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
		game.clear(row, col);
		
		// update ui
		// TODO: only update the cells that changed
		for (int r = 0; r < mineField.getNumRows(); r++) {
			for (int c = 0; c < mineField.getNumCols(); c++) {
				if (mineField.getCell(r, c).isCleared())
					mineFieldButtons[r][c].clear();
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
