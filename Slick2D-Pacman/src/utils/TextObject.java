package utils;

import org.newdawn.slick.UnicodeFont;

public class TextObject {
	
	private int x;
	private int y;
	private String text;
	private int displayTime;
	private UnicodeFont font;

	public TextObject(int x, int y, String text, int displayTime, UnicodeFont font) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.displayTime = displayTime;
		this.font = font;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getText() {
		return text;
	}

	public int getDisplayTime() {
		return displayTime;
	}

	public UnicodeFont getFont() {
		return font;
	}
	
	
	
}
