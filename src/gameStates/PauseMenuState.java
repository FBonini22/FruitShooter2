package gameStates;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

import globals.Globals;
import view.GameWindow;

public class PauseMenuState extends GameStateTemplate{
	
	//CONSTANTS
	private final int NUM_MENU_CHOICES = 4;						//The number of menu choices available
	private final float CURSOR_WIDTH = GameWindow.SCREEN_WIDTH * 0.5f;
	private final float CURSOR_HEIGHT = 24f;
	private final float STARTING_X = GameWindow.SCREEN_WIDTH * 0.25f;		//The starting X value for ALL menu options
	private final float STARTING_Y = GameWindow.SCREEN_HEIGHT * 0.25f;		//The starting Y value for the FIRST menu option
	private final float Y_INCREMENT = 1;
	
	//Instance Variables
	private final String[] choices = {"Resume","Options","Quit"};
	List<TextField> menuChoices = new ArrayList<TextField>();
	

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		for(int i = 0; i < choices.length; i++){
			//TextField t = new TextField(container, null, 50, , i, i)
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Globals.PAUSE_GAME_STATE_ID;
	}
	
	@Override
	public void disposeObjects(){
		
	}

}
