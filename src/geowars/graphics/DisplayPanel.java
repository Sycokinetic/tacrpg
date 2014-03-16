package geowars.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

import geowars.entity.*;

public class DisplayPanel extends JPanel {
	private ArrayList<Entity> entityList;
	
	public DisplayPanel() {
		this.entityList = new ArrayList<Entity>();
		
		setBackground(Color.BLACK);
	}
	
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
			g.drawImage(i.getCurFrame(), i.getPos().x, i.getPos().y, this);
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
