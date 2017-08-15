package globals;

public class Globals {


	public static boolean DEBUGGING = false;			//Change this variable to true for debugging mode.
	public static boolean INVINCIBLE = false;		//Setting to true will disable removal of the player entity
	public static boolean MULTIPLAYER = false;		//Setting to true will enable multiplayer features. TODO: implement multiplayer features

	
	public static final String GAME_TITLE = "Fruit Shooter";
	public static final String GAME_VERSION = "1.0a";
	
	//DIMENSIONAL GLOBALS
	
	public static final float BULLET_WIDTH = 24f;	//Bullet width
	public static final float BULLET_HEIGHT = 15f;	//Bullet height
	
	

	public static final float PLAYER_WIDTH = 48f;
	public static final float PLAYER_HEIGHT = 48f;
	
	public static final float GRUNT_WIDTH = 48f;			//Grunt Width
	public static final float GRUNT_HEIGHT = 48f;			//Grunt Height
	
	public static final float BOSS_HEIGHT1 = 235f;		//Height for the fluffy squirrel
	public static final float BOSS_WIDTH1 = 326f;			//Width for the fluffy squirrel
	public static final float BOSS_HEIGHT2 = 300f;		//Height for the fighting squirrel
	public static final float BOSS_WIDTH2 = 212f;			//Width for the fighting squirrel
	
	public static final float BOMB_WIDTH = 24f;			//Width of the bomb
	public static final float BOMB_HEIGHT = 24f;			//Height of the bomb
	
	
	//Variables for timer based game
	public static final int TIMER = 5000;			// Changed for testing purposes. Should be 1000.
	public static final int BossTimer = 60000;		// Changed for testing purposes. Should be 60000.
	
	//GAMEPLAY MECHANICS
	public static final int gruntValue = 100;
	public static final int bossValue = 1000;			
	public static final int LevelChange = 61000;		// Changed for testing purposes. Should be 61000.
	public static final float LevelChangeAmount = 0.25f; // Determine how much the health changes
	public static float Delay = 1000f;			//Time between spawning of enemies once spawning method is triggered by a boolean
	public static float reset = 5500f;		//Time until the spawning method is reset
	public static final int spawnMethods = 9;		//Variable that determines how many spawning methods are available to be used. Increasing this will increase the number of possibilities that the random number generator can pick

	//GAME STATE IDS
	public static final int MAIN_SCREEN_STATE_ID = 999;
	public static final int CHARACTER_SELECT_STATE_ID = 1000;
	public static final int GAME_ENGINE_STATE_ID = 1001;
	public static final int GAME_OVER_STATE_ID = 1002;
	public static final int PAUSE_GAME_STATE_ID = 1003;


}
