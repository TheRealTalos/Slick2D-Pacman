package main;

import java.awt.Rectangle;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Player {
	
	private Animation pacMan, pacAnimUp, pacAnimDown, pacAnimLeft, pacAnimRight, pacAnimStart, pacAnimIdleUp, pacAnimIdleDown, pacAnimIdleLeft, pacAnimIdleRight;
	
	private Rectangle colBox;
	
	private float x = 10*Pacman.getTilesize();
	private float y = 15*Pacman.getTilesize();
	
	private int dir = 4;
	private int lastDir = 4;
	private int nextDir = 4;
	private boolean moving, mUp, mDown, mLeft, mRight = false;
	
	private static final float SPEED = 0.07f;
	
	private static final int UP = 0;
	private static final int DOWN = 1;
	private static final int LEFT = 2;
	private static final int RIGHT = 3;

	public Player(){
		
	}
	
	
	public void init(){
		
		Image[] pacImageStart = {Pacman.getSheet().getSprite(0, 0)};
		Image[] pacImageIdleUp = {Pacman.getSheet().getSprite(5, 0)};
		Image[] pacImageIdleDown = {Pacman.getSheet().getSprite(7, 0)};
		Image[] pacImageIdleLeft = {Pacman.getSheet().getSprite(1, 0)};
		Image[] pacImageIdleRight = {Pacman.getSheet().getSprite(3, 0)};
		
		Image[] pacImageUp = {Pacman.getSheet().getSprite(0, 0), Pacman.getSheet().getSprite(5,0), Pacman.getSheet().getSprite(6, 0), Pacman.getSheet().getSprite(5, 0)};
		Image[] pacImageDown = {Pacman.getSheet().getSprite(0, 0), Pacman.getSheet().getSprite(7,0), Pacman.getSheet().getSprite(8, 0), Pacman.getSheet().getSprite(7, 0)};
		Image[] pacImageLeft = {Pacman.getSheet().getSprite(0, 0), Pacman.getSheet().getSprite(1,0), Pacman.getSheet().getSprite(2, 0), Pacman.getSheet().getSprite(1, 0)};
		Image[] pacImageRight = {Pacman.getSheet().getSprite(0, 0), Pacman.getSheet().getSprite(3,0), Pacman.getSheet().getSprite(4, 0), Pacman.getSheet().getSprite(3, 0)};
		
		pacAnimStart = new Animation(pacImageStart, 50, false);
		pacAnimIdleUp = new Animation(pacImageIdleUp, 50, false);
		pacAnimIdleDown = new Animation(pacImageIdleDown, 50, false);
		pacAnimIdleLeft = new Animation(pacImageIdleLeft, 50, false);
		pacAnimIdleRight = new Animation(pacImageIdleRight, 50, false);
		
		pacAnimUp = new Animation(pacImageUp, 50, false);
		pacAnimDown = new Animation(pacImageDown, 50, false);
		pacAnimLeft = new Animation(pacImageLeft, 50, false);
		pacAnimRight = new Animation(pacImageRight, 50, false);
				
		pacMan = pacAnimStart;
		
		colBox = new Rectangle(Pacman.getTilesize(), Pacman.getTilesize());
		
	}
	
	public void render(){
		
		pacMan.draw(x, y);
		
	}
	
	public void update(GameContainer container, int delta){	
		Input input = container.getInput();
		
		float dx = x;
		float dy = y;
		
		if (input.isKeyPressed(input.KEY_UP)){
			dir = UP;
			nextDir = 4;
			
		}else if (input.isKeyPressed(input.KEY_DOWN)){
			dir = DOWN;
			nextDir = 4;
			
		}else if (input.isKeyPressed(input.KEY_LEFT)){
			dir = LEFT;
			nextDir = 4;
			
		}else if (input.isKeyPressed(input.KEY_RIGHT)){
			dir = RIGHT;
			nextDir = 4;
			
		}
		
		if (dir == UP){
			dy -= delta * SPEED;
			if (!isIntersecting(UP, dx, dy)){
				y -= delta * SPEED;
				pacMan = pacAnimUp;
			}else{
				pacMan = pacAnimIdleUp;
				dir = lastDir;
				nextDir = UP;
			}
		}else if (dir == DOWN){
			dy += delta * SPEED;
			if (!isIntersecting(DOWN, dx, dy)){
				y += delta * SPEED;
				pacMan = pacAnimDown;
			}else{
				pacMan = pacAnimIdleDown;
				dir = lastDir;
				nextDir = DOWN;
			}
		}else if (dir == LEFT){
			dx -= delta * SPEED;
			if (!isIntersecting(LEFT, dx, dy)){
				x -= delta * SPEED;
				pacMan = pacAnimLeft;
			}else{
				pacMan = pacAnimIdleLeft;
				dir = lastDir;
				nextDir = LEFT;
			}
		}else if (dir == RIGHT){
			dx += delta * SPEED;
			if (!isIntersecting(RIGHT, dx, dy)){
				x += delta * SPEED;
				pacMan = pacAnimRight;
			}else{
				pacMan = pacAnimIdleRight;
				dir = lastDir;
				nextDir = RIGHT;
			}
		}
		
		float ndx = x;
		float ndy = y;
		
		if (nextDir == UP){
			ndy -= delta * SPEED;
			if (!isIntersecting(UP, ndx, ndy)){
				dir = UP;
				nextDir = 4;
			}
		}else if (nextDir == DOWN){
			ndy += delta * SPEED;
			if (!isIntersecting(DOWN, ndx, ndy)){
				dir = DOWN;
				nextDir = 4;
			}
		}else if (nextDir == LEFT){
			ndx -= delta * SPEED;
			if (!isIntersecting(LEFT, ndx, ndy)){
				dir = LEFT;
				nextDir = 4;
			}
		}else if (nextDir == RIGHT){
			ndx += delta * SPEED;
			if (!isIntersecting(RIGHT, ndx, ndy)){
				dir = RIGHT;
				nextDir = 4;
			}
		}
		
		if (colBox.getMinX() > Pacman.getWorldsize()) x = 0;
		else if (colBox.getMaxX() < 0) x = Pacman.getWorldsize() - Pacman.getTilesize();
		
		pacMan.update(delta);
		colBox.setLocation((int)x, (int)y);
		
		lastDir = dir;
	}
	
	public boolean isIntersecting(int d, float dx, float dy){
		
		Rectangle tempColBox = new Rectangle((int)dx, (int)dy, Pacman.getTilesize(), Pacman.getTilesize());
		
		for (int k = 0; k < Pacman.walls.length; k++){
			if (tempColBox.intersects(Pacman.walls[k])){
				
				return true;
				
			}
		}
		
		return false;

	}
	
	public Rectangle getPacBox(){
		return colBox;
	}
	
	public double getPacBoxX(){
		return colBox.getMinX();
	}
	
	public double getPacBoxY(){
		return colBox.getMinY();
	}
	
}
