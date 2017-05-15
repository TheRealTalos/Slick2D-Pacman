package main;

import java.awt.Rectangle;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Dots {
	
	public static Image[][] dots = new Image[Pacman.getWorldsize()][Pacman.getWorldsize()];
	private static Rectangle[][] dotsRec = new Rectangle[Pacman.getWorldsize() / Pacman.getTilesize()][Pacman.getWorldsize() / Pacman.getTilesize()];
	
	public static Image[][] bigDots = new Image[Pacman.getWorldsize()][Pacman.getWorldsize()];
	private static Rectangle[][] bigDotsRec = new Rectangle[Pacman.getWorldsize() / Pacman.getTilesize()][Pacman.getWorldsize() / Pacman.getTilesize()];
	
	public Dots(){
		
	}
	
	public void init(){
		for (int x = 0; x < Pacman.getWorldsize() / Pacman.getTilesize(); x++){
			for (int y = 0; y < Pacman.getWorldsize() / Pacman.getTilesize(); y++){
				if (Game.getMap().getTileProperty(Game.getMap().getTileId(x, y, 0), "Dot", "nope").equals("true")){
					dots[x][y] = Game.getSheet().getSprite(9, 0);
					dotsRec[x][y] = new Rectangle(x * Pacman.getTilesize() + Pacman.getTilesize()/4, y * Pacman.getTilesize() + Pacman.getTilesize()/4, Pacman.getTilesize()/2, Pacman.getTilesize()/2);
				}
				if (Game.getMap().getTileProperty(Game.getMap().getTileId(x, y, 0), "BigDot", "nope").equals("true")){
					bigDots[x][y] = Game.getSheet().getSprite(10, 0);
					bigDotsRec[x][y] = new Rectangle(x * Pacman.getTilesize(), y * Pacman.getTilesize(), Pacman.getTilesize(), Pacman.getTilesize());
				}
			}
		}
	}
	
	public void render(){
		for (int x = 0; x < Pacman.getWorldsize() / Pacman.getTilesize(); x++){
			for (int y = 0; y < Pacman.getWorldsize() / Pacman.getTilesize(); y++){
				if (dots[x][y] != null) dots[x][y].draw(x * Pacman.getTilesize(), y * Pacman.getTilesize());
				if (bigDots[x][y] != null) bigDots[x][y].draw(x * Pacman.getTilesize(), y * Pacman.getTilesize());
			}
		}
	}
	
	public void update(){
		for (int x = 0; x < Pacman.getWorldsize() / Pacman.getTilesize(); x++){
			for (int y = 0; y < Pacman.getWorldsize() / Pacman.getTilesize(); y++){
				if (dotsRec[x][y] != null && Game.getPlayer().getMoveBox().intersects(dotsRec[x][y])){
					dots[x][y] = null;
					dotsRec[x][y] = null;
					Hud.score += 10;
					Game.getPlayer().dotsEaten++;
				}
				if (bigDotsRec[x][y] != null && Game.getPlayer().getMoveBox().intersects(bigDotsRec[x][y])){
					bigDots[x][y] = null;
					bigDotsRec[x][y] = null;
					Game.getRedGhost().setMode(Ghost.SCARED);
					Game.getPinkGhost().setMode(Ghost.SCARED);
					Game.getBlueGhost().setMode(Ghost.SCARED);
					Game.getOrangeGhost().setMode(Ghost.SCARED);
				}
			}
		}
	}
	
}
