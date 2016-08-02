package pt314.just4fun.minesweeper.util;

/**
 * Simple stop watch.
 * 
 * This stop watch can only be started and stopped once.
 */
public class StopWatch {

	private boolean started;
	private boolean stopped;

	private long startTime;
	private long stopTime;
	
	public StopWatch() {
		started = false;
		stopped = false;
		stopTime = startTime;
	}
	
	public synchronized void start() {
		if (started)
			throw new IllegalStateException("Stopwatch already started");
		startTime = System.nanoTime();
		started = true;
	}
	
	public synchronized void stop() {
		if (!started)
			throw new IllegalStateException("Stopwatch not yet started");
		if (stopped)
			throw new IllegalStateException("Stopwatch already stopped");
		stopTime = System.nanoTime();
		stopped = true;
	}
	
	/**
	 * Returns the elapsed time in milliseconds.
	 */
	public synchronized long getTime() {
		if (!started)
			return 0;
		long endTime = stopped ? stopTime : System.nanoTime();
		return (endTime - startTime) / 1000000;
	}
}
