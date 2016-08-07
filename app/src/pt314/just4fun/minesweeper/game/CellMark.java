package pt314.just4fun.minesweeper.game;

/**
 * These are the different marks that a player can
 * put on a cell.
 */
public enum CellMark {

	/**
	 * No mark.
	 */
	NONE,

	/**
	 * Flags may be used for cells where the player
	 * believes there is a mine.
	 */
	FLAG,
	
	/**
	 * Question marks may be used for cells where the
	 * player believes there could be a mine, but is
	 * not sure if there is.
	 */
	QUESTION_MARK	

}