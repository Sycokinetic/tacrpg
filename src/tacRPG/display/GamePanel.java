package tacRPG.display;


import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import tacRPG.Game;
import tacRPG.resource.entity.Entity;

public class GamePanel extends JPanel {
	private Camera cam;

	private static final long serialVersionUID = 1L;

	public GamePanel() {
		cam = new Camera(true);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		ArrayList<Entity> entityList = new ArrayList<Entity>();
		entityList.addAll(Game.getPersonList());
		entityList.addAll(Game.getWorldList());

		cam.follow();

		for (Entity i : entityList) {
			int xPos = i.getCurPos().x - cam.getCurPos().x;
			int yPos = i.getCurPos().y - cam.getCurPos().y;

			g.drawImage(i.getCurFrame(), xPos, yPos, this);
		}
	}
}

// INVESTIGATE THIS IMPLEMENTATION
//public class GamePanel extends Canvas implements Runnable {
//	private Camera cam;
//	private volatile int count;
//	private volatile boolean isRunning;
//
//	public GamePanel() {
//		cam = new Camera(true);
//		createBufferStrategy(3);
//	}
//
//	protected void draw() {
//		BufferStrategy bs = getBufferStrategy();
//		if (bs == null) {
//			createBufferStrategy(3);
//			return;
//		}
//
//		Graphics g = bs.getDrawGraphics();
//		g.setColor(Color.BLACK);
//		g.fillOval(0, 0, 20, 20);
//		g.dispose();
//
//		try {
//			bs.show();
//		} catch (NullPointerException e) {
//
//		}
//
//		count++;
//	}
//
//	public void setRunning(boolean b) {
//		isRunning = false;
//	}
//
//	@Override
//	public void run() {
//		while (count < 3) {
//			this.draw();
//		}
//	}
//}