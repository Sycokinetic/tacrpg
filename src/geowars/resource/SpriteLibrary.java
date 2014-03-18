package geowars.resource;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class SpriteLibrary {
	private static HashMap<String, HashMap<String, BufferedImage[]>> SpriteList = new HashMap<String, HashMap<String, BufferedImage[]>>();
	
	public static void clearLibrary() {
		SpriteList = null;
	}
	
	public static void loadSheet(String fn, Dimension tileSize, int width, HashMap<Integer, String> keys) {
		try {
			HashMap<String, BufferedImage[]> set = new HashMap<String, BufferedImage[]>();
			
			BufferedImage sheet = ImageIO.read(SpriteLibrary.class.getResource(fn));
			
			for (int i = 0; i < keys.size(); i++) {
				BufferedImage[] row = new BufferedImage[width];
				
				for (int j = 0; j < width; j++) {
					BufferedImage img = sheet.getSubimage(j*tileSize.width, i*tileSize.height, tileSize.width, tileSize.height);
					row[j] = img;
				}
				
				set.put(keys.get(i), row);
			}
			
			SpriteList.put(fn, set);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkSheet(String nm) {
		if (SpriteList.containsValue(nm)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static BufferedImage getFrame(String fn, String k, int fr) {
		return SpriteList.get(fn).get(k)[fr];
	}
	
	public static BufferedImage[] getFrameList(String fn, String k) {
		return SpriteList.get(fn).get(k);
	}
}
