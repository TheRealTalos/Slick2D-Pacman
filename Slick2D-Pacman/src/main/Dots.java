package main;

import java.awt.Rectangle;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Dots {
	
	public static Image[][] dots = new Image[Pacman.getWorldsize()][Pacman.getWorldsize()];
	private static Rectangle[][] dotsRec = new Rectangle[Pacman.getWorldsize() / Pacman.getTilesize()][Pacman.getWorldsize() / Pacman.getTilesize()];
	
	public Dots(){
		
	}
	
	public void init(){
		for (int x = 0; x < Pacman.getWorldsize() / Pacman.getTilesize(); x++){
			for (int y = 0; y < Pacman.getWorldsize() / Pacman.getTilesize(); y++){
				//if (Game.getMap().getTileProperty(Game.getMap().getTileId(x, y, 0), "Dot", "nope").equals("true")){
					dots[x][y] = Game.getSheet().getSprite(9, 0);
					dotsRec[x][y] = new Rectangle(x * Pacman.getTilesize(), y * Pacman.getTilesize(), Pacman.getTilesize()/2, Pacman.getTilesize()/2);
				//}
			}
		}
	}
	
	public void render(){
		for (int x = 0; x < Pacman.getWorldsize() / Pacman.getTilesize(); x++){
			for (int y = 0; y < Pacman.getWorldsize() / Pacman.getTilesize(); y++){
				if (dots[x][y] != null) dots[x][y].draw(x * Pacman.getTilesize(), y * Pacman.getTilesize());
			}
		}
	}
	
	public void update(){
		for (int x = 0; x < Pacman.getWorldsize() / Pacman.getTilesize(); x++){
			for (int y = 0; y < Pacman.getWorldsize() / Pacman.getTilesize(); y++){
				if (dotsRec[x][y] != null && Game.getPlayer().getPacBox().intersects(dotsRec[x][y])){
					dots[x][y] = null;
					dotsRec[x][y] = null;
					Hud.score += 10;
				}
			}
		}
	}
	
}
