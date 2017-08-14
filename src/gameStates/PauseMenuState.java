package gameStates;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

import globals.Globals;

public class PauseMenuState extends GameStateTemplate{
	
	private int Pause_Control		= Input.KEY_ESCAPE;	
	
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
		if(container.getInput().isKeyPressed(Pause_Control))
			this.switchGameState(container, game, Globals.GAME_ENGINE_STATE_ID);
		
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
