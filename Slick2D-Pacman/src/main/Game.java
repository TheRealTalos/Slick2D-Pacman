package main;

import java.awt.Font;
import java.awt.Rectangle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.ResourceLoader;

public class Game extends BasicGameState {

	private static int state;
	
	private static final int RED = 0;;
	private static final int PINK = 1;
	private static final int BLUE = 2;
	private static final int ORANGE = 3;

	private static TiledMap map;
	private static Player player = new Player();
	private static SpriteSheet sheet;
	private static Dots dots = new Dots();
	private static Hud hud = new Hud();
	private static Ghost redGhost = new Ghost(RED);
	private static Ghost pinkGhost = new Ghost(PINK);
	private static Ghost blueGhost = new Ghost(BLUE);
	private static Ghost orangeGhost = new Ghost(ORANGE);

	public static Rectangle[] walls = new Rectangle[202];
	public static Rectangle[] semiWalls = new Rectangle[1];
	
	private static double timer;
	private static double s;

	public Game(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		map = new TiledMap("res/maps/map.tmx");
		sheet = new SpriteSheet(new Image("res/spritesheets/SpriteSheet.png"), Pacman.getTilesize(),
				Pacman.getTilesize());

		player.init();
		redGhost.init();
		pinkGhost.init();
		blueGhost.init();
		orangeGhost.init();
		dots.init();
		hud.init();

		int w = 0;
		int sw = 0;

		for (int y = 0; y < Pacman.getWorldsize() / Pacman.getTilesize(); y++) {
			for (int x = 0; x < Pacman.getWorldsize() / Pacman.getTilesize(); x++) {
				if (map.getTileProperty(map.getTileId(x, y, 0), "Wall", "nope").equals("true")) {
					walls[w] = new Rectangle(x * Pacman.getTilesize(), y * Pacman.getTilesize(), Pacman.getTilesize(),
							Pacman.getTilesize());
					w++;
				}
				if (map.getTileProperty(map.getTileId(x, y, 0), "SemiWall", "nope").equals("true")) {
					semiWalls[sw] = new Rectangle(x * Pacman.getTilesize(), y * Pacman.getTilesize(), Pacman.getTilesize(),
							Pacman.getTilesize());
					sw++;
				}
			}
		}
		s = 1;
		s = s/60;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		map.render(0, 0);
		dots.render();
		player.render(g);
		redGhost.render();
		pinkGhost.render();
		blueGhost.render();
		orangeGhost.render();
		hud.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta) throws SlickException {
		if (!player.isDead()){
			player.update(gc, delta);
			redGhost.update(delta);
			pinkGhost.update(delta);
			blueGhost.update(delta);
			orangeGhost.update(delta);
		}
		dots.update();
		timer += s;
	}

	@Override
	public int getID() {
		return state;
	}
	
	public static Ghost getRedGhost() {
		return redGhost;
	}

	public static Ghost getPinkGhost() {
		return pinkGhost;
	}

	public static Ghost getBlueGhost() {
		return blueGhost;
	}

	public static Ghost getOrangeGhost() {
		return orangeGhost;
	}

	public static Double getTimer(){
		return timer;
	}

	public static SpriteSheet getSheet() {
		return sheet;
	}

	public static TiledMap getMap() {
		return map;
	}

	public static Player getPlayer() {
		return player;
	}

}
