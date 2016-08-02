package pt314.just4fun.minesweeper.util;

/**
 * Utility class with basic time operations.
 */
public class Time {

	/**
	 * Returns the number hours that are not part of a whole day,
	 * given a time in milliseconds.
	 * 
	 * @param time time in milliseconds
	 * 
	 * @return number of hours that are not part of a whole day
	 */
	public static int timeHours(long time) {
		return (int) ((time / 1000 / 60 / 60) % 24);
	}

	/**
	 * Returns the number minutes that are not part of a whole hour,
	 * given a time in milliseconds.
	 * 
	 * @param time time in milliseconds
	 * 
	 * @return number of minutes that are not part of a whole hour
	 */
	public static int timeMinutes(long time) {
		return (int) ((time / 1000 / 60) % 60);
	}

	/**
	 * Returns the number seconds that are not part of a whole minute,
	 * given a time in milliseconds.
	 * 
	 * @param time time in milliseconds
	 * 
	 * @return number of seconds that are not part of a whole minute
	 */
	public static int timeSeconds(long time) {
		return (int) ((time / 1000) % 60);
	}

	/**
	 * Returns the number milliseconds that are not part of a whole second,
	 * given a time in milliseconds.
	 * 
	 * @param time time in milliseconds
	 * 
	 * @return number of milliseconds that are not part of a whole second
	 */
	public static int timeMillis(long time) {
		return (int) (time % 1000);
	}
}
