package pt314.just4fun.minesweeper.game;

public class Game {

	public MineField mineField; // TODO: make private

	public Game(int rows, int cols, int mines) {
		mineField = new MineFieldGenerator().generate(rows, cols, mines);
	}
	
	public void flag(int row, int col) {
		// TODO: implement me
	}

	public void clear(int row, int col) {
		MineFieldCell cell = mineField.getCell(row, col);
		if (cell.isCleared())
			return;	// already cleared
		
		cell.clear();
		if (cell.isMined())
			return; // found mine -> game over
		
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
	
	public boolean isOver() {
		return false; // TODO: implement me
	}
	
	public boolean isWin() {
		return false; // TODO: implement me
	}
}
