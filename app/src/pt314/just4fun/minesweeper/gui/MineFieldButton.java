package pt314.just4fun.minesweeper.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

import pt314.just4fun.minesweeper.game.MineField;
import pt314.just4fun.minesweeper.images.ImageLoader;

public class MineFieldButton extends JButton {

	private static final int FONT_SIZE = 24;
	
	private MineField mineField;
	
	private int row;
	
	private int col;
	
	private boolean cleared = false;
	
	public MineFieldButton(MineField mineField, int row, int col) {
		this.mineField = mineField;
		this.row = row;
		this.col = col;

		if (mineField.hasMineAt(row, col)) {
			setIcon(ImageLoader.createImageIcon("blue_ball.png"));
			setDisabledIcon(ImageLoader.createImageIcon("red_ball.png"));
		}
		else {
			//setText(String.valueOf(mineField.getSurroundingMineCount(row, col)));
			setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
			setForeground(Color.BLUE);
		}
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public boolean isCleared() {
		return cleared;
	}

	public void clear() {
		if (isCleared())
			return;	// already cleared
		
		if (mineField.hasMineAt(row, col)) {
			// show mine
			setIcon(ImageLoader.createImageIcon("blue_ball.png"));
			setDisabledIcon(ImageLoader.createImageIcon("red_ball.png"));
		}
		else {
			// show number of surrounding mines
			int surroundingMines = mineField.getSurroundingMineCount(row, col);
			setText(surroundingMines == 0 ? "" : String.valueOf(surroundingMines));
		}
		setEnabled(false);
		
		cleared = true;	// clear
	}
}
