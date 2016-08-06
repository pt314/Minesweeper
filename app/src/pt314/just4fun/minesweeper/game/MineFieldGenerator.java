package pt314.just4fun.minesweeper.game;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Generates mine fields with mines placed at random locations.
 * 
 * Note: Floyd's sampling algorithm is used to select cells.
 * Adding the cells to a list, shuffling them, and then selecting
 * from the left is simpler, but slower (although speed should not
 * be an issue here with the size of the fields used)
 */
public class MineFieldGenerator {

	private Random random;
	
	public MineFieldGenerator(long seed) {
		random = new Random(seed);
	}

	public MineFieldGenerator() {
		random = new Random();
	}

	/**
	 * Generates a mine field.
	 * 
	 * @param rows the number of rows in the field
	 * @param cols the number of columns in the field
	 * @param mines the number of mines to place in the field
	 * 
	 * @return a mine field with {@code mines} mines
	 */
	public MineField generate(int rows, int cols, int mines) {
		MineField field = new MineField(rows, cols);
		generate(field, mines); // add random mines
		return field;
	}

	// Add random mines to an existing field
	public void generate(MineField field, int mines) {
		// TODO: check for valid input
		Set<MineFieldCell> cells = sample(field, mines);
		for (MineFieldCell cell : cells)
			cell.setMined(true);
	}

	// Add random mines to an existing field
	public void generate(MineField field, int mines, int row, int col) {
		// TODO: check for valid input
		Set<MineFieldCell> cells = sample(field, mines, row, col);
		for (MineFieldCell cell : cells)
			cell.setMined(true);
	}

	/**
	 * Selects a random set of cells from a mine field.
	 * 
	 * @param field the mine field
	 * @param numberOfCells the number of cells to select
	 * 
	 * @return {@code numberOfCells} random cells from {@code field}.
	 */
	private Set<MineFieldCell> sample(MineField field, int numberOfCells) {
		Set<MineFieldCell> cells = new HashSet<>();
		
		int rows = field.getRows();
		int cols = field.getCols();
		int totalNumberOfCells = rows * cols;
		
		// Sample cell indices (from 0 to totalNumberOfCells)
		// Then get the actual cells for the chosen indices
		Set<Integer> indices = sample(totalNumberOfCells, numberOfCells);
		for (Integer index : indices)
			cells.add(getCellFromIndex(field, index));
		
		return cells;
	}
	
	/**
	 * Selects a random set of cells from a mine field, ignoring
	 * the starting cell at {@code (startRow, startCol)}.
	 * 
	 * @see MineFieldGenerator#sample(MineField, int)
	 */
	private Set<MineFieldCell> sample(MineField field, int numberOfCells, int startRow, int startCol) {
		Set<MineFieldCell> cells = new HashSet<>();
		
		int rows = field.getRows();
		int cols = field.getCols();
		int totalNumberOfCells = rows * cols - 1; // ignore starting cell
		
		// Sample cell indices (from 0 to totalNumberOfCells)
		// Then get the actual cells for the chosen indices
		Set<Integer> indices = sample(totalNumberOfCells, numberOfCells);
		int startCellIndex = getCellIndex(field, startRow, startCol);
		for (Integer index : indices) {
			if (index >= startCellIndex)
				index++; // ignore the starting cell
			cells.add(getCellFromIndex(field, index));
		}
		
		return cells;
	}

	/**
	 * Generate a random sample from the numbers in [0, n-1].
	 * 
	 * Uses Floyd's algorithm, described in Programming Pearls:
	 * http://dl.acm.org/citation.cfm?id=315746
	 * 
	 * @param n the number of elements
	 * @param m the sample size
	 * 
	 * @return {@code m} random numbers between 0 and {@code n-1}
	 */
	private Set<Integer> sample(int n, int m) {
		// Expected running time is O(m)
		Set<Integer> sample = new HashSet<>();
		for (int j = n - m + 1; j <= n; j++) {
			int t = 1 + random.nextInt(j);
			int x = !sample.contains(t - 1) ? t - 1 : j - 1;
			sample.add(x);
		}
		return sample;
	}

	/**
	 * Gets the index of a cell, given its row and column.
	 * 
	 * The index of a cell is the index it would have in a 1D array
	 * where each row in the 2D field is placed to the right of the
	 * row above it. This is a number between 0 and (rows * cols - 1).
	 * 
	 * @param field the mine field
	 * @param row the cell row
	 * @param col the cell column
	 * 
	 * @return the index of the cell at {@code (row, col)}
	 */
	private int getCellIndex(MineField field, int row, int col) {
		return row * field.getCols() + col;
	}

	/**
	 * Gets a cell, given its index.
	 * 
	 * @see MineFieldGenerator#getCellIndex(MineField, int, int)
	 * @param field the mine field
	 * @param index the cell index
	 * 
	 * @return the cell with the given {@code index}
	 */
	private MineFieldCell getCellFromIndex(MineField field, int index) {
		// Determine row and column from index
		int cellRow = index / field.getCols();
		int cellCol = index % field.getCols();
		return field.getCell(cellRow, cellCol);
	}
}
