package pt314.just4fun.minesweeper.game;

/**
 * A single cell in a mine field.
 */
public class MineFieldCell {

	/**
	 * A cell can only be cleared if it is enabled.
	 */
	private boolean enabled;
	
	/**
	 * After a cell is cleared, it is possible to see if there is a mine on it.
	 */
	private boolean cleared;
	
	/**
	 * A cell may or may not have a mine on it.
	 */
	private boolean mine;
	
	public MineFieldCell() {
		this.mine = false;
		this.enabled = true;
		this.cleared = false;
	}
	
	public boolean isMined() {
		return mine;
	}
	
	public void setMine(boolean mine) {
		this.mine = mine;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public void enable() {
		enabled = true;
	}

	public boolean isCleared() {
		return cleared;
	}
	
	public void clear() {
		cleared = true;
	}
}
