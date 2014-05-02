package geowars.entity.world;

import geowars.Constant;
import geowars.entity.Entity;
import java.awt.Point;

public abstract class WorldObject extends Entity {
	protected String orientation;
	
	public WorldObject() {
		collideAction = Constant.BLOCK;
	}
	
	public WorldObject(int x, int y) {
		super(x, y);
		collideAction = Constant.BLOCK;
	}
	
	public WorldObject(Point p) {
		super(p);
		collideAction = Constant.BLOCK;
	}
	
	public String getOrientation() {
		return this.orientation;
	}
	
	public String getCollideAction() {
		return this.collideAction;
	}
}
