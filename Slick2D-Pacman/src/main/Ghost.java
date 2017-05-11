package main;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

public class Ghost extends Character {
	private int mode;
	public static final int SCATTER = 0;
	public static final int CHASE = 1;
	public static final int LEAVE = 2;
	public static final int SCARED = 3;

	private int colour;
	private static final int RED = 0;;
	private static final int PINK = 1;
	private static final int BLUE = 2;
	private static final int ORANGE = 3;
	
	private static final float SCAREDSPEED = 0.7f;

	private int endScared = 0;
	
	private int scatterPointX = 0;
	private int scatterPointY = 0;

	private int releaseDots = 0;

	public Ghost(int colour) {
		this.colour = colour;
	}

	public void init() {
		initGhosts();
		initAnim();

		colBox = new Rectangle((int) x, (int) y, Pacman.getTilesize(), Pacman.getTilesize());

	}

	public void render() {
		curAnim.draw((float) x, (float) y);
	}

	public void update(long delta) {
		double pacX = Game.getPlayer().getColBox().getMinX();
		double pacY = Game.getPlayer().getColBox().getMinY();

		if (mode != SCARED && SPEED != 1){
			SPEED = 1f;
		}
		
		if (mode != LEAVE && mode != SCARED)
			setMode();
		
		if (mode == SCARED){
			curAnim = anim[4];
		}

		if (dir == NULL && Game.getPlayer().dotsEaten >= releaseDots) {

			double[] dists = new double[4];
			List<Double> sortedDists = new ArrayList<Double>();

			if (mode == LEAVE) {
				List<Integer> p = wouldNotIntersectWalls();
				dir = p.get(0);
				if (y <= 7 * Pacman.getTilesize()) {
					setMode();
				}
			}
			if (mode == SCATTER) {
				dists[0] = scatterPointY * Pacman.getTilesize() - y;
				dists[1] = y - scatterPointY * Pacman.getTilesize();
				dists[2] = scatterPointX * Pacman.getTilesize() - x;
				dists[3] = x - scatterPointX * Pacman.getTilesize();
				
			} else if (mode == SCARED){
				if (Game.getTimer().getTime() < endScared){
					Random r = new Random();
					
					ArrayList<Integer> di = wouldNotIntersectWalls();
					
					if (di.size() > 0){
						dir = r.nextInt(di.size());
					}
				}else {
					endScared = 1000;
					setMode();
				}
				
			} else if (mode == CHASE) {
				int q = 0;
				int n = q*Pacman.getTilesize();
				
				if (colour == RED){
					dists[0] = (pacY - y);
					dists[1] = (y - pacY);
					dists[2] = (pacX - x);
					dists[3] = (x - pacX);
					
				}else if (colour == PINK){
					if (Game.getPlayer().getDir() == UP || Game.getPlayer().getDir() == LEFT) q = -2;
					else if (Game.getPlayer().getDir() == DOWN || Game.getPlayer().getDir() == RIGHT) q = +2;
			
					dists[0] = (pacY - y) + n;
					dists[1] = (y - pacY) + n;
					dists[2] = (pacX - x) + n;
					dists[3] = (x - pacX) + n;
					
				}else if (colour == BLUE){
					if (Game.getPlayer().getDir() == UP || Game.getPlayer().getDir() == LEFT) q = -1;
					else if (Game.getPlayer().getDir() == DOWN || Game.getPlayer().getDir() == RIGHT) q = +1;
			
					dists[0] = (pacY - Game.getRedGhost().getY()) + n;
					dists[1] = (Game.getRedGhost().getY() - pacY) + n;
					dists[2] = (pacX - Game.getRedGhost().getX()) + n;
					dists[3] = (Game.getRedGhost().getX() - pacX) + n;
					
					for (int i = 0; i < dists.length; i++){
						dists[i] *= 2;
					}
					
				}else if (colour == ORANGE){
					dists[0] = (pacY - y);
					dists[1] = (y - pacY);
					dists[2] = (pacX - x);
					dists[3] = (x - pacX);
					ArrayList<Double> k = new ArrayList<Double>();
					for (int i = 0; i < dists.length; i++) {
						k.add(dists[i]);
					}
					Collections.sort(k, Collections.reverseOrder());
					if (Math.abs(k.get(k.size() - 1)) < 8*Pacman.getTilesize()){
						dists[0] = scatterPointY * Pacman.getTilesize() - y;
						dists[1] = y - scatterPointY * Pacman.getTilesize();
						dists[2] = scatterPointX * Pacman.getTilesize() - x;
						dists[3] = x - scatterPointX * Pacman.getTilesize();
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
		
		for (int i = 0; i < 4; i++){
			if (dir == i){
				if (!wouldIntersectWalls(i)){
					if (mode != SCARED){
						curAnim = anim[i];
						if (dir == UP) y -= SPEED; 
						else if (dir == DOWN) y += SPEED; 
						else if (dir == LEFT) x -= SPEED; 
						else if (dir == RIGHT) x += SPEED;
					}else{
						if (dir == UP) y -= SCAREDSPEED; 
						else if (dir == DOWN) y += SCAREDSPEED; 
						else if (dir == LEFT) x -= SCAREDSPEED; 
						else if (dir == RIGHT) x += SCAREDSPEED;
					}
				}else {
					lastDir = setLastDir(dir);
					dir = NULL;
				}
			}
		}

//		ArrayList<Integer> n = wouldNotIntersectWalls();
//		if (n.size()  >= 2){
//			lastDir = setLastDir(dir);
//			dir = NULL;
//		}
		int n = 0;
		for (int i = 0; i < 4; i++)
			if (!wouldIntersectWalls(i))
				n++;
		if (n >= 2) {
			lastDir = setLastDir(dir);
			dir = NULL;
		}

		if (colBox.getMinX() < 0 - Pacman.getTilesize() / 2) {
			if (dir == LEFT)
				x = Pacman.getWorldsize() + Pacman.getTilesize() / 2;
			if (y != 144.31007)
				y = 144.31007f;
		}

		if (colBox.getMaxX() > Pacman.getWorldsize() + Pacman.getTilesize() / 2) {
			if (dir == RIGHT)
				x = 0 - Pacman.getTilesize() / 2;
			if (y != 144.31007)
				y = 144.31007f;
		}

		colBox.setLocation((int) x, (int) y);
		curAnim.update(delta);
	}

	private void initGhosts() {
		int startX = 0;
		int startY = 0;
		
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

		x = startX * Pacman.getTilesize();
		y = startY * Pacman.getTilesize();
		
	}
	
	private void initAnim(){
		anim = new Animation[5];
		
		int n = 5;
		n += colour;

		Image[] ghostImageUp = { Game.getSheet().getSprite(0, n), Game.getSheet().getSprite(1, n) };
		Image[] ghostImageDown = { Game.getSheet().getSprite(2, n), Game.getSheet().getSprite(3, n) };
		Image[] ghostImageLeft = { Game.getSheet().getSprite(4, n), Game.getSheet().getSprite(5, n) };
		Image[] ghostImageRight = { Game.getSheet().getSprite(6, n), Game.getSheet().getSprite(7, n) };
		
		Image[] ghostImageScared = { Game.getSheet().getSprite(0, 9), Game.getSheet().getSprite(1, 9) };

		anim[0] = new Animation(ghostImageUp, 200, false);
		anim[1] = new Animation(ghostImageDown, 200, false);
		anim[2] = new Animation(ghostImageLeft, 200, false);
		anim[3] = new Animation(ghostImageRight, 200, false);
		
		anim[4] = new Animation(ghostImageScared, 200, false);

		curAnim = anim[1];
	}
	
	public void setMode() {
		if (Game.getTimer().getTime() < 7){
			mode = (SCATTER);
		}else if (Game.getTimer().getTime() < 27){
			mode = (CHASE);
		}else if (Game.getTimer().getTime() < 34){
			mode = (SCATTER);
		}else if (Game.getTimer().getTime() < 54){
			mode = (CHASE);
		}else if (Game.getTimer().getTime() < 59){
			mode = (SCATTER);
		}else if (Game.getTimer().getTime() < 79){
			mode = (CHASE);
		}else if (Game.getTimer().getTime() < 84){
			mode = (SCATTER);
		}else{
			mode = (CHASE);
		}
	}

	public void setMode(int m) {
		dir = lastDir;
		setLastDir(dir);
		mode = m;
		if (m == SCARED){
			endScared = (int)(Game.getTimer().getTime() + 10);
		}
	}
	
	public String getMode() {
		if (mode == SCATTER)
			return "SCATTER";
		if (mode == CHASE)
			return "CHASE";
		return "";
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

	private ArrayList<Integer> wouldNotIntersectWalls() {

		ArrayList<Integer> dirs = new ArrayList<Integer>();

		for (int i = 0; i < 4; i++) {
			if (!wouldIntersectWalls(i) && lastDir != i){
				dirs.add(i);
			}
		}

		return dirs;

	}

}
