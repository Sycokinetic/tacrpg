package geowars.entity.person;

import java.awt.Point;

public class Nonplayer extends Person {
	public Nonplayer() {
		this.loadData();
	}
	
	public Nonplayer(int x, int y) {
		super(x, y);
		this.loadData();
	}
	
	public Nonplayer(Point p) {
		super(p);
		this.loadData();
	}
	
	protected void loadData() {
		
	}
}
