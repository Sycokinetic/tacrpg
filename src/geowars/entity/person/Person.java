package geowars.entity.person;

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
	
	public void moveDown(int n) {
		//setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.DOWN));
		this.curPos.y += n;
		
		this.ANIMLIST.get(this.curKey).cycle();
	}
	
	public void moveLeft(int n) {
		//setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.LEFT));
		this.curPos.x -= n;
		
		this.ANIMLIST.get(this.curKey).cycle();
	}
	
	public void moveRight(int n) {
		//setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.RIGHT));
		this.curPos.x += n;
		
		this.ANIMLIST.get(this.curKey).cycle();
	}
	
	public void moveUp(int n) {
		//setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.UP));
		this.curPos.y -= n;
		
		this.ANIMLIST.get(this.curKey).cycle();
	}
}
