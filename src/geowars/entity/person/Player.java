package geowars.entity.person;

import geowars.Constant;
import geowars.Constant.*;

import java.awt.Dimension;
import java.awt.Point;

public class Player extends Person {
	public Player() {
		this.loadData();
	}
	
	public Player(int x, int y) {
		super(x, y);
		this.loadData();
	}
	
	public Player(Point p) {
		super(p);
		this.loadData();
	}
	
	protected void loadData() {
		this.SHEETFILE = "/res/ring.png";
		this.TILESIZE = new Dimension(99, 99);
		this.SHEETWIDTH = 4;
		
		this.KEYVALS.put(0, Constant.concatKeys(EventKey.MAIN));
		
		this.loadSheet();
		this.loadAnims();
		
		this.curKey = Constant.concatKeys(EventKey.MAIN);
	}
}
