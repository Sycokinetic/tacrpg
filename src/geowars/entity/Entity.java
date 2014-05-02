package geowars.entity;

import geowars.Game;
import geowars.Constant;
import geowars.resource.Animation;
import geowars.resource.SpriteLibrary;

import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;

public abstract class Entity {
	// == Protected attributes ==
	protected String SHEETFILE;
	protected Dimension TILESIZE;
	protected int SHEETWIDTH;
	protected HashMap<Integer, String> KEYVALS;
	protected HashMap<String, Animation> ANIMLIST;

	protected String curKey;
	protected Point curPos;
	protected double curAngle;

	protected String lifeStatus;

	protected double pixelErr;
	protected String vectorQuad;

	protected int moveRate; // pixels per second
	protected double[] moveErr;

	protected String collideAction;
	protected boolean[] canMove;

	// == Constructors ==
	/**
	 * Class constructor.
	 * <p>
	 * Initializes attributes to their default values.
	 */
	public Entity() {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.SHEETWIDTH = 0;

		this.KEYVALS = new HashMap<Integer, String>();
		this.ANIMLIST = new HashMap<String, Animation>();

		this.curKey = null;
		this.curPos = new Point(0, 0);

		this.lifeStatus = Constant.ALIVE;

		this.pixelErr = 0;
		this.vectorQuad = null;
		this.moveRate = 0;
		this.moveErr = new double[4];

		this.canMove = new boolean[4];
		this.setCanMove(true);

		this.loadData();
	}

	/**
	 * Class Constructor
	 * <p>
	 * Initializes entity with position (x, y)
	 * 
	 * @param x
	 *            x-coordinate of entity position on map
	 * @param y
	 *            y-coordinate of entity position on map
	 */
	public Entity(int x, int y) {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.SHEETWIDTH = 0;

		this.KEYVALS = new HashMap<Integer, String>();
		this.ANIMLIST = new HashMap<String, Animation>();

		this.curKey = null;
		this.curPos = new Point(x, y);

		this.lifeStatus = Constant.ALIVE;

		this.pixelErr = 0;
		this.vectorQuad = null;
		this.moveRate = 0;
		this.moveErr = new double[4];

		this.canMove = new boolean[4];
		this.setCanMove(true);

		this.loadData();
	}

	/**
	 * Class constructor.
	 * <p>
	 * Initializes entity with position p
	 * 
	 * @param p
	 *            Point representing entity position on map
	 */
	public Entity(Point p) {
		this.SHEETFILE = null;
		this.TILESIZE = null;
		this.SHEETWIDTH = 0;

		this.KEYVALS = new HashMap<Integer, String>();
		this.ANIMLIST = new HashMap<String, Animation>();

		this.curKey = null;
		this.curPos = new Point(0, 0);

		this.lifeStatus = Constant.ALIVE;

		this.pixelErr = 0;
		this.vectorQuad = null;
		this.moveRate = 0;
		this.moveErr = new double[4];

		this.canMove = new boolean[4];
		this.setCanMove(true);

		this.loadData();
	}

	// == Protected Methods
	/**
	 * Stores entity's key-animation pairs in ANIMLIST
	 */
	protected void loadAnims() {
		for (Integer i : this.KEYVALS.keySet()) {
			String k = this.KEYVALS.get(i);
			this.ANIMLIST.put(k, new Animation(this.SHEETFILE, k));
		}
	}

	/**
	 * Tells SpriteLibrary to load entity's animations with the corresponding
	 * keys
	 */
	protected void loadSheet() {
		if (!SpriteLibrary.checkSheet(this.SHEETFILE)) {
			SpriteLibrary.loadSheet(this.SHEETFILE, this.TILESIZE, this.SHEETWIDTH, this.KEYVALS);
		}
	}

	protected String getVector() {
		return this.vectorQuad;
	}

	protected void moveDown() {
		// setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.DOWN));
		if (canMove[0]) {
			double n = (double) this.moveRate * Game.getPrevTickLength() / 1_000_000_000;

			this.moveErr[0] += n;
			for (; this.moveErr[0] > 1; this.moveErr[0]--) {
				this.curPos.y += 1;
			}

			this.ANIMLIST.get(this.curKey).cycle();
		}
	}

	protected void moveLeft() {
		// setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.LEFT));
		if (canMove[1]) {
			double n = (double) this.moveRate * Game.getPrevTickLength() / 1_000_000_000;

			this.moveErr[1] += n;
			for (; this.moveErr[1] > 1; this.moveErr[1]--) {
				this.curPos.x -= 1;
			}

			this.ANIMLIST.get(this.curKey).cycle();
		}
	}

	protected void moveRight() {
		// setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.RIGHT));
		if (canMove[2]) {
			double n = (double) this.moveRate * Game.getPrevTickLength() / 1_000_000_000;

			this.moveErr[2] += n;
			for (; this.moveErr[2] > 1; this.moveErr[2]--) {
				this.curPos.x += 1;
			}

			this.ANIMLIST.get(this.curKey).cycle();
		}
	}

	protected void moveUp() {
		// setCurAnim(Constant.concatKeys(EventKey.MOVE, ModKey.UP));
		if (canMove[3]) {
			double n = (double) this.moveRate * Game.getPrevTickLength() / 1_000_000_000;

			this.moveErr[3] += n;
			if (this.moveErr[3] >= 1) {
				for (; this.moveErr[3] > 1; this.moveErr[3]--) {
					this.curPos.y -= 1;
				}
			}

			this.ANIMLIST.get(this.curKey).cycle();
		}
	}

	protected void moveTowardPoint(Point p) {
		double dx = p.x - this.getCenter().x;
		double dy = this.getCenter().y - p.y;

		if (dx == 0 && dy == 0) {
			this.pixelErr = 0;
			// If this is at player, do nothing
		} else if (dx == 0 && dy != 0) {
			this.pixelErr = 0;
			// If this is below player, move up
			if (dy > 0) {
				this.moveUp();
			} else {
				this.moveDown();
			}
		} else if (dx != 0 && dy == 0) {
			this.pixelErr = 0;
			// If this is right of player, move left
			if (dx > 0) {
				this.moveRight();
			} else {
				this.moveLeft();
			}
		} else {
			double m = 0;

			if (Math.abs(dx) >= Math.abs(dy)) {
				m = dy / dx;
				this.pixelErr += m;

				// Octant 1 / 5
				if (m > 0) {
					if (this.vectorQuad != Constant.UP) {
						this.vectorQuad = Constant.UP;
						this.pixelErr = m;
					}

					if (dx > 0) {
						this.moveRight();
					} else {
						this.moveLeft();
					}

					if (dy > 0) {
						if (Math.floor(this.pixelErr) > 0) {
							this.moveUp();
						}
					} else {
						if (Math.floor(this.pixelErr) > 0) {
							this.moveDown();
						}
					}

					if (this.pixelErr >= 1) {
						this.pixelErr -= 1;
					}
				}
				// Octant 4 / 8
				else {
					if (this.vectorQuad != Constant.DOWN) {
						this.vectorQuad = Constant.DOWN;
						this.pixelErr = m;
					}

					if (dx > 0) {
						this.moveRight();
					} else {
						this.moveLeft();
					}

					if (dy > 0) {
						if (Math.ceil(this.pixelErr) < 0) {
							this.moveUp();
						}
					} else {
						if (Math.ceil(this.pixelErr) < 0) {
							this.moveDown();
						}
					}

					if (this.pixelErr <= -1) {
						this.pixelErr += 1;
					}
				}
			} else {
				m = dx / dy;
				this.pixelErr += m;

				// Octant 2 / 6
				if (m > 0) {
					if (this.vectorQuad != Constant.RIGHT) {
						this.vectorQuad = Constant.RIGHT;
						this.pixelErr = m;
					}

					if (dy > 0) {
						this.moveUp();
					} else {
						this.moveDown();
					}

					if (dx > 0) {
						if (Math.floor(this.pixelErr) > 0) {
							this.moveRight();
						}
					} else {
						if (Math.floor(this.pixelErr) > 0) {
							this.moveLeft();
						}
					}

					if (this.pixelErr >= 1) {
						this.pixelErr -= 1;
					}
				}
				// Octant 3, 7
				else {
					if (this.vectorQuad != Constant.LEFT) {
						this.vectorQuad = Constant.LEFT;
						this.pixelErr = m;
					}

					if (dx > 0) {
						this.moveDown();
					} else {
						this.moveUp();
					}

					if (dy > 0) {
						if (Math.ceil(this.pixelErr) < 0) {
							this.moveLeft();
						}
					} else {
						if (Math.ceil(this.pixelErr) < 0) {
							this.moveRight();
						}
					}

					if (this.pixelErr <= -1) {
						this.pixelErr += 1;
					}
				}
			}
		}
	}

	// == Public methods
	/**
	 * @return the alive/dead status of entity
	 */
	public String getAlive() {
		return this.lifeStatus;
	}

	/**
	 * @return Point representing the center of entity's image
	 */
	public Point getCenter() {
		Point p = new Point(this.curPos.x, this.curPos.y);
		p.x += this.TILESIZE.width / 2;
		p.y += this.TILESIZE.height / 2;
		return p;
	}

	/**
	 * @return the key from ANIMLIST representing the current animation
	 */

	public String getCurAnim() {
		return this.curKey;
	}

	/**
	 * @return the image of the current frame of the current animation
	 */
	public BufferedImage getCurFrame() {
		return this.ANIMLIST.get(this.curKey).getCurFrame();
	}

	/**
	 * @return the index of the current frame of the current animation
	 */
	public int getCurFrameVal() {
		return this.ANIMLIST.get(this.curKey).getCurFrameVal();
	}

	/**
	 * @return the current position of the top left corner of the entity's image
	 */
	public Point getCurPos() {
		return this.curPos;
	}

	/**
	 * @return the distance from the center of the entity's image to the middle
	 *         of the further edge.
	 */
	public int getRadius() {
		return Math.max(this.TILESIZE.width / 2, this.TILESIZE.height / 2);
	}

	/**
	 * @return the set of keys in ANIMLIST
	 */
	public String[] getSheetKeys() {
		return this.ANIMLIST.keySet().toArray(new String[0]);
	}

	/**
	 * @return the filename representing the entity's spritesheet
	 */
	public String getSheetName() {
		return SHEETFILE;
	}

	/**
	 * @return the number of sub-images that make up a row in the spritesheet
	 */
	public int getSheetWidth() {
		return this.SHEETWIDTH;
	}

	/**
	 * @return the dimensions of a sub-image in the spritesheet
	 */
	public Dimension getTileSize() {
		return TILESIZE;
	}

	/**
	 * Set the life-status of the entity
	 * 
	 * @param l
	 *            new LifeStatus to store
	 */
	public void setAlive(String l) {
		this.lifeStatus = l;
	}

	/**
	 * Set the current animation. Resets all other animations.
	 * 
	 * @param k
	 *            string from KEYVALS representing the new animation
	 */
	public void setCurAnim(String k) {
		if (!this.curKey.equals(k)) {
			for (String i : this.ANIMLIST.keySet()) {
				this.ANIMLIST.get(i).reset();
			}

			this.curKey = k;
		}
	}

	/**
	 * Set the current position of the entity
	 * 
	 * @param p
	 *            the point representing the new location of the top left corner
	 *            of the entity's image
	 */
	public void setCurPos(Point p) {
		this.curPos = p;
	}

	public boolean[] getCanMove() {
		return this.canMove;
	}

	public String getCollideAction() {
		return this.collideAction;
	}

	public void setCanMove(int i, boolean b) {
		this.canMove[i] = b;
	}

	public void setCanMove(boolean b) {
		this.canMove[0] = b;
		this.canMove[1] = b;
		this.canMove[2] = b;
		this.canMove[3] = b;
	}

	// == Abstract methods
	/**
	 * If defined, sets the hard-coded attributes that link an entity subclass
	 * to the file system and loads those files.
	 * <p>
	 * Needs to call loadSheet() and loadAnims() and should be called in the
	 * constructor
	 */
	protected abstract void loadData();

	/**
	 * If defined, runs one cycle of the entity's processing
	 */
	public abstract void update();
}
