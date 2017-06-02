package utils;

import java.awt.Color;

import org.newdawn.slick.UnicodeFont;

public class TextObject {
	
	private static int x;
	private static int y;
	private static String text;
	private static int displayTime;
	private static UnicodeFont font;

	public TextObject(int x, int y, String text, int displayTime, UnicodeFont font) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.displayTime = displayTime;
		this.font = font;
	}

	public static int getX() {
		return x;
	}

	public static int getY() {
		return y;
	}

	public static String getText() {
		return text;
	}

	public static int getDisplayTime() {
		return displayTime;
	}

	public static UnicodeFont getFont() {
		return font;
	}
	
	
	
}
