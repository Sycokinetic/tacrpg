package geowars.entity;

import geowars.graphics.Texture;

import java.awt.Dimension;

public class Player extends Entity {
	public Player() {
		this.SHEETFILE = "/res/ring.png";
		this.TILESIZE = new Dimension(99, 99);
		this.SHEETWIDTH = 4;
		this.SHEETKEYS = new AnimKey[2];
		this.SHEETKEYS[0] = AnimKey.MAIN;
		this.SHEETKEYS[1] = AnimKey.JUMP;
		
		if (!Texture.checkSheet(this.SHEETFILE)) {
			Texture.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.SHEETKEYS);
		}
		
		this.update();
	}
	
	public Player(int[] p) {
		super(p);
		
		this.SHEETFILE = "/res/animation.png";
		this.TILESIZE = new Dimension(99, 99);
		this.SHEETWIDTH = 4;
		this.SHEETKEYS = new AnimKey[2];
		this.SHEETKEYS[0] = AnimKey.MAIN;
		this.SHEETKEYS[1] = AnimKey.JUMP;
		
		if (!Texture.checkSheet(this.SHEETFILE)) {
			Texture.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.SHEETKEYS);
		}
	}
	
	public void moveDown(int n) {
		this.curFrame = 0;
		this.curPos.y += n;
	}
	
	public void moveLeft(int n) {
		this.curFrame = 1;
		this.curPos.x -= n;
	}
	
	public void moveRight(int n) {
		this.curFrame = 2;
		this.curPos.x += n;
	}
	
	public void moveUp(int n) {
		this.curFrame = 3;
		this.curPos.y -= n;
	}
	
	public void jump() {
		this.curAnim = AnimKey.JUMP;
	}
	
	@Override
	public void update() {}
}
