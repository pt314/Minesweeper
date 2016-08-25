package pt314.just4fun.minesweeper.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import pt314.just4fun.minesweeper.game.Game;
import pt314.just4fun.minesweeper.util.Time;

/**
 * Shows game status info: time, number of mines, etc. 
 */
public class StatusPanel extends JPanel implements Observer {

	private Game game;
	
	private JLabel minesLabel;
	
	private JLabel flagsLabel;
	
	private JLabel timeLabel;

	private Timer timer;

	public StatusPanel(Game game) {

		this.game = game;
		game.addObserver(this);

		setBackground(Color.LIGHT_GRAY);
		setLayout(new BorderLayout());
		
		// Time
		timeLabel = new JLabel("00:00.00", JLabel.CENTER);
		timeLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
		timeLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		timeLabel.setForeground(Color.BLACK);

		// Number of mines
		minesLabel = new JLabel("00", new ImageIcon("./res/img/small/mine-dark-gray.png"), JLabel.CENTER);
		minesLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
		minesLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		minesLabel.setForeground(Color.RED);

		// Number of flags
		flagsLabel = new JLabel("00", new ImageIcon("./res/img/small/flag.png"), JLabel.CENTER);
		flagsLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
		flagsLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		flagsLabel.setForeground(Color.RED);

		add(timeLabel, BorderLayout.CENTER);
		add(minesLabel, BorderLayout.WEST);
		add(flagsLabel, BorderLayout.EAST);
		
		// Timer for updating clock display
		timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				updateTime();
			}
		});
	}


	@Override
	public void update(Observable o, Object arg) {

		// Update number of mines and flags
		updateMinesAndFlags();
		
		// Start/stop timer
		if (game.isStarted() && !timer.isRunning())
			timer.start();
		else if (game.isOver() && timer.isRunning())
			timer.stop();
	}

	private void updateMinesAndFlags() {

		int mines = game.mineField.getMineCount();
		int flags = game.mineField.getFlagCount();
		
		String minesString = String.format("%02d", mines - flags);
		minesLabel.setText(minesString);

		String flagsString = String.format("%02d", flags);
		flagsLabel.setText(flagsString);
	}
	
	private void updateTime() {
		
		long time = game.getTime();
		long millis = Time.timeMillis(time);
		long seconds = Time.timeSeconds(time);
		long minutes = Time.timeMinutes(time);
		
		// Max timer at 59:59.99, right before 1h
		if (minutes > 59) {
			minutes = 59;
			seconds = 59;
			millis = 999;
		}

		String timeString = String.format("%02d:%02d.%02d", minutes, seconds, millis / 10);
		timeLabel.setText(timeString);
	}
}
