package tacRPG.resource.entity.person;


import java.awt.Dimension;
import java.awt.Point;

import tacRPG.Constant;
import tacRPG.Game;

public class Nonplayer extends Person {
	private static final long serialVersionUID = 1L;

	public Nonplayer() {
	}

	public Nonplayer(int x, int y) {
		super(x, y);
	}

	public Nonplayer(Point p) {
		super(p);
	}

	@Override
	public void update() {
		this.moveTowardPoint(Game.getPlayer().getCenter());
	}

	@Override
	protected void loadData() {
		this.SHEETFILE = "/res/dot.png";
		this.TILESIZE = new Dimension(19, 19);
		this.SHEETWIDTH = 1;

		this.KEYVALS.put(0, Constant.MAIN);

		this.loadSheet();
		this.loadAnims();

		this.curKey = Constant.MAIN;
		this.moveRate = 100;
	}
}
