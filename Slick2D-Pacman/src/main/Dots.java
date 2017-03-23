package main;

import java.awt.Rectangle;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Dots {
	
	public static Image[][] dots = new Image[Pacman.getWorldsize()][Pacman.getWorldsize()];
	public static Rectangle[][] dotsRec = new Rectangle[Pacman.getWorldsize()][Pacman.getWorldsize()];

	public Dots(){
		
	}
	
	public void init(){
		for (int x = 0; x < Pacman.getWorldsize() / Pacman.getTilesize(); x++){
			for (int y = 0; y < Pacman.getWorldsize() / Pacman.getTilesize(); y++){
				dots[x][y] = Pacman.getSheet().getSprite(0, 1);
				dotsRec[x][y] = new Rectangle(x, y, Pacman.getTilesize(), Pacman.getTilesize());
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
				if (Pacman.getPlayer().getPacBox().intersects(dotsRec[x][y])){
					System.out.println("manlymen");
					dots[x][y] = null;
				}
			}
		}
	}
	
}
