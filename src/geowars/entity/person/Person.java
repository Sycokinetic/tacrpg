package geowars.entity.person;

import geowars.Game;
import geowars.entity.Entity;

import java.awt.Point;

public abstract class Person extends Entity {	
	public Person() {}
	
	public Person(int x, int y) {
		super(x, y);
	}
	
	public Person(Point p) {
		super(p);
	}
	
	public void moveDown() {
		//setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.DOWN));
		double dt = Game.getCurFrameTime() - Game.getPrevFrameTime();
		double n = (this.moveRate * dt) / 1_000_000_000;
		this.moveErr[0] += n;
		
		if (this.moveErr[0] >= 1) {
			this.curPos.y += 1;
			this.moveErr[0] -= 1;
		}
		
		this.ANIMLIST.get(this.curKey).cycle();
	}
	
	public void moveLeft() {
		//setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.LEFT));
		double dt = Game.getCurFrameTime() - Game.getPrevFrameTime();
		double n = (this.moveRate * dt) / 1_000_000_000;
		this.moveErr[1] += n;
		if (this.moveErr[1] >= 1) {
			this.curPos.x -= 1;
			this.moveErr[1] -= 1;
		}
		
		this.ANIMLIST.get(this.curKey).cycle();
	}
	
	public void moveRight() {
		//setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.RIGHT));
		double dt = Game.getCurFrameTime() - Game.getPrevFrameTime();
		double n = (this.moveRate * dt) / 1_000_000_000;
		this.moveErr[2] += n;
		if (this.moveErr[2] >= 1) {
			this.curPos.x += 1;
			this.moveErr[2] -= 1;
		}
		
		this.ANIMLIST.get(this.curKey).cycle();
	}
	
	public void moveUp() {
		//setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.UP));
		double dt = Game.getCurFrameTime() - Game.getPrevFrameTime();
		double n = (this.moveRate * dt) / 1_000_000_000;
		this.moveErr[3] += n;
		if (this.moveErr[3] >= 1) {
			this.curPos.y -= 1;
			this.moveErr[3] -= 1;
		}
		
		this.ANIMLIST.get(this.curKey).cycle();
	}
}
