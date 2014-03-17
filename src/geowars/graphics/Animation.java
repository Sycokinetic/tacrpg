package geowars.graphics;

import java.awt.image.BufferedImage;

public class Animation {
	public static enum AnimKey {
		MAIN,
		DOWN,
		LEFT,
		RIGHT,
		UP,
		JUMP_DOWN,
		JUMP_LEFT,
		JUMP_RIGHT,
		JUMP_UP
	};
	
	private AnimKey key;
	private BufferedImage[] frameList;
	private int curFrame;
	
	public Animation() {
		this.key = null;
		this.curFrame = 0;
		this.frameList = null;
	}
	
	public Animation(String fn, AnimKey k) {
		this.key = k;
		this.frameList = Library.getFrameList(fn, k);
		this.curFrame = 0;
	}
	
	public Animation(AnimKey k, BufferedImage[] fl) {
		this.key = k;
		this.frameList = fl;
		this.curFrame = 0;
	}
	
	public void cycle() {
		if (this.curFrame < this.frameList.length-1) {
			this.curFrame++;
		}
		else {
			this.curFrame = 0;
		}
	}
	
	public BufferedImage getCurFrame() {
		return this.frameList[this.curFrame];
	}
	
	public int getCurFrameVal() {
		return this.curFrame;
	}
	
	public AnimKey getKey() {
		return this.key;
	}
}
