package geowars.entity;

import geowars.graphics.Texture;

import java.awt.Dimension;
import java.awt.Point;

public class WorldObject extends Entity {
	public WorldObject() {
		this.SHEETFILE = "/res/animation.png";
		this.TILESIZE = new Dimension(100, 100);
		this.FRAMECOUNT = 10;
		
		if (Texture.getFrameSet(SHEETFILE) == null) {
			Texture.loadSheet(this.SHEETFILE, this.FRAMECOUNT, this.TILESIZE);
		}
		
		this.update();
	}
	
	public WorldObject(int[] p) {
		super(p);
		
		this.SHEETFILE = "/res/animation.png";
		this.TILESIZE = new Dimension(100, 100);
		this.FRAMECOUNT = 10;
		
		if (Texture.getFrameSet(SHEETFILE) == null) {
			Texture.loadSheet(this.SHEETFILE, this.FRAMECOUNT, this.TILESIZE);
		}
	}
	
	public WorldObject(Point p) {
		super(p);
		
		this.SHEETFILE = "/res/animation.png";
		this.TILESIZE = new Dimension(100, 100);
		this.FRAMECOUNT = 10;
		
		if (Texture.getFrameSet(SHEETFILE) == null) {
			Texture.loadSheet(this.SHEETFILE, this.FRAMECOUNT, this.TILESIZE);
		}
	}
	
	public void update() {
		if (this.frame < this.FRAMECOUNT - 1) {
			this.frame++;
		}
		else {
			this.frame = 0;
		}
		
		if (this.pos.x < 100) {
			this.pos.x += 10;
		}
		else {
			this.pos.x = 0;
		}
	}
}
