package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
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
			pacFont = new UnicodeFont(Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/emulogic.ttf")), 21, false, false);
			pacFont.addAsciiGlyphs();
			pacFont.getEffects().add(new ColorEffect(Color.WHITE));
			pacFont.loadGlyphs();
			System.out.println("Font Initialized");
		} catch (FontFormatException | IOException | SlickException e) {
			System.out.println("Font Not Initialized");
			e.printStackTrace();
		}
		
	}
	
	public void render(Graphics g){
		
		pacFont.drawString(20, 330, Integer.toString(score));
		pacFont.drawString(120, 330, "HIGH " + Integer.toString(score));
		
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