package geowars.display;

import geowars.Game;

import java.awt.Point;

public class Camera {
	private boolean followPlayer;
	private Point curPos;

	public Camera(boolean follow) {
		followPlayer = follow;
		curPos = new Point(0, 0);
	}

	public void moveRight() {
		if (this.followPlayer) {
			this.follow();
		}
	}
	
	public void moveLeft() {
		if (this.followPlayer) {
			this.follow();
		}
	}
	
	public void moveUp() {
		if (this.followPlayer) {
			this.follow();
		}
	}
	
	public void moveDown() {
		if (this.followPlayer) {
			this.follow();
		}
	}

	public void follow() {
//		System.out.println(Game.getPlayer().getRadius() - Game.getWinSize().width / 2);
		curPos.x = Game.getPlayer().getCurPos().x + Game.getPlayer().getRadius() - Game.getWinSize().width / 2;
		curPos.y = Game.getPlayer().getCurPos().y + Game.getPlayer().getRadius() - Game.getWinSize().height / 2;
//		curPos.x = 0;
//		curPos.y = 0;
	}
	
	public Point getCurPos() {
		return curPos;
	}
}
