package geowars.graphics;

import geowars.Game;
import geowars.entity.Entity;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class DisplayPanel extends JPanel {
	private static ArrayList<Entity> entityList = Game.getEntityList();
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (Entity i: entityList) {
			g.drawImage(i.getCurFrame(), i.getCurPos().x, i.getCurPos().y, this);
		}
	}
}
