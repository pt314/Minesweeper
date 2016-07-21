package pt314.just4fun.minesweeper.game;

import java.util.Random;

/**
 * Rectangular mine field.
 * 
 * Note: this class is not thread safe, which is probably OK for this game.
 */
public class MineField {
	
	private int numRows;
	private int numCols;
	private int numMines; // TODO: remove?
	
	private MineFieldCell[][] mineField;
	
	public MineField(int numRows, int numCols) {
		// Initialize member variables
		this.numRows = numRows;
		this.numCols = numCols;
		this.numMines = 0;

		// Create mine field array
		mineField = new MineFieldCell[numRows][numCols];
		for (int r = 0; r < numRows; r++)
			for (int c = 0; c < numCols; c++)
				mineField[r][c] = new MineFieldCell(r, c);
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumCols() {
		return numCols;
	}

	public int getNumMines() {
		return numMines;
	}
	
	public MineFieldCell getCell(int row, int col) {
		if (!withinBounds(row, col))
			return null;
		return mineField[row][col];
	}

	/**
	 * Returns the number of mines adjacent to the specified location.
	 */
	public int getSurroundingMineCount(int row, int col) {
		int count = 0;
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				if (i != 0 || j != 0) {
					int neighborRow = row + i;
					int neighborCol = col + j;
					if (hasMineAt(neighborRow, neighborCol))
						count++;
				}
		return count;
	}

	/**
	 * Returns true if there is a mine at the specified location.
	 * If the location is out of bounds, it always returns false.
	 */
	public boolean hasMineAt(int row, int col) {
		return withinBounds(row, col) && mineField[row][col].isMined();
	}

	public void setMineAt(int row, int col, boolean mine) {
		if (!withinBounds(row, col))
			return;
		if (!hasMineAt(row, col)) {
			mineField[row][col].setMine(mine);
			numMines++; // update number of mines to reflect real number
		}
	}

	/**
	 * Returns true if the specified row and column
	 * are within the boundaries of the mine field.
	 */
	public boolean withinBounds(int row, int col) {
		if (row < 0 || row >= numRows)
			return false;
		if (col < 0 || col >= numCols)
			return false;
		return true;
	}
}
