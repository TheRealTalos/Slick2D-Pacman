package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.util.ResourceLoader;

public class Hud {
	
	public static int score = 0;
	
	private static UnicodeFont pacFont;

	public Hud(){
		
	}
	
	public void init(){
	
		try {
			pacFont = new UnicodeFont(Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/emulogic.ttf")));
			pacFont.addAsciiGlyphs();
			pacFont.getEffects().add(new ColorEffect());
			System.out.println("Font Initialized");
		} catch (FontFormatException | IOException e) {
			System.out.println("Font Not Initialized");
			e.printStackTrace();
		}
		
	}
	
	public void render(Graphics g){
		
		pacFont.drawString(50, 340, Integer.toString(score));
		pacFont.drawString(150, 340, "HIGH  " + Integer.toString(score));
		
	}

//	public void update(){
//		
//	}
//	
//	public void incScore(){
//		score++;
//	}
//	
//	public void setScore(int s){
//		score = s;
//	}
	
}
