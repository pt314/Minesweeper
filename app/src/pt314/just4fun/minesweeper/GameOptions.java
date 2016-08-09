package pt314.just4fun.minesweeper;

/**
 * A single place to keep game options.
 */
public class GameOptions {

	private boolean allowQuestionMarks;
	private boolean allowRemovingMines;
	private boolean showHiddenMines;
	
	public GameOptions() {
		this.allowQuestionMarks = true;
		this.allowRemovingMines = false;
		this.showHiddenMines = true;
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
	
	public boolean isShowHiddenMines() {
		return showHiddenMines;
	}
	
	public void setShowHiddenMines(boolean show) {
		this.showHiddenMines = show;
	}
}
