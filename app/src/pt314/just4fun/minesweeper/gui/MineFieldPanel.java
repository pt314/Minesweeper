package pt314.just4fun.minesweeper.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MineFieldPanel extends JPanel {

	private static final int CELL_SIZE = 32;

	public MineFieldPanel() {
		int numRows = 10;
		int numCols = 15;
		
		GridLayout layout = new GridLayout(numRows, numCols);
		setLayout(layout);
		
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				JButton button = new JButton();
				button.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
				add(button);
			}
		}
	}
}
