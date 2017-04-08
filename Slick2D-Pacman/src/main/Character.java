package main;

import java.awt.Rectangle;

public class Character {

	protected static final int UP = 0;
	protected static final int DOWN = 1;
	protected static final int LEFT = 2;
	protected static final int RIGHT = 3;
	protected static final int NULL = 4;

	protected static final float SPEED = 1.1f;

	private double x;
	private double y;

	private Rectangle colBox;

	public Character(Rectangle colBox, double x, double y) {
		this.colBox = colBox;
		this.x = x;
		this.y = y;
	}

	protected boolean canMove(int d, Character c) {
		if (!wouldIntersectWalls(d, c.getRect()))
			return true;
		return false;
	}

	protected double move(int d) {
		if (d == UP)
			return (double) -SPEED;
		else if (d == DOWN)
			return (double) SPEED;
		else if (d == LEFT)
			return (double) -SPEED;
		else if (d == RIGHT)
			return (double) SPEED;
		return 0;
	}

	protected double move(int d, Character c) {
		if (d == UP && canMove(d, c))
			return (double) -SPEED;
		else if (d == DOWN && canMove(d, c))
			return (double) SPEED;
		else if (d == LEFT && canMove(d, c))
			return (double) -SPEED;
		else if (d == RIGHT && canMove(d, c))
			return (double) SPEED;
		return 0;
	}

	protected Rectangle getRect() {
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

	protected boolean wouldIntersectWalls(int d, Rectangle a) {

		double x = a.getMinX();
		double y = a.getMinY();

		if (d == UP) {
			Rectangle r = new Rectangle((int) x, (int) (y - SPEED), Pacman.getTilesize(), Pacman.getTilesize());
			for (int i = 0; i < Game.walls.length; i++) {
				if (r.intersects(Game.walls[i]))
					return true;
			}
		} else if (d == DOWN) {
			Rectangle r = new Rectangle((int) x, (int) (y + SPEED), Pacman.getTilesize(), Pacman.getTilesize());
			for (int i = 0; i < Game.walls.length; i++) {
				if (r.intersects(Game.walls[i]))
					return true;
			}
		} else if (d == LEFT) {
			Rectangle r = new Rectangle((int) (x - SPEED), (int) y, Pacman.getTilesize(), Pacman.getTilesize());
			for (int i = 0; i < Game.walls.length; i++) {
				if (r.intersects(Game.walls[i]))
					return true;
			}
		} else if (d == RIGHT) {
			Rectangle r = new Rectangle((int) (x + SPEED), (int) y, Pacman.getTilesize(), Pacman.getTilesize());
			for (int i = 0; i < Game.walls.length; i++) {
				if (r.intersects(Game.walls[i]))
					return true;
			}
		}
		return false;

	}
}
