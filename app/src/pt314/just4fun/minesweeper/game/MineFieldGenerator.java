package pt314.just4fun.minesweeper.game;

import java.util.Random;

public class MineFieldGenerator {

	public MineFieldGenerator() {}

	public MineField generate(int rows, int cols, int mines) {
		MineField mineField = new MineField(rows, cols);
		addRandomMines(mineField, mines);
		return mineField;
	}

	/**
	 * Add mines at random locations.
	 * 
	 * TODO: prevent selecting the same location twice.
	 */
	private void addRandomMines(MineField field, int numberOfMines) {
		System.out.println("Desired number of mines: " + numberOfMines);
		
		Random rand = new Random();
		
		// select locations with repetition, so the number may be less than specified
		// TODO: update to select exactly numberOfMines mines
		for (int i = 0; i < numberOfMines; i++) {
			int row = rand.nextInt(field.getNumRows());
			int col = rand.nextInt(field.getNumCols());
			field.setMineAt(row, col, true);
		}

		System.out.println("Real number of mines: " + field.getNumMines());
	}
}
