package geowars.map;

import geowars.Constant;
import geowars.Game;
import geowars.entity.person.Nonplayer;
import geowars.entity.world.Wall;

import java.awt.Point;
import java.util.Random;

public class TestMap extends Map {
	@Override
	public void loadMap() {
		playerPos = new Point(0, 0);
		
		Game.getPlayer().setCurPos(playerPos);
		personList.add(Game.getPlayer());
		
		for (int y = -400; y <= 400; y += 10) {
			worldList.add(new Wall(-490, y, Constant.LEFT));
			worldList.add(new Wall(490, y, Constant.RIGHT));
		}
		for (int x = -400; x <= 400; x += 10) {
			worldList.add(new Wall(x, -500, Constant.UP));
			worldList.add(new Wall(x, 500, Constant.DOWN));
		}
		
		Random randGen = new Random();
		for (int i = 0; i < 10; i++) {
			personList.add(new Nonplayer(randGen.nextInt(1000) - 500, randGen.nextInt(1000) - 500));
		}
	}
}
