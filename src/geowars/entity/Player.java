package geowars.entity;

import geowars.graphics.Library;
import geowars.graphics.Animation;
import geowars.graphics.Animation.AnimKey;

import java.awt.Dimension;

public class Player extends Entity {
	private boolean isJumping;
	
	public Player() {
		this.SHEETFILE = "/res/ring.png";
		this.TILESIZE = new Dimension(99, 99);
		this.SHEETWIDTH = 4;
		
		this.KEYVALS.put(0, AnimKey.DOWN);
		this.KEYVALS.put(1, AnimKey.LEFT);
		this.KEYVALS.put(2, AnimKey.RIGHT);
		this.KEYVALS.put(3, AnimKey.UP);
		this.KEYVALS.put(4, AnimKey.JUMP_DOWN);
		this.KEYVALS.put(5, AnimKey.JUMP_LEFT);
		this.KEYVALS.put(6, AnimKey.JUMP_RIGHT);
		this.KEYVALS.put(7, AnimKey.JUMP_UP);
		
		if (!Library.checkSheet(this.SHEETFILE)) {
			Library.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.KEYVALS);
		}
		
		for (Integer i: this.KEYVALS.keySet()) {
			AnimKey k = this.KEYVALS.get(i);
			this.ANIMLIST.put(k, new Animation(this.SHEETFILE, k));
		}
		
		this.curKey = AnimKey.UP;
		this.isJumping = false;
	}
	
	public Player(int[] p) {
		super(p);
		
		this.SHEETFILE = "/res/animation.png";
		this.TILESIZE = new Dimension(99, 99);
		this.SHEETWIDTH = 4;
		
		if (!Library.checkSheet(this.SHEETFILE)) {
			Library.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.KEYVALS);
		}
		
		this.KEYVALS.put(0, AnimKey.DOWN);
		this.KEYVALS.put(1, AnimKey.LEFT);
		this.KEYVALS.put(2, AnimKey.RIGHT);
		this.KEYVALS.put(3, AnimKey.UP);
		this.KEYVALS.put(4, AnimKey.JUMP_DOWN);
		this.KEYVALS.put(5, AnimKey.JUMP_LEFT);
		this.KEYVALS.put(6, AnimKey.JUMP_RIGHT);
		this.KEYVALS.put(7, AnimKey.JUMP_UP);
		
		if (!Library.checkSheet(this.SHEETFILE)) {
			Library.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.KEYVALS);
		}
		
		for (Integer i: this.KEYVALS.keySet()) {
			AnimKey k = this.KEYVALS.get(i);
			this.ANIMLIST.put(k, new Animation(this.SHEETFILE, k));
		}
		
		this.curKey = AnimKey.UP;
	}
	
	public void moveDown(int n) {
		if (this.isJumping) {
			this.curKey = this.KEYVALS.get(4);
			this.curPos.y += 3*n;
		}
		else {
			this.curKey = this.KEYVALS.get(0);
			this.curPos.y += n;
		}
	}
	
	public void moveLeft(int n) {
		if (this.isJumping) {
			this.curKey = this.KEYVALS.get(5);
			this.curPos.x -= 3*n;
		}
		else {
			this.curKey = this.KEYVALS.get(1);
			this.curPos.x -= n;
		}
	}
	
	public void moveRight(int n) {
		if (this.isJumping) {
			this.curKey = this.KEYVALS.get(6);
			this.curPos.x += 3*n;
		}
		else {
			this.curKey = this.KEYVALS.get(2);
			this.curPos.x += n;
		}
	}
	
	public void moveUp(int n) {
		if (this.isJumping) {
			this.curKey = this.KEYVALS.get(7);
			this.curPos.y -= 3*n;
		}
		else {
			this.curKey = this.KEYVALS.get(3);
			this.curPos.y -= n;
		}
	}
	
	public void setJumping(boolean b) {
		this.isJumping = b;
	}
	
	@Override
	public void update() {}
}
