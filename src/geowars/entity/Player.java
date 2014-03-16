package geowars.entity;

import geowars.graphics.Texture;

import java.awt.Dimension;

public class Player extends WorldObject {
	public Player() {
		this.SHEETFILE = "/res/ring.png";
		this.TILESIZE = new Dimension(99, 99);
		this.FRAMECOUNT = 1;
		
		if (Texture.getFrameSet(SHEETFILE) == null) {
			Texture.loadSheet(this.SHEETFILE, this.FRAMECOUNT, this.TILESIZE);
		}
		
		this.update();
	}
	
	public Player(int[] p) {
		super(p);
		
		this.SHEETFILE = "/res/animation.png";
		this.TILESIZE = new Dimension(99, 99);
		this.FRAMECOUNT = 1;
		
		if (Texture.getFrameSet(SHEETFILE) == null) {
			Texture.loadSheet(this.SHEETFILE, this.FRAMECOUNT, this.TILESIZE);
		}
	}
	
	public void moveDown(int n) {
		this.pos.y += n;
	}
	
	public void moveLeft(int n) {
		this.pos.x -= n;
	}
	
	public void moveRight(int n) {
		this.pos.x += n;
	}
	
	public void moveUp(int n) {
		this.pos.y -= n;
	}
	
	@Override
	public void update() {}
}
