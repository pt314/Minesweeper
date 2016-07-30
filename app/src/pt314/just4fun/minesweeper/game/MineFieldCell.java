package pt314.just4fun.minesweeper.game;

/**
 * A single cell in a mine field.
 */
public class MineFieldCell {
	
	private int row;
	
	private int col;

	/**
	 * A cell may or may not have a mine on it.
	 */
	private boolean mined;
	
	/**
	 * A cell can only be cleared if it is enabled.
	 */
	private boolean enabled;
	
	/**
	 * After a cell is cleared, it is possible to see if there is a mine on it.
	 */
	private boolean cleared;
	
	public MineFieldCell(int row, int col) {
		this.row = row;
		this.col = col;
		this.mined = false;
		this.enabled = true;
		this.cleared = false;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public boolean isMined() {
		return mined;
	}
	
	public void setMine(boolean mine) {
		this.mined = mine;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isCleared() {
		return cleared;
	}
	
	public void clear() {
		cleared = true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MineFieldCell other = (MineFieldCell) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}
