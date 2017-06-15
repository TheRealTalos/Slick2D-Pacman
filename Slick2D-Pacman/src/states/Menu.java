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
	
	private static Image play;
	private static Image playAgain;
	private static Image tutorial;
	
	private static boolean playAgainn = false;
	private static boolean tutoriall = false;
	
	public Menu(int state){
		STATE = state;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		play = new Image("res/maps/Menu.png");
		playAgain = new Image("res/maps/PlayAgain.png");
		tutorial = new Image("res/maps/Guide.png");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		if (tutoriall){
			tutorial.draw();
		}else if (playAgainn){
			playAgain.draw();
		}else {
			play.draw();
		}
		
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
