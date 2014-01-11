package tacticalRPG;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Texture {
	public static Map<String, String> FILES = new HashMap<String, String>();
	public static Map<String, BufferedImage> SpriteSheets = new HashMap<String, BufferedImage>();

	public static void clearLibrary() {
		SpriteSheets = null;
	}
	
	public static void loadSheet(String filename) {
		try {
			BufferedImage sheet = ImageIO.read(Texture.class.getResource(filename));
			SpriteSheets.put(filename, sheet);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}	
}
