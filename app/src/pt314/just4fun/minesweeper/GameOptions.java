package pt314.just4fun.minesweeper;

/**
 * A single place to keep game options.
 */
public class GameOptions {

	public static final int DIFFICULTY_EASY = 1;
	public static final int DIFFICULTY_MEDIUM = 2;
	public static final int DIFFICULTY_HARD = 3;

	// The difficulty determines the field size and the number of mines
	private int difficulty;
	private int numberOfRows;
	private int numberOfColumns;
	private int numberOfMines;
	
	// Special option flags
	private boolean allowQuestionMarks;
	private boolean allowRemovingMines;
	private boolean showHiddenMines;
	
	public GameOptions() {
		// Set default configuration
		setDifficulty(DIFFICULTY_EASY);
		setAllowQuestionMarks(true);
		setAllowRemovingMines(false);
		setShowHiddenMines(false);
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(int difficulty) {
		if (difficulty == DIFFICULTY_EASY) {
			numberOfRows = 8;
			numberOfColumns = 8;
			numberOfMines = 10;
		}
		else if (difficulty == DIFFICULTY_MEDIUM) {
			numberOfRows = 16;
			numberOfColumns = 16;
			numberOfMines = 40;
		}
		else if (difficulty == DIFFICULTY_HARD) {
			numberOfRows = 24;
			numberOfColumns = 24;
			numberOfMines = 99;
		}
		else
			throw new IllegalArgumentException("Invalid difficulty: " + difficulty);
	}
	
	public int getNumberOfRows() {
		return numberOfRows;
	}
	
	public int getNumberOfColumns() {
		return numberOfColumns;
	}
	
	public int getNumberOfMines() {
		return numberOfMines;
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
