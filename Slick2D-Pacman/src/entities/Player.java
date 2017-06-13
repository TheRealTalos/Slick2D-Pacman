package entities;

import java.awt.Rectangle;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import main.Main;
import states.Game;
import utils.HUD;

public class Player extends Character {
	
	private Sound scaredMoveSound;
	private Sound moveSound;
	private Sound deathSound;
	
	private static final int IDLE = 4;

	private boolean deading;
	private boolean gotDeaded;
	private double deadTime;

	public static float speed = SPEED;
	
	public int dotsEaten;
	
	private int deaths = 0;

	public Player() {
	}

	public void restart(){
		dead = false;
		deading = false;
		gotDeaded = false;
		
		curAnim = anim[8];

		x = 10 * Main.getTilesize();
		y = 15 * Main.getTilesize();

		dir = NULL;
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
		
		try{
			moveSound = new Sound("/res/sounds/Moving.ogg");
			scaredMoveSound = new Sound("/res/sounds/ScaredMoving.ogg");
			deathSound = new Sound("/res/sounds/PacDeath.ogg");
			
		}catch (SlickException e){
			e.printStackTrace();
		}
		
		curAnim = anim[8];

		moveBox = new Rectangle(Main.getTilesize(), Main.getTilesize());
		fightBox = new Rectangle(Main.getTilesize() / 2, Main.getTilesize() / 2);

	}

	public void render(Graphics g) {
		curAnim.draw(x, y);

	}

	public void update(GameContainer container, int delta) {
		Input input = container.getInput();

		if (input.isMousePressed(0)) {
			System.out.println("x: " + input.getMouseX() / Main.getTilesize());
			System.out.println("y: " + input.getMouseY() / Main.getTilesize());
		}

		if (!deading) {

			if (input.isKeyPressed(Input.KEY_UP) || input.isKeyPressed(Input.KEY_W)) {
				nextDir = UP;
			} else if (input.isKeyPressed(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_S)) {
				nextDir = DOWN;
			} else if (input.isKeyPressed(Input.KEY_LEFT) || input.isKeyPressed(Input.KEY_A)) {
				nextDir = LEFT;
			} else if (input.isKeyPressed(Input.KEY_RIGHT) || input.isKeyPressed(Input.KEY_D)) {
				nextDir = RIGHT;
			}

			for (int i = 0; i < 4; i++){
				if (nextDir == i && !wouldIntersectWalls(i, this)) {
					dir = i;
					nextDir = NULL;
					break;
				}
			}
			
			for (int i = 0; i < 4; i++) {
				if (dir == i) {
					if (!wouldIntersectWalls(i, this)) {
						curAnim = anim[i];
						if (dir == UP)
							y -= speed;
						if (dir == DOWN)
							y += speed;
						if (dir == LEFT)
							x -= speed;
						if (dir == RIGHT)
							x += speed;
						boolean scared = false;
						for (int j = 0; j < Game.getGhosts().length; j++){
							if (Game.getGhosts()[i].getMode() == Ghost.SCARED)
								scared = true;
						}
						if (scared) {
							if (!scaredMoveSound.playing())
								scaredMoveSound.play();
						}else {
							if (!moveSound.playing())
								moveSound.play();
						}
					} else {
						if (moveSound.playing()){
							moveSound.stop();
						}
						curAnim = anim[IDLE + i];
						dir = NULL;
					}
				}
			}

			if (moveBox.getMinX() < 0 - Main.getTilesize() / 2) {
				if (dir == LEFT)
					x = Main.getWorldsize() + Main.getTilesize() / 2;
				if (curAnim == anim[UP] || curAnim == anim[DOWN] || curAnim == anim[IDLE + UP]
						|| curAnim == anim[IDLE + DOWN])
					dir = RIGHT;
				if (y != 144.31007)
					y = 144.31007f;
			}

			if (moveBox.getMaxX() > Main.getWorldsize() + Main.getTilesize() / 2) {
				if (dir == RIGHT)
					x = 0 - Main.getTilesize() / 2;
				if (curAnim == anim[UP] || curAnim == anim[DOWN] || curAnim == anim[IDLE + UP]
						|| curAnim == anim[IDLE + DOWN])
					dir = LEFT;
				if (y != 144.31007)
					y = 144.31007f;
			}

			for (int i = 0; i < Game.getGhosts().length; i++) {
				if (fightBox.intersects(Game.getGhosts()[i].getFightBox())
						&& Game.getGhosts()[i].getMode() != Ghost.DEAD) {
					if (Game.getGhosts()[i].getMode() != Ghost.SCARED) {
						startDeading();
					} else {
						Ghost.deadGhosts++;
						int points = 200 * Ghost.deadGhosts;
						HUD.score += points;
						if (HUD.score > Main.highscore) Main.highscore = HUD.score;
						Game.getHUD().draw((int) x, (int) y, Integer.toString(points), Ghost.pauseTime + (int) Game.getTimer().getTime(), HUD.blueFont);
						Game.getGhosts()[i].die();
					}
				}
			}

		} else {
			die();
		}

		curAnim.update(delta);
		moveBox.setLocation((int) x, (int) y);
		fightBox.setLocation((int) x + Main.getTilesize() / 4, (int) y + Main.getTilesize() / 4);
	}
	
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	private void startDeading() {
		deading = true;
		if (!gotDeaded)
			deadTime = Game.getTimer().getTime() + 0.55;
		gotDeaded = true;
	}
	
	

	public void setDotsEaten(int dotsEaten) {
		this.dotsEaten = dotsEaten;
	}

	public int getDeaths() {
		return deaths;
	}

	private void die() {
		if (!deathSound.playing()) deathSound.play();
		curAnim = anim[9];
		if (Game.getTimer().getTime() >= deadTime) {
			dead = true;
			deaths++;
			if (deaths < 3) Game.getHUD().removeLife();
		}
	}

	private void initAnim() {
		anim = new Animation[10];

		List<Image[]> imageArrays = new ArrayList<Image[]>();

		for (int i = 1; i < 5; i++) {
			imageArrays.add(new Image[] { Game.getSheet().getSprite(0, 0), Game.getSheet().getSprite(i * 2 - 1, 0),
					Game.getSheet().getSprite(i * 2, 0), Game.getSheet().getSprite(i * 2 - 1, 0) });
		}

		for (int i = 1; i < 5; i++) {
			imageArrays.add(new Image[] { Game.getSheet().getSprite(i * 2 - 1, 0) });
		}

		imageArrays.add(new Image[] { Game.getSheet().getSprite(0, 0) });

		Image[] deathImage = new Image[20];

		for (int i = 0; i < deathImage.length; i += 2) {
			deathImage[i] = Game.getSheet().getSprite(i, 11);
			deathImage[i + 1] = Game.getSheet().getSprite(i, 11);
		}

		imageArrays.add(deathImage);

		for (int i = 0; i < 10; i++) {
			anim[i] = new Animation(imageArrays.get(i), 50, false);
		}

		curAnim = anim[8];
	}

	public boolean isDeading() {
		return deading;
	}

	public Rectangle getPacBox() {
		return moveBox;
	}
}
