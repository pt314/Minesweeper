package pt314.just4fun.minesweeper.game;

/**
 * Rectangular mine field.
 * 
 * Note: this class is not thread safe, which is probably OK for this game.
 */
public class MineField {
	
	private int rows;
	private int cols;
	
	private MineFieldCell[][] mineField;
	
	public MineField(int rows, int cols) {
		// Initialize member variables
		this.rows = rows;
		this.cols = cols;

		// Create mine field array
		mineField = new MineFieldCell[rows][cols];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				mineField[r][c] = new MineFieldCell(r, c);
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}

	public MineFieldCell getCell(int row, int col) {
		if (!withinBounds(row, col))
			return null;
		return mineField[row][col];
	}

	public boolean isMined(int row, int col) {
		return withinBounds(row, col) && mineField[row][col].isMined();
	}

	public boolean isEnabled(int row, int col) {
		return withinBounds(row, col) && mineField[row][col].isEnabled();
	}

	public boolean isFlagged(int row, int col) {
		return withinBounds(row, col) && mineField[row][col].isFlagged();
	}

	public boolean isCleared(int row, int col) {
		return withinBounds(row, col) && mineField[row][col].isCleared();
	}

	/**
	 * Returns the total number of mines on this field.
	 */
	public int getMineCount() {
		int count = 0;
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < cols; col++)
				if (getCell(row, col).isMined())
					count++;
		return count;
	}

	/**
	 * Returns the number of mines on or around a cell.
	 */
	public int getMineCount(int row, int col) {
		int count = 0;
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				if (isMined(row + i, col + j))
					count++;
		return count;
	}

	/**
	 * Returns the total number of flags on this field.
	 */
	public int getFlagCount() {
		int count = 0;
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < cols; col++)
				if (getCell(row, col).isFlagged())
					count++;
		return count;
	}

	/**
	 * Returns the number of flags on or around a cell.
	 */
	public int getFlagCount(int row, int col) {
		int count = 0;
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				if (isFlagged(row + i, col + j))
					count++;
		return count;
	}

	/**
	 * Returns the total number of cells in this field.
	 */
	public int getCellCount() {
		return rows * cols;
	}

	/**
	 * Returns the total number of cleared cells in this field.
	 */
	public int getClearedCellCount() {
		int n = 0;
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < cols; col++)
				if (isCleared(row, col))
					n++;
		return n;
	}

	/**
	 * Returns true if the specified row and column
	 * are within the boundaries of the mine field.
	 */
	private boolean withinBounds(int row, int col) {
		if (row < 0 || row >= rows)
			return false;
		if (col < 0 || col >= cols)
			return false;
		return true;
	}
}
