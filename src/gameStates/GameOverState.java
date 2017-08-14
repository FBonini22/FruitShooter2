package gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import globals.Globals;

public class GameOverState extends GameStateTemplate{
	
	
	//Constants
	
	//Instance Variables
	private int pNum = 1;
	private Input input;
	

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		//Draw GAME-OVER message
		g.drawString("GAME OVER", 50, 50);
		g.drawString("Player " + String.valueOf(pNum) + " has died!", 50, 65);
		g.drawString("Press ESC to exit.", 50, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		input = gc.getInput();
		
//TODO: For future implementation: add ability to restart game from inside the program
//		if(input.isKeyPressed(Input.KEY_SPACE)){
//			switchGameState(gc, sbg, Globals.CHARACTER_SELECT_STATE_ID);
//		}
		
		//Exit the game
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			gc.exit();
		}
		
	}

	@Override
	public void disposeObjects() {
		//NOT USED
	}

	@Override
	public int getID() {
		return Globals.GAME_OVER_STATE_ID;
	}
}
