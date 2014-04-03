package geowars.entity.world;

import geowars.entity.Entity;
import java.awt.Point;

public abstract class WorldObject extends Entity {
	public WorldObject() {}
	
	public WorldObject(int x, int y) {
		super(x, y);
	}
	
	public WorldObject(Point p) {
		super(p);
	}
}
