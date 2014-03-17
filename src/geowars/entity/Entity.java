package geowars.entity;

import geowars.graphics.Animation;
import geowars.graphics.Animation.AnimKey;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;

public abstract class Entity {
	protected String SHEETFILE;
	protected Dimension TILESIZE;
	protected int SHEETWIDTH;
	protected HashMap<Integer, AnimKey> KEYVALS;
	protected HashMap<AnimKey, Animation> ANIMLIST;
	
	protected Point curPos;
	protected AnimKey curKey;
	
	public Entity() {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.SHEETWIDTH = 0;
		
		this.KEYVALS = new HashMap<Integer, AnimKey>();
		this.ANIMLIST = new HashMap<AnimKey, Animation>();
		
		this.curKey = AnimKey.MAIN;
		this.curPos = new Point(0, 0);
	}
	
	public Entity(int[] p) {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.SHEETWIDTH = 0;
		
		this.curKey = AnimKey.MAIN;
		this.curPos = new Point(0, 0);
	}
	
	public Entity(Point p) {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.SHEETWIDTH = 0;
		
		this.curKey = AnimKey.MAIN;
		this.curPos = new Point(0, 0);
	}
	
	public AnimKey getCurAnim() {
		return this.curKey;
	}
	
	public BufferedImage getCurFrame() {
		return this.ANIMLIST.get(this.curKey).getCurFrame();
	}
	
	public int getCurFrameVal() {
		return this.ANIMLIST.get(this.curKey).getCurFrameVal();
	}
	
	public Point getCurPos() {
		return this.curPos;
	}
	
	public int getSheetWidth() {
		return this.SHEETWIDTH;
	}
	
	public AnimKey[] getSheetKeys() {
		return this.ANIMLIST.keySet().toArray(new AnimKey[0]);
	}
	
	public String getSheetName() {
		return SHEETFILE;
	}
	
	public Dimension getTileSize() {
		return TILESIZE;
	}
	
	public void setCurAnim(AnimKey a) {
		this.curKey = a;
	}
	
	public void setCurPos(Point p) {
		this.curPos = p;
	}
	
	@Override
	public String toString() {
		String s = "Type: %s, Sheet Path: %s";
		return String.format(s, this.getClass(), SHEETFILE);
	}
	
	public  abstract void update();
}
