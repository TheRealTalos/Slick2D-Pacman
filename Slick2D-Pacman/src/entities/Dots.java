package entities;

import java.awt.Rectangle;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import main.Main;
import states.Game;
import utils.HUD;

public class Dots {
	
	public static Image[][] dots = new Image[Main.getWorldsize()][Main.getWorldsize()];
	private static Rectangle[][] dotsRec = new Rectangle[Main.getWorldsize() / Main.getTilesize()][Main.getWorldsize() / Main.getTilesize()];
	
	public static Image[][] bigDots = new Image[Main.getWorldsize()][Main.getWorldsize()];
	private static Rectangle[][] bigDotsRec = new Rectangle[Main.getWorldsize() / Main.getTilesize()][Main.getWorldsize() / Main.getTilesize()];
	
	public Dots(){
		
	}
	
	public void init(){
		for (int x = 0; x < Main.getWorldsize() / Main.getTilesize(); x++){
			for (int y = 0; y < Main.getWorldsize() / Main.getTilesize(); y++){
				if (Game.getMap().getTileProperty(Game.getMap().getTileId(x, y, 0), "Dot", "nope").equals("true")){
					dots[x][y] = Game.getSheet().getSprite(9, 0);
					dotsRec[x][y] = new Rectangle(x * Main.getTilesize() + Main.getTilesize()/4, y * Main.getTilesize() + Main.getTilesize()/4, Main.getTilesize()/2, Main.getTilesize()/2);
				}
				if (Game.getMap().getTileProperty(Game.getMap().getTileId(x, y, 0), "BigDot", "nope").equals("true")){
					bigDots[x][y] = Game.getSheet().getSprite(10, 0);
					bigDotsRec[x][y] = new Rectangle(x * Main.getTilesize(), y * Main.getTilesize(), Main.getTilesize(), Main.getTilesize());
				}
			}
		}
	}
	
	public void render(){
		for (int x = 0; x < Main.getWorldsize() / Main.getTilesize(); x++){
			for (int y = 0; y < Main.getWorldsize() / Main.getTilesize(); y++){
				if (dots[x][y] != null) dots[x][y].draw(x * Main.getTilesize(), y * Main.getTilesize());
				if (bigDots[x][y] != null) bigDots[x][y].draw(x * Main.getTilesize(), y * Main.getTilesize());
			}
		}
	}
	
	public void update(){
		for (int x = 0; x < Main.getWorldsize() / Main.getTilesize(); x++){
			for (int y = 0; y < Main.getWorldsize() / Main.getTilesize(); y++){
				if (dotsRec[x][y] != null && Game.getPlayer().getMoveBox().intersects(dotsRec[x][y])){
					dots[x][y] = null;
					dotsRec[x][y] = null;
					HUD.score += 10;
					if (HUD.score >= Main.highscore) Main.highscore += 10;
					Game.getPlayer().dotsEaten++;
				}
				if (bigDotsRec[x][y] != null && Game.getPlayer().getMoveBox().intersects(bigDotsRec[x][y])){
					bigDots[x][y] = null;
					bigDotsRec[x][y] = null;
					Game.getPlayer().dotsEaten++;
					HUD.score += 50;
					if (HUD.score >= Main.highscore) Main.highscore += 50;
					for (int i = 0; i < Game.getGhosts().length; i++){
						Game.getGhosts()[i].scare();
					}
				}
			}
		}
	}
	
}
