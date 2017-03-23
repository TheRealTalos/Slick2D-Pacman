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
		Image[] pacImageIdleUp = {Pacman.getSheet().getSprite(0, 0)};
		Image[] pacImageIdleDown = {Pacman.getSheet().getSprite(0, 0)};
		Image[] pacImageIdleLeft = {Pacman.getSheet().getSprite(0, 0)};
		Image[] pacImageIdleRight = {Pacman.getSheet().getSprite(0, 0)};
		
		Image[] pacImageUp = {Pacman.getSheet().getSprite(0, 0), Pacman.getSheet().getSprite(5,0), Pacman.getSheet().getSprite(6, 0), Pacman.getSheet().getSprite(5, 0)};
		Image[] pacImageDown = {Pacman.getSheet().getSprite(0, 0), Pacman.getSheet().getSprite(7,0), Pacman.getSheet().getSprite(8, 0), Pacman.getSheet().getSprite(7, 0)};
		Image[] pacImageRight = {Pacman.getSheet().getSprite(0, 0), Pacman.getSheet().getSprite(3,0), Pacman.getSheet().getSprite(4, 0), Pacman.getSheet().getSprite(3, 0)};
		Image[] pacImageLeft = {Pacman.getSheet().getSprite(0, 0), Pacman.getSheet().getSprite(1,0), Pacman.getSheet().getSprite(2, 0), Pacman.getSheet().getSprite(1, 0)};
		
		pacAnimStart = new Animation(pacImageStart, 50, false);
		pacAnimIdleUp = new Animation(pacImageUp, 50, false);
		pacAnimIdleDown = new Animation(pacImageDown, 50, false);
		pacAnimIdleLeft = new Animation(pacImageLeft, 50, false);
		pacAnimIdleRight = new Animation(pacImageRight, 50, false);
		
		pacAnimUp = new Animation(pacImageUp, 50, false);
		pacAnimDown = new Animation(pacImageDown, 50, false);
		pacAnimLeft = new Animation(pacImageLeft, 50, false);
		pacAnimRight = new Animation(pacImageRight, 50, false);
				
		pacMan = pacAnimStart;
		
		colBox = new Rectangle(Pacman.getTilesize(), Pacman.getTilesize());
		
	}
	
	public void render(){
		
		pacMan.draw(x, y);
		colBox.setLocation((int)x, (int)y);
		
	}
	
	public void update(GameContainer container, int delta){	
		Input input = container.getInput();
		
//		Rectangle colUp = new Rectangle((int)x, (int)y, Pacman.getTilesize(), Pacman.getTilesize());
//		Rectangle colDown = new Rectangle((int)x, (int)y, Pacman.getTilesize(), Pacman.getTilesize());
//		Rectangle colLeft = new Rectangle((int)x, (int)y, Pacman.getTilesize(), Pacman.getTilesize());
//		Rectangle colRight = new Rectangle((int)x, (int)y, Pacman.getTilesize(), Pacman.getTilesize());
		
		if (input.isKeyPressed(input.KEY_UP)){
			
			dir = UP;
			
		}else if (input.isKeyPressed(input.KEY_DOWN)){
			
			dir = DOWN;
			
		}else if (input.isKeyPressed(input.KEY_LEFT)){
			
			dir = LEFT;
			
		}else if (input.isKeyPressed(input.KEY_RIGHT)){
			
			dir = RIGHT;
			
		}
		
		if (dir == UP){
			if (!isIntersecting(colUp)){
				y -= delta * SPEED;
				pacMan = pacAnimUp;
			}
		}else if (dir == DOWN){
			if (!isIntersecting(colDown)){
				y += delta * SPEED;
				pacMan = pacAnimDown;
			}
		}else if (dir == LEFT){
			if (!isIntersecting(colLeft)){
				x -= delta * SPEED;
				pacMan = pacAnimLeft;
			}
		}else if (dir == RIGHT){
			if (!isIntersecting(colRight)){
				x += delta * SPEED;
				pacMan = pacAnimRight;
			}
		}
		
		
		pacMan.update(delta);
		colBox.setLocation((int)x, (int)y);
	}
	
	public boolean isIntersecting(){
		for (int i = 0; i < Pacman.walls.length; i++){
			
			Rectangle rec = Pacman.walls[i];
			
			float w = (float) (0.5 * (colBox.getWidth() + rec.getWidth()));
			float h = (float) (0.5 * (colBox.getHeight() + rec.getHeight()));
			float dx = (float) (colBox.getCenterX() - rec.getCenterX());
			float dy = (float) (colBox.getCenterY() - rec.getCenterY());
	
			if (abs(dx) <= w && abs(dy) <= h) {
			    /* collision! */
			    float wy = w * dy;
			    float hx = h * dx;
			    
			    if (wy > hx)
			        if (wy > -hx)
			            /* collision at the top */
			        else
			            /* on the left */
			    else
			        if (wy > -hx)
			            /* on the right */
			        else
			            /* at the bottom */
			}
	
			
	//		for (int k = 0; k < Pacman.walls.length; k++){
	//			if (colBox.intersects(Pacman.walls[k])) return true;
	//		}
			return false;
		}
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
