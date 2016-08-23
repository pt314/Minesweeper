package pt314.just4fun.minesweeper.game;

import java.util.Observable;

/**
 * A single cell in a mine field.
 */
public class MineFieldCell extends Observable {
	
	private int row;
	
	private int col;
	
	/**
	 * A cell may or may not have a mine on it.
	 */
	private boolean mined;
	
	/**
	 * A cell with a with a mine can blow up when it is cleared.
	 */
	private boolean blown;

	/**
	 * A cell can only be cleared if it is enabled.
	 */
	private boolean enabled;
	
	/**
	 * After a cell is cleared, it is possible to see if there is a mine on it.
	 */
	private boolean cleared;
	
	/**
	 * A mark can be added to a cell that has not been cleared.
	 */
	private CellMark mark;
	
	public MineFieldCell(int row, int col) {
		this.row = row;
		this.col = col;
		this.mined = false;
		this.blown = false;
		this.enabled = true;
		this.cleared = false;
		this.mark = CellMark.NONE;
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
	
	public void setMined(boolean mine) {
		this.mined = mine;
		setChanged();
		notifyObservers();
	}
	
	public boolean isBlown() {
		return blown;
	}

	public void setBlown(boolean blown) {
		this.blown = blown;
		setChanged();
		notifyObservers();
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		setChanged();
		notifyObservers();
	}

	public CellMark getMark() {
		return mark;
	}
	
	public void setMark(CellMark mark) {
		if (mark == null)
			throw new IllegalArgumentException("mark cannot be null");
		this.mark = mark;
		setChanged();
		notifyObservers();
	}

	public boolean isMarked() {
		return mark != CellMark.NONE;
	}

	public boolean isFlagged() {
		return mark == CellMark.FLAG;
	}
	
	public boolean isQuestionMarked() {
		return mark == CellMark.QUESTION_MARK;
	}
	
	public boolean isCleared() {
		return cleared;
	}
	
	public void clear() {
		cleared = true;
		enabled = false;
		mark = CellMark.NONE;
		setChanged();
		notifyObservers();
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
