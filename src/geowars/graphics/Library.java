package geowars.graphics;

import geowars.graphics.Animation.AnimKey;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class Library {
	private static HashMap<String, HashMap<AnimKey, BufferedImage[]>> SpriteList = new HashMap<String, HashMap<AnimKey, BufferedImage[]>>();
	private static HashMap<String, Animation[]> AnimList = new HashMap<String, Animation[]>();
	
	public static void clearLibrary() {
		SpriteList = null;
	}
	
	public static void loadSheet(String fn, Dimension tileSize, int width, HashMap<Integer, AnimKey> keys) {
		try {
			HashMap<AnimKey, BufferedImage[]> set = new HashMap<AnimKey, BufferedImage[]>();
			
			BufferedImage sheet = ImageIO.read(Library.class.getResource(fn));
			
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
	
	/*
	public static BufferedImage getSheet(String nm) {
		return SpriteSheets.get(nm);
	}
	*/
	
	public static boolean checkSheet(String nm) {
		if (SpriteList.containsValue(nm)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static BufferedImage getFrame(String fn, AnimKey k, int fr) {
		return SpriteList.get(fn).get(k)[fr];
	}
	
	public static BufferedImage[] getFrameList(String fn, AnimKey k) {
		return SpriteList.get(fn).get(k);
	}
}
