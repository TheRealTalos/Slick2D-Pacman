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
	
	public boolean dead;

	protected float x;
	protected float y;
	
	protected Animation curAnim;
	protected Animation[] anim;
	
	protected Rectangle moveBox;
	protected Rectangle fightBox;
	
	protected boolean canGhosthouse = false;

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

	protected boolean inMapBounds(){
		return inBounds(1, 0, 19, 20);
	}
	
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	public boolean isDead(){
		return dead;
	}
	
	protected boolean inBounds(int x1, int y1, int x2, int y2){
		if (x >= x1*Main.getTilesize() && y >= y1*Main.getTilesize() && x <= x2*Main.getTilesize() && y <= y2*Main.getTilesize())
			return true;
		
		return false;
	}
	
	protected boolean wouldIntersectWalls(int d, Character c) {
		double x = moveBox.getMinX();
		double y = moveBox.getMinY();
		
		Rectangle[] r = {new Rectangle((int) x, (int) (y - SPEED), Main.getTilesize(), Main.getTilesize()), 
				new Rectangle((int) x, (int) (y + SPEED), Main.getTilesize(), Main.getTilesize()),
				new Rectangle((int) (x - SPEED), (int) y, Main.getTilesize(), Main.getTilesize()),
				new Rectangle((int) (x + SPEED), (int) y, Main.getTilesize(), Main.getTilesize())
		};

		boolean ghost = false;
		for(int i = 0; i < 4; i++){
			if (c == Game.getGhosts()[i]){
				ghost = true;
				break;
			}
		}
		
		for (int i = 0; i < Game.walls.length; i++) {
			for (int j = 0; j < r.length; j++){
				if (r[j].intersects(Game.walls[i]) && d == j){
					return true;
				}
			}
		}
		
		if (!ghost || ghost && !c.canGhosthouse){
			for (int i = 0; i < Game.semiWalls.length; i++){
				if (!dead && r[1].intersects(Game.semiWalls[i]) && d == DOWN)
					return true;
			}
		}
		
		return false;

	}
	
	protected ArrayList<Integer> wouldNotIntersectWalls(Character c) {

		ArrayList<Integer> dirs = new ArrayList<Integer>();

		for (int i = 0; i < 4; i++) {
			if (!wouldIntersectWalls(i, c) && lastDir != i) {
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