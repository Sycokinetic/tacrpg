package geowars.display;

import geowars.Constant;
import geowars.Game;
import geowars.entity.Entity;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		List<Entity> entityList = Game.getEntityList();
				
		for (Entity i : entityList) {
			int camX = Game.getPlayer().getCenter().x;// + Game.getWinSize().width/2;
			int camY = Game.getPlayer().getCenter().y;// + Game.getWinSize().height/2;
			
			g.drawImage(i.getCurFrame(), i.getCurPos().x, i.getCurPos().y, this);
		}
	}
}
