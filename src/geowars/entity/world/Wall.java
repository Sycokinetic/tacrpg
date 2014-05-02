package geowars.entity.world;

import geowars.Constant;

import java.awt.Dimension;
import java.awt.Point;

public class Wall extends WorldObject {
	public Wall(String s) {
		this.curKey = s;
		this.orientation = s;
	}
	
	public Wall(int x, int y, String s) {
		super(x, y);
		this.curKey = s;
		this.orientation = s;
	}
	
	public Wall(Point p, String s) {
		super(p);
		this.curKey = s;
		this.orientation = s;
	}

	@Override
	protected void loadData() {
		this.SHEETFILE = "/res/wall.png";
		this.TILESIZE = new Dimension(100, 100);
		this.SHEETWIDTH = 1;

		this.KEYVALS.put(0, Constant.DOWN);
		this.KEYVALS.put(1, Constant.LEFT);
		this.KEYVALS.put(2, Constant.RIGHT);
		this.KEYVALS.put(3, Constant.UP);

		this.loadSheet();
		this.loadAnims();
	}

	@Override
	public void update() {
	}
}
