package entities;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.Animation;

import main.Main;
import states.Game;

public class Character {

	protected static final int UP = 0;
	protected static final int DOWN = 1;
	protected static final int LEFT = 2;
	protected static final int RIGHT = 3;
	protected static final int NULL = 4;

	protected static final float SPEED = 1f;
	
	protected int dir = NULL;
	protected int nextDir = NULL;
	protected int lastDir = NULL;
	
	protected boolean dead;

	protected float x;
	protected float y;
	
	protected Animation curAnim;
	protected Animation[] anim;
	
	protected Rectangle moveBox;
	protected Rectangle fightBox;

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
	
	protected Rectangle getMoveBox() {
		return moveBox;
	}
	
	protected Rectangle getFightBox() {
		return fightBox;
	}

	protected boolean wouldIntersect(int d, Rectangle a, Rectangle b) {

		double x = a.getMinX();
		double y = a.getMinY();

		if (d == UP) {
			Rectangle r = new Rectangle((int) x, (int) (y - SPEED), Main.getTilesize(), Main.getTilesize());
			if (r.intersects(b))
				return true;
		} else if (d == DOWN) {
			Rectangle r = new Rectangle((int) x, (int) (y + SPEED), Main.getTilesize(), Main.getTilesize());
			if (r.intersects(b))
				return true;
		} else if (d == LEFT) {
			Rectangle r = new Rectangle((int) (x - SPEED), (int) y, Main.getTilesize(), Main.getTilesize());
			if (r.intersects(b))
				return true;
		} else if (d == RIGHT) {
			Rectangle r = new Rectangle((int) (x + SPEED), (int) y, Main.getTilesize(), Main.getTilesize());
			if (r.intersects(b))
				return true;
		}
		return false;
	}

	protected boolean insideBounds(){
		
		if (x > 0 && x < Main.getWorldsize())
			return true;
		
		return false;
	}
	
	protected boolean wouldIntersectWalls(int d) {
		
		double x = moveBox.getMinX();
		double y = moveBox.getMinY();

		Rectangle ru = new Rectangle((int) x, (int) (y - SPEED), Main.getTilesize(), Main.getTilesize());
		Rectangle rd = new Rectangle((int) x, (int) (y + SPEED), Main.getTilesize(), Main.getTilesize());
		Rectangle rl = new Rectangle((int) (x - SPEED), (int) y, Main.getTilesize(), Main.getTilesize());
		Rectangle rr = new Rectangle((int) (x + SPEED), (int) y, Main.getTilesize(), Main.getTilesize());

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
	
	protected ArrayList<Integer> wouldNotIntersectWalls() {

		ArrayList<Integer> dirs = new ArrayList<Integer>();

		for (int i = 0; i < 4; i++) {
			if (!wouldIntersectWalls(i) && lastDir != i) {
				dirs.add(i);
			}
		}

		return dirs;

	}
	
	protected boolean intersectsGhosts(Rectangle a){
		
		for (int i = 0; i < Game.getGhosts().length; i++){
			if (a.intersects(Game.getGhosts()[i].getMoveBox())){
				return true;
			}
		}
		
		return false;
	}
}