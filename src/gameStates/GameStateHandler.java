package gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import engine.Engine;
/**
 * Class for handling all of the game's states. This is by the main class and is considered the entire "Game".
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
	}


	/**
	 * This method is automatically called ONCE. Add game states here.
	 * This method initializes all of the game states that will be used by the game.
	 * @param gc The game container/window in which the game is currently running
	 */
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.addState(new MainScreenState());
		this.addState(new CharacterSelectState());
		this.addState(Engine.instance);
		this.addState(new GameOverState());
		this.addState(new PauseMenuState());

	}

}
