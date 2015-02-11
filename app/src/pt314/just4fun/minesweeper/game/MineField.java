package pt314.just4fun.minesweeper.game;

import java.util.Random;

/**
 * Rectangular mine field.
 */
public class MineField {
	
	private int numRows;
	private int numCols;
	private int numMines;
	
	private boolean[][] mineField;
	
	private int[][] mineCounts;

	public MineField(int numRows, int numCols, int numMines) {
		// Initialize member variables
		this.numRows = numRows;
		this.numCols = numCols;
		this.numMines = numMines;

		// Create mine field array
		mineField = new boolean[numRows][numCols];
		
		addRandomMines(numMines);
		
		// Pre-compute mine counts
		mineCounts = new int[numRows][numCols];
		for (int r = 0; r < numRows; r++)
			for (int c = 0; c < numCols; c++)
				mineCounts[r][c] = getSurroundingMineCount(r, c);
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

	/**
	 * Add mines at random locations.
	 * 
	 * TODO: prevent selecting the same location twice.
	 */
	private void addRandomMines(int numberOfMines) {
		System.out.println("Desired number of mines: " + numberOfMines);
		
		Random rand = new Random();
		for (int i = 0; i < numberOfMines; i++) {
			int r = rand.nextInt(numRows);
			int c = rand.nextInt(numCols);
			mineField[r][c] = true;
		}
		// update number of mines to reflect real number
		numMines = 0;
		for (int row = 0; row < numRows; row++)
			for (int col = 0; col < numCols; col++)
				if (mineField[row][col])
					numMines++;

		System.out.println("Real number of mines: " + numMines);
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
		return withinBounds(row, col) && mineField[row][col];
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
