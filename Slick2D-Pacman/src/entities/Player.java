package entities;

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

import main.Main;
import states.Game;

public class Player extends Character {
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

		x = 10 * Main.getTilesize();
		y = 15 * Main.getTilesize();
		
		dir = NULL;

		initAnim();

		moveBox = new Rectangle(Main.getTilesize(), Main.getTilesize());
		fightBox = new Rectangle(Main.getTilesize()/2, Main.getTilesize()/2);

	}

	public void render(Graphics g) {

		curAnim.draw(x, y);

	}

	public void update(GameContainer container, int delta) {
		Input input = container.getInput();

		if (input.isMousePressed(0)) {
			System.out.println("x: " + input.getMouseX()/Main.getTilesize());
			System.out.println("y: " + input.getMouseY()/Main.getTilesize());
		}
		
		if (!deading){

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
						curAnim = anim[IDLE+i];
						dir = NULL;
					}
				}
			}
	
			if (moveBox.getMinX() < 0 - Main.getTilesize() / 2) {
				if (dir == LEFT)
					x = Main.getWorldsize() + Main.getTilesize() / 2;
				if (curAnim == anim[UP] || curAnim == anim[DOWN] || curAnim == anim[IDLE + UP] || curAnim == anim[IDLE + DOWN])
					dir = RIGHT;
				if (y != 144.31007)
					y = 144.31007f;
			}
	
			if (moveBox.getMaxX() > Main.getWorldsize() + Main.getTilesize() / 2) {
				if (dir == RIGHT)
					x = 0 - Main.getTilesize() / 2;
				if (curAnim == anim[UP] || curAnim == anim[DOWN] || curAnim == anim[IDLE + UP] || curAnim == anim[IDLE + DOWN])
					dir = LEFT;
				if (y != 144.31007)
					y = 144.31007f;
			}
			
			for (int i = 0; i < Game.getGhosts().length; i++){
				if (fightBox.intersects(Game.getGhosts()[i].getFightBox()) && Game.getGhosts()[i].getMode() != Ghost.DEAD){
					if (Game.getGhosts()[i].getMode() != Ghost.SCARED){
						startDeading();
					}else{
						Game.getGhosts()[i].die();
					}
				}
			}

		}else{
			die();
		}

		curAnim.update(delta);
		moveBox.setLocation((int) x, (int) y);
		fightBox.setLocation((int) x + Main.getTilesize()/4, (int) y + Main.getTilesize()/4);
	}
	
	private void startDeading(){
		deading = true;
		if (!gotDeaded) deadTime = Game.getTimer().getTime() + 0.55;
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
		
		Image[] deathImage = new Image[20];
		
		for (int i = 0; i < deathImage.length; i+=2){
			deathImage[i] = Game.getSheet().getSprite(i, 11);
			deathImage[i+1] = Game.getSheet().getSprite(i, 11);
		}
		
		imageArrays.add(deathImage);
		
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
		return moveBox;
	}
}
