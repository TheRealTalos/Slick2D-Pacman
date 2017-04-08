package main;

import java.awt.Rectangle;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

public class RedGhost extends Character {

	private Animation redGhost, redGhostUp, redGhostDown, redGhostLeft, redGhostRight;

	private static double x = 10 * Pacman.getTilesize();
	private static double y = 7 * Pacman.getTilesize();

	private static Rectangle colBox;

	public RedGhost() {
		super(colBox, x, y);
	}

	public void init() {

		Image[] redGhostImageUp = { Game.getSheet().getSprite(0, 5), Game.getSheet().getSprite(1, 5) };
		Image[] redGhostImageDown = { Game.getSheet().getSprite(2, 5), Game.getSheet().getSprite(3, 5) };
		Image[] redGhostImageLeft = { Game.getSheet().getSprite(4, 5), Game.getSheet().getSprite(5, 5) };
		Image[] redGhostImageRight = { Game.getSheet().getSprite(6, 5), Game.getSheet().getSprite(7, 5) };

		redGhostUp = new Animation(redGhostImageUp, 200, false);
		redGhostDown = new Animation(redGhostImageDown, 200, false);
		redGhostLeft = new Animation(redGhostImageLeft, 200, false);
		redGhostRight = new Animation(redGhostImageRight, 200, false);

		redGhost = redGhostDown;

	}
	
	public void render(){
		redGhost.draw((float)x, (float)y);
	}
	
	public void update(long delta){
		redGhost.update(delta);
	}

}
