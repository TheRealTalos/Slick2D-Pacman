package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.ResourceLoader;

public class Pacman extends StateBasedGame{
	
	//MAKE SCALABLE GAME
	//MESS WITH SETTINGS
	
	private static final int SCALE = 2;
	
	private static final int WINDOWWIDTH = 336;
	private static final int WINDOWHEIGHT = 400;
	
	private static final int WORLDSIZE = 336;
	private static final int TILESIZE = 16;
	
	private static final int MENU = 0;
	private static final int GAME = 1;
	
	
	public Pacman() {
		super("Pacman");
		this.addState(new Menu(MENU));
		this.addState(new Game(GAME));
	}
	
	public static void main(String[] args){
		try{
			AppGameContainer app = new AppGameContainer(new Pacman());
			app.setDisplayMode(WINDOWWIDTH, WINDOWHEIGHT, false);
			app.setTargetFrameRate(60);
			app.start();
			
		}catch(SlickException e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(MENU).init(gc, this);
		this.getState(GAME).init(gc, this);
		this.enterState(MENU);
	}
	
	public static int getWorldsize() {
		return WORLDSIZE;
	}

	public static int getTilesize() {
		return TILESIZE;
	}
	
}
