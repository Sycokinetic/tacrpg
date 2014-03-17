package geowars;

import java.util.ArrayList;
import java.util.Arrays;

public class Constant {
	public static enum EventKey {
		MAIN,
		MOVE,
		JUMP,
		ATTACK,
		EXIT
	}
	
	public static enum ModKey {
		NULL,
		DOWN,
		LEFT,
		RIGHT,
		UP
	}
	
	public static String concatKeys(EventKey e, ModKey m) {
		return e.toString() + "_" + m.toString();
	}
	
	public static String concatKeys(EventKey e, ArrayList<String> m) {
		String concat = e.toString();
		
		for (Object i: m) {
			concat += "_" + i.toString();
		}
		
		concat = concat.substring(0, concat.length());
		
		return concat;
	}
	
	public static ArrayList<String> splitKeyString(String s) {
		ArrayList<String> keys = new ArrayList<String>(Arrays.asList(s.split("_")));
		
		return keys;
	}
}
