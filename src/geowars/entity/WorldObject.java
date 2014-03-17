package geowars.entity;

import geowars.Constant;
import geowars.Constant.*;
import geowars.graphics.Library;
import geowars.graphics.Animation;

import java.awt.Dimension;
import java.awt.Point;

public class WorldObject extends Entity {
	public WorldObject() {
		this.SHEETFILE = "/res/animation.png";
		this.TILESIZE = new Dimension(100, 100);
		this.SHEETWIDTH = 10;
		
		this.KEYVALS.put(0, Constant.concatKeys(EventKey.MAIN, ModKey.NULL));
		
		if (!Library.checkSheet(this.SHEETFILE)) {
			Library.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.KEYVALS);
		}
		
		for (Integer i: this.KEYVALS.keySet()) {
			String k = this.KEYVALS.get(i);
			this.ANIMLIST.put(k, new Animation(this.SHEETFILE, k));
		}
		
		this.curKey = Constant.concatKeys(EventKey.MAIN, ModKey.NULL);
	}
	
	public WorldObject(int[] p) {
		super(p);
		
		this.SHEETFILE = "/res/animation.png";
		this.TILESIZE = new Dimension(100, 100);
		this.SHEETWIDTH = 10;
		
		this.KEYVALS.put(0, Constant.concatKeys(EventKey.MAIN, ModKey.NULL));
		
		if (!Library.checkSheet(SHEETFILE)) {
			Library.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.KEYVALS);
		}
		
		for (Integer i: this.KEYVALS.keySet()) {
			String k = this.KEYVALS.get(i);
			this.ANIMLIST.put(k, new Animation(this.SHEETFILE, k));
		}
		
		this.curKey = Constant.concatKeys(EventKey.MAIN, ModKey.NULL);
	}
	
	public WorldObject(Point p) {
		super(p);
		
		this.SHEETFILE = "/res/animation.png";
		this.TILESIZE = new Dimension(100, 100);
		
		this.KEYVALS.put(0, Constant.concatKeys(EventKey.MAIN, ModKey.NULL));
		
		if (!Library.checkSheet(SHEETFILE)) {
			Library.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.KEYVALS);
		}
		
		for (Integer i: this.KEYVALS.keySet()) {
			String k = this.KEYVALS.get(i);
			this.ANIMLIST.put(k, new Animation(this.SHEETFILE, k));
		}
		
		this.curKey = Constant.concatKeys(EventKey.MAIN, ModKey.NULL);
	}
	
	public void update() {
		this.ANIMLIST.get(this.curKey).cycle();
		
		if (this.curPos.x < 90) {
			this.curPos.x += 10;
		}
		else {
			this.curPos.x = 0;
		}
	}
}
