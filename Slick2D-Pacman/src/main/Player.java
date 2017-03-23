package main;

import java.awt.Rectangle;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Player {
	
	private Animation pacMan, pacAnimUp, pacAnimDown, pacAnimLeft, pacAnimRight;
	
	private Rectangle colBox;
	
	private float x = 10*Pacman.getTilesize();
	private float y = 15*Pacman.getTilesize();
	
	private int dir = 0;
	private boolean moving = false;
	
	private static final float SPEED = 0.1f;
	
	private static final int UP = 0;
	private static final int DOWN = 1;
	private static final int LEFT = 2;
	private static final int RIGHT = 3;

	public Player(){
		
	}
	
	
	public void init(){
		
		Image[] pacImageUp = {Pacman.getSheet().getSprite(0, 0), Pacman.getSheet().getSprite(5,0), Pacman.getSheet().getSprite(6, 0), Pacman.getSheet().getSprite(5, 0)};
		Image[] pacImageDown = {Pacman.getSheet().getSprite(0, 0), Pacman.getSheet().getSprite(7,0), Pacman.getSheet().getSprite(8, 0), Pacman.getSheet().getSprite(7, 0)};
		Image[] pacImageRight = {Pacman.getSheet().getSprite(0, 0), Pacman.getSheet().getSprite(3,0), Pacman.getSheet().getSprite(4, 0), Pacman.getSheet().getSprite(3, 0)};
		Image[] pacImageLeft = {Pacman.getSheet().getSprite(0, 0), Pacman.getSheet().getSprite(1,0), Pacman.getSheet().getSprite(2, 0), Pacman.getSheet().getSprite(1, 0)};
		
		pacAnimUp = new Animation(pacImageUp, 50, false);
		pacAnimDown = new Animation(pacImageDown, 50, false);
		pacAnimLeft = new Animation(pacImageLeft, 50, false);
		pacAnimRight = new Animation(pacImageRight, 50, false);
				
		pacMan = pacAnimRight;
		
		colBox = new Rectangle(Pacman.getTilesize(), Pacman.getTilesize());
		
	}
	
	public void render(){
		
		pacMan.draw(x, y);
		colBox.setLocation((int)x, (int)y);;
		
	}
	
	public void update(GameContainer container, int delta){	
		Input input = container.getInput();

		if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)){
			moving = true;
			dir = UP;
			
		}else if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)){
			moving = true;
			dir = DOWN;
			
		}else if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)){
			moving = true;
			dir = LEFT;
			
		}else if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)){
			moving = true;
			dir = RIGHT;

		}
			
		if (x > 336) x = 0 - Pacman.getTilesize()/2;
		if (x < 0 - Pacman.getTilesize()/2) x = 336;
		
		int dx = 0; 
		int dy = 0;
		
		if (moving){
			if (dir == UP) {
				pacMan = pacAnimUp;
				pacMan.update(delta);
				dy -= delta * SPEED;
			}else if (dir == DOWN) {
				pacMan = pacAnimDown;
				pacMan.update(delta);
				dy += delta * SPEED;
			}else if (dir == LEFT) {
				pacMan = pacAnimLeft;
				pacMan.update(delta);
				dx -= delta * SPEED;
			}else if (dir == RIGHT) {
				pacMan = pacAnimRight;
				pacMan.update(delta);
				dx += delta * SPEED;
			}
		}
		
		Rectangle tempColBox = new Rectangle((int)x + dx, (int)y + dy, Pacman.getTilesize(), Pacman.getTilesize());
		
		boolean m = false;
		
		for (int i = 0; i < Pacman.walls.length; i++){
			if (tempColBox.intersects(Pacman.walls[i])){
				moving = false;
				m = true;
			}
		}
		
		if (!m){
			x += dx;
			y += dy;
		}
		
		colBox.setLocation((int)x, (int)y);
		
//		for (int x = 0; x < Pacman.getWorldsize() / Pacman.getTilesize(); x++){
//			for (int y = 0; y < Pacman.getWorldsize() / Pacman.getTilesize(); y++){
//				if (colBox.intersects(Dots.dotsRec[x][y])){
//					System.out.println("manlymen");
//					Dots.dots[x][y] = null;
//				}
//			}
//		}
		
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
