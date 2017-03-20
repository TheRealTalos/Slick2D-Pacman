package main;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class Pacman extends BasicGame{
	
	private TiledMap map;
	private Animation pacAnimRight, pacAnimLeft;
	
	public Pacman() {
		super("Pacman");
			
	}
	
	public static void main(String[] args){
		try{
			
			AppGameContainer app = new AppGameContainer(new Pacman());
			app.setDisplayMode(960, 540, false);
			app.start();
			
			Image[] pacImageRight = {new Image("res/sprites/right1.png"), new Image("res/sprites/right2.png")};
			Image[] pacImageLeft = {new Image("res/sprites/left1.png"), new Image("res/sprites/left2.png")};
			
		}catch(SlickException e){
			e.printStackTrace();
		}
		
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		
		map = new TiledMap("res/maps/map.tmx");
		
		pacAnimLeft = new Animation(pacImageLeft, 16);
		pacAnimRight = new Animation(pacImageRight, 16);
	}
	
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		
		map.render(0, 0);
		
		
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		
		
		
	}

}
