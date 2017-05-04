package main;

import java.awt.Rectangle;

import org.newdawn.slick.Animation;

public class Character {

	protected static final int UP = 0;
	protected static final int DOWN = 1;
	protected static final int LEFT = 2;
	protected static final int RIGHT = 3;
	protected static final int NULL = 4;

	protected float SPEED = 1f;
	
	protected int dir = NULL;
	protected int nextDir = NULL;
	protected int lastDir = NULL;
	
	protected boolean dead;

	protected float x;
	protected float y;
	
	protected Animation curAnim;
	protected Animation[] anim;
	
	protected Rectangle colBox;

	public Character() {
	}
	
	protected float getX(){
		return x;
	}
	
	protected float getY(){
		return y;
	}

	protected int getDir(){
		return dir;
	}
	
	protected Rectangle getColBox() {
		return colBox;
	}

	protected boolean wouldIntersect(int d, Rectangle a, Rectangle b) {

		double x = a.getMinX();
		double y = a.getMinY();

		if (d == UP) {
			Rectangle r = new Rectangle((int) x, (int) (y - SPEED), Pacman.getTilesize(), Pacman.getTilesize());
			if (r.intersects(b))
				return true;
		} else if (d == DOWN) {
			Rectangle r = new Rectangle((int) x, (int) (y + SPEED), Pacman.getTilesize(), Pacman.getTilesize());
			if (r.intersects(b))
				return true;
		} else if (d == LEFT) {
			Rectangle r = new Rectangle((int) (x - SPEED), (int) y, Pacman.getTilesize(), Pacman.getTilesize());
			if (r.intersects(b))
				return true;
		} else if (d == RIGHT) {
			Rectangle r = new Rectangle((int) (x + SPEED), (int) y, Pacman.getTilesize(), Pacman.getTilesize());
			if (r.intersects(b))
				return true;
		}
		return false;
	}

	protected boolean wouldIntersectWalls(int d) {
		
		double x = colBox.getMinX();
		double y = colBox.getMinY();

		Rectangle ru = new Rectangle((int) x, (int) (y - SPEED), Pacman.getTilesize(), Pacman.getTilesize());
		Rectangle rd = new Rectangle((int) x, (int) (y + SPEED), Pacman.getTilesize(), Pacman.getTilesize());
		Rectangle rl = new Rectangle((int) (x - SPEED), (int) y, Pacman.getTilesize(), Pacman.getTilesize());
		Rectangle rr = new Rectangle((int) (x + SPEED), (int) y, Pacman.getTilesize(), Pacman.getTilesize());

		for (int i = 0; i < Game.walls.length; i++) {
			if (ru.intersects(Game.walls[i]) && d == UP)
				return true;
			if (rd.intersects(Game.walls[i]) && d == DOWN)
				return true;
			if (rl.intersects(Game.walls[i]) && d == LEFT)
				return true;
			if (rr.intersects(Game.walls[i]) && d == RIGHT)
				return true;
		}
		
		for (int i = 0; i < Game.semiWalls.length; i++){
			if (!dead && rd.intersects(Game.semiWalls[i]) && d == DOWN)
				return true;
		}
		
		return false;

	}
}
