package geowars.entity.person;

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
}
