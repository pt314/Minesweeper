package pt314.just4fun.minesweeper.game;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Game {

	private boolean isOver;
	
	private boolean isWin;
	
	public MineField mineField; // TODO: make private

	public Game(int rows, int cols, int mines) {
		isOver = false;
		isWin = false;
		mineField = new MineFieldGenerator().generate(rows, cols, mines);
	}
	
	public void flag(int row, int col) {
		// TODO: implement me
	}

	/**
	 * Clears a cell and returns all the cells that get cleared.
	 */
	public Set<MineFieldCell> clear(int row, int col) {
		
		Set<MineFieldCell> cells = new HashSet<>();
		
		MineFieldCell startCell = mineField.getCell(row, col);
		if (startCell == null || startCell.isCleared())
			return cells;	// null or already cleared
		
		// Clear starting cell
		startCell.clear();
		cells.add(startCell);
		if (startCell.isMined()) {
			isOver = true;
			isWin = false;
			return cells;	// found mine -> game over
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
		return clearedCells;
	}
	
	public boolean isOver() {
		return isOver;
	}
	
	public boolean isWin() {
		return isWin;
	}
}
