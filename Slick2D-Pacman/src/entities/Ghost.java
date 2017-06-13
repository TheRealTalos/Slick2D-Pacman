package entities;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import main.Main;
import states.Game;

public class Ghost extends Character {
	private int mode;
	public static final int SCATTER = 0;
	public static final int CHASE = 1;
	public static final int LEAVE = 2;
	public static final int SCARED = 3;
	public static final int DEAD = 4;

	private int colour;
	private static final int RED = 0;;
	private static final int PINK = 1;
	private static final int BLUE = 2;
	private static final int ORANGE = 3;

	private static final float SCAREDSPEED = 0.7f;
	private static final float DEADSPEED = SPEED;

	private int startX = 0;
	private int startY = 0;
	private int scatterPointX = 0;
	private int scatterPointY = 0;
	private int releaseDots = 0;
	
	private Sound diedSound;
	private Sound deadSound;

	private static int endPaused = 0;
	private int endScared = 0;

	private boolean wasJustChase = true;
	private boolean wasJustInGhosthouse = false;

	private static boolean paused;
	public static int pauseTime;

	public static int deadGhosts;

	public double[] dists = new double[4];

	public Ghost(int colour) {
		this.colour = colour;
	}

	public void init() {
		initGhosts();
		initAnim();
		
		try{
			diedSound = new Sound("/res/sounds/GhostDeath.ogg");
			deadSound = new Sound("/res/sounds/GhostDead.ogg");
		}catch (SlickException e){
			e.printStackTrace();
		}

		paused = false;
		endPaused = 0;
		pauseTime = 1;

		moveBox = new Rectangle((int) x, (int) y, Main.getTilesize(), Main.getTilesize());
		fightBox = new Rectangle((int) x, (int) y, Main.getTilesize() / 2, Main.getTilesize() / 2);
	}

	public void render() {
		curAnim.draw((float) x, (float) y);
	}

	public void update(long delta) {
		double pacX = Game.getPlayer().getMoveBox().getMinX();
		double pacY = Game.getPlayer().getMoveBox().getMinY();

		if (dir == NULL && Game.getPlayer().dotsEaten >= releaseDots) {
			List<Double> sortedDists = new ArrayList<Double>();

			if (mode == LEAVE) {
				List<Integer> p = wouldNotIntersectWalls(this);

				setDists(10 * Main.getTilesize(), 7 * Main.getTilesize());
				if (y <= 7 * Main.getTilesize()) {
					setMode();
				}
			}
			if (mode == SCATTER) {
				setDists(scatterPointX * Main.getTilesize(), scatterPointY * Main.getTilesize());

			} else if (mode == SCARED) {
				if (Game.getTimer().getTime() < endScared) {
					Random r = new Random();
					ArrayList<Integer> di = wouldNotIntersectWalls(this);

					if (di.size() > 0) {
						dir = r.nextInt(di.size());
					}
				} else {
					endScared = 1000;
					reverseDir();
					setMode();
				}

			} else if (mode == DEAD) {
				setDists(startX * Main.getTilesize(), startY * Main.getTilesize());
				if (inGhosthouse()) {
					wasJustInGhosthouse = true;
					reverseDir();
				} else if (wasJustInGhosthouse) {
					wasJustInGhosthouse = false;
					setMode(LEAVE);
				}

			} else if (mode == CHASE) {
				int modifierBase = 0;
				int modifier = modifierBase * Main.getTilesize();

				if (colour == RED) {
					setDists((int) pacX, (int) pacY);

				} else if (colour == PINK) {
					modifierBase = setModifier(2);

					setDists((int) pacX, (int) pacY, modifier);

				} else if (colour == BLUE) {
					modifierBase = setModifier(1);

					setDists((int) pacX, (int) pacY, (int) Game.getRedGhost().getX(), (int) Game.getRedGhost().getY(),
							modifier);

					for (int i = 0; i < dists.length; i++) {
						dists[i] *= 2;
					}

				} else if (colour == ORANGE) {
					setDists((int) pacX, (int) pacY);
					ArrayList<Double> k = new ArrayList<Double>();
					for (int i = 0; i < dists.length; i++) {
						k.add(dists[i]);
					}
					Collections.sort(k, Collections.reverseOrder());
					if (Math.abs(k.get(k.size() - 1)) < 8 * Main.getTilesize()) {
						setDists(scatterPointX * Main.getTilesize(), scatterPointY * Main.getTilesize());
					}
				}
			}

			for (int i = 0; i < dists.length; i++) {
				sortedDists.add(dists[i]);
			}
			Collections.sort(sortedDists, Collections.reverseOrder());

			ArrayList<Integer> dirs = wouldNotIntersectWalls(this);

			for (int i = 0; i < sortedDists.size(); i++) {
				for (int j = 0; j < dirs.size(); j++) {
					if (dists[(dirs.get(j))] == sortedDists.get(i)) {
						dir = dirs.get(j);
						break;
					}
				}
			}
		}

		checkMove();

		// ArrayList<Integer> n = wouldNotIntersectWalls();
		// if (n.size() >= 2 && inMapBounds()){
		// lastDir = setLastDir(dir);
		// dir = NULL;
		// }

		int n = 0;
		for (int i = 0; i < 4; i++)
			if (!wouldIntersectWalls(i, this))
				n++;
		if (n >= 2 && inMapBounds()) {
			lastDir = setLastDir(dir);
			dir = NULL;
		}

		checkTeleport();

		if (mode != LEAVE && mode != SCARED && mode != DEAD)
			setMode();

		if (inGhosthouse()) {
			curAnim = anim[1];
		}

		moveBox.setLocation((int) x, (int) y);
		fightBox.setLocation((int) x + Main.getTilesize() / 4, (int) y + Main.getTilesize() / 4);

		curAnim.update(delta);
	}

	public static int getEndPaused() {
		return endPaused;
	}

	public static void setPaused(boolean value) {
		paused = value;
	}

	private int setModifier(int m) {
		if (Game.getPlayer().getDir() == UP || Game.getPlayer().getDir() == LEFT)
			return -m;
		else
			return m;
	}

	private void checkTeleport() {
		if (moveBox.getMinX() < 0 - Main.getTilesize() / 2) {
			if (dir == LEFT)
				x = Main.getWorldsize() + Main.getTilesize() / 2;
			if (y != 144.31007)
				y = 144.31007f;
		}

		if (moveBox.getMaxX() > Main.getWorldsize() + Main.getTilesize() / 2) {
			if (dir == RIGHT)
				x = 0 - Main.getTilesize() / 2;
			if (y != 144.31007)
				y = 144.31007f;
		}
	}

	private void checkMove() {
		for (int i = 0; i < 4; i++) {
			if (dir == i) {
				if (!wouldIntersectWalls(i, this)) {
					if (mode == SCARED) {
						curAnim = anim[8];
						if (Game.getTimer().getTime() >= (endScared - 3)) {
							curAnim = anim[9];
						}
						move(SCAREDSPEED);

					} else if (mode == DEAD) {
						if (!deadSound.playing()) deadSound.play();
						curAnim = anim[i + DEAD];
						move(DEADSPEED);

					} else {
						curAnim = anim[i];
						move(SPEED);
					}
				} else {
					lastDir = setLastDir(dir);
					dir = NULL;
				}
			}
		}
	}

	public static boolean getPaused() {
		return paused;
	}

	private void move(float speed) {
		if (dir == UP)
			y -= speed;
		else if (dir == DOWN)
			y += speed;
		else if (dir == LEFT)
			x -= speed;
		else if (dir == RIGHT)
			x += speed;
	}

	private boolean inGhosthouse() {
		return inBounds(9, 8, 12, 10);
	}

	private void initGhosts() {
		if (colour == RED) {
			setGhost(SCATTER, 10, 7, 17, 1, 0);
		} else if (colour == PINK) {
			setGhost(SCATTER, 10, 9, 3, 1, 0);
		} else if (colour == BLUE) {
			setGhost(LEAVE, 9, 9, 1, 19, 20);
		} else if (colour == ORANGE) {
			setGhost(LEAVE, 11, 9, 20, 19, 50);
		}

		x = startX * Main.getTilesize();
		y = startY * Main.getTilesize();

	}

	private void setGhost(int mode, int startX, int startY, int scatterX, int scatterY, int release) {
		setMode(mode);
		this.startX = startX;
		this.startY = startY;
		scatterPointX = scatterX;
		scatterPointY = scatterY;
		releaseDots = release;
	}

	private void initAnim() {
		anim = new Animation[10];
		List<Image[]> imageArrays = new ArrayList<Image[]>();

		int n = 5;
		n += colour;

		for (int i = 0; i < 8; i += 2) {
			imageArrays.add(new Image[] { Game.getSheet().getSprite(i, n), Game.getSheet().getSprite(i + 1, n) });
		}

		for (int i = 0; i < 4; i++) {
			imageArrays.add(new Image[] { Game.getSheet().getSprite(i, 10) });
		}

		imageArrays.add(new Image[] { Game.getSheet().getSprite(0, 9), Game.getSheet().getSprite(1, 9) });

		imageArrays.add(new Image[] { Game.getSheet().getSprite(0, 9), Game.getSheet().getSprite(3, 9) });

		for (int i = 0; i < imageArrays.size(); i++) {
			anim[i] = new Animation(imageArrays.get(i), 200, false);
		}

		curAnim = anim[1];
	}

	public void setMode() {
		int t = 2;
		for (int i = 0; i < 6; i++) {
			if (i % 2 == 0)
				t += 5;
			else
				t += 20;
			if (Game.getTimer().getTime() < t) {
				if (i % 2 == 0) {
					if (wasJustChase && i != 0) {
						reverseDir();
					}
					wasJustChase = false;
					setMode(SCATTER);
				} else {
					if (!wasJustChase) {
						reverseDir();
					}
					wasJustChase = true;
					setMode(CHASE);
				}
				break;
			}
		}
	}

	public void die() {
		if (!diedSound.playing()) diedSound.play();
		paused = true;
		endPaused = (int) (Game.getTimer().getTime() + pauseTime);
		setMode(DEAD);
	}

	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}

	private void setDists(int xPoint, int yPoint, int xPoint2, int yPoint2, int modifier) {
		dists[0] = yPoint - yPoint2 + modifier;
		dists[1] = yPoint2 - yPoint + modifier;
		dists[2] = xPoint - xPoint2 + modifier;
		dists[3] = xPoint2 - xPoint + modifier;
	}

	private void setDists(int xPoint, int yPoint, int modifier) {
		setDists(xPoint, yPoint, (int) x, (int) y, modifier);
	}

	private void setDists(int xPoint, int yPoint) {
		setDists(xPoint, yPoint, 0);
	}

	public void setMode(int m) {
		mode = m;
		if (m == SCARED) {
			endScared = (int) (Game.getTimer().getTime() + 10);
		}
		if (m == DEAD) {
			canGhosthouse = true;
		} else {
			canGhosthouse = false;
		}
	}

	public void scare() {
		if (mode != DEAD) {
			deadGhosts = 0;
			reverseDir();
			setMode(SCARED);
		}
	}

	public void reverseDir() {
		dir = lastDir;
		setLastDir(dir);
	}

	public int getMode() {
		return mode;
	}

	private int setLastDir(int dir) {
		if (dir == UP)
			return DOWN;
		if (dir == DOWN)
			return UP;
		if (dir == LEFT)
			return RIGHT;
		if (dir == RIGHT)
			return LEFT;

		return NULL;

	}
}
