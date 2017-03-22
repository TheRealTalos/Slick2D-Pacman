package main;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class Pacman extends BasicGame{
	
	//MAKE SCALABLE GAME
	//MESS WITH SETTINGS
	//ADD COLLISION
	
	private static TiledMap map;
	private Player player = new Player();
	private Dots dots = new Dots();
	
	private static SpriteSheet sheet;
	
	private static final int WORLDSIZE = 336;
	private static final int TILESIZE = 16;
	private static final int SCALE = 2;
	
	private static Boolean[][] isWall = new Boolean[TILESIZE][TILESIZE];
	
	public static Rectangle[] walls = new Rectangle[TILESIZE];
	
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
		
		int k = 0;
		
		for (int i = 0; i < 3; i++){
			if (map.getTileProperty(map.getTileId(i, 0, 0), "Wall", "nope").equals("true")){
				walls[i] = new Rectangle(i * TILESIZE, i * TILESIZE, TILESIZE, TILESIZE);
				i++;
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
		
		if (player.getPacBox().getBounds().intersects(walls[1])){
			
		}
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
}
