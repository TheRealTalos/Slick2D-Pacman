package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.util.ResourceLoader;

import main.Main;
import states.Game;

public class HUD {
	public static int score;

	private boolean first;
	
	private List<TextObject> texts = new ArrayList<TextObject>();
	private static List<Image> lives = new ArrayList<Image>();
	
	public static UnicodeFont whiteFont;
	public static UnicodeFont blueFont;
	public static UnicodeFont yellowFont;
	public static UnicodeFont blackFont;
	
	
	private Sound readySound;
	
	public HUD() {

	}

	public void gameInit(){
		try{
			readySound = new Sound("/res/sounds/Ready.ogg");
		}catch (SlickException e){
			e.printStackTrace();
		}
		score = 0;
		restart();
		
		for (int i = 0; i < 2; i++){
			lives.add(Game.getSheet().getSprite(5, 0));
		}
	}
	
	public void menuInit() {
		whiteFont = createFont(18, Color.WHITE);
		blueFont = createFont(12, Color.CYAN);
		yellowFont = createFont(15, Color.YELLOW);
		blackFont = createFont(18, Color.BLACK);
	}
	
	public void restart(){
		draw(125, 173, "Ready!", 2, yellowFont);
		first = true;
	}

	public void gameRender(Graphics g) {
		whiteFont.drawString(20, 330, Integer.toString(score));
		whiteFont.drawString(140, 330, "HIGH " + Integer.toString(Main.highscore));
		whiteFont.drawString(140, 370, "LVL " + Integer.toString(Game.level));
		
		if (!readySound.playing() && first) {
			readySound.play();
			first = false;
		}
		
		for (int i = 0; i < texts.size(); i++){
			texts.get(i).getFont().drawString(texts.get(i).getX(), texts.get(i).getY(), texts.get(i).getText());
			if (Game.getTimer().getTime() > texts.get(i).getDisplayTime())
				texts.remove(i);
		}
		
		for (int i = 0; i < lives.size(); i++){
			lives.get(i).draw(20*i + 20, 380);
		}
	}
	
	public void menuRender(){
		blackFont.drawString(0, 0, "HIGH " + Integer.toString(Main.highscore));
	}
	
	public void removeLife(){
		lives.remove(lives.size()-1);
	}
	
	public void draw(int x, int y, String text, int displayTime, UnicodeFont font){
		texts.add(new TextObject(x, y, text, displayTime, font));
	}
	
	@SuppressWarnings("unchecked")
	private UnicodeFont createFont(int size, Color colour){
		UnicodeFont font = null;
		try{ 
			font = new UnicodeFont(
					Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/emulogic.ttf")), size,
					false, false);
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect(colour));
			font.loadGlyphs();
		} catch (FontFormatException | IOException | SlickException e) {
			System.out.println("Font(s) Not Initialized");
			e.printStackTrace();
		}
		return font;
	}
}