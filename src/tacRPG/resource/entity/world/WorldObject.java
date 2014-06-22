package tacRPG.resource.entity.world;

import java.awt.Point;

import tacRPG.Constant;
import tacRPG.resource.entity.Entity;

public abstract class WorldObject extends Entity {
	private static final long serialVersionUID = 1L;
	
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
