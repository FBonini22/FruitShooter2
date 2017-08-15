
package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;

import engine.Engine;
import globals.Globals;
import sounds.FX;
import utilities.D;
import view.GameWindow;

public class Player extends Entity{
	
	//Controls
	private int UP_CONTROL		= Input.KEY_UP;										//Up control movement
	private int DOWN_CONTROL 	= Input.KEY_DOWN;									//Down control movement
	private int LEFT_CONTROL 	= Input.KEY_LEFT;									//Left control movement
	private int RIGHT_CONTROL 	= Input.KEY_RIGHT;									//Right control movement
	private int FIRE_CONTROL 	= Input.KEY_SPACE;									//Shooting control movement
	private int BOMB_CONTROL 	= Input.KEY_LCONTROL;								//Bomb control movement
	
	//Constants
	private final float startingX = (float)(GameWindow.SCREEN_WIDTH) / 2f;			//Player starts in the middle of the screen, horizontally
	private final float startingY = (float)(GameWindow.SCREEN_HEIGHT) * 0.90f;		//Player starts 10% up the screen
	private final float DEFAULT_MOVEMENT_SPEED = 10f;								//Movement speed
	private final double STARTING_HEALTH = 100f;									//Default starting player health
	private final int	HIT_COOLDOWN = 1000;										//Milliseconds before player can be hit again
	private final int	HIT_ANIM_FLASH = 200;										//Milliseconds between each flash of the player hit animation
	private final float PLAYER_HEIGHT = 48f;										//Height of the player
	private final float PLAYER_WIDTH = 48f;											//Width of the player

	private final int	DEFAULT_FIRE_COOLDOWN = 250;								//Milliseconds for the default time between each firing
	private final int	DEFAULT_BOMB_COOLDOWN = 3000;								//Milliseconds for the cooldown for throwing a cleaing bomb

	private final int	BOMB_CAPACITY = 3;											//How many bombs the player may have at a given time
	
	
	//Instance Variables
	private Input input;															//Variable that is called to poll for key inputs
	private FruitType currentFruit;													//The user-selected fruit
	private int playerNum;															//The player number. 1 or 2 ONLY
	
	private String imgPath = "img/test.png";										//Path to image that will be loaded for this Player instance
	private double health;															//Player health
	private int powerLevel = 1;														//Player power up level
	private float movementSpeed = DEFAULT_MOVEMENT_SPEED;
	
	private boolean hitCoolingDown = false;											//Boolean to keep track of whether the player is in a cooling down state
	private int timeSinceLastHit = 0;												//Used to keep track of time since last collision with player
	private int timeSinceLastFlash = 0;												//Used for hit animation flashing
	private boolean isTransparent = false;											//Boolean to determine if something is transparent
	
	private boolean firing = false;													//Boolean to determine if the player is firing
	private boolean isInFiringCooldown = false;										//Boolean to determine if the player is in firing cooldown
	private int timeSinceLastFire = 0;												//Variable to determine how long since the player last fired
	
	private boolean isInBombCooldown = false;										//Boolean to determine if the player is in bomb cooldown
	private int timeSinceLastBomb = 0;												//Variable to determine how long since the player last used a bomb
	
	private int fireCooldown = DEFAULT_FIRE_COOLDOWN;								//Assign a default fire cooldown
	private int bombCooldown = DEFAULT_BOMB_COOLDOWN;								//Assign a default bomb cooldown
	
	private int numBombs = 0;														//Variable to keep track of the number of bombs
	
	private boolean isDead = false;													//Boolean to determine if the player is dead
	
	//private Image healthBarBackground;
	//private Image healthBar;
	
	private Rectangle healthBarBackground;
	private Rectangle healthBar;
	
	Random random = new Random();
	private int temp = 0;

	List<Image> bombBar = new ArrayList<Image>(BOMB_CAPACITY);	//ArrayList used to render the amount of bombs the player posesses
	
	private int points = 0;
	
	//Audio
	Sound fireSound;
	Sound hitSound;
	Sound powerUpSound;
	Sound bombSound;
	
	
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
			

			fireCooldown = 150;

			
			imgPath = "img/Banana.png";
			health = STARTING_HEALTH * 0.5f;
			break;
		case Lemon:
			
			fireCooldown = 200;
			
			imgPath = "img/Lemon.png";
			health = STARTING_HEALTH * 0.75f;
			break;
		case Watermelon:
			imgPath = "img/Watermelon.png";
			health = STARTING_HEALTH * 1.5f;
			break;
		}
		
		numBombs = BOMB_CAPACITY;
	}
	
	private void initializeAudio(){
		try{
			fireSound = new Sound(FX.PLAYER_FIRE);
			hitSound = new Sound(FX.PLAYER_HIT);
			bombSound = new Sound(FX.PLAYER_BOMB);
			powerUpSound = new Sound(FX.POWERUP_COLLECT);			
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
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	
	public int getPlayerNum(){
		return playerNum;
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		_entityImg = new Image(imgPath);
		//healthBarBackground = new Image("img/Health_Bar_Background.png");
		//healthBar = new Image("img/Health_Bar_Foreground.png");
		healthBarBackground = new Rectangle(GameWindow.SCREEN_WIDTH - (GameWindow.HBAR_WIDTH + 3), 5, GameWindow.HBAR_WIDTH, GameWindow.HBAR_HEIGHT);
		healthBar = new Rectangle(GameWindow.SCREEN_WIDTH - (GameWindow.HBAR_WIDTH + 3), 5, GameWindow.HBAR_WIDTH, GameWindow.HBAR_HEIGHT);

		initializeBombBar();
	}


	@Override
	public void render(GameContainer gc, Graphics g){
		
		
		//Anything drawn ABOVE this comment will appear UNDERNEATH the player
		
		
		//Draw the player at the default starting coordinates
		if(hitCoolingDown){
			//Run the hit animation
			hitAnim();
		}
		g.drawImage(_entityImg, x, y);	
		
		//Draw a rectangle around the player. USED FOR DEBUGGING ONLY
//		g.setColor(Color.green);
//		g.drawRect(x, y, width, height);
//		g.setColor(Color.white);

		
		//Anything drawn BELOW this comment will appear ON TOP of the player
		drawHealthBar(g);
		drawBombBar(g);

	}

	
	@Override
	public void update(GameContainer gc, int delta) {
		input = gc.getInput();
		checkForUserInput();
		
		manageCooldowns(delta);

		
		updateBombBar();
		updateHealthBar();

		//Check the player's health
		checkHealth();
	}
	
	//COOLDOWNS
	/**
	 * Method to manage all player cooldowns. Call during update method.
	 * @param delta
	 */
	private void manageCooldowns(int delta){
		
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

		//Manage bomb cooldown
		if(isInBombCooldown){
			timeSinceLastBomb += delta;
			if(timeSinceLastBomb >= bombCooldown){
				timeSinceLastBomb = 0;
				isInBombCooldown = false;
			}
		}
	}
	
	//GRAPHICAL AND HUD
	private void drawHealthBar(Graphics g){
		
		//Draw the player's health
		//TODO: move this string next to the health bar
		g.drawString(String.format("Player Health: %1$s", String.valueOf((float)health)), 16, 32);
		
		//Keep track of the graphic's previous color
		Color prevC = g.getColor();
		
		//Draw the portion of health remaining
		g.setColor(Color.green);
		g.fill(healthBar);
		g.draw(healthBar);
		

		//Draw health consumed
		g.setColor(Color.red);
		g.fill(healthBarBackground);
		g.draw(healthBarBackground);

		//Reset graphic's color
		g.setColor(prevC);
		
	}
	
	/**
	 * Method to update the size and position of the health bar
	 */
	private void updateHealthBar(){
		healthBarBackground.setHeight((float) (GameWindow.HBAR_HEIGHT * (1f - health / STARTING_HEALTH)));
	}
	
	/**
	 * Initializes the [bombBar] list. To be called ONLY ONCE during the [init] method.
	 * @throws SlickException
	 */
	private void initializeBombBar() throws SlickException{
		for(int i = 0; i < BOMB_CAPACITY; i++){
			bombBar.add(new Image("img/Bomb.png"));
		}
	}

	/**
	 * Method to draw the visual bomb bar. Call ONCE during the render() method
	 * @param g 
	 */
	private void drawBombBar(Graphics g){
		for(int i = 0; i < bombBar.size(); i++){
			
			int xOffset = (int)((float)i * Globals.BOMB_WIDTH);			//Offset for bomb position. Bombs are displayed horizontally
			
			g.drawImage(bombBar.get(i), (GameWindow.BBAR_X + xOffset), GameWindow.BBAR_Y);
		}
	}

	/**
	 * Method to update the Visual bomb bar. Call ONCE during the update() method
	 */
	private void updateBombBar(){
		for(Image bomb : bombBar){
			
			//If the player does not have this many bombs, draw the bomb as transparent
			if(bombBar.indexOf(bomb) + 1 > numBombs){
				bomb.setAlpha(0.3f);
			}
			else{
				bomb.setAlpha(1f);
			}
		}
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
			fireBomb();
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
			if(getPowerLevel() >= 1){
				Engine.instance.addEntity(new PlayerBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 0, -20, 1, 1, playerNum));
			}
			if (getPowerLevel() >= 2){
				Engine.instance.addEntity(new PlayerBullet(x + 46, y + 10, 5, -15, 1, 1, playerNum));
				Engine.instance.addEntity(new PlayerBullet(x - 16, y + 10, -5, -15, 1, 1, playerNum));
			}
			if (getPowerLevel() >= 3){
				Engine.instance.addEntity(new PlayerBullet(x + 46, y + 10, 10, -10, 1, 1, playerNum));
				Engine.instance.addEntity(new PlayerBullet(x - 16, y + 10, -10, -10, 1, 1, playerNum));
			}
			
			break;
		case Banana:
			if(getPowerLevel() >= 1){
				Engine.instance.addEntity(new PlayerBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 0, -20, 1, 1, playerNum));
			}
			if (getPowerLevel() >= 2){
				Engine.instance.addEntity(new PlayerBullet(x + 46, y + 10, 0, -20, 1, 1, playerNum));
				Engine.instance.addEntity(new PlayerBullet(x - 16, y + 10, 0, -20, 1, 1, playerNum));
			}
			if (getPowerLevel() >= 3){
				setFirecooldown(200);
			}

			break;
		case Lemon:
			if(getPowerLevel() >= 1){
				Engine.instance.addEntity(new PlayerBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, 0, -20, 1, 1, playerNum));
			}
			if (getPowerLevel() >= 2){
				setFirecooldown(100);
			}
			if (getPowerLevel() >= 3){
				setFirecooldown(75);
			}

			break;
		case Watermelon:
			if(getPowerLevel() >= 1){
				temp = random.nextInt(10) - 5;
				Engine.instance.addEntity(new PlayerBullet(this.getCenterX() - Globals.BULLET_WIDTH/2, y + 10, temp, -20, 1, 1, playerNum));
			}
			if (getPowerLevel() >= 2){
				temp = random.nextInt(14) - 7;
				setFirecooldown(200);
			}
			if (getPowerLevel() >= 3){
				setFirecooldown(100);
			}
			break;
		}
	}

	/**
	 * Method for firing a bomb
	 */
	private void fireBomb(){
		
		if(numBombs < 1){
			return;
		}
		
		if(!isInBombCooldown){
			timeSinceLastBomb = 0;
			isInBombCooldown = true;
		}
		else{
			return;
		}
		
		
		//Play a noise
		bombSound.play();
		
		//Decrement number of bombs
		numBombs --;
		
		//Clear the screen
		Engine.instance.clearScreen();
	}
	
	
	@Override
	public boolean isDangerous() {
		return false;
	}
	
	/**
	 * Method to handle collisions with the player
	 * @param collidedWith The entity that has collided with this player instance
	 */
	@SuppressWarnings("incomplete-switch")
	public boolean onCollide(Entity collidedWith){
		
		//Handle collisions
		
		//Figure out what kind of collision
		//Subtract health
		//Play a sound

		String collisionType = collidedWith.getClass().getSimpleName();
		D.BUG("Player collided with: " + collisionType);
		
		//Switch for regular collisions
		switch(collisionType){
		
		case "PowerUp":		
			return isDead;

		case "Collectible":
			
			//Play a sound
			powerUpSound.play();
			
			switch(((Collectible)collidedWith).getType()){
			case Bomb:
				if(numBombs < BOMB_CAPACITY){
					numBombs++;
				}
				
				Engine.instance.markForRemoval(collidedWith);
				break;
			case Health:
				health += Globals.DEFAULT_HEALTH_POWERUP_VALUE;
				Engine.instance.markForRemoval(collidedWith);
			}
			return isDead;
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
					case JumboSquirrel_1:
						health -= 10d;			//Damage that the boss does
						break;
					case JumboSquirrel_2:
						health -= 10d;
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
		return isDead;
	}

	private void onDie(){
		isDead = true;
		
		
	}
	
	public boolean onDieCheck(){
		return isDead;
	}
	
	
	private void checkHealth(){
		
		if(!Globals.INVINCIBLE){
			if(health <= 0f){
				D.BUG(String.valueOf(health));
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
