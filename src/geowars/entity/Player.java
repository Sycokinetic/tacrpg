package geowars.entity;

import geowars.Constant;
import geowars.Constant.*;
import geowars.graphics.Library;
import geowars.graphics.Animation;

import java.awt.Dimension;
import java.util.ArrayList;

public class Player extends Entity {
	private boolean isJumping;
	
	public Player() {
		this.SHEETFILE = "/res/ring.png";
		this.TILESIZE = new Dimension(99, 99);
		this.SHEETWIDTH = 4;
		
		this.KEYVALS.put(0, Constant.concatKeys(EventKey.MOVE, ModKey.DOWN));
		this.KEYVALS.put(1, Constant.concatKeys(EventKey.MOVE, ModKey.LEFT));
		this.KEYVALS.put(2, Constant.concatKeys(EventKey.MOVE, ModKey.RIGHT));
		this.KEYVALS.put(3, Constant.concatKeys(EventKey.MOVE, ModKey.UP));
		this.KEYVALS.put(4, Constant.concatKeys(EventKey.JUMP, ModKey.DOWN));
		this.KEYVALS.put(5, Constant.concatKeys(EventKey.JUMP, ModKey.LEFT));
		this.KEYVALS.put(6, Constant.concatKeys(EventKey.JUMP, ModKey.RIGHT));
		this.KEYVALS.put(7, Constant.concatKeys(EventKey.JUMP, ModKey.UP));
		
		if (!Library.checkSheet(this.SHEETFILE)) {
			Library.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.KEYVALS);
		}
		
		for (Integer i: this.KEYVALS.keySet()) {
			String k = this.KEYVALS.get(i);
			this.ANIMLIST.put(k, new Animation(this.SHEETFILE, k));
		}
		
		this.curKey = Constant.concatKeys(EventKey.MOVE, ModKey.UP);
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
		
		this.KEYVALS.put(0, Constant.concatKeys(EventKey.MOVE, ModKey.DOWN));
		this.KEYVALS.put(1, Constant.concatKeys(EventKey.MOVE, ModKey.LEFT));
		this.KEYVALS.put(2, Constant.concatKeys(EventKey.MOVE, ModKey.RIGHT));
		this.KEYVALS.put(3, Constant.concatKeys(EventKey.MOVE, ModKey.UP));
		this.KEYVALS.put(4, Constant.concatKeys(EventKey.JUMP, ModKey.DOWN));
		this.KEYVALS.put(5, Constant.concatKeys(EventKey.JUMP, ModKey.LEFT));
		this.KEYVALS.put(6, Constant.concatKeys(EventKey.JUMP, ModKey.RIGHT));
		this.KEYVALS.put(7, Constant.concatKeys(EventKey.JUMP, ModKey.UP));
		
		if (!Library.checkSheet(this.SHEETFILE)) {
			Library.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.KEYVALS);
		}
		
		for (Integer i: this.KEYVALS.keySet()) {
			String k = this.KEYVALS.get(i);
			this.ANIMLIST.put(k, new Animation(this.SHEETFILE, k));
		}
		
		this.curKey = Constant.concatKeys(EventKey.MOVE, ModKey.UP);
	}
		
	public void moveDown(int n) {
		if (this.isJumping) {
			setCurKey("JUMP_DOWN");
			this.curPos.y += 3*n;
		}
		else {
			setCurKey("MOVE_DOWN");
			this.curPos.y += n;
		}
		
		this.ANIMLIST.get(this.curKey).cycle();
	}
	
	public void moveLeft(int n) {
		if (this.isJumping) {
			setCurKey("JUMP_LEFT");
			this.curPos.x -= 3*n;
		}
		else {
			setCurKey("MOVE_LEFT");
			this.curPos.x -= n;
		}
		
		this.ANIMLIST.get(this.curKey).cycle();
	}
	
	public void moveRight(int n) {
		if (this.isJumping) {
			setCurKey("JUMP_RIGHT");
			this.curPos.x += 3*n;
		}
		else {
			setCurKey("MOVE_RIGHT");
			this.curPos.x += n;
		}
		
		this.ANIMLIST.get(this.curKey).cycle();
	}
	
	public void moveUp(int n) {
		if (this.isJumping) {
			setCurKey("JUMP_UP");
			this.curPos.y -= 3*n;
		}
		else {
			setCurKey("MOVE_UP");
			this.curPos.y -= n;
		}
		
		this.ANIMLIST.get(this.curKey).cycle();
	}
	
	public void setJumping(boolean b) {
		this.isJumping = b;
		
		ArrayList<String> oldKey = Constant.splitKeyString(this.curKey);
		oldKey.remove(0);
		
		if (b) {
			this.curKey = Constant.concatKeys(Constant.EventKey.JUMP, oldKey);
		}
		else {
			this.curKey = Constant.concatKeys(Constant.EventKey.MOVE, oldKey);
		}
	}
	
	public void setCurKey(String k) {
		if (!this.curKey.equals(k)) {
			for (String i: this.ANIMLIST.keySet()) {
				this.ANIMLIST.get(i).setCurFrameVal(-1);
			}
			
			this.curKey = k;
		}
	}
	
	@Override
	public void update() {}
}
