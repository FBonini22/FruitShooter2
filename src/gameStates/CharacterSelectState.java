package gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import globals.Globals;

public class CharacterSelectState extends BasicGameState{

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.drawString("We are in the character selection screen", 50, 50);
		
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
		
		//Automatically go to the engine state for debugging
		if(Globals.DEBUGGING){
			game.enterState(Globals.GAME_ENGINE_STATE_ID);			
		}

		
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Globals.CHARACTER_SELECT_STATE_ID;
	}

}
