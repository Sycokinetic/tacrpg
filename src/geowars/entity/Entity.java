package geowars.entity;

import geowars.graphics.Animation;
import geowars.resource.SpriteLibrary;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;

public abstract class Entity {
	protected String SHEETFILE;
	protected Dimension TILESIZE;
	protected int SHEETWIDTH;
	protected HashMap<Integer, String> KEYVALS;
	protected HashMap<String, Animation> ANIMLIST;
	
	protected String curKey;
	protected Point curPos;
	protected double curAngle;
	
	public Entity() {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.SHEETWIDTH = 0;
		
		this.KEYVALS = new HashMap<Integer, String>();
		this.ANIMLIST = new HashMap<String, Animation>();
		
		this.curKey = null;
		this.curPos = new Point(0, 0);
		this.curAngle = 0;
	}
	
	public Entity(int x, int y) {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.SHEETWIDTH = 0;
		
		this.curKey = null;
		this.curPos = new Point(x, y);
	}
	
	public Entity(Point p) {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.SHEETWIDTH = 0;
		
		this.curKey = null;
		this.curPos = new Point(0, 0);
	}
	
	public String getCurAnim() {
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
	
	public String[] getSheetKeys() {
		return this.ANIMLIST.keySet().toArray(new String[0]);
	}
	
	public String getSheetName() {
		return SHEETFILE;
	}
	
	public Dimension getTileSize() {
		return TILESIZE;
	}
	
	public void setCurAnim(String k) {
		if (!this.curKey.equals(k)) {
			for (String i: this.ANIMLIST.keySet()) {
				this.ANIMLIST.get(i).reset();
			}
			
			this.curKey = k;
		}
	}
	
	public void setCurPos(Point p) {
		this.curPos = p;
	}
	
	@Override
	public String toString() {
		String s = "Type: %s, Sheet Path: %s";
		return String.format(s, this.getClass(), SHEETFILE);
	}
	
	protected void loadAnims() {
		for (Integer i: this.KEYVALS.keySet()) {
			String k = this.KEYVALS.get(i);
			this.ANIMLIST.put(k, new Animation(this.SHEETFILE, k));
		}
	}
	
	protected void loadSheet() {
		if (!SpriteLibrary.checkSheet(this.SHEETFILE)) {
			SpriteLibrary.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.KEYVALS);
		}
	}
	
	public abstract void update();
	protected abstract void loadData();
}
