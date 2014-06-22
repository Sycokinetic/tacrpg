package tacRPG.map;


import java.awt.Point;
import java.util.Random;

import tacRPG.Constant;
import tacRPG.Game;
import tacRPG.resource.entity.person.Nonplayer;
import tacRPG.resource.entity.world.Wall;

public class TestMap extends Map {
	@Override
	public void loadMap() {
		playerPos = new Point(0, 0);
		
		Game.getPlayer().setCurPos(playerPos);
		personList.add(Game.getPlayer());
		
		for (int y = -800; y <= 800; y += 10) {
			worldList.add(new Wall(-890, y, Constant.LEFT));
			worldList.add(new Wall(890, y, Constant.RIGHT));
		}
		for (int x = -800; x <= 800; x += 10) {
			worldList.add(new Wall(x, -900, Constant.UP));
			worldList.add(new Wall(x, 900, Constant.DOWN));
		}
		
		Random randGen = new Random();
		for (int i = 0; i < 100; i++) {
			personList.add(new Nonplayer(randGen.nextInt(1600) - 800, randGen.nextInt(1600) - 800));
		}
	}
}
