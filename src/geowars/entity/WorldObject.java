package geowars.entity;

import geowars.graphics.Texture;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;

public class WorldObject extends Entity {
	public WorldObject() {
		this.SHEETFILE = "/res/animation.png";
		this.TILESIZE = new Dimension(100, 100);
		this.SHEETWIDTH = 10;
		this.SHEETKEYS = new AnimKey[1];
		this.SHEETKEYS[0] = AnimKey.MAIN;
		
		if (!Texture.checkSheet(this.SHEETFILE)) {
			Texture.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.SHEETKEYS);
		}
		
		this.update();
	}
	
	public WorldObject(int[] p) {
		super(p);
		
		this.SHEETFILE = "/res/animation.png";
		this.TILESIZE = new Dimension(100, 100);
		this.SHEETWIDTH = 10;
		this.SHEETKEYS = new AnimKey[1];
		this.SHEETKEYS[0] = AnimKey.MAIN;
		
		if (!Texture.checkSheet(SHEETFILE)) {
			Texture.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.SHEETKEYS);
		}
	}
	
	public WorldObject(Point p) {
		super(p);
		
		this.SHEETFILE = "/res/animation.png";
		this.TILESIZE = new Dimension(100, 100);
		this.SHEETWIDTH = 10;
		this.SHEETKEYS = new AnimKey[1];
		this.SHEETKEYS[0] = AnimKey.MAIN;
		
		if (!Texture.checkSheet(SHEETFILE)) {
			Texture.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.SHEETKEYS);
		}
	}
	
	public void update() {
		if (this.curFrame < this.SHEETWIDTH - 1) {
			this.curFrame++;
		}
		else {
			this.curFrame = 0;
		}
		
		if (this.curPos.x < 100) {
			this.curPos.x += 10;
		}
		else {
			this.curPos.x = 0;
		}
	}
}
