package pt314.just4fun.minesweeper;

import javax.swing.JFrame;

import pt314.just4fun.minesweeper.gui.MineFieldPanel;

public class Minesweeper extends JFrame {

	public Minesweeper() {
		super("Just for fun Minesweeper game!");
		
		add(new MineFieldPanel());
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new Minesweeper();
	}
}
