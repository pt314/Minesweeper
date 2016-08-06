package pt314.just4fun.minesweeper.gui;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
		updateUI();
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
		cleared = true;
		setEnabled(false);
	}
	
	// TODO: this is too expensive!!! creating icons every time...
	public void updateUI() {
		super.updateUI();
		
		if (mineField == null)
			return;
		
		boolean mined = mineField.isMined(row, col);
		boolean enabled = mineField.isEnabled(row, col);
		boolean flagged = mineField.isFlagged(row, col);
		boolean cleared = mineField.isCleared(row, col);

		// enabled
//		setEnabled(enabled);
		
		// border
		if (!cleared) {
			setBackground(enabled ? Color.LIGHT_GRAY : Color.GRAY);
			setBorder(BorderFactory.createRaisedBevelBorder());
		}
		else {
			setBackground(Color.WHITE);
			setBorder(BorderFactory.createLineBorder(Color.GRAY));
		}
		
		// flags, mines, and mine counts
		String imgFolder = "./res/img/";
		int size = Math.min(getWidth(), getHeight());
		imgFolder += size < 64 ? "small/" : "normal/";
		ImageIcon icon = null;
		if (!cleared) {
			if (flagged) {
				String imgFile = imgFolder + "flag.png";
				icon = new ImageIcon(imgFile);
			}
			else if (mined) {
				icon = ImageLoader.createImageIcon("blue_ball.png");	
			}
		}
		else {
			if (mined)
				icon = ImageLoader.createImageIcon("red_ball.png");
			else {
				// show number of surrounding mines
				int mineCount = mineField.getMineCount(row, col);
				if (mineCount > 0) {
					String imgFile = imgFolder + mineCount + ".png";
					icon = new ImageIcon(imgFile);
				}
			}
		}
		setIcon(icon);
		setDisabledIcon(icon); // disabled icon only shown if icon is not null
	}
}
