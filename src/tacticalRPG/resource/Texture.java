package tacticalRPG.resource;

import java.util.HashMap;
import java.util.Map;

import tacticalRPG.resource.SpriteSheet;

public class Texture {
	public static final String sep = System.getProperty("file.separator");

	public static Map<String, String> FILES = new HashMap<String, String>();
	public static Map<String, SpriteSheet> SpriteSheets = new HashMap<String, SpriteSheet>();

	public static void clearLibrary() {
		SpriteSheets = null;
	}
	
	public static void loadSheet(String filename) {
		SpriteSheets.put(filename, new SpriteSheet(filename));
	}
}
