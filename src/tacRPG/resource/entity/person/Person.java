package tacRPG.resource.entity.person;


import java.awt.Point;

import tacRPG.Constant;
import tacRPG.resource.entity.Entity;

public abstract class Person extends Entity {
	private static final long serialVersionUID = 1L;

	public Person() {
		collideAction = Constant.ATTACK;
	}

	public Person(int x, int y) {
		super(x, y);
		collideAction = Constant.ATTACK;
	}

	public Person(Point p) {
		super(p);
		collideAction = Constant.ATTACK;
	}
}
