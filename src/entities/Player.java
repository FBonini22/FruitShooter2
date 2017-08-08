
package entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

import Utilities.D;
import globals.Globals;
import sounds.*;
import view.Engine;
import view.GameWindow;

public class Player extends Entity{
	
	//Controls
	private int UP_CONTROL		= Input.KEY_UP;
	private int DOWN_CONTROL 	= Input.KEY_DOWN;
	private int LEFT_CONTROL 	= Input.KEY_LEFT;
	private int RIGHT_CONTROL 	= Input.KEY_RIGHT;
	private int FIRE_CONTROL 	= Input.KEY_SPACE;
	private int BOMB_CONTROL 	= Input.KEY_LCONTROL;
	
	//Constants
	private final float startingX = (float)(GameWindow.SCREEN_WIDTH) / 2f;			//Player starts in the middle of the screen, horizontally
	private final float startingY = (float)(GameWindow.SCREEN_HEIGHT) * 0.90f;		//Player starts 10% up the screen
	private final float DEFAULT_MOVEMENT_SPEED = 10f;
	private final double STARTING_HEALTH = 100f;									//Default starting player health
	private final int	HIT_COOLDOWN = 1000;										//Milliseconds before player can be hit again
	private final int	HIT_ANIM_FLASH = 200;										//Milliseconds between each flash of the player hit animation
	private final float PLAYER_HEIGHT = 48f;
	private final float PLAYER_WIDTH = 48f;

	private final int	DEFAULT_FIRE_COOLDOWN = 250;								//Milliseconds for the default time between each firing
	private final int	DEFAULT_BOMB_COOLDOWN = 3000;								//Milliseconds for the cooldown for throwing a cleaing bomb

	
	
	//Instance Variables
	private Input input;								//Variable that is called to poll for key inputs
	private FruitType currentFruit;					//The user-selected fruit
	private int playerNum;								//The player number. 1 or 2 ONLY
	
	private String imgPath = "img/test.png";			//Path to image that will be loaded for this Player instance
	private double health;								//Player health
	private int powerLevel = 1;							//Player power up level
	private float movementSpeed = DEFAULT_MOVEMENT_SPEED;
	
	private boolean hitCoolingDown = false;				//Boolean to keep track of whether the player is in a cooling down state
	private int timeSinceLastHit = 0;					//Used to keep track of time since last collision with player
	private int timeSinceLastFlash = 0;					//Used for hit animation flashing
	private boolean isTransparent = false;
	
	private boolean firing = false;
	private boolean isInFiringCooldown = false;
	private int timeSinceLastFire = 0;
	
	private boolean isInBombCooldown = false;
	private int timeSinceLastBomb = 0;
	
	private int fireCooldown = DEFAULT_FIRE_COOLDOWN;
	private int bombCooldown = DEFAULT_BOMB_COOLDOWN;
	
	private int numBombs = 0;
	
	private boolean isDead = false;
	
	private Image healthBarBackground;
	private Image healthBar;
	
	private int points = 0;
	
	//Audio
	Sound fireSound;
	Sound hitSound;
	Sound powerUpSound;
	
	
	/**
	 * Main constructor for the Player Class.
	 * @param selectedFruit The fruit that the player has selected. This will be used to determine attributes for the 
	 * current player
	 * @param pNum Player Number. 1 or 2 ONLY
	 */
	public Player(FruitType selectedFruit, int pNum){

		super(0, 0, 64, 64);
		//Instantiate instance variables
		currentFruit = selectedFruit;
		playerNum = pNum;		
		//input = new Input(pNum);		//Instantiate input. Unknown function of integer. CHECK JAVADOC
		
		this.x = startingX;
		this.y = startingY;
		this.height = PLAYER_HEIGHT;
		this.width = PLAYER_WIDTH;
		
		InitializePlayerAttributes();
		InitializeControls();
		initializeAudio();
	}
	

	/**
	 * Method for initializing player controls. SHOULD BE CALLED ONCE AND ONLY AFTER VARIABLE INSTANTIATION!
	 */
	private void InitializeControls(){
		D.BUG("Initializing player controls...");
		
		//TO DO: Initialize controls here
		if(playerNum == 2){
			//Change the controls. Otherwise do nothing
		}
		
	}

	/**
	 * Local method for initializing the player's attributes. SHOULD BE CALLED ONCE AND ONLY AFTER VARIABLE INSTANTIATION!
	 */
	private void InitializePlayerAttributes(){
		D.BUG("Initializing player attributes...");
		
		//All attributes will be those of watermelon for the time being.
		switch(currentFruit){
		case Apple:
			
			fireCooldown = DEFAULT_FIRE_COOLDOWN;
			
			imgPath = "img/Apple.png";
			health = STARTING_HEALTH;
			break;
		case Banana:
			

			fireCooldown = 200;

			
			imgPath = "img/Banana.png";
			health = STARTING_HEALTH * 0.5f;
			break;
		case Lemon:
			
			fireCooldown = 500;
			
			imgPath = "img/Lemon.png";
			health = STARTING_HEALTH * 0.75f;
			break;
		case Watermelon:
			imgPath = "img/Watermelon.png";
			health = STARTING_HEALTH * 1.5f;
			break;
		}
	}
	
	private void initializeAudio(){
		try{
			fireSound = new Sound(FX.PLAYER_FIRE);
			hitSound = new Sound(FX.PLAYER_HIT);
			//powerUpSound = new Sound(SoundEffects.POWERUP);			
		}
		catch(Exception e){
			
		}

	}
	
	
	//Getters for Power Up bullet shooting
	public FruitType getFruit(){
		return currentFruit;
	}
	public int getPowerLevel(){
		return powerLevel;
	}
	public void setFirecooldown(int num){
		fireCooldown = num;
	}
	public void setPowerlevel(int num){
		powerLevel = num;
	}
	
	public float getHealth(){
		return (float) health;
	}
	
	public boolean playerIsDead(){
		return isDead;
	}
	
	public void setPoints(int point){
		points += point;
	}
	public int getPoints(){
		return points;
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		_entityImg = new Image(imgPath);
		healthBarBackground = new Image("img/Health_Bar_Background.png");
		healthBar = new Image("img/Health_Bar_Foreground.png");
	}


	@Override
	public void render(GameContainer gc, Graphics g){
		
		//Draw the player at the default starting coordinates
		
		if(hitCoolingDown){
			hitAnim();
		}
		g.drawImage(_entityImg, x, y);	
		

		drawHealthBar(g);

	}

	
	@Override
	public void update(GameContainer gc, int delta) {
		input = gc.getInput();
		checkForUserInput();
		
		
		//If the player was just hit, alter graphics and enter cooldown check
		if(hitCoolingDown){
			timeSinceLastHit += delta;
			timeSinceLastFlash += delta;
			if(timeSinceLastHit >= HIT_COOLDOWN){
				hitCoolingDown = false;
				timeSinceLastHit = 0;
				timeSinceLastFlash = 0;
				isTransparent = false;
			}
			//D.BUG(String.valueOf(timeSinceLastHit));
		}
		
		//Manage firing cooldown
		if(isInFiringCooldown){
			timeSinceLastFire += delta;
			if(timeSinceLastFire >= fireCooldown){
				timeSinceLastFire = 0;
				isInFiringCooldown = false;
			}
		}

		//Manage firing cooldown
		if(isInBombCooldown){
			timeSinceLastBomb += delta;
			if(timeSinceLastBomb >= bombCooldown){
				timeSinceLastBomb = 0;
				isInBombCooldown = false;
			}
		}

		checkHealth();
	}
	
	//GRAPHICAL AND HUD
	private void drawHealthBar(Graphics g){
		//TO DO: Make the player's health displayed as a health bar
		//Draw the player's health
//		g.drawString(String.format("Player Health: %1$s", String.valueOf((float)health)), 16, 32);
//		g.setColor(Color.red);
//		g.drawRect(GameWindow.SCREEN_WIDTH - (GameWindow.HBAR_WIDTH + 3), 5, GameWindow.HBAR_WIDTH, GameWindow.HBAR_HEIGHT);
//		
//		g.setColor(Color.green);
//		g.drawRect(GameWindow.SCREEN_WIDTH - (GameWindow.HBAR_WIDTH + 3), 5, GameWindow.HBAR_WIDTH, (float) (GameWindow.HBAR_HEIGHT * (health / STARTING_HEALTH)));

//		g.drawImage(healthBarBackground, GameWindow.SCREEN_WIDTH - (GameWindow.HBAR_WIDTH + 3), 5, GameWindow.SCREEN_WIDTH - 3, GameWindow.HBAR_HEIGHT, DEFAULT_MOVEMENT_SPEED, DEFAULT_MOVEMENT_SPEED);
	
		
	}
	
	//USER INPUT AND FIRING
	/**
	 * Method to check for user input
	 */
	private void checkForUserInput(){

		if(input.isKeyDown(UP_CONTROL)){
			this.moveBy(0, -movementSpeed);
		}
		else if(input.isKeyDown(DOWN_CONTROL)){
			this.moveBy(0, movementSpeed);
		}
		
		if(input.isKeyDown(LEFT_CONTROL)){
			this.moveBy(-movementSpeed, 0);
		}
		else if(input.isKeyDown(RIGHT_CONTROL)){
			this.moveBy(movementSpeed, 0);
		}
		
		if(input.isKeyDown(FIRE_CONTROL)){
			fireBullet();
		}
		else{
			firing = false;
		}
		
		if(input.isKeyDown(BOMB_CONTROL)){
			
		}
	}
	
	//Get Set for Player Firing State
	public boolean getFiring(){
		return firing && !isInFiringCooldown;
	}

	/**
	 * Method for firing a bullet
	 * @throws SlickException 
	 */
	private void fireBullet(){
		firing = true;
		if(!isInFiringCooldown){
			timeSinceLastFire = 0;
			isInFiringCooldown = true;
		}
		else{
			return;
		}
		
		//Play sound
		fireSound.play();

		
		//How the bullets will be handled depends on the current fruit that the player is
		switch(currentFruit){
		case Apple:
			if(getPowerLevel() == 1){
				Engine.instance.addEntity(new PlayerBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 0, -20, 1, 1));
			}
			
			break;
		case Banana:
			if(getPowerLevel() == 1){
				Engine.instance.addEntity(new PlayerBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 0, -20, 1, 1));
			}

			break;
		case Lemon:
			if(getPowerLevel() == 1){
				Engine.instance.addEntity(new PlayerBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 0, -20, 1, 1));
			}

			break;
		case Watermelon:
			if (getPowerLevel() >= 2){
				
				Engine.instance.addEntity(new PlayerBullet(x + 46, y + 10, 0, -20, 1, 1));

				Engine.instance.addEntity(new PlayerBullet(x - 16, y + 10, 0, -20, 1, 1));
			}
			if (getPowerLevel() >= 3){
				setFirecooldown(200);
			}
			break;
		}
//		for(Bullet e : bullets){
//			e.init(gc);
//		}
		
	}

	/**
	 * Method for firing a bomb
	 */
	private void fireBomb(){
		
	}
	
	
	@Override
	public boolean isDangerous() {

		return false;
	}
	
	/**
	 * Method to handle collisions with the player
	 * @param collidedWith The entity that has collided with this player instance
	 */
	public void onCollide(Entity collidedWith){
		
		//Handle collisions
		
		//Figure out what kind of collision
		//Subtract health
		//Play a sound

		String collisionType = collidedWith.getClass().getSimpleName();
		D.BUG("Player collided with: " + collisionType);
		
		//Switch for regular collisions
		switch(collisionType){
		
		case "PowerUp":		
			break;
		case "Collectible":
			break;
		}
		
		
		//If the player wasn't just previously hit
		if(!hitCoolingDown){

			//Play a sound
			hitSound.play();
			
			switch(collisionType){
			
			case "Enemy":
				
				Enemy e = (Enemy)collidedWith;
				
				switch(e.getEnemyType()){
					case Squirrel:
						health -= 5d;			//Damage that the grunt does
						break;
					case JumboSquirrel:
						health -= 10d;			//Damage that the boss does
						break;
				}			
				break;
			case "EnemyBullet":
				
				health -= 5d;
				break;
			}
			
			hitCoolingDown = true;
			timeSinceLastHit = 0;
		}
	}

	private void onDie(){
		isDead = true;
		
		
	}
	
	
	private void checkHealth(){
		
		if(!Globals.INVINCIBLE){
			if(health <= 0){
				onDie();
			}
		}		
	}
	
	
	

	//ANIMATIONS
	
	/**
	 * Method to call for a hit animation to be executed when the player is hit. Changes 
	 * opacity of player image, alternating. Call this only in the render() method
	 */
	public void hitAnim(){
		
		if(timeSinceLastFlash >= HIT_ANIM_FLASH){
			isTransparent = !isTransparent;
			timeSinceLastFlash = 0;
		}
		
		if(isTransparent){
			_entityImg.setAlpha(0.3f);
		}
		else{
			_entityImg.setAlpha(1.0f);
		}
		
	}
	
	




}



