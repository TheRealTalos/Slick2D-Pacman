package main;

import java.awt.Container;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class Pacman extends BasicGame{
	
	//MAKE SCALABLE GAME
	//MESS WITH SETTINGS
	
	private TiledMap map;
	private Player player = new Player();
	
	private static SpriteSheet sheet;
	
	private static final int WORLDSIZE = 336;
	private static final int TILESIZE = 16;
	private static final int SCALE = 2;
	
	public Pacman() {
		super("Pacman");
			
	}
	
	public static void main(String[] args){
		try{
			
			AppGameContainer app = new AppGameContainer(new Pacman());
			app.setDisplayMode(WORLDSIZE, WORLDSIZE, false);
			app.start();
			
		}catch(SlickException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		map = new TiledMap("res/maps/map.tmx");
		
		sheet = new SpriteSheet(new Image("res/spritesheets/SpriteSheet.png"), TILESIZE, TILESIZE);
		
		player.init();
		
	}
	
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		
		map.render(0, 0);
		player.render();
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		player.update(container, delta);
		
	}
	
	public static SpriteSheet getSheet() {
		return sheet;
	}
	
	public static int getWorldsize() {
		return WORLDSIZE;
	}

	public static int getTilesize() {
		return TILESIZE;
	}
	
}
