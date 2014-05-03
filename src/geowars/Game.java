/*
 * Classname: Game
 * Author: Sycokinetic
 */

package geowars;

import geowars.display.Controller;
import geowars.display.MainWindow;
import geowars.entity.Entity;
import geowars.entity.person.Person;
import geowars.entity.person.Player;
import geowars.entity.world.WorldObject;
import geowars.map.Map;
import geowars.map.TestMap;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
	// == Private attributes ==
	private static final String VERSION = "Geowars PC v0.1";
	private static volatile boolean isRunning = false;

	private static volatile Player player;
	private static Thread windowThread;

	private static long prevFrameStart;
	private static long curFrameStart;
	private static long prevTickLength;
	private static int tickTarget = 5; // Base UPS Rate of n ms per update

	private static int tickCount = 0;
	private static int timeElapse = 0;
	private static int ups = 0;
	private static MainWindow window;
	private static Rectangle winSize;

//	private static volatile List<Entity> entityList;
	private static volatile List<Person> personList;
	private static volatile List<WorldObject> worldList;

	private static String status;
	private static ScheduledExecutorService scheduler;

	private static Map map;

	// == Private methods ==
	/**
	 * Identifies collision by checking the radii of the circles inscribed in
	 * the image of each entity. If the distance between the center of each
	 * image is less than the sum off the radii, then they collide.
	 * 
	 * @param a
	 *            An entity to be checked for collision
	 * @param b
	 *            The entity to be compared against a.
	 * @return boolean True if a and b collide.
	 */
	private static boolean sphereCollide(Entity a, Entity b) {
		Point p1 = a.getCenter();
		Point p2 = b.getCenter();

		double d = p1.distance(p2);

		if (d < a.getRadius() + b.getRadius()) {
			return true;
		} else {
			return false;
		}
	}
	
	private static int getDirCode(WorldObject w) {
		int i = -1;

		String dir = w.getOrientation();
		
		if (dir.compareTo(Constant.DOWN) == 0) {
			i = 0;
		} else if (dir.compareTo(Constant.LEFT) == 0) {
			i = 1;
		} else if (dir.compareTo(Constant.RIGHT) == 0) {
			i = 2;
		} else if (dir.compareTo(Constant.UP) == 0) {
			i = 3;
		}
		
		return i;
	}

	// == Public Methods ==
	/**
	 * @return the list of all entities in the game
	 */
//	public static synchronized List<Entity> getEntityList() {
//		return entityList;
//	}

	public static synchronized List<Person> getPersonList() {
		return personList;
	}

	public static synchronized List<WorldObject> getWorldList() {
		return worldList;
	}

	/**
	 * @return the game's player entity
	 */
	public static synchronized Player getPlayer() {
		return player;
	}

	public static long getPrevTickLength() {
		return prevTickLength;
	}

	public static String getStatus() {
		return new String(status);
	}

	public static int getTickTarget() {
		return tickTarget;
	}

	public static int getTickCount() {
		return tickCount;
	}

	public static int getTimeElapse() {
		return timeElapse;
	}

	public static int getUPS() {
		return ups;
	}

	public static Rectangle getWinSize() {
		return winSize;
	}

	public static String getVersion() {
		return VERSION;
	}

	public static boolean isPaused() {
		if (status.compareTo(Constant.PAUSE) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isRunning() {
		return isRunning;
	}

	/**
	 * Sets the game's running status to b
	 * 
	 * @param b
	 *            boolean where true means the game will cycle through its
	 *            primary loop.
	 */
	public static synchronized void setRunning(boolean b) {
		isRunning = b;
	}

	public static synchronized void setPaused(boolean b) {
		if (b) {
			status = Constant.PAUSE;
		} else {
			status = Constant.PLAY;
		}
	}

	public static void togglePaused() {
		if (status.compareTo(Constant.PLAY) != 0) {
			status = Constant.PLAY;
		} else {
			status = Constant.PAUSE;
		}
	}

	public static void start() {
		isRunning = true;
		status = Constant.MAIN;

		window = new MainWindow();
		windowThread = new Thread(window);

		winSize = window.getWinSize();
		player = new Player(0, 0);

		map = new TestMap();
		personList = map.getPersonList();
		worldList = map.getWorldList();

		windowThread.start();

		scheduler = Executors.newScheduledThreadPool(1);
		Runnable run = new Runnable() {
			@Override
			public void run() {
				prevFrameStart = curFrameStart;
				curFrameStart = System.nanoTime();
				prevTickLength = curFrameStart - prevFrameStart;

				Controller.processKeys();

//				for (int a = 0; a < 4; a++) {
//					System.out.print(player.getCanMove()[a] + ", ");
//				}
//				System.out.println();

				if (status.compareTo(Constant.PLAY) == 0) {	
					for (Person i : personList) {
						i.update();

						if (i != player && sphereCollide(player, i)) {
							i.setAlive(Constant.DEAD);
						}

						if (i.getAlive() == Constant.DEAD) {
							personList.remove(i);
						}
					}

					player.setCanMove(true);
					for (WorldObject i : worldList) {
						if (sphereCollide(player, i)) {
							player.setCanMove(getDirCode(i), false);
						}
					}
				}

				tickCount++;
				timeElapse += prevTickLength;
				if (tickCount % (tickTarget / 2) == 0) {
					ups = (int) (1_000_000_000 * ((double) tickCount / timeElapse));
					tickCount = 0;
					timeElapse = 0;
				}
			}
		};

		prevFrameStart = 0;
		curFrameStart = System.nanoTime();

		scheduler.scheduleAtFixedRate(run, 0, tickTarget, TimeUnit.MILLISECONDS);
	}

	public static void stop() {
		isRunning = false;
		scheduler.shutdown();
		window.stop();

		try {
			windowThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		player = null;
		personList = null;
		worldList = null;
	}
}