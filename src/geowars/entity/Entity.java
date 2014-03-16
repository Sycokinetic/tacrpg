package geowars.entity;

import geowars.graphics.Texture;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JLabel;

public abstract class Entity extends JLabel {
	protected String SHEETFILE;
	protected Dimension TILESIZE;
	protected int FRAMECOUNT;
	
	protected int frame;
	protected Point pos;
	
	public Entity() {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.FRAMECOUNT = 0;
		
		this.frame = 0;
		this.pos = new Point(0, 0);
	}
	
	public Entity(int[] p) {
		this.frame = 0;
		this.pos = new Point(p[0], p[1]);
	}
	
	public Entity(Point p) {
		this.frame = 0;
		this.pos = p;
	}
	
	public BufferedImage getCurFrame() {
		return Texture.getFrame(SHEETFILE, frame);
	}
	
	public int getCurFrameVal() {
		return this.frame;
	}
	
	public int getFrameCount() {
		return FRAMECOUNT;
	}
	
	public Point getPos() {
		return this.pos;
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
	
	public void setFrame(int fr) {
		this.frame = fr;
	}
	
	@Override
	public String toString() {
		String s = "Type: %s, Sheet Path: %s, Current Frame: %d";
		return String.format(s, this.getClass(), SHEETFILE, this.frame);
	}
	
	public  abstract void update();
}
