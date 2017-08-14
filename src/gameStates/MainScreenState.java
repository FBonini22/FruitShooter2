package gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import globals.Globals;
import view.GameWindow;

public class MainScreenState extends GameStateTemplate{
	
	//CONSTANTS
	
	
	//INSTANCE VARIABLES
	private Input input;
	
	//Display
	private String welcomeMsg = "Welcome to " + Globals.GAME_TITLE + Globals.GAME_VERSION + "!";
	private String contMsg = "Press SPACE to continue...";
	
	private float textX = 50;
	private float textY = 50;
	
	
	public MainScreenState(){
		super();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		
		//Display a title image
		//Have a background as well
		
		g.drawString(welcomeMsg, textX, textY);
		g.drawString(contMsg, textX, textY + 50);
		
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		input = gc.getInput();
		
		if(input.isKeyPressed(Input.KEY_SPACE)){
			switchGameState(gc, game, Globals.CHARACTER_SELECT_STATE_ID);
		}
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Globals.MAIN_SCREEN_STATE_ID;
	}

	@Override
	public void disposeObjects() {
		// TODO Auto-generated method stub
		
	}



	
	

}
