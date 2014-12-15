package pt314.just4fun.minesweeper;

import javax.swing.JFrame;

public class Minesweeper extends JFrame {

	public Minesweeper() {
		super("Just for fun Minesweeper game!");
		
		setSize(600, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new Minesweeper();
	}
}
