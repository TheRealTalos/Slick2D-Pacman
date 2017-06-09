package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.ResourceLoader;

import states.Game;
import states.Menu;

public class Main extends StateBasedGame{
	
	// MAKE SCALABLE GAME
	// MESS WITH SETTINGS
	// ADD HIGH SCORE
	
	/*
	Make menu look nice
	different menu when gameovered
	show highscore on menu
	add menu buttons: reset highscore, quit??
	 * 
	start sound
	wakka wakka - when eating in chase/scatter
	alarm sound - when opening and closing mouth in chase/scatter
	really fast bing- when opening and closing mouth in scared mode
	ding ding - when ghosts die and are eyes
	death souund - when pac dies 
	 
	 */

	private static final int SCALE = 2;

	private static final int WINDOWWIDTH = 336;
	private static final int WINDOWHEIGHT = 400;

	private static final int WORLDSIZE = 336;
	private static final int TILESIZE = 16;
	
	private static final int MENU = 0;
	private static final int GAME = 1;
	
	public static int highscore = 0;
	private static int origHighscore = 0;
	
	private static FileReader fileReader;
	
	public static BufferedReader reader;
	public static BufferedWriter writer;

	public Main() {
		super("Pacman");
		this.addState(new Menu(MENU));
		this.addState(new Game(GAME));
	}
	
	public static void main(String[] args){
		try (BufferedReader br = new BufferedReader(new FileReader("res/HIGH.txt"))) {
			String s;

			if ((s = br.readLine()) != null)
				highscore = Integer.parseInt(s);
			else highscore = 0;
			origHighscore = highscore;

		} catch (IOException e) {
			e.printStackTrace();
		}
	
		try{
			AppGameContainer app = new AppGameContainer(new Main());
			app.setDisplayMode(WINDOWWIDTH, WINDOWHEIGHT, false);
			app.setTargetFrameRate(60);
			app.start();
			
		}catch(SlickException e){
			e.printStackTrace();
		}
		
	}
	
	public static void setHighscore(){
		if (origHighscore < highscore){
			try (BufferedWriter bw = new BufferedWriter(new FileWriter("res/HIGH.txt"))) {
				bw.write(Integer.toString(highscore));
	
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.enterState(MENU);
	}
	
	public static int getWorldsize() {
		return WORLDSIZE;
	}

	public static int getTilesize() {
		return TILESIZE;
	}
	
}
