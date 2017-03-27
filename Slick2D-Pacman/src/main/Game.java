package main;

import java.awt.Rectangle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Game extends BasicGameState{
	
	private static TiledMap map;
	private static Player player = new Player();
	private static SpriteSheet sheet;
	private static Dots dots = new Dots();
	
	public static Rectangle[] walls = new Rectangle[202];
	
	public Game(int state){
		
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		map = new TiledMap("res/maps/map.tmx");
		
		sheet = new SpriteSheet(new Image("res/spritesheets/SpriteSheet.png"), Pacman.getTilesize(), Pacman.getTilesize());
		
		player.init();
		
		dots.init();
		
		int w = 0;
		
		for (int y = 0; y < Pacman.getWorldsize()/Pacman.getTilesize(); y++){
			for (int x = 0; x < Pacman.getWorldsize()/Pacman.getTilesize(); x++){
				if (map.getTileProperty(map.getTileId(x, y, 0), "Wall", "nope").equals("true")){
					walls[w] = new Rectangle(x * Pacman.getTilesize(), y * Pacman.getTilesize(), Pacman.getTilesize(), Pacman.getTilesize());
					w++;
				}
			}
		}
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		dots.render();
		map.render(0, 0);
		player.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame arg1, int delta) throws SlickException {
		player.update(gc, delta);
		dots.update();
	}

	@Override
	public int getID() {
		return 0;
	}
	
	public static SpriteSheet getSheet() {
		return sheet;
	}

	public static TiledMap getMap() {
		return map;
	}
	
	public static Player getPlayer(){
		return player;
	}
	
}
