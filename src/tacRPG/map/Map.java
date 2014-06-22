package tacRPG.map;


import java.awt.Point;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import tacRPG.resource.entity.person.Person;
import tacRPG.resource.entity.world.WorldObject;

public abstract class Map {
	List<Person> personList;
	List<WorldObject> worldList;
	Point playerPos;
	
	public Map() {
		personList = new CopyOnWriteArrayList<Person>();
		worldList = new CopyOnWriteArrayList<WorldObject>();
		loadMap();
	}
	
	public List<Person> getPersonList() {
		return this.personList;
	}
	
	public List<WorldObject> getWorldList() {
		return this.worldList;
	}
	
	public abstract void loadMap();
}
