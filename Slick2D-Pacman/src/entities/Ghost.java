package entities;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

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
	private static final float DEADSPEED = 1.3f;

	private int endScared = 0;

	private int startX = 0;
	private int startY = 0;
	
	private int scatterPointX = 0;
	private int scatterPointY = 0;

	private int releaseDots = 0;
	
	public double[] dists = new double[4];

	public Ghost(int colour) {
		this.colour = colour;
	}

	public void init() {
		initGhosts();
		initAnim();
		
		moveBox = new Rectangle((int) x, (int) y, Main.getTilesize(), Main.getTilesize());
		fightBox = new Rectangle((int) x, (int) y, Main.getTilesize()/2, Main.getTilesize()/2);
	}

	public void render() {
		curAnim.draw((float) x, (float) y);
	}

	public void update(long delta) {
		double pacX = Game.getPlayer().getMoveBox().getMinX();
		double pacY = Game.getPlayer().getMoveBox().getMinY();

//		System.out.println();
		
		if (mode != SCARED && mode != DEAD && SPEED != 1) {
			SPEED = 1f;
		}

		if (mode != LEAVE && mode != SCARED && mode != DEAD)
			setMode();

		if (mode == SCARED) {
			curAnim = anim[8];
			if (Game.getTimer().getTime() >= (endScared - 3)){
				curAnim = anim[9];
			}
		}
		
		if (mode == LEAVE){
			curAnim = anim[1];
		}

		if (dir == NULL && Game.getPlayer().dotsEaten >= releaseDots) {
			List<Double> sortedDists = new ArrayList<Double>();

			if (mode == LEAVE) {
				List<Integer> p = wouldNotIntersectWalls();
				dir = p.get(0);
				if (y <= 7 * Main.getTilesize()) {
					setMode();
				}
			}
			if (mode == SCATTER) {
				setDists(scatterPointX * Main.getTilesize(), scatterPointY * Main.getTilesize());

			} else if (mode == SCARED) {
				if (Game.getTimer().getTime() < endScared) {
					Random r = new Random();

					ArrayList<Integer> di = wouldNotIntersectWalls();

					if (di.size() > 0) {
						dir = r.nextInt(di.size());
					}
				} else {
					endScared = 1000;
					setMode();
				}

			} else if (mode == DEAD) {
				setDists(startX * Main.getTilesize(), startY * Main.getTilesize());
				if (inBox()){
					setMode();
					System.out.println("inbox");
				}

			} else if (mode == CHASE) {
				int q = 0;
				int n = q * Main.getTilesize();

				if (colour == RED) {
					setDists((int)pacX, (int)pacY);

				} else if (colour == PINK) {
					if (Game.getPlayer().getDir() == UP || Game.getPlayer().getDir() == LEFT)
						q = -2;
					else if (Game.getPlayer().getDir() == DOWN || Game.getPlayer().getDir() == RIGHT)
						q = +2;

					setDists((int)pacX, (int)pacY, n);

				} else if (colour == BLUE) {
					if (Game.getPlayer().getDir() == UP || Game.getPlayer().getDir() == LEFT)
						q = -1;
					else if (Game.getPlayer().getDir() == DOWN || Game.getPlayer().getDir() == RIGHT)
						q = +1;

					setDists((int)pacX, (int)pacY, (int)Game.getRedGhost().getX(), (int)Game.getRedGhost().getY());

					for (int i = 0; i < dists.length; i++) {
						dists[i] *= 2;
					}

				} else if (colour == ORANGE) {
					setDists((int)pacX, (int)pacY);
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

			ArrayList<Integer> dirs = wouldNotIntersectWalls();

			for (int i = 0; i < sortedDists.size(); i++) {
				for (int j = 0; j < dirs.size(); j++) {
					if (dists[(dirs.get(j))] == sortedDists.get(i)) {
						dir = dirs.get(j);
						break;
					}
				}
			}
		}

		for (int i = 0; i < 4; i++) {
			if (dir == i) {
				if (!wouldIntersectWalls(i)) {
					if (mode == SCARED) {
						move(SCAREDSPEED);
						
					}else if (mode == DEAD){
						curAnim = anim[i+DEAD];
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

//		 ArrayList<Integer> n = wouldNotIntersectWalls();
//		 if (n.size() >= 2){
//			 lastDir = setLastDir(dir);
//			 dir = NULL;
//		 }
		
		int n = 0;
		for (int i = 0; i < 4; i++)
			if (!wouldIntersectWalls(i))
				n++;
		if (n >= 2 && insideBounds()) {
			lastDir = setLastDir(dir);
			dir = NULL;
		}

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

		moveBox.setLocation((int) x, (int) y);
		fightBox.setLocation((int) x + Main.getTilesize()/4, (int) y + Main.getTilesize()/4);
		
		curAnim.update(delta);
	}
	
	private void move(float speed){
		if (dir == UP)
			y -= speed;
		else if (dir == DOWN)
			y += speed;
		else if (dir == LEFT)
			x -= speed;
		else if (dir == RIGHT)
			x += speed;
	}
	
	private boolean inBox(){
		if (x > 9*Main.getTilesize() && x < 12*Main.getTilesize() && y > 10*Main.getTilesize() && y < 8*Main.getTilesize())
			return true;
		
		return false;
	}
	
	private void initGhosts() {
		if (colour == RED) {
			setMode(SCATTER);
			startX = 10;
			startY = 7;
			scatterPointX = 17;
			scatterPointY = 1;
		} else if (colour == PINK) {
			setMode(SCATTER);
			startX = 10;
			startY = 9;
			scatterPointX = 3;
			scatterPointY = 1;
		} else if (colour == BLUE) {
			setMode(LEAVE);
			startX = 9;
			startY = 9;
			scatterPointX = 1;
			scatterPointY = 19;
			releaseDots = 20;
		} else if (colour == ORANGE) {
			setMode(LEAVE);
			startX = 11;
			startY = 9;
			scatterPointX = 20;
			scatterPointY = 19;
			releaseDots = 50;
		}

		x = startX * Main.getTilesize();
		y = startY * Main.getTilesize();

	}

	private void initAnim() {
		anim = new Animation[10];
		List<Image[]> imageArrays = new ArrayList<Image[]>();
		
		int n = 5;
		n += colour;
		
		for (int i = 0; i < 8; i+=2){
			imageArrays.add(new Image[] { Game.getSheet().getSprite(i, n), Game.getSheet().getSprite(i+1, n) } );
		}

		
		for (int i = 0; i < 4; i++){
			imageArrays.add(new Image[] { Game.getSheet().getSprite(i, 10) }) ;
		}	

		imageArrays.add(new Image[] { Game.getSheet().getSprite(0, 9), Game.getSheet().getSprite(1, 9) }) ;
		
		imageArrays.add(new Image[] { Game.getSheet().getSprite(0, 9), Game.getSheet().getSprite(3, 9) }) ;
		
		for (int i = 0; i < imageArrays.size(); i++){
			anim[i] = new Animation(imageArrays.get(i), 200, false);
		}

		curAnim = anim[1];
	}

	public void setMode() {
		if (Game.getTimer().getTime() < 7) {
			mode = (SCATTER);
		} else if (Game.getTimer().getTime() < 27) {
			mode = (CHASE);
		} else if (Game.getTimer().getTime() < 34) {
			mode = (SCATTER);
		} else if (Game.getTimer().getTime() < 54) {
			mode = (CHASE);
		} else if (Game.getTimer().getTime() < 59) {
			mode = (SCATTER);
		} else if (Game.getTimer().getTime() < 79) {
			mode = (CHASE);
		} else if (Game.getTimer().getTime() < 84) {
			mode = (SCATTER);
		} else {
			mode = (CHASE);
		}
	}

	public void die() {
		setMode(DEAD);
	}

	private void setDists(int xPoint, int yPoint, int xPoint2, int yPoint2){
		dists[0] = yPoint - yPoint2;
		dists[1] = yPoint2 - yPoint;
		dists[2] = xPoint - xPoint2;
		dists[3] = xPoint2 - xPoint;
	}
	
	private void setDists(int xPoint, int yPoint){
		setDists(xPoint, yPoint, (int)x, (int)y);
	}
	
	private void setDists(int xPoint, int yPoint, int modifier){
		setDists(xPoint + modifier, yPoint + modifier);
	}

	public void setMode(int m) {
		dir = lastDir;
		setLastDir(dir);
		mode = m;
		if (m == SCARED) {
			endScared = (int) (Game.getTimer().getTime() + 10);
		}
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
