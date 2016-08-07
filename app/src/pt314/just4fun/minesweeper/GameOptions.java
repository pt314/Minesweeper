package pt314.just4fun.minesweeper;

public class GameOptions {

	private boolean allowQuestionMarks;
	
	public GameOptions() {
		this.allowQuestionMarks = false;
	}
	
	public boolean isAllowQuestionMarks() {
		return allowQuestionMarks;
	}
	
	public void setAllowQuestionMarks(boolean allow) {
		this.allowQuestionMarks = allow;
	}
}
