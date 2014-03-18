package geowars.entity.world;

import geowars.Constant;
import geowars.Constant.*;
import geowars.entity.Entity;
import geowars.graphics.Animation;
import geowars.resource.SpriteLibrary;

import java.awt.Dimension;
import java.awt.Point;

public class WorldObject extends Entity {
	public WorldObject() {
		loadData();
	}
	
	public WorldObject(int x, int y) {
		super(x, y);
		loadData();
	}
	
	public WorldObject(Point p) {
		super(p);
		loadData();
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
	
	public void loadData() {
		this.SHEETFILE = "/res/animation.png";
		this.TILESIZE = new Dimension(100, 100);
		this.SHEETWIDTH = 10;
		
		this.KEYVALS.put(0, Constant.concatKeys(EventKey.MAIN, ModKey.NULL));
		
		if (!SpriteLibrary.checkSheet(this.SHEETFILE)) {
			SpriteLibrary.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.KEYVALS);
		}
		
		for (Integer i: this.KEYVALS.keySet()) {
			String k = this.KEYVALS.get(i);
			this.ANIMLIST.put(k, new Animation(this.SHEETFILE, k));
		}
		
		this.curKey = Constant.concatKeys(EventKey.MAIN, ModKey.NULL);
	}
}
