package main;

import java.awt.Rectangle;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.TrueTypeFont;
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
	
	private String nextDirec = "";
	private String lastDirec = "";

	public Player(){
		
	}
	
	
	public void init(){
		
		Image[] pacImageStart = {Game.getSheet().getSprite(0, 0)};
		Image[] pacImageIdleUp = {Game.getSheet().getSprite(5, 0)};
		Image[] pacImageIdleDown = {Game.getSheet().getSprite(7, 0)};
		Image[] pacImageIdleLeft = {Game.getSheet().getSprite(1, 0)};
		Image[] pacImageIdleRight = {Game.getSheet().getSprite(3, 0)};
		
		Image[] pacImageUp = {Game.getSheet().getSprite(0, 0), Game.getSheet().getSprite(5,0), Game.getSheet().getSprite(6, 0), Game.getSheet().getSprite(5, 0)};
		Image[] pacImageDown = {Game.getSheet().getSprite(0, 0), Game.getSheet().getSprite(7,0), Game.getSheet().getSprite(8, 0), Game.getSheet().getSprite(7, 0)};
		Image[] pacImageLeft = {Game.getSheet().getSprite(0, 0), Game.getSheet().getSprite(1,0), Game.getSheet().getSprite(2, 0), Game.getSheet().getSprite(1, 0)};
		Image[] pacImageRight = {Game.getSheet().getSprite(0, 0), Game.getSheet().getSprite(3,0), Game.getSheet().getSprite(4, 0), Game.getSheet().getSprite(3, 0)};
		
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
	
	public void render(Graphics g){
		
		pacMan.draw(x, y);
		g.drawString(nextDirec, 200, 200);
		
	}
	
	public void update(GameContainer container, int delta){	
		Input input = container.getInput();
		
		float dx = x;
		float dy = y;
		
		if (input.isMousePressed(0)){
			System.out.println("x: " + input.getMouseX());
			System.out.println("y: " + input.getMouseY());
		}
		
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
		
		if (colBox.getMinX() < 0 - Pacman.getTilesize()/2){
			if (dir == LEFT) x = Pacman.getWorldsize() + Pacman.getTilesize()/2;
			if (pacMan == pacAnimIdleUp || pacMan == pacAnimIdleDown || pacMan == pacAnimUp || pacMan == pacAnimDown) dir = RIGHT;
			if (y != 144.31007) y = 144.31007f;
		}
		
		if (colBox.getMaxX() > Pacman.getWorldsize() + Pacman.getTilesize()/2){
			if (dir == RIGHT) x = 0 - Pacman.getTilesize()/2;
			if (pacMan == pacAnimIdleUp || pacMan == pacAnimIdleDown || pacMan == pacAnimUp || pacMan == pacAnimDown) dir = LEFT;
			if (y != 144.31007) y = 144.31007f;
		}
		
		pacMan.update(delta);
		colBox.setLocation((int)x, (int)y);
		
		lastDir = dir;
		
		if (nextDir == UP) nextDirec = "Up";
		if (nextDir == DOWN) nextDirec = "Down";
		if (nextDir == LEFT) nextDirec = "Left";
		if (nextDir == RIGHT) nextDirec = "Right";
		
		if (lastDir == UP) lastDirec = "Up";
		if (lastDir == DOWN) lastDirec = "Down";
		if (lastDir == LEFT) lastDirec = "Left";
		if (lastDir == RIGHT) lastDirec = "Right";
		
		

	}
	
	public boolean isIntersecting(int d, float dx, float dy){
		
		Rectangle tempColBox = new Rectangle((int)dx, (int)dy, Pacman.getTilesize(), Pacman.getTilesize());
		
		for (int k = 0; k < Game.walls.length; k++){
			if (tempColBox.intersects(Game.walls[k])){
				
				return true;
				
			}
		}
		
		return false;

	}
	
//	public void tryMove(int dir){
//		if ()
//	}
	
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
