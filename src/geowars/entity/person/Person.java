package geowars.entity.person;

import geowars.Game;
import geowars.entity.Entity;

import java.awt.Point;

public abstract class Person extends Entity {
	public Person() {
	}

	public Person(int x, int y) {
		super(x, y);
	}

	public Person(Point p) {
		super(p);
	}

	// BUG: IS STILL HIGHLY SENSITIVE TO UPS, POSSIBLY RELATED TO
	// GAME.UPDATECAP() STABILITY
	public void moveDown() {
		// setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.DOWN));
		double dt = Game.getMarker2() - Game.getMarker1();
//		double n = (this.moveRate * dt) / 1_000_000_000;
		double n = ((double) this.moveRate * Game.getTickTarget()) / 1000;

//		System.out.println(n);

		this.moveErr[0] += n;
		if (this.moveErr[0] >= 1) {
			this.curPos.y += this.moveRate;
			this.moveErr[0] -= 1;
		}

		this.ANIMLIST.get(this.curKey).cycle();
	}

	public void moveLeft() {
		// setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.LEFT));
		double dt = Game.getMarker2() - Game.getMarker1();
		double n = (this.moveRate * dt) / 1_000_000_000;

//		System.out.println(n);

		this.moveErr[1] += n;
		if (this.moveErr[1] >= 1) {
			this.curPos.x -= this.moveRate;
			this.moveErr[1] -= 1;
		}

		this.ANIMLIST.get(this.curKey).cycle();
	}

	public void moveRight() {
		// setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.RIGHT));
		double dt = Game.getMarker2() - Game.getMarker1();
		double n = (this.moveRate * dt) / 1_000_000_000;

//		System.out.println(n);

		this.moveErr[2] += n;
		if (this.moveErr[2] >= 1) {
			this.curPos.x += this.moveRate;
			this.moveErr[2] -= 1;
		}

		this.ANIMLIST.get(this.curKey).cycle();
	}

	public void moveUp() {
		// setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.UP));
		double dt = Game.getMarker2() - Game.getMarker1();
		double n = (this.moveRate * dt) / 1_000_000_000;

//		System.out.println(n);

		this.moveErr[3] += n;
		if (this.moveErr[3] >= 1) {
			this.curPos.y -= this.moveRate;
			this.moveErr[3] -= 1;
		}

		this.ANIMLIST.get(this.curKey).cycle();
	}
}
