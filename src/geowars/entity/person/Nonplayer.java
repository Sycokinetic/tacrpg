package geowars.entity.person;

import geowars.Constant;
import geowars.Constant.EventKey;
import geowars.Game;

import java.awt.Dimension;
import java.awt.Point;
import java.util.HashSet;

public class Nonplayer extends Person {
	private static enum Direction {
		DOWN,
		LEFT,
		RIGHT,
		UP,
	}
	
	private double err;
	private Direction curQuad;
	
	public Nonplayer() {
		this.err = 0;
		this.curQuad = null;
	}
	
	public Nonplayer(int x, int y) {
		super(x, y);
		this.err = 0;
		this.curQuad = null;
	}
	
	public Nonplayer(Point p) {
		super(p);
		this.err = 0;
		this.curQuad = null;
	}
	
	@Override
	public void update() {
		Point pPos = Game.getPlayer().getCenter();
		
		//pPos = new Point(100, 800); // 1000, 200 -> 100, 800
		
		double dx = pPos.x - this.getCenter().x;
		double dy = this.getCenter().y - pPos.y;
		
		//System.out.println(dx + ", " + dy + ", " + dy/dx);
		//System.out.println(this.getCenter());
		
		if (dx == 0 && dy == 0) {
			this.err = 0;
			// If this is at player, do nothing
		}
		else if (dx == 0 && dy != 0) {
			this.err = 0;
			// If this is below player, move up
			if (dy > 0) {
				this.curPos.y -= 1;
			}
			else {
				this.curPos.y += 1;
			}
		}
		else if (dx != 0 && dy == 0) {
			this.err = 0;
			// If this is right of player, move left
			if (dx > 0) {
				this.curPos.x += 1;
			}
			else {
				this.curPos.x -= 1;
			}
		}
		else {
			double m = 0;

			if (Math.abs(dx) >= Math.abs(dy)) {
				m = dy/dx;
				this.err += m;
				
				if (m > 0) {
					if (this.curQuad != Direction.UP){
						this.curQuad = Direction.UP;
						this.err = m;
					}
					
					if (dx > 0) {
						this.curPos.x += 1;
					}
					else {
						this.curPos.x -= 1;
					}
					
					if (dy > 0) {
						this.curPos.y -= Math.floor(this.err);
					}
					else {
						this.curPos.y += Math.floor(this.err);
					}
					
					if (this.err >= 1) {
						this.err -= 1;
					}
				}
				else {
					if (this.curQuad != Direction.DOWN) {
						this.curQuad = Direction.DOWN;
						this.err = m;
					}
					
					if (dx > 0) {
						this.curPos.x += 1;
					}
					else {
						this.curPos.x -= 1;
					}
					
					if (dy > 0) {
						this.curPos.y += Math.ceil(this.err);
					}
					else {
						this.curPos.y -= Math.ceil(this.err);
					}
					
					if (this.err <= -1) {
						this.err += 1;
					}
				}
			}
			else {
				m = dx/dy;
				this.err += m;
				
				if (m > 0) {
					if (this.curQuad != Direction.RIGHT) {
						this.curQuad = Direction.RIGHT;
						this.err = m;
					}
					
					if (dy > 0) {
						this.curPos.y -= 1;
					}
					else {
						this.curPos.y += 1;
					}
					
					if (dx > 0) {
						this.curPos.x += Math.floor(this.err);
					}
					else {
						this.curPos.x -= Math.floor(this.err);
					}
					
					if (this.err >= 1) {
						this.err -= 1;
					}
				}
				else {
					if (this.curQuad != Direction.LEFT) {
						this.curQuad = Direction.LEFT;
						this.err = m;
					}
					
					if (dx > 0) {
						this.curPos.y += 1;
					}
					else {
						this.curPos.y -= 1;
					}
					
					if (dy > 0) {
						this.curPos.x += Math.ceil(this.err);
					}
					else {
						this.curPos.x -= Math.ceil(this.err);
					}
					
					if (this.err <= -1) {
						this.err += 1;
					}
				}
			}
		}
	}
	
	@Override
	protected void loadData() {
		this.SHEETFILE = "/res/dot.png";
		this.TILESIZE = new Dimension(19, 19);
		this.SHEETWIDTH = 1;
		
		this.KEYVALS.put(0, Constant.concatKeys(EventKey.MAIN));
		
		this.loadSheet();
		this.loadAnims();
		
		this.curKey = Constant.concatKeys(EventKey.MAIN);
		this.err = 0;
	}
	
	public int GCD(int a, int b){
	   if (b==0) return a;
	   return GCD(b,a%b);
	}
}
