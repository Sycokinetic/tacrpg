package tacRPG.resource.entity.person;


import java.awt.Dimension;
import java.awt.Point;

import tacRPG.Constant;

public class Player extends Person {
	private static final long serialVersionUID = 1L;

	public Player() {
	}

	public Player(int x, int y) {
		super(x, y);
	}

	public Player(Point p) {
		super(p);
	}

	public void moveDown() {
		super.moveDown();
	}

	public void moveLeft() {
		super.moveLeft();
	}

	public void moveRight() {
		super.moveRight();
	}

	public void moveUp() {
		super.moveUp();
	}

	@Override
	protected void loadData() {
		this.SHEETFILE = "/res/ring.png";
		this.TILESIZE = new Dimension(99, 99);
		this.SHEETWIDTH = 4;

		this.KEYVALS.put(0, Constant.MAIN);

		this.loadSheet();
		this.loadAnims();

		this.curKey = Constant.MAIN;
		this.moveRate = 400;
	}
	
	/**
	 * Empty function inherited from Entity.
	 */
	@Override
	public void update() {
	}
}