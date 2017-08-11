package gameStates;

import org.newdawn.slick.state.BasicGameState;

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
}
