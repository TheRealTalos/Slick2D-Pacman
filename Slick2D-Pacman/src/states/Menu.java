package states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import main.Main;

public class Menu extends BasicGameState{
	
	private int STATE;
	
	private static Image playImage;
	private static Image playAgainImage;
	private static Image tutorialImage;
	
	public static boolean playAgain = false;
	public static boolean tutorial = false;
	
	public Menu(int state){
		STATE = state;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		playImage = new Image("res/maps/Menu.png");
		playAgainImage = new Image("res/maps/PlayAgain.png");
		tutorialImage = new Image("res/maps/Guide.png");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException {
		if (tutorial){
			tutorialImage.draw();
		}else if (playAgain){
			playAgainImage.draw();
		}else {
			playImage.draw();
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		Input input = gc.getInput();
		
		if (input.isMousePressed(0)){
			if (input.getMouseY() > Main.getWorldsize() && !tutorial){
				if (input.getMouseX() > Main.getWorldsize()/2){
					Main.highscore = 0;
					Main.setHighscore();
				}else {
					tutorial = true;
				}
			}else if (tutorial){
				tutorial = false;
			}else {
				sbg.enterState(1);
			}
		}
		
	}

	@Override
	public int getID() {
		return STATE;
	}

}
