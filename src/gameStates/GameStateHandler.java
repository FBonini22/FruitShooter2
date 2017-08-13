package gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
/**
 * Class for handling all of the game's states. This is run from the main class.
 * @author Frank
 *
 */
public class GameStateHandler extends StateBasedGame{

	/**
	 * Main constructor for the GameStateHandler class
	 * @param gameName Name to display on the game window
	 */
	public GameStateHandler(String gameName) {
		super(gameName);
		// TODO Auto-generated constructor stub
	}


	/**
	 * This method is automatically called ONCE. Add game states here
	 * @param The game container/window in which the game is currently running
	 */
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new CharacterSelectState());
		this.addState(Engine.instance);
		
		//this.addState(new );
		
	}

}
