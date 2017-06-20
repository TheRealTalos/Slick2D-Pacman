package states;

import java.awt.Rectangle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import entities.Dots;
import entities.Ghost;
import entities.Player;
import main.Main;
import utils.HUD;
import utils.Timer;

public class Game extends BasicGameState {
	private int state;
	
	public static final int NUMLIVES = 3;

	private static TiledMap map;
	private static Player player = new Player();
	private static SpriteSheet sheet;
	private static Dots dots = new Dots();
	private static HUD hud = new HUD();
	private static Ghost[] ghosts = new Ghost[4];
	
	public static Rectangle[] walls = new Rectangle[202];
	public static Rectangle[] semiWalls = new Rectangle[1];
	
	private static Timer timer = new Timer();
	
	public static int level = 1;
	
	private static boolean gameOver = false;
	
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
		hud.gameInit();
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
		hud.gameRender(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (Game.getTimer().getTime() < Ghost.getEndPaused()){
			Ghost.setPaused(true);
		}else {
			Ghost.setPaused(false);
		}
		
		if (!gameOver){
			if (player.getDeaths() >= NUMLIVES){
				gameOver = true;
			}else if (player.isDead()){
				timer.init();
				player.restart();
				for (int i = 0; i < ghosts.length; i++){
					ghosts[i].init();
				}
				hud.restart();
			}
			if (!player.isDead() && !Ghost.getPaused()){
				player.update(gc, delta);
			}
			if (!player.isDeading() && !Ghost.getPaused()){
				for (int i = 0; i < ghosts.length; i++){
					ghosts[i].update(delta);
				}
			}
			dots.update();
			timer.update();
		}else {
			if (Main.origHighscore > Main.highscore) Main.setHighscore();
			level = 1;
			gameOver = false;
			Menu.playAgain = true;
			Menu.tutorial = false;
			Player.speed = 1f;
			player.setDeaths(0);
			hud.gameInit();
			player.setDotsEaten(0);
			restart();
			sbg.enterState(0);
		}
		if (player.dotsEaten == 150){
			if (Player.speed > 0.8f) Player.speed -= 0.02f;
			level++;
			hud.restart();
			restart();
		}
	}
	
	public void restart(){
		player.init();
		for (int i = 0; i < ghosts.length; i++){
			ghosts[i] = new Ghost(i);
			ghosts[i].init();
		}
		dots.init();
		timer.init();
	}

	@Override
	public int getID() {
		return state;
	}
	
	public static void setGameOver(boolean gameOver) {
		Game.gameOver = gameOver;
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
	
	public static HUD getHUD() {
		return hud;
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
