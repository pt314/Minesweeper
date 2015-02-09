package pt314.just4fun.minesweeper.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import pt314.just4fun.minesweeper.game.MineField;
import pt314.just4fun.minesweeper.images.ImageLoader;

public class MineFieldPanel extends JPanel {

	private static final int CELL_SIZE = 64;

	private MineField mineField = null;

	private MineFieldButton[][] mineFieldButtons = null;
	
	public MineFieldPanel(MineField mineField) {
		this.mineField = mineField;
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
		mineFieldButtons[row][col].clear();
		if (mineField.getSurroundingMineCount(row, col) == 0) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					int r_ = row + i;
					int c_ = col + j;
					if (!mineField.withinBounds(r_, c_))
						continue;
					if (mineField.hasMineAt(r_, c_))
						continue;
					clear(r_, c_);
				}
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
				if (!mineField.hasMineAt(row, col))
					clear(row, col);
				button.clear();
			}
		}
		
	}
}
