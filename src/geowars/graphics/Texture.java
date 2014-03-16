package geowars.graphics;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Texture {
	//private static Map<String, BufferedImage> SpriteSheets = new HashMap<String, BufferedImage>();
	private static Map<String, BufferedImage[]> SpriteList = new HashMap<String, BufferedImage[]>();
	
	public static void clearLibrary() {
		//SpriteSheets = null;
		SpriteList = null;
	}
	
	public static void loadSheet(String fn, int fc, Dimension d) {
		try {
			BufferedImage sheet = ImageIO.read(Texture.class.getResource(fn));
			//SpriteSheets.put(filename, sheet);
			
			BufferedImage[] sprites = new BufferedImage[fc];
			for (int i = 0; i < fc; i++) {
				BufferedImage s = sheet.getSubimage(i*d.width, 0, d.width, d.height);
				sprites[i] = s;
			}
			
			SpriteList.put(fn, sprites);
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
	
	public static BufferedImage getFrame(String nm, int fr) {
		return SpriteList.get(nm)[fr];
	}
	
	public static BufferedImage[] getFrameSet(String nm) {
		return SpriteList.get(nm);
	}
}
