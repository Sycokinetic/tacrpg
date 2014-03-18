package geowars.graphics;

import geowars.entity.Entity;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class DisplayPanel extends JPanel {
	private ArrayList<Entity> entityList;
	
	public DisplayPanel(ArrayList<Entity> el) {
		this.entityList = el;
	}
	
	public ArrayList<Entity> getEntityList() {
		return this.entityList;
	}
	
	public void setEntityList(ArrayList<Entity> el) {
		this.entityList = el;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (Entity i: this.entityList) {
			g.drawImage(i.getCurFrame(), i.getCurPos().x, i.getCurPos().y, this);
		}
	}
	
	/*
	private void render(Graphics g) {
		for (Entity i: this.entityList) {
			g.drawImage(i.getCurFrame(), i.getPos().x, i.getPos().y, this);
		}
	}
	*/
}
