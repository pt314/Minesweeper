package pt314.just4fun.minesweeper.game;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import pt314.just4fun.minesweeper.util.StopWatch;

public class Game {

	// Used to add mines
	private final MineFieldGenerator fieldGenerator = new MineFieldGenerator();
	
	private GameState gameState;

	private StopWatch stopWatch;
	
	public MineField mineField; // TODO: make private
	
	private int numberOfMines;

	public Game(int rows, int cols, int mines) {
		gameState = GameState.NEW;
		stopWatch = new StopWatch();
		mineField = new MineField(rows, cols); // start with an empty field
		numberOfMines = mines; // save the number of mines for later
	}
	/**
	 * Toggle between possible marks on a cell.
	 * 
	 * @see CellMark
	 */
	public void toggleMark(int row, int col) {
		
		MineFieldCell cell = mineField.getCell(row, col);
		if (cell == null || cell.isCleared())
			return;
		
		CellMark mark = cell.getMark();
		switch (mark) {
			case NONE:
				mark = CellMark.FLAG;
				break;
			case FLAG:
				mark = CellMark.QUESTION_MARK;
				break;
			case QUESTION_MARK:
			default:
				mark = CellMark.NONE;
				break;
		}
		cell.setMark(mark);
	}

	/**
	 * Clears a cell and returns all the cells that get cleared
	 * as a result of clearing the starting cell.
	 */
	public synchronized Set<MineFieldCell> clear(int row, int col) {
		
		if (gameState == GameState.NEW) {
			// Add random mines, ignoring the first cell cleared
			fieldGenerator.generate(mineField, numberOfMines, row, col);
			startGame();
		}
		
		Set<MineFieldCell> cells = new HashSet<>();
		
		MineFieldCell startCell = mineField.getCell(row, col);
		if (startCell == null)
			return cells;
		
		// Clear starting cell
		startCell.clear();
		cells.add(startCell);
		if (startCell.isMined()) {
			endGame(false); // stepped on a mine -> game over
			return cells;
		}
		
		// Clear other cells
		Set<MineFieldCell> clearedCells = new HashSet<>();
		Queue<MineFieldCell> queue = new LinkedList<>();
		queue.add(startCell);
		startCell.clear();
		clearedCells.add(startCell);
		while (!queue.isEmpty()) {
			MineFieldCell cell = queue.poll();
			int r = cell.getRow();
			int c = cell.getCol();
			if (mineField.getMineCount(r, c) == 0) {
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						MineFieldCell cell_ = mineField.getCell(r + i, c + j);
						if (cell_ != null && !cell_.isCleared() && !cell_.isMined())
							if (!clearedCells.contains(cell_)) {
								queue.add(cell_);
								cell_.clear();
								clearedCells.add(cell_);
							}
					}
				}
			}
		}
		if (isGameOver())
			endGame(true); // all cells cleared
		return clearedCells;
	}

	/**
	 * Clears all the cells surrounding an empty cell and returns
	 * all the cells that get cleared as a result of clearing the
	 * starting cell.
	 * 
	 * Surrounding cells are only cleared if the starting cell has
	 * been previously cleared and the number of surrounding flags
	 * is equal to the number of surrounding mines.
	 */
	// TODO: make it safe -> only clear cells if number of flags = number of mines
	public synchronized Set<MineFieldCell> clearSurrounding(int row, int col) {
		
		Set<MineFieldCell> cells = new HashSet<>();

		// Ignore uncleared cells
		MineFieldCell startCell = mineField.getCell(row, col);
		if (startCell == null || !startCell.isCleared())
			return cells;
		
		// Ignore if number of flags is incorrect
		int mineCount = mineField.getMineCount(row, col);
		int flagCount = mineField.getFlagCount(row, col);
		if (mineCount != flagCount)
			return cells;
		
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				MineFieldCell cell = mineField.getCell(row + i, col + j);
				if (cell != null && !cell.isFlagged()) {
					Set<MineFieldCell> clearedCells = clear(cell.getRow(), cell.getCol());
					cells.addAll(clearedCells);
				}
			}
		}
		return cells;
	}

	/**
	 * Removed a mine from a cell, clears it and its surrounding cells,
	 * and returns all the cells that get cleared as a result.
	 */
	public synchronized Set<MineFieldCell> removeMine(int row, int col) {

		Set<MineFieldCell> cells = new HashSet<>();

		// Ignore if cell is not mined
		MineFieldCell startCell = mineField.getCell(row, col);
		if (!startCell.isMined())
			return cells;

		// Remove mine
		startCell.setMined(false);

		// Clear surrounding cells
		for (int i = -1; i <= 1; i++) {
			for (int j = -1; j <= 1; j++) {
				MineFieldCell cell = mineField.getCell(row + i, col + j);
				if (cell != null && !cell.isFlagged()) {
					Set<MineFieldCell> clearedCells = clear(cell.getRow(), cell.getCol());
					cells.addAll(clearedCells);
				}
			}
		}
		return cells;
	}

	private boolean isGameOver() {
		int numberOfCells = mineField.getRows() * mineField.getCols();
		return numberOfCells - mineField.getMineCount() == mineField.getClearedCellCount();
	}

	public synchronized void startGame() {
		stopWatch.start();
		gameState = GameState.STARTED;
	}

	public synchronized void endGame(boolean win) {
		if (!isOver()) {
			stopWatch.stop();
			gameState = win ? GameState.WIN : GameState.LOSE;
		}
	}

	public synchronized boolean isOver() {
		return gameState == GameState.WIN || gameState == GameState.LOSE;
	}
	
	public synchronized boolean isWin() {
		return gameState == GameState.WIN;
	}
	
	public long getTime() {
		return stopWatch.getTime();
	}
}
