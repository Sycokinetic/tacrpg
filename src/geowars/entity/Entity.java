package geowars.entity;

import geowars.graphics.Texture;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JLabel;

public abstract class Entity extends JLabel {
	public enum AnimKey {MAIN, JUMP};
	
	protected String SHEETFILE;
	protected Dimension TILESIZE;
	protected int SHEETWIDTH;
	protected AnimKey[] SHEETKEYS;
	
	protected AnimKey curAnim;
	protected int curFrame;
	protected Point curPos;
	
	public Entity() {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.SHEETWIDTH = 0;
		this.SHEETKEYS = null;
		
		this.curAnim = AnimKey.MAIN;
		this.curFrame = 0;
		this.curPos = new Point(0, 0);
	}
	
	public Entity(int[] p) {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.SHEETWIDTH = 0;
		this.SHEETKEYS = null;
		
		this.curAnim = AnimKey.MAIN;
		this.curFrame = 0;
		this.curPos = new Point(0, 0);
	}
	
	public Entity(Point p) {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.SHEETWIDTH = 0;
		this.SHEETKEYS = null;
		
		this.curAnim = AnimKey.MAIN;
		this.curFrame = 0;
		this.curPos = new Point(0, 0);
	}
	
	public AnimKey getCurAnim() {
		return this.curAnim;
	}
	
	public BufferedImage getCurFrame() {
		return Texture.getFrame(SHEETFILE, this.curAnim, this.curFrame);
	}
	
	public int getCurFrameVal() {
		return this.curFrame;
	}
	
	public Point getCurPos() {
		return this.curPos;
	}
	
	public int getSheetWidth() {
		return this.SHEETWIDTH;
	}
	
	public AnimKey[] getSheetKeys() {
		return this.SHEETKEYS;
	}
	
	/*
	public static BufferedImage getSheetImage() {
		return Texture.getSheet(SHEETFILE);
	}
	*/
	
	public String getSheetName() {
		return SHEETFILE;
	}
	
	public Dimension getTileSize() {
		return TILESIZE;
	}
	
	public void setCurAnim(AnimKey a) {
		this.curAnim = a;
	}
	
	public void setCurFrame(int fr) {
		this.curFrame = fr;
	}
	
	public void setCurPos(Point p) {
		this.curPos = p;
	}
	
	@Override
	public String toString() {
		String s = "Type: %s, Sheet Path: %s, Current Frame: %d";
		return String.format(s, this.getClass(), SHEETFILE, this.curFrame);
	}
	
	public  abstract void update();
}
