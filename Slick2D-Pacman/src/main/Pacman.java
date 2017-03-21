package main;

import java.awt.Container;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class Pacman extends BasicGame{
	
	//MAKE SCALABLE GAME
	//MAKE SPRITESHEET WORK
	//ADD PLAYER CLASS
	//MESS WITH SETTINGS
	
	private TiledMap map;
	private Animation pacMan, pacAnimUp, pacAnimDown, pacAnimRight, pacAnimLeft;
	
	private static final int WORLDSIZE = 336;
	private static final int TILESIZE = 16;
	private static final int SCALE = 2;
	
	private float x = 10*TILESIZE;
	private float y = 15*TILESIZE;
	
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
		
		Image[] pacImageUp = {new Image("res/sprites/PacUp1.png"), new Image("res/sprites/PacUp2.png")};
		Image[] pacImageDown = {new Image("res/sprites/PacDown1.png"), new Image("res/sprites/PacDown2.png")};
		Image[] pacImageRight = {new Image("res/sprites/PacRight1.png"), new Image("res/sprites/PacRight2.png")};
		Image[] pacImageLeft = {new Image("res/sprites/PacLeft1.png"), new Image("res/sprites/PacLeft2.png")};
		
		pacAnimUp = new Animation(pacImageUp, 100, false);
		pacAnimDown = new Animation(pacImageDown, 100, false);
		pacAnimLeft = new Animation(pacImageLeft, 100, false);
		pacAnimRight = new Animation(pacImageRight, 100, false);
		
		pacMan = pacAnimRight;
	}
	
	@Override
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		
		map.render(0, 0);
		pacMan.draw(x, y);
		
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		
		Input input = container.getInput();
		if (input.isKeyDown(Input.KEY_UP)){
			pacMan = pacAnimUp;
			pacMan.update(delta);
			y -= delta * 0.1f;
			
		}else if (input.isKeyDown(Input.KEY_DOWN)){
			pacMan = pacAnimDown;
			pacMan.update(delta);
			y += delta * 0.1f;
			
		}else if (input.isKeyDown(Input.KEY_LEFT)){
			pacMan = pacAnimLeft;
			pacMan.update(delta);
			x -= delta * 0.1f;
			
		}else if (input.isKeyDown(Input.KEY_RIGHT)){
			pacMan = pacAnimRight;
			pacMan.update(delta);
			x += delta * 0.1f;
		}
	}
}
