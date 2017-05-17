package states;

import java.awt.Font;
import java.awt.Rectangle;
import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.ResourceLoader;

import entities.Dots;
import entities.Ghost;
import entities.Player;
import main.Main;
import utils.HUD;
import utils.Timer;

public class Game extends BasicGameState {

	private int state;
	
	private static final int RED = 0;;
	private static final int PINK = 1;
	private static final int BLUE = 2;
	private static final int ORANGE = 3;

	private static TiledMap map;
	private static Player player = new Player();
	private static SpriteSheet sheet;
	private static Dots dots = new Dots();
	private static HUD hud = new HUD();
	private static Ghost[] ghosts = new Ghost[4];
	
	public static Rectangle[] walls = new Rectangle[202];
	public static Rectangle[] semiWalls = new Rectangle[1];
	
	private static Timer timer = new Timer();
	
	private static boolean win = false;

	public Game(int state) {
		this.state = state;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		map = new TiledMap("res/maps/map.tmx");
		sheet = new SpriteSheet(new Image("res/spritesheets/SpriteSheet.png"), Main.getTilesize(),
				Main.getTilesize());

		player.init();
		for (int i = 0; i < ghosts.length; i++){
			ghosts[i] = new Ghost(i);
			ghosts[i].init();
		}
		dots.init();
		hud.init();
		timer.init();
		
		int w = 0;
		int sw = 0;

		for (int y = 0; y < Main.getWorldsize() / Main.getTilesize(); y++) {
			for (int x = 0; x < Main.getWorldsize() / Main.getTilesize(); x++) {
				if (map.getTileProperty(map.getTileId(x, y, 0), "Wall", "nope").equals("true")) {
					walls[w] = new Rectangle(x * Main.getTilesize(), y * Main.getTilesize(), Main.getTilesize(),
							Main.getTilesize());
					w++;
				}
				if (map.getTileProperty(map.getTileId(x, y, 0), "SemiWall", "nope").equals("true")) {
					semiWalls[sw] = new Rectangle(x * Main.getTilesize(), y * Main.getTilesize(), Main.getTilesize(),
							Main.getTilesize());
					sw++;
				}
			}
		}
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		map.render(0, 0);
		dots.render();
		player.render(g);
		for (int i = 0; i < ghosts.length; i++){
			ghosts[i].render();
		}
		hud.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbc, int delta) throws SlickException {
		if (!win){
			if (!player.isDead()) player.update(gc, delta);
			if (!player.isDeading()){
				for (int i = 0; i < ghosts.length; i++){
					ghosts[i].update(delta);
				}
			}
			dots.update();
			timer.update();
		}
		if (player.dotsEaten == 146){
			init(gc, sbc);
		}
	}

	@Override
	public int getID() {
		return state;
	}
	
	public static Ghost getRedGhost() {
		return ghosts[0];
	}

	public static Ghost getPinkGhost() {
		return ghosts[1];
	}

	public static Ghost getBlueGhost() {
		return ghosts[2];
	}

	public static Ghost getOrangeGhost() {
		return ghosts[3];
	}
	
	public static Ghost[] getGhosts(){
		return ghosts;
	}

	public static Timer getTimer(){
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
