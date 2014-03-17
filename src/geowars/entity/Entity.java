package geowars.entity;

import geowars.graphics.Animation;

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
	
	protected Point curPos;
	protected String curKey;
	
	public Entity() {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.SHEETWIDTH = 0;
		
		this.KEYVALS = new HashMap<Integer, String>();
		this.ANIMLIST = new HashMap<String, Animation>();
		
		this.curKey = null;
		this.curPos = new Point(0, 0);
	}
	
	public Entity(int[] p) {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.SHEETWIDTH = 0;
		
		this.curKey = null;
		this.curPos = new Point(0, 0);
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
	
	public void setCurAnim(String a) {
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
