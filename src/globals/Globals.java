package globals;

public class Globals {


	public static boolean DEBUGGING = true;			//Change this variable to true for debugging mode.
	public static boolean INVINCIBLE = true;

	
	
	
	//DIMENSIONAL GLOBALS
	
	public static final float BULLET_WIDTH = 24f;
	public static final float BULLET_HEIGHT = 15f;
	
	/*
	//DO NOT USE THESE VARIABLES YET
	public static float PLAYER_WIDTH = 48f;
	public static float PLAYER_HEIGHT = 48f;
	*/
	public static float GRUNT_WIDTH = 48f;
	public static float GRUNT_HEIGHT = 48f;
	
	public static float BOSS_HEIGHT1 = 355f;
	public static float BOSS_WIDTH1 = 355f;
	public static float BOSS_HEIGHT2 = 300f;
	public static float BOSS_WIDTH2 = 212f;
	
	public static float BOMB_WIDTH = 24f;
	public static float BOMB_HEIGHT = 24f;
	
	
	//Variables for timer based game
	public static final int TIMER = 5000;			//TODO Changed for testing purposes. Should be 1000.
	public static final int BossTimer = 10000;		//TODO Changed for testing purposes. Should be 60000.
	
	//GAMEPLAY MECHANICS
	public static final int gruntValue = 1;
	public static final int bossValue = 10;			
	public static final int LevelChange = 2000;		//TODO Changed for testing purposes. Should be 61000.

}
