package geowars.display;

import geowars.Game;
import geowars.entity.Entity;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

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
