package tacRPG.resource.element;

import java.awt.Point;

import tacRPG.resource.Resource;

public class Element extends Resource {
	private static final long serialVersionUID = 1L;
	
	private Point pos;
	
	public Element() {};
	
	public Element(int x, int y) {
		this.pos = new Point(x, y);
	}
	
	public Point getPos() {
		return this.pos;
	}
}
