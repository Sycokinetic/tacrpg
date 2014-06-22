/*
 * Classname: Constant
 * Author: Sycokinetic
 * 
 * Notes:
 * - Contains application-wide constant values, namely enumerated types for 
 */

package tacRPG;

public class Constant {
	public static final String NULL = "NULL ";
	public static final String ALIVE = "ALIVE ";
	public static final String ATTACK = "ATTACK ";
	public static final String BLOCK = "BLOCK ";
	public static final String DEAD = "DEAD ";
	public static final String DOWN = "DOWN ";
	public static final String EXIT = "EXIT ";
	public static final String GAME = "GAME ";
	public static final String HIDDEN = "HIDDEN ";
	public static final String LEFT = "LEFT ";
	public static final String MAIN = "MAIN ";
	public static final String MOVE = "MOVE ";
	public static final String PAUSE = "PAUSE ";
	public static final String PLAY = "PLAY ";
	public static final String RIGHT = "RIGHT ";
	public static final String TURN = "TURN ";
	public static final String UP = "UP ";
	
//	// Keys for communication of actions and events
//	public static enum EventKey {
//		MAIN,
//		MOVE,
//		TURN,
//		ATTACK,
//		EXIT,
//		PAUSE
//	}
//	
//	// Modifier keys for communication of actions and events
//	public static enum ModKey {
//		NULL,
//		GAME,
//		DOWN,
//		LEFT,
//		RIGHT,
//		UP
//	}
//	
//	// Keys for communication of Person status.
//	public static enum LifeStatus {
//		ALIVE,		// entities that will be processed as usual
//		DEAD,		// entities that are ready for destruction
//		HIDDEN		// entities that will neither be processed nor destroyed
//	}
//	
//	
//	/**
//	 * Converts an EventKey into a string
//	 * @param e		EventKey to be converted
//	 * @return		a string representing e
//	 */
//	public static String concatKeys(EventKey e) {
//		return e.toString();
//	}
//	
//	/**
//	 * Concatenates an EventKey and a ModKey into a string
//	 * @param e		EventKey to be converted
//	 * @param m		ModKey to be converted
//	 * @return		an underscore-delineated string representing e and m
//	 * 				- The first word is e. The second is m.
//	 */
//	public static String concatKeys(EventKey e, ModKey m) {
//		return e.toString() + "_" + m.toString();
//	}
//	
//	/**
//	 * Concatenates an EventKey and a list of ModKeys into a string
//	 * @param e		EventKey to be converted
//	 * @param m		ArrayList of ModKeys to be converted
//	 * @return		an underscore-delineated string representing e and m
//	 * 				- The first word is e. The rest are elements of m.
//	 */
//	public static String concatKeys(EventKey e, ArrayList<String> m) {
//		String concat = e.toString();
//		
//		for (Object i: m) {
//			concat += "_" + i.toString();
//		}
//		
//		concat = concat.substring(0, concat.length());
//		
//		return concat;
//	}
//	
//	/**
//	 * Separates a string produced by concatKeys() into its component keys
//	 * @param s		The underscore-delineated string to be split
//	 * 				- The first word should be an EventKey. The rest should
//	 * 				  be ModKeys.
//	 * @return		An ArrayList of strings representing the keys s is composed of
//	 */
//	public static ArrayList<String> splitKeyString(String s) {
//		ArrayList<String> keys = new ArrayList<String>(Arrays.asList(s.split("_")));
//		
//		return keys;
//	}
}
