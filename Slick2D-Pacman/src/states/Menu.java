package states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState{
	
	private int STATE;
	
	private static Image menu;
	
	public Menu(int state){
		STATE = state;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		menu = new Image("res/maps/menu.png");
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		
		menu.draw();
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		Input input = gc.getInput();
		
		if (input.isMousePressed(0)){
			sbg.enterState(1);
		}
		
	}

	@Override
	public int getID() {
		return STATE;
	}

}
