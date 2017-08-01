package entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.SlickException;

import Utilities.D;
import view.*;

public class Player extends Entity{
	
	//Controls
	private int UP_CONTROL = Input.KEY_UP;
	private int DOWN_CONTROL = Input.KEY_DOWN;
	private int LEFT_CONTROL = Input.KEY_LEFT;
	private int RIGHT_CONTROL = Input.KEY_RIGHT;
	private int FIRE_CONTROL = Input.KEY_SPACE;
	
	//Constants
	private final float startingX = (float)(GameWindow.SCREEN_WIDTH) / 2f;			//Player starts in the middle of the screen, horizontally
	private final float startingY = (float)(GameWindow.SCREEN_HEIGHT) * 0.90f;		//Player starts 10% up the screen
	private final float MOVEMENT_SPEED = 10f;
	private final double STARTING_HEALTH = 100f;									//Default starting player health
	private final int	HIT_COOLDOWN = 1000;										//Milliseconds before player can be hit again
	private final int	HIT_ANIM_FLASH = 200;										//Milliseconds between each flash of the player hit animation
	private final float PLAYER_HEIGHT = 48f;
	private final float PLAYER_WIDTH = 48f;
	private final int	DEFAULT_FIRE_COOLDOWN = 250;								//Milliseconds for the default time between each firing
	
	
	//Instance Variables
	private Input input;								//Variable that is called to poll for key inputs
	private FruitType _currentFruit;					//The user-selected fruit
	private int _playerNum;								//The player number. 1 or 2 ONLY
	
	private String imgPath = "img/test.png";			//Path to image that will be loaded for this Player instance
	
	private double _health;								//Player health
	private int powerLevel = 3;							//Player power up level
	
	private boolean hitCoolingDown = false;				//Boolean to keep track of whether the player is in a cooling down state
	private int timeSinceLastHit = 0;					//Used to keep track of time since last collision with player
	private int timeSinceLastFlash = 0;					//Used for hit animation flashing
	private boolean isTransparent = false;
	
	private boolean firing = false;
	private boolean isInFiringCooldown = false;
	private int timeSinceLastFire = 0;
	
	private int _fireCooldown = DEFAULT_FIRE_COOLDOWN;
	
	
	/**
	 * Main constructor for the Player Class.
	 * @param selectedFruit The fruit that the player has selected. This will be used to determine attributes for the 
	 * current player
	 * @param pNum Player Number. 1 or 2 ONLY
	 */
	public Player(FruitType selectedFruit, int pNum){

		super(0, 0, 64, 64);
		//Instantiate instance variables
		_currentFruit = selectedFruit;
		_playerNum = pNum;		
		//input = new Input(pNum);		//Instantiate input. Unknown function of integer. CHECK JAVADOC
		
		this.x = startingX;
		this.y = startingY;
		this.height = PLAYER_HEIGHT;
		this.width = PLAYER_WIDTH;
		
		InitializePlayerAttributes();
		InitializeControls();
	}
	

	/**
	 * Method for initializing player controls. SHOULD BE CALLED ONCE AND ONLY AFTER VARIABLE INSTANTIATION!
	 */
	private void InitializeControls(){
		D.BUG("Initializing player controls...");
		
		//TO DO: Initialize controls here
		if(_playerNum == 2){
			//Change the controls. Otherwise do nothing
		}
		
	}

	private void InitializePlayerAttributes(){
		D.BUG("Initializing player attributes...");
		
		//All attributes will be those of watermelon for the time being.
		switch(_currentFruit){
		case Apple:
			
			_fireCooldown = 750;
			
//			imgPath = "img/Apple.png";
//			break;
		case Banana:
//			imgPath = "img/Banana.png";
//			break;
		case Lemon:
//			imgPath = "img/Lemon.png";
//			break;
		case Watermelon:
			imgPath = "img/Watermelon.png";
			_health = STARTING_HEALTH;
			break;
		}
	}
	
	//Getters for Power Up bullet shooting
	public FruitType getFruit(){
		return _currentFruit;
	}
	public int getPowerLevel(){
		return powerLevel;
	}
	public void setFirecooldown(int num){
		_fireCooldown = num;
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		_entityImg = new Image(imgPath);
	}


	@Override
	public void render(GameContainer gc, Graphics g){
		
		//Draw the player at the default starting coordinates
		
		if(hitCoolingDown){
			hitAnim();
		}
		g.drawImage(_entityImg, x, y);	
		
		//TO DO: Make the player's health displayed as a health bar
		//Draw the player's health
		g.drawString(String.format("Player Health: %1$s", String.valueOf((float)_health)), 16, 32);
		

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
			if(timeSinceLastFire >= _fireCooldown){
				timeSinceLastFire = 0;
				isInFiringCooldown = false;
			}
		}


	}
	
	
	/**
	 * Method to check for user input
	 */
	private void checkForUserInput(){

		if(input.isKeyDown(UP_CONTROL)){
			this.moveBy(0, -MOVEMENT_SPEED);
		}
		else if(input.isKeyDown(DOWN_CONTROL)){
			this.moveBy(0, MOVEMENT_SPEED);
		}
		
		if(input.isKeyDown(LEFT_CONTROL)){
			this.moveBy(-MOVEMENT_SPEED, 0);
		}
		else if(input.isKeyDown(RIGHT_CONTROL)){
			this.moveBy(MOVEMENT_SPEED, 0);
		}
		
		if(input.isKeyDown(FIRE_CONTROL)){
			fireBullet();
		}
		else{
			firing = false;
		}
	}
	
	//Get Set for Player Firing State
	public boolean getFiring(){
		return firing && !isInFiringCooldown;
	}

	private void fireBullet(){
		firing = true;
		if(!isInFiringCooldown){
			timeSinceLastFire = 0;
			isInFiringCooldown = true;
		}
		else{
			return;
		}
		
		
		switch(_currentFruit){
		case Apple:
			
//			break;
		case Banana:

//			break;
		case Lemon:

//			break;
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
		
		
		
		//If the player wasn't just previously hit
		if(!hitCoolingDown){
			String collisionType = collidedWith.getClass().getSimpleName();
			D.BUG("Player collided with: " + collisionType);
			
			switch(collisionType){
			
			case "Enemy":
				
				Enemy e = (Enemy)collidedWith;
				
				switch(e.getEnemyType()){
					case Squirrel:
						_health -= 5d;
						break;
					case JumboSquirrel:
						break;
				}			
				break;
			case "EnemyBullet":
				
				_health -= 5d;
				break;
			
			}
			
			hitCoolingDown = true;
			timeSinceLastHit = 0;
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


