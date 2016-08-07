package pt314.just4fun.minesweeper;

/**
 * A single place to keep game options.
 */
public class GameOptions {

	private boolean allowQuestionMarks;
	private boolean allowRemovingMines;
	
	public GameOptions() {
		this.allowQuestionMarks = true;
		this.allowRemovingMines = false;
	}
	
	public boolean isAllowQuestionMarks() {
		return allowQuestionMarks;
	}
	
	public void setAllowQuestionMarks(boolean allow) {
		this.allowQuestionMarks = allow;
	}
	
	public boolean isAllowRemovingMines() {
		return allowRemovingMines;
	}
	
	public void setAllowRemovingMines(boolean allow) {
		this.allowRemovingMines = allow;
	}
}
