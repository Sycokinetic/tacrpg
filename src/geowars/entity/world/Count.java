package geowars.entity.world;

import geowars.Constant;

import java.awt.Dimension;
import java.awt.Point;

public class Count extends WorldObject {
	public Count() {
		this.loadData();
	}
	
	public Count(int x, int y) {
		super(x, y);
		this.loadData();
	}
	
	public Count(Point p) {
		super(p);
		this.loadData();
	}
	
	@Override
	public void update() {
		this.ANIMLIST.get(this.curKey).cycle();
		
		if (this.curPos.x < 90) {
			this.curPos.x += 10;
		}
		else {
			this.curPos.x = 0;
		}
	}
	
	@Override
	protected void loadData() {
		this.SHEETFILE = "/res/animation.png";
		this.TILESIZE = new Dimension(100, 100);
		this.SHEETWIDTH = 10;
		
		this.KEYVALS.put(0, Constant.MAIN);
		
		this.loadSheet();
		this.loadAnims();
		
		this.curKey = Constant.MAIN;
	}
}
