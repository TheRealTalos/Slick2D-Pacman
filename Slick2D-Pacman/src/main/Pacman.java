package main;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class Pacman extends BasicGame{
	
	//MAKE SCALABLE GAME
	//MESS WITH SETTINGS
	//ADD DOT COLLISION
	
	private static TiledMap map;
	private static Player player = new Player();
	private Dots dots = new Dots();
	
	private static SpriteSheet sheet;
	
	private static final int WORLDSIZE = 336;
	private static final int TILESIZE = 16;
	private static final int SCALE = 2;
	
	public static Rectangle[] walls = new Rectangle[202];
	
	public Pacman() {
		super("Pacman");
	}
	
	public static void main(String[] args){
		try{
			
			AppGameContainer app = new AppGameContainer(new Pacman());
			app.setDisplayMode(WORLDSIZE, WORLDSIZE, false);
			app.setTargetFrameRate(60);
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
		
		dots.init();
		
		int w = 0;
		
		for (int y = 0; y < WORLDSIZE/TILESIZE; y++){
			for (int x = 0; x < WORLDSIZE/TILESIZE; x++){
				if (map.getTileProperty(map.getTileId(x, y, 0), "Wall", "nope").equals("true")){
					walls[w] = new Rectangle(x * TILESIZE, y * TILESIZE, TILESIZE, TILESIZE);
					w++;
				}
			}
		}
	}

	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		
		dots.render();
		map.render(0, 0);
		player.render();
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		player.update(container, delta);
		dots.update();
		
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
	
	public static TiledMap getMap() {
		return map;
	}
	
	public static Player getPlayer(){
		return player;
	}
}
