package main;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

public class Player {
	
	private Animation pacMan, pacAnimUp, pacAnimDown, pacAnimLeft, pacAnimRight;
	
	private float x = 10*Pacman.getTilesize();
	private float y = 15*Pacman.getTilesize();
	
	private int dir = 0;
	private boolean moving = false;
	
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
		
	}
	
	public void render(){
		
		pacMan.draw(x, y);
		
	}
	
	public void update(GameContainer container, int delta){
		
		Input input = container.getInput();
		
		//if (!hitWall)
		
		
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
		
		if (moving){
			if (dir == UP) {
				pacMan = pacAnimUp;
				pacMan.update(delta);
				y -= delta * 0.1f;
			}else if (dir == DOWN) {
				pacMan = pacAnimDown;
				pacMan.update(delta);
				y += delta * 0.1f;
			}else if (dir == LEFT) {
				pacMan = pacAnimLeft;
				pacMan.update(delta);
				x -= delta * 0.1f;
			}else if (dir == RIGHT) {
				pacMan = pacAnimRight;
				pacMan.update(delta);
				x += delta * 0.1f;
			}
		}
	}
}
