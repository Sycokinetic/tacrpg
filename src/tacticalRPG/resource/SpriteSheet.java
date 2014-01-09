package tacticalRPG.resource;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.MessageFormat;

import javax.imageio.ImageIO;

/**
 * Class to define a sprite sheet
 * @author alex
 *
 */
public class SpriteSheet {
	private BufferedImage sheet;
	
	/**
	 * Constructor for class SpriteSheet
	 * @param filename  the name of the image file to be loaded 
	 * @param w  the width of a tile in the file
	 * @param h  the height of a tile in the file
	 */
	public SpriteSheet(String filename) {
		filename = MessageFormat.format(filename, tacticalRPG.resource.Texture.sep);
		
		try {
			sheet = ImageIO.read(Texture.class.getResource(filename));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage() {
		return sheet;
	}
}