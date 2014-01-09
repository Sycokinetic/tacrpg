package tacticalRPG.resource;

import java.awt.image.BufferedImage;

public class Entity {
	private static String spriteSheetFile = "{0}res{0}animation.png";
	private static int tileWidth = 100;
	private static int tileHeight = 100;
	
	public int posX = 0;
	public int posY = 0;
	
	public SpriteSheet spriteSheet;
	public BufferedImage sprite;
	
	public Entity() {
		getSheet();
		this.sprite = spriteSheet.getImage().getSubimage(0, 0, tileWidth, tileHeight);
	}
	
	public void getSheet() {
		spriteSheet = Texture.SpriteSheets.get(spriteSheetFile);
		if (spriteSheet == null) {
			Texture.loadSheet(spriteSheetFile);
			spriteSheet = Texture.SpriteSheets.get(spriteSheetFile);
		}
	}
}
