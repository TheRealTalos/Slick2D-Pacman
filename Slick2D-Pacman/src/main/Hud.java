package main;

import org.newdawn.slick.Graphics;

public class Hud {
	
	public static int score = 0;

	public Hud(){
		
	}
	
	public void init(){
		
	}
	
	public void render(Graphics g){
		
		g.drawString(Integer.toString(score), 50, 340);
		g.drawString("HIGH  " + Integer.toString(score), 150, 340);
		
	}
	
	public void update(){
		
	}
	
//	public void incScore(){
//		score++;
//	}
//	
//	public void setScore(int s){
//		score = s;
//	}
	
}
