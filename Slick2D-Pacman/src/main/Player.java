package main;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

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

public class Player extends Character {

	private Animation animStart, animDeath;
	
	private static final int IDLE = 4;
	
	private boolean deading;
	private boolean gotDeaded;
	private double deadTime;
	
	public int dotsEaten;

	public Player() {
	}

	public void init() {
		dead = false;
		deading = false;
		gotDeaded = false;
		
		dotsEaten = 0;

		x = 10 * Pacman.getTilesize();
		y = 15 * Pacman.getTilesize();

		initAnim();

		colBox = new Rectangle(Pacman.getTilesize(), Pacman.getTilesize());

	}

	public void render(Graphics g) {

		curAnim.draw(x, y);

	}

	public void update(GameContainer container, int delta) {
		Input input = container.getInput();

		if (input.isMousePressed(0)) {
			System.out.println("x: " + input.getMouseX()/Pacman.getTilesize());
			System.out.println("y: " + input.getMouseY()/Pacman.getTilesize());
		}
		
		if (!dead && !deading){

			if (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_W)) {
				nextDir = UP;
			} else if (input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_A)) {
				nextDir = DOWN;
			} else if (input.isKeyPressed(Input.KEY_LEFT) || input.isKeyPressed(Input.KEY_S)) {
				nextDir = LEFT;
			} else if (input.isKeyPressed(Input.KEY_RIGHT) || input.isKeyPressed(Input.KEY_D)) {
				nextDir = RIGHT;
			}
	
			if (nextDir == UP) {
				if (!wouldIntersectWalls(UP)) {
					dir = UP;
					nextDir = NULL;
				}
			} else if (nextDir == DOWN) {
				if (!wouldIntersectWalls(DOWN)) {
					dir = DOWN;
					nextDir = NULL;
				}
			} else if (nextDir == LEFT) {
				if (!wouldIntersectWalls(LEFT)) {
					dir = LEFT;
					nextDir = NULL;
				}
			} else if (nextDir == RIGHT) {
				if (!wouldIntersectWalls(RIGHT)) {
					dir = RIGHT;
					nextDir = NULL;
				}
			}
			
			for (int i = 0; i < 4; i++){
				if (dir == i){
					if (!wouldIntersectWalls(i)){
						curAnim = anim[i];
						if (dir == UP) y -= SPEED; 
						if (dir == DOWN) y += SPEED; 
						if (dir == LEFT) x -= SPEED; 
						if (dir == RIGHT) x += SPEED; 
					}else {
						curAnim = anim[i+4];
						dir = NULL;
					}
				}
			}
	
			if (colBox.getMinX() < 0 - Pacman.getTilesize() / 2) {
				if (dir == LEFT)
					x = Pacman.getWorldsize() + Pacman.getTilesize() / 2;
				if (curAnim == anim[UP] || curAnim == anim[DOWN] || curAnim == anim[IDLE + UP] || curAnim == anim[IDLE + DOWN])
					dir = RIGHT;
				if (y != 144.31007)
					y = 144.31007f;
			}
	
			if (colBox.getMaxX() > Pacman.getWorldsize() + Pacman.getTilesize() / 2) {
				if (dir == RIGHT)
					x = 0 - Pacman.getTilesize() / 2;
				if (curAnim == anim[UP] || curAnim == anim[DOWN] || curAnim == anim[IDLE + UP] || curAnim == anim[IDLE + DOWN])
					dir = LEFT;
				if (y != 144.31007)
					y = 144.31007f;
			}
			
			if (colBox.intersects(Game.getRedGhost().getColBox()) || 
					colBox.intersects(Game.getPinkGhost().getColBox()) || 
					colBox.intersects(Game.getBlueGhost().getColBox()) || 
					colBox.intersects(Game.getOrangeGhost().getColBox())){
				startDeading();
			}
		}else if (deading) {
			die();
		}

		curAnim.update(delta);
		colBox.setLocation((int) x, (int) y);

	}
	
	private void startDeading(){
		deading = true;
		if (!gotDeaded) deadTime = Game.getTimer().getTime() + 1.2;
		gotDeaded = true;
	}
	
	private void die(){
		curAnim = anim[9];
		if (Game.getTimer().getTime() >= deadTime){
			dead = true;
		}
	}
	
	private void initAnim(){
		anim = new Animation[10];
		
		List<Image[]> imageArrays = new ArrayList<Image[]>();
		
		for (int i = 1; i < 5; i++){
			imageArrays.add(new Image[] { Game.getSheet().getSprite(0, 0), Game.getSheet().getSprite(i*2-1, 0), Game.getSheet().getSprite(i*2, 0), Game.getSheet().getSprite(i*2-1, 0)});
		}
		
		for (int i = 1; i < 5; i++){
			imageArrays.add(new Image[] { Game.getSheet().getSprite(i*2-1, 0) });
		}

		imageArrays.add(new Image[] { Game.getSheet().getSprite(0, 0) });
		imageArrays.add(new Image[] {Game.getSheet().getSprite(0, 10), Game.getSheet().getSprite(1, 10),
				Game.getSheet().getSprite(2, 10), Game.getSheet().getSprite(3, 10), Game.getSheet().getSprite(4, 10),
				Game.getSheet().getSprite(5, 10), Game.getSheet().getSprite(6, 10), Game.getSheet().getSprite(7, 10),
				Game.getSheet().getSprite(8, 10), Game.getSheet().getSprite(9, 10), Game.getSheet().getSprite(10, 10),
				Game.getSheet().getSprite(10, 10), Game.getSheet().getSprite(10, 10), Game.getSheet().getSprite(10, 10)});
		
		for (int i = 0; i < 10; i++){
			anim[i] = new Animation(imageArrays.get(i), 50, false);
		}
		
		curAnim = anim[8];
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public boolean isDeading(){
		return deading;
	}

	public Rectangle getPacBox(){
		return colBox;
	}
}
