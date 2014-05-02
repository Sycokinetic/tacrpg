package geowars.entity.person;

import geowars.Constant;
import geowars.entity.Entity;

import java.awt.Point;

public abstract class Person extends Entity {
	
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
