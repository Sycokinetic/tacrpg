package geowars.graphics;

import geowars.entity.Entity.AnimKey;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class Texture {
	private static HashMap<String, HashMap<AnimKey, BufferedImage[]>> SpriteList = new HashMap<String, HashMap<AnimKey, BufferedImage[]>>();
	
	public static void clearLibrary() {
		SpriteList = null;
	}
	
	public static void loadSheet(String fn, Dimension tileSize, int width, AnimKey[] keys) {
		try {
			HashMap<AnimKey, BufferedImage[]> set = new HashMap<AnimKey, BufferedImage[]>();
			
			BufferedImage sheet = ImageIO.read(Texture.class.getResource(fn));
			
			for (int i = 0; i < keys.length; i++) {
				BufferedImage[] row = new BufferedImage[width];
				
				for (int j = 0; j < width; j++) {
					System.out.println(keys[i]);
					BufferedImage img = sheet.getSubimage(j*tileSize.width, i*tileSize.height, tileSize.width, tileSize.height);
					row[j] = img;
				}
				
				set.put(keys[i], row);
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
	
	public static BufferedImage getFrame(String nm, AnimKey k, int fr) {
		return SpriteList.get(nm).get(k)[fr];
	}
}
