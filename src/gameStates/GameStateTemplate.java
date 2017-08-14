package gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Class that extends BasicGameState, but contains extra methods that must be overridden.
 * All game states should extend this class. PLEASE DO NOT MAKE THE ENGINE CLASS EXTEND THIS YET
 * @author Frank
 *
 */
public abstract class GameStateTemplate extends BasicGameState{

	
	public GameStateTemplate(){
		super();
	}
	
	/**
	 * To override. Method for disposing objects in the current game state
	 * in order to free-up memory.
	 */
	public abstract void disposeObjects();
	
	
	/**
	 * Method for handling switching game states. Automatically clears key-press records
	 * @param gc The game container of the current game
	 * @param sbg The current instance of the state based game
	 * @param ID The ID of the game state to switch to
	 */
	public void switchGameState(GameContainer gc, StateBasedGame sbg, int ID){
		gc.getInput().clearKeyPressedRecord();
		sbg.enterState(ID);
	}
}
