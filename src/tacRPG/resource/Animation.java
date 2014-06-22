package tacRPG.resource;


import java.awt.image.BufferedImage;

public class Animation {	
	private String key;
	private BufferedImage[] frameList;
	private int curFrame;
	private boolean started;
	
	public Animation() {
		this.key = null;
		this.curFrame = 0;
		this.frameList = null;
		this.started = false;
	}
	
	public Animation(String fn, String k) {
		this.key = k;
		this.frameList = SpriteLibrary.getFrameList(fn, k);
		this.curFrame = 0;
	}
	
	public Animation(String k, BufferedImage[] fl) {
		this.key = k;
		this.frameList = fl;
		this.curFrame = 0;
	}
	
	public void cycle() {
		if (this.started) {
			if (this.curFrame < this.frameList.length-1) {
				this.curFrame++;
			}
			else {
				this.curFrame = 0;
			}
		}
		else {
			this.started = true;
		}
	}
	
	public BufferedImage getCurFrame() {
		return this.frameList[this.curFrame];
	}
	
	public int getCurFrameVal() {
		return this.curFrame;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public void reset() {
		this.curFrame = 0;
		this.started = false;
	}
	
	public void setCurFrameVal(int n) {
		this.curFrame = n;
	}
}
