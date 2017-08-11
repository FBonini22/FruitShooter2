package view;

/**
 * Class for the Game's window. Constants and container attributes should go here
 * @author Frank
 *
 */
public class GameWindow {
	
	//Constants
	public static final int SCREEN_WIDTH = 600;			//Width of the game screen in pixels
	public static final int SCREEN_HEIGHT = 900;		//Height of the game screen in pixels

	
	//---------------------
	//HUD
	//---------------------
	//Health Bar
	public static final float HBAR_WIDTH = 16;			//Width of the health bar
	public static final float HBAR_HEIGHT = SCREEN_HEIGHT * 0.95f;	//Height of the health bar
	
	//Bomb Layout
	public static final int BBAR_X = (int)((float)SCREEN_WIDTH * 0.1f);
	public static final int BBAR_Y = (int)((float)SCREEN_HEIGHT * 0.9f);
	
}
